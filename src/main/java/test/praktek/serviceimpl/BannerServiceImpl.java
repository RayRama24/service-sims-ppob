package test.praktek.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import test.praktek.dto.ApiResponse;
import test.praktek.dto.BannerDTO;
import test.praktek.entity.BannerEntity;
import test.praktek.repository.BannerRepository;
import test.praktek.service.BannerService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;

    @Override
    public ResponseEntity<ApiResponse> getBannerList() {

        List<BannerEntity> banners = bannerRepository.findAll();

        List<BannerDTO> result = banners.stream()
                .map(b -> new BannerDTO(
                        b.getBannerName(),
                        b.getBannerImage(),
                        b.getDescription()
                )).toList();

        return ResponseEntity.ok(new ApiResponse(200, "Sukses", result));
    }
}
