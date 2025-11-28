package test.praktek.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import test.praktek.dto.LoginRequestDTO;
import test.praktek.dto.RegistrationRequestDTO;
import test.praktek.service.UserService;

@RestController
@RequiredArgsConstructor
@Tag(name = "1. Module Membership")
public class AuthController {

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationRequestDTO request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        return userService.login(request);
    }

}
