package kr.gymbuddyback.service.impl;

import kr.gymbuddyback.entity.GymEntity;
import kr.gymbuddyback.repository.GymRepository;
import kr.gymbuddyback.service.CrawlService;
import kr.gymbuddyback.service.GymService;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class CrawlServiceImpl implements CrawlService {
    private final GymService gymService;
    private final GymRepository gymRepository;
    private static final int NUM_THREADS = 4;


    @Override
    public void crawlAndSaveInfos() {
        // 스레드 풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<List<GymEntity>>> futures = new ArrayList<>();
        List<String> existingNames = gymRepository.findAllNames();

        try {
            // 브라우저 설정
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");

            // 크롤링할 페이지 URLs
            List<String> pageUrls = getPageUrls();

            for (String url : pageUrls) {
                Future<List<GymEntity>> future = executorService.submit(() -> crawlPage(url, existingNames, chromeOptions));
                futures.add(future);
            }

            List<GymEntity> allGyms = new ArrayList<>();
            for (Future<List<GymEntity>> future : futures) {
                allGyms.addAll(future.get());
            }

            gymRepository.saveAll(allGyms);
            System.out.println("크롤링된 정보: " + allGyms);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private List<String> getPageUrls() {
        List<String> urls = new ArrayList<>();
        String[] regions = {"동대문", "광화문", "마포", "동대문"};

        for (String region : regions) {
            urls.add("https://map.naver.com/p/search/" + region + " 헬스장");
        }
        return urls;
    }

    private List<GymEntity> crawlPage(String url, List<String> existingNames, ChromeOptions chromeOptions) {
        List<GymEntity> crawledList = new ArrayList<>();
        WebDriver webDriver = new ChromeDriver(chromeOptions);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        try { // 이건 지금 해당 검색해서 나온 페이지의 모든 걸 다 크롤링 하는 부분임 나중에 등록하면 네이버에 등록한 정보가 알아서 오도록 해야함
            webDriver.get(url);

            // 결과 로드까지 대기
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("searchIframe")));

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".place_bluelink")));
            List<WebElement> titleElements = webDriver.findElements(By.cssSelector(".place_bluelink"));

            int maxItemsToProcess = 10; //여기서 각 지역 별 몇개 등록할껀지
            int count = 0;
            for (int i=0; i<titleElements.size() && count < maxItemsToProcess; i++) {
                WebElement titleElement = titleElements.get(i);

                // 스크롤 해서 요소가 화면에 보이도록 함
                ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true)",titleElement);

                // 검색 결과 클릭
                ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", titleElement);
                wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

                // 상세보기로 프레임 이동
                webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
                //wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("entryIframe")));
                webDriver.switchTo().defaultContent();
                webDriver.switchTo().frame("entryIframe");


                // 이미지 => 여기서 부터는 12/29 이후로 아직 안함 짐버디 맞춰서 가야해!!!
                List<WebElement> imageElements = webDriver.findElements(By.cssSelector(".place_thumb.QX0J7 img"));
                String thumbnailImageUrl = imageElements.size() > 0 ? imageElements.get(0).getAttribute("src") : null;
                String secondaryImageUrl = imageElements.size() > 1 ? imageElements.get(1).getAttribute("src") : null;

                // 가게이름, 종류
                WebElement nameElement = webDriver.findElement(By.cssSelector(".GHAhO"));
                WebElement typeElement = webDriver.findElement(By.cssSelector(".lnJFt"));

                // 가게번호
                String telText = getTextSafely(webDriver, By.cssSelector(".xlx7Q"), "번호가 존재하지 않습니다.");

                // 평점
                Double rating = getRatingSafely(webDriver, wait);

                // 주소
                String address = getAddressSafely(webDriver, wait);

                // 운영시간
                String combinedOperation = getOperationTimesSafely(webDriver, wait);

                // 메뉴
                String combinedMenu = getMenuSafely(webDriver, wait);

                String nameText = nameElement.getText();
                String typeText = typeElement.getText();

                if (!existingNames.contains(nameText)) {
                    RestaurantEntity restaurant = RestaurantEntity.builder()
                            .name(nameText)
                            .type(typeText)
                            .address(address)
                            .rate(rating)
                            .operation(combinedOperation)
                            .tel(telText)
                            .menu(combinedMenu)
                            .thumbnailImageUrl(thumbnailImageUrl)
                            .subImageUrl(secondaryImageUrl)

                            .build();

                    crawledList.add(restaurant);
                    count++;
                }

                webDriver.switchTo().defaultContent();
                webDriver.switchTo().frame("searchIframe");
                titleElements = webDriver.findElements(By.cssSelector(".place_bluelink"));
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
        }


        return crawledList;
    }



}
