package com.example.baek_server.repo;

import com.example.baek_server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String userName);

    boolean existsByUsername(String userName);
}
