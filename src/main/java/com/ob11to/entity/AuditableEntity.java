package com.ob11to.entity;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.Instant;

public abstract class AuditableEntity<T extends Serializable> implements BaseEntity<T> {

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "created_by")
    private String createdBy;
}
