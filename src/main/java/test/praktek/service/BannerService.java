package test.praktek.service;

import org.springframework.http.ResponseEntity;
import test.praktek.dto.ApiResponse;

public interface BannerService {

    ResponseEntity<ApiResponse> getBannerList();
}
