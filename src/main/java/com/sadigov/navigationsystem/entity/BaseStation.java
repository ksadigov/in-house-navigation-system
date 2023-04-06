package com.sadigov.navigationsystem.entity;


import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
public class BaseStation {

    @Id
    @NotNull
    @Column(nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid;

    private String name;

    @NotNull
    @Column(nullable = false)
    private Float x;

    @NotNull
    @Column(nullable = false)
    private Float y;

    @NotNull
    @Column(nullable = false)
    private Float detectionRadiusInMeters;
}