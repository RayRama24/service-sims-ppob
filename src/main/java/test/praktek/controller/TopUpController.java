package test.praktek.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import test.praktek.dto.ApiResponse;
import test.praktek.dto.TopupRequestDTO;
import test.praktek.service.TopupService;
import test.praktek.util.JwtUtil;

@RestController
@RequiredArgsConstructor
@Tag(name = "3. Module Transaction")
public class TopUpController {

    private final TopupService topUpService;
    private final JwtUtil jwtUtil;

    @PostMapping("/topup")
    public ResponseEntity<ApiResponse> topup(HttpServletRequest request,
                                             @RequestBody TopupRequestDTO dto) {

        String token = jwtUtil.resolveToken(request);

        return topUpService.doTopUp(token, dto);
    }
}
