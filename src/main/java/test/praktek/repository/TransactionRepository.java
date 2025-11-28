package test.praktek.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import test.praktek.entity.TransactionEntity;
import test.praktek.entity.UserEntity;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity,Long> {

    List<TransactionEntity> findByUserOrderByCreatedOnDesc(UserEntity user);

    Page<TransactionEntity> findByUserOrderByCreatedOnDesc(UserEntity user, Pageable pageable);
}
