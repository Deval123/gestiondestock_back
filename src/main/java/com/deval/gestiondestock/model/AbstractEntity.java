package com.deval.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {

    @Id
    @GeneratedValue
    private  Integer id;

    @CreatedDate
    @Column(name = "creationDate", nullable = false, updatable = false)
    private Instant creationDate;

    @Column(name = "lastModifiedDate")
    @LastModifiedDate
    private Instant lastModifiedDate;


}
