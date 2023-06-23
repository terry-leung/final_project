package com.fsse2305.final_project.repository;

import com.fsse2305.final_project.data.cartItem.domainObject.CartItemDetailsData;
import com.fsse2305.final_project.data.cartItem.entity.CartItemEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends CrudRepository<CartItemEntity, Integer> , JpaRepository<CartItemEntity, Integer> {
    @Query(value = "SELECT * FROM cart_item WHERE uid = ?1" , nativeQuery = true)
    List<CartItemEntity> getAllCartItemsByUid(Integer uid);

    @Query(value = "SELECT * FROM cart_item WHERE uid = ?1 AND pid = ?2", nativeQuery = true)
    Optional<CartItemEntity> findCartItemsByUidAndPid(Integer uid, Integer pid);

    @Modifying
    @Query(value = "DELETE FROM cart_item WHERE uid = ?1 AND pid = ?2", nativeQuery = true)
    int deleteByUidAndPid (Integer uid, Integer pid);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cart_item WHERE uid =?1", nativeQuery = true)
    void deleteByUid (Integer uid);

//    @Query(value = "SELECT * FROM cart_item WHERE pid = ?1", nativeQuery = true)
//    CartItemEntity findByPid(Integer pid);
}
