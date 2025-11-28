package test.praktek.service;

import org.springframework.http.ResponseEntity;
import test.praktek.dto.ApiResponse;

public interface BalanceService {

    ResponseEntity<ApiResponse> getBalance(String token);
}
