package com.fsse2305.final_project.repository;

import com.fsse2305.final_project.data.user.domainObject.FirebaseUserData;
import com.fsse2305.final_project.data.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository <UserEntity, Integer> {
    Optional<UserEntity> findByFirebaseUid(String firebaseUid);
}
