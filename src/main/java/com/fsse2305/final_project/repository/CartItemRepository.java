package com.fsse2305.final_project.repository;

import com.fsse2305.final_project.data.cartItem.domainObject.CartItemDetailsData;
import com.fsse2305.final_project.data.cartItem.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends CrudRepository<CartItemEntity, Integer> , JpaRepository<CartItemEntity, Integer> {
    @Query(value = "SELECT * FROM cart_item WHERE uid = ?1" , nativeQuery = true)
    CartItemEntity[] getAllCartItemsByUid(Integer uid);

    @Query(value = "SELECT * FROM cart_item WHERE uid = ?1 AND pid = ?2", nativeQuery = true)
    CartItemEntity findCartItemsByUidAndPid(Integer uid, Integer pid);

//    @Query(value = "SELECT * FROM cart_item WHERE pid = ?1", nativeQuery = true)
//    CartItemEntity findByPid(Integer pid);
}
