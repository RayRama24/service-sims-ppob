package test.praktek.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import test.praktek.dto.ApiResponse;
import test.praktek.dto.TransactionHistoryDTO;
import test.praktek.dto.TransactionRequestDTO;
import test.praktek.dto.TransactionResponseDTO;
import test.praktek.entity.ServiceEntity;
import test.praktek.entity.TransactionEntity;
import test.praktek.entity.UserEntity;
import test.praktek.repository.ServiceRepository;
import test.praktek.repository.TransactionRepository;
import test.praktek.repository.UserRepository;
import test.praktek.service.TransactionService;
import test.praktek.util.JwtUtil;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public ResponseEntity<ApiResponse> doTransaction(String token, TransactionRequestDTO request) {

        // Hilangkan "Bearer "
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (token == null || token.isEmpty() || !jwtUtil.validateToken(token)) {
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

        ServiceEntity service = serviceRepository.findByServiceCode(request.getServiceCode())
                .orElse(null);

        if (service == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(400, "Service ataus Layanan tidak ditemukan", null));
        }

        Long currentBalance = user.getBalance() == null ? 0L : user.getBalance();

        if (currentBalance < service.getServiceTarif()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(400, "Saldo tidak mencukupi", null));
        }

        user.setBalance(currentBalance - service.getServiceTarif());
        userRepository.save(user);

        String invoice = "INV" + System.currentTimeMillis();

        TransactionEntity trx = TransactionEntity.builder()
                .invoiceNumber(invoice)
                .serviceCode(service.getServiceCode())
                .serviceName(service.getServiceName())
                .transactionType("PAYMENT")
                .totalAmount(service.getServiceTarif())
                .createdOn(LocalDateTime.now())
                .user(user)
                .build();

        transactionRepository.save(trx);

        TransactionResponseDTO responseDTO = new TransactionResponseDTO(
                invoice,
                service.getServiceCode(),
                service.getServiceName(),
                "PAYMENT",
                service.getServiceTarif(),
                trx.getCreatedOn()
        );

        return ResponseEntity.ok(new ApiResponse(0, "Transaksi berhasil", responseDTO));
    }


    @Override
    public ResponseEntity<ApiResponse> getHistory(String token, Integer offset, Integer limit) {

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (!StringUtils.hasText(token) || !jwtUtil.validateToken(token)) {
            return new ResponseEntity<>(
                    new ApiResponse(401, "Token tidak tidak valid atau kadaluwarsa", null),
                    HttpStatus.UNAUTHORIZED
            );
        }

        String email = jwtUtil.getEmailFromToken(token);
        UserEntity user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return new ResponseEntity<>(
                    new ApiResponse(401, "Token tidak tidak valid atau kadaluwarsa", null),
                    HttpStatus.UNAUTHORIZED
            );
        }

        if (offset == null) offset = 0;

        List<TransactionEntity> transactions;

        if (limit == null) {
            transactions = transactionRepository.findByUserOrderByCreatedOnDesc(user);
        } else {
            int safeOffset = (offset == null || offset < 0) ? 0 : offset;
            Pageable pageable = PageRequest.of(safeOffset,limit);

            transactions = transactionRepository
                    .findByUserOrderByCreatedOnDesc(user, pageable)
                    .getContent();
        }

        List<TransactionResponseDTO> records = transactions.stream()
                .map(t -> TransactionResponseDTO.builder()
                        .invoiceNumber(t.getInvoiceNumber())
                        .transactionType(t.getTransactionType())
                        .serviceName(t.getServiceName())
                        .totalAmount(t.getTotalAmount())
                        .createdOn(t.getCreatedOn())
                        .build()
                ).toList();

        TransactionHistoryDTO responseData = TransactionHistoryDTO.builder()
                .offset(offset)
                .limit(limit)
                .records(records)
                .build();

        return ResponseEntity.ok(new ApiResponse(0, "Get History Berhasil", responseData));
    }
}
