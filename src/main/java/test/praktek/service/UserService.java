package test.praktek.service;

import org.springframework.http.ResponseEntity;
import test.praktek.dto.ApiResponse;
import test.praktek.dto.LoginRequestDTO;
import test.praktek.dto.RegistrationRequestDTO;
import test.praktek.dto.UpdateProfileDTO;

public interface UserService {

    ResponseEntity<ApiResponse> register(RegistrationRequestDTO request);

    ResponseEntity<ApiResponse> login(LoginRequestDTO request);

    ResponseEntity<ApiResponse> getProfile(String token);

    ResponseEntity<ApiResponse> updateProfile(String emailFromToken, UpdateProfileDTO request);
}
