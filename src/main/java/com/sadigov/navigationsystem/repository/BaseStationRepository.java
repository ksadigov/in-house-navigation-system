package com.sadigov.navigationsystem.repository;

import com.sadigov.navigationsystem.entity.BaseStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BaseStationRepository extends JpaRepository<BaseStation, UUID> {

}
