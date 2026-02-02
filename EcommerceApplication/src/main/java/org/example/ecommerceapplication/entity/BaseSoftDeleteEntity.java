package org.example.ecommerceapplication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@MappedSuperclass
@Getter
@Setter
@SQLRestriction("is_deleted = false")
public abstract class BaseSoftDeleteEntity extends BaseEntity {

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}

