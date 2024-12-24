package kr.gymbuddyback.controller;

import jakarta.validation.Valid;
import kr.gymbuddyback.model.EmailCheckModel;
import kr.gymbuddyback.model.EmailRequestModel;
import kr.gymbuddyback.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final EmailService emailService;

    @PostMapping("/mailSend")
    public String mailSend(@RequestBody @Valid EmailRequestModel emailRequestModel) {
        System.out.println("이메일 인증 이메일 : " + emailRequestModel.getEmail());
        return emailService.joinEmail(emailRequestModel.getEmail());

    }

    @PostMapping("/mailauthCheck")
    public String AuthCheck(@RequestBody @Valid EmailCheckModel emailCheckModel) {
        Boolean Checked = emailService.CheckAuthNum(emailCheckModel.getEmail(),emailCheckModel.getAuthNum());
        if(Checked){
            return "ok";
        }
        else{
            throw new NullPointerException("인증번호가 잘못 입력되었습니다.");
        }
    }

}
