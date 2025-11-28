package test.praktek.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import test.praktek.dto.ApiResponse;
import test.praktek.dto.ServiceDTO;
import test.praktek.entity.ServiceEntity;
import test.praktek.repository.ServiceRepository;
import test.praktek.repository.UserRepository;
import test.praktek.service.ServicePpobService;
import test.praktek.util.JwtUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicePpobServiceImpl implements ServicePpobService {

    private final ServiceRepository serviceRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ApiResponse> getServiceList(String token) {
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

        if (userRepository.findByEmail(email).isEmpty()) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse(401, "Token tidak tidak valid atau kadaluwarsa", null));
    }

    List<ServiceEntity> services = serviceRepository.findAll();

    List<ServiceDTO> result = services.stream()
            .map(s -> new ServiceDTO(
                    s.getServiceCode(),
                    s.getServiceName(),
                    s.getServiceIcon(),
                    s.getServiceTarif()
            )).toList();

        return ResponseEntity.ok(new ApiResponse(200, "Sukses", result));
}
}
