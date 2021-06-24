package com.daytodayhealth.rating.repository;

import com.daytodayhealth.rating.entity.PlatformUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<PlatformUser, UUID> {

    Optional<PlatformUser> findByUsername(String username);
}
