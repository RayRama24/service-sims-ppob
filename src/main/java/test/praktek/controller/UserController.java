package test.praktek.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.praktek.dto.ApiResponse;
import test.praktek.dto.UpdateProfileDTO;
import test.praktek.service.UserService;
import test.praktek.util.JwtUtil;

@RestController
@RequiredArgsConstructor
@Tag(name = "1. Module Membership")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getProfile(HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        return userService.getProfile(token);
    }

    @PutMapping("/profile/update")
    public ResponseEntity<ApiResponse> updateProfile(
            @RequestHeader("Authorization") String token,
            @RequestBody UpdateProfileDTO request
    ) {
        return userService.updateProfile(token, request);
    }
}
