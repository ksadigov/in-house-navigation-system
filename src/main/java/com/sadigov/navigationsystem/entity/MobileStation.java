package com.sadigov.navigationsystem.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
public class MobileStation {
    @Id
    @NotNull
    @Column(nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid;

    @Column(name = "last_known_x")
    private float lastKnownLongitude;

    @Column(name = "last_known_y")
    private float lastKnownLatitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_station_uuid", referencedColumnName = "uuid")
    private BaseStation baseStation;
}
