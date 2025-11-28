package test.praktek.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import test.praktek.dto.ApiResponse;
import test.praktek.dto.TopupRequestDTO;
import test.praktek.entity.TransactionEntity;
import test.praktek.entity.UserEntity;
import test.praktek.repository.TransactionRepository;
import test.praktek.repository.UserRepository;
import test.praktek.service.TopupService;
import test.praktek.util.JwtUtil;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TopUpServiceImpl implements TopupService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public ResponseEntity<ApiResponse> doTopUp(String token, TopupRequestDTO dto) {

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(401, "Token tidak tidak valid atau kadaluwarsa", null));
        }

        String email = jwtUtil.getEmailFromToken(token);

        UserEntity user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(401, "Token tidak tidak valid atau kadaluwarsa", null));
        }

        Long amount = dto.getTopUpAmount();

        if (amount == null || amount < 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(400, "Paramter amount hanya boleh angka dan tidak boleh lebih kecil dari 0", null));
        }

        if (user.getBalance() == null){
            user.setBalance((long) 0.0);
        }
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);

        String invoice = "INV-" + System.currentTimeMillis();

        TransactionEntity trx = TransactionEntity.builder()
                .invoiceNumber(invoice)
                .serviceCode("TOPUP")
                .serviceName("Top Up")
                .transactionType("TOPUP")
                .totalAmount(amount)
                .createdOn(LocalDateTime.now())
                .user(user)
                .build();

        transactionRepository.save(trx);

        Map<String, Object> data = Map.of("balance", user.getBalance());

        return ResponseEntity.ok(
                new ApiResponse(200, "Top Up Balance berhasil", data)
        );
    }
}
