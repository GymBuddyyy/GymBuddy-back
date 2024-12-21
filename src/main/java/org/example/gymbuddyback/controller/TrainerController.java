package org.example.gymbuddyback.controller;


import lombok.RequiredArgsConstructor;
import org.example.gymbuddyback.entity.TrainerOffDays;
import org.example.gymbuddyback.repository.TrainerRepository;
import org.example.gymbuddyback.repository.custom.TrainerCustom;
import org.example.gymbuddyback.service.TrainerService;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/trainer")
public class TrainerController {
    private final TrainerService trainerService;


    @PostMapping("/off-days")
    public List<TrainerOffDays> addOffDays(
            @RequestHeader("trainerId") Long trainerId, // 헤더에서 trainerId 받기
            @RequestBody List<OffDayRequest> offDays // 본문에서 날짜 목록 받기
    ) {
        // 날짜 문자열을 LocalDate 객체로 변환
        List<LocalDate> parsedOffDays = offDays.stream()
                .map(offDayRequest -> LocalDate.parse(offDayRequest.getDate())) // String을 LocalDate로 변환
                .collect(Collectors.toList());

        // QueryDSL을 사용하여 휴무일 삽입
        return trainerService.addOffDays(trainerId, parsedOffDays);
    }

    public static class OffDayRequest {
        private String date;

        // Getter & Setter
        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}