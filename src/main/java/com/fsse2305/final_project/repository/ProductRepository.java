package com.fsse2305.final_project.repository;

import com.fsse2305.final_project.data.product.entity.ProductEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Integer>, JpaRepository<ProductEntity, Integer> {
    ProductEntity findByPid(int pid);

    @Modifying
    @Transactional
    @Query (value = "UPDATE product SET stock = stock - ?2 WHERE pid = ?1", nativeQuery = true)
    void setProductStockByPid(Integer pid, Integer quantity);
}
