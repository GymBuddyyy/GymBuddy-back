package kr.gymbuddyback.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.gymbuddyback.entity.UserEntity;
import kr.gymbuddyback.model.UserModel;
import kr.gymbuddyback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody UserModel user) {
        System.out.println("회원가입 컨트롤러 실행" + user);
        userService.joinUser(user);
        System.out.println("회원가입 완료");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/findEmail")
    public ResponseEntity<?> findUserName(@RequestParam String email) {
        return ResponseEntity.ok(userService.findEmail(email));
    }

    @GetMapping("/loginOk")
    public ResponseEntity<Map<String, String>> loginOk() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        String authorities = authentication.getAuthorities().toString();

        System.out.println("로그인한 유저 이메일:" + email);
        System.out.println("유저 권한:" + authentication.getAuthorities());

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("email", email);
        userInfo.put("authorities", authorities);

        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/logoutOk")
    public ResponseEntity<Boolean> logoutOk() {
        System.out.println("로그아웃 성공");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin")
    public ResponseEntity<Boolean> getAdminPage() {
        System.out.println("어드민 인증 성공");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user")
    public ResponseEntity<UserEntity> getUserPage() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        UserEntity user = userService.getUserInfo(email);

        return ResponseEntity.ok(user);
    }





}
