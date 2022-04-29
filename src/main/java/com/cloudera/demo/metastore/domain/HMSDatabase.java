package com.cloudera.demo.metastore.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DBS")
@Data
public class HMSDatabase {

    @Id
    @Column(name="DB_ID")
    private Long id;

    @Column(name="NAME")
    private String name;

}
