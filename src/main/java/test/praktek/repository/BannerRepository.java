package test.praktek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.praktek.entity.BannerEntity;

public interface BannerRepository extends JpaRepository<BannerEntity, Long> {

}
