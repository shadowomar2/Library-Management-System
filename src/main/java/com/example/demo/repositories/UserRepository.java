package com.example.demo.repositories;

 
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
 
    
    @Modifying             
    @Query(value = "DELETE FROM `user_roles` WHERE `user_roles`.`user_id` = ?1  AND `user_roles`.`role_id` = 1", nativeQuery = true)
    void deleteRolesByUserId(Long userId);   
    
    @Override
    @Modifying
    @Query(value = "DELETE FROM `users` WHERE `users`.`id` = ?1", nativeQuery = true)
    void deleteById(Long id);
    
    @Modifying
    @Query(value = "DELETE FROM `users_details` WHERE `users_details`.`id` = ?1", nativeQuery = true)
    void deleteUserDetailsByUserId(Long id);
 
    
    default void deleteUser(Long id) {
        deleteRolesByUserId(id);
       
    }
    
}