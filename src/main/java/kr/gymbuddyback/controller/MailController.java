package kr.gymbuddyback.controller;

import jakarta.validation.Valid;
import kr.gymbuddyback.model.EmailRequestDto;
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
    public String mailSend(@RequestBody @Valid EmailRequestDto requestDto) {
        System.out.println("이메일 인증 이메일 : " + requestDto.getEmail());

        return null;

    }

}
