package org.example.practicemanagementsystem.repository;

import org.example.practicemanagementsystem.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository <UserModel, UUID> {
    UserDetails findByUsername(String username);
}
