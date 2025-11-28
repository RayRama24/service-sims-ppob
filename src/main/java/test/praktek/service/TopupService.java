package test.praktek.service;

import org.springframework.http.ResponseEntity;
import test.praktek.dto.ApiResponse;
import test.praktek.dto.TopupRequestDTO;

public interface TopupService {

    ResponseEntity<ApiResponse> doTopUp(String token, TopupRequestDTO dto);
}
