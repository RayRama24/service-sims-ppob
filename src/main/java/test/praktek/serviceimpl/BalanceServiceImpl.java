package test.praktek.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import test.praktek.dto.ApiResponse;
import test.praktek.dto.BalanceDTO;
import test.praktek.entity.UserEntity;
import test.praktek.repository.UserRepository;
import test.praktek.service.BalanceService;
import test.praktek.util.JwtUtil;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public ResponseEntity<ApiResponse> getBalance(String token) {

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (!StringUtils.hasText(token)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(401, "Token tidak tidak valid atau kadaluwarsa", null));
        }

        if (!jwtUtil.validateToken(token)) {
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

        BalanceDTO dto = new BalanceDTO(user.getBalance());

        return ResponseEntity.ok(new ApiResponse(200, "Get Balance Berhasil", dto));
    }
}
