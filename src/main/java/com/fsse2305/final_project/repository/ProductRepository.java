package com.fsse2305.final_project.repository;

import com.fsse2305.final_project.data.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Integer>, JpaRepository<ProductEntity, Integer> {
    ProductEntity findByPid(int pid);
}
