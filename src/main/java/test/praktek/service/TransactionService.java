package test.praktek.service;

import org.springframework.http.ResponseEntity;
import test.praktek.dto.ApiResponse;
import test.praktek.dto.TransactionRequestDTO;

public interface TransactionService {

    ResponseEntity<ApiResponse> doTransaction(String token, TransactionRequestDTO request);

    ResponseEntity<ApiResponse> getHistory(String token, Integer offset, Integer limit);
}
