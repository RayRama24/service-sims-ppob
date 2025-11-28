package test.praktek.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import test.praktek.dto.*;
import test.praktek.entity.UserEntity;
import test.praktek.repository.UserRepository;
import test.praktek.service.UserService;
import test.praktek.util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final JwtUtil jwtUtil;


    @Override
    public ResponseEntity<ApiResponse> register(RegistrationRequestDTO request) {

        if (!isValidEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(400, "Paramter email tidak sesuai format", null));
        }

        // Validasi panjang password
        if (request.getPassword().length() < 8) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(400, "Password minimal 8 karakter", null));
        }

        // Apakah email sudah terpakai
        if (repo.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(400, "Paramter email tidak sesuai format", null));
        }

        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .firstName(request.getFirst_name())
                .lastName(request.getLast_name())
                .password(request.getPassword())
                .build();

        repo.save(user);

        return ResponseEntity.ok(
                new ApiResponse(200, "Registrasi berhasil silahkan login", null)
        );
    }

    @Override
    public ResponseEntity<ApiResponse> login(LoginRequestDTO req) {

        if (!isValidEmail(req.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(400, "Paramter email tidak sesuai format", null));
        }

        if (req.getPassword().length() < 8) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(400, "Password minimal 8 karakter", null));
        }

        UserEntity user = repo.findByEmail(req.getEmail()).orElse(null);

        if (user == null || !user.getPassword().equals(req.getPassword())) {
            return ResponseEntity.status(401)
                    .body(new ApiResponse(401, "Username atau password salah", null));
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(
                new ApiResponse(200, "Login Sukses", Map.of("token", token))
        );
    }

    private boolean isValidEmail(String email) {
        if (email == null) return false;

        if (!email.contains("@")) return false;

        int atIndex = email.indexOf("@");

        if (!email.substring(atIndex).contains(".")) return false;

        return true;
    }


    @Override
    public ResponseEntity<ApiResponse> getProfile(String token) {

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

        UserEntity user = repo.findByEmail(email).orElse(null);

        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(401, "Token tidak tidak valid atau kadaluwarsa", null));
        }

        UserProfileDTO userProfileDTO = new UserProfileDTO(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );

        return ResponseEntity.ok(new ApiResponse(200, "Sukses", userProfileDTO));
    }

    @Override
    public ResponseEntity<ApiResponse> updateProfile(String token, UpdateProfileDTO req) {

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

        UserEntity user = repo.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(401, "Token tidak tidak valid atau kadaluwarsa", null));
        }

        user.setFirstName(req.getFirst_name());
        user.setLastName(req.getLast_name());

        repo.save(user);

        UserProfileDTO dto = UserProfileDTO.builder()
                .email(user.getEmail())
                .first_name(user.getFirstName())
                .last_name(user.getLastName())
                .build();

        ApiResponse response = new ApiResponse(
                0,
                "Update Pofile berhasil",
                dto
        );

        return ResponseEntity.ok(response);
    }
}

