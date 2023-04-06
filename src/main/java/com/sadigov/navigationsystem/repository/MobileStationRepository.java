package com.sadigov.navigationsystem.repository;

import com.sadigov.navigationsystem.entity.MobileStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MobileStationRepository extends JpaRepository<MobileStation, UUID> {

}
