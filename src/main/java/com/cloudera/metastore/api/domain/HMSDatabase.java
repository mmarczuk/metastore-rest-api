package com.cloudera.metastore.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DBS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HMSDatabase {

    @Id
    @Column(name="DB_ID")
    private Long id;

    @Column(name="NAME")
    private String name;

}
