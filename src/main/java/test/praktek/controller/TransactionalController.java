package test.praktek.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.praktek.dto.ApiResponse;
import test.praktek.dto.TransactionRequestDTO;
import test.praktek.service.TransactionService;
import test.praktek.util.JwtUtil;

@RestController
@RequiredArgsConstructor
@Tag(name = "3. Module Transaction")
public class TransactionalController {

    private final TransactionService transactionService;
    private final JwtUtil jwtUtil;

    @PostMapping("/transaction")
    public ResponseEntity<ApiResponse> doTransaction(
            @RequestBody TransactionRequestDTO request,
            HttpServletRequest httpRequest) {

        String token = jwtUtil.resolveToken(httpRequest);

        return transactionService.doTransaction(token, request);
    }

    @GetMapping("/transaction/history")
    public ResponseEntity<?> getHistory(
            HttpServletRequest request,
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) Integer limit
    ) {

        String token = jwtUtil.resolveToken(request);

        return transactionService.getHistory(token, offset, limit);
    }
}
