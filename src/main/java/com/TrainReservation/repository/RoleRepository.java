package com.TrainReservation.repository;

import com.TrainReservation.entity.Role;
import com.TrainReservation.support.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}
