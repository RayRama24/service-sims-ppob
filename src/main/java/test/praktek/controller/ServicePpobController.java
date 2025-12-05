package test.praktek.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import test.praktek.dto.ApiResponse;
import test.praktek.service.ServicePpobService;
import test.praktek.util.JwtUtil;

@RestController
@RequiredArgsConstructor
@Tag(name = "2. Module Information")
@SecurityRequirement(name = "bearerAuth")
public class ServicePpobController {

    private final ServicePpobService servicePpobService;
    private final JwtUtil jwtUtil;


    @GetMapping("/services")
    public ResponseEntity<ApiResponse> getServices(HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);

        return servicePpobService.getServiceList(token);
    }
}
