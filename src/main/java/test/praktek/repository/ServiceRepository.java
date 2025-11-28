package test.praktek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.praktek.entity.ServiceEntity;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<ServiceEntity,Long> {

    Optional<ServiceEntity> findByServiceCode(String code);
}
