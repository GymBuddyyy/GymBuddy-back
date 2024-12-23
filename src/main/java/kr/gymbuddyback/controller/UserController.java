package kr.gymbuddyback.controller;

import kr.gymbuddyback.model.UserModel;
import kr.gymbuddyback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserModel userModel) {
        return ResponseEntity.ok(userService.register(userModel));
    }

    @PostMapping("/findEmail")
    public ResponseEntity<?> findUserName(@RequestParam String email) {
        return ResponseEntity.ok(userService.findEmail(email));
    }



}
