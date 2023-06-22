package com.fsse2305.final_project.repository;

import com.fsse2305.final_project.data.transaction.entity.TransactionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, Integer> {
    @Query(value = "SELECT * FROM transaction WHERE tid = ?1 AND buyer_uid = ?2", nativeQuery = true)
    Optional<TransactionEntity> findByTidAndUid(Integer tid, Integer uid);
}
