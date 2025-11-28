package test.praktek.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import test.praktek.dto.ApiResponse;
import test.praktek.service.BannerService;

@RestController
@RequiredArgsConstructor
@Tag(name = "2. Module Information")
public class BannerController {

    private final BannerService bannerService;

    @GetMapping("/banner")
    public ResponseEntity<ApiResponse> getBanner() {
        return bannerService.getBannerList();
    }
}
