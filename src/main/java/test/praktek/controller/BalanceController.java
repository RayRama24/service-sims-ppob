package test.praktek.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import test.praktek.dto.ApiResponse;
import test.praktek.service.BalanceService;
import test.praktek.util.JwtUtil;

@RestController
@RequiredArgsConstructor
@Tag(name = "3. Module Transaction")
public class BalanceController {

    private final BalanceService balanceService;
    private final JwtUtil jwtUtil;

    @GetMapping("/balance")
    public ResponseEntity<ApiResponse> getBalance(HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);

        return balanceService.getBalance(token);
    }
}
