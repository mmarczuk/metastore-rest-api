package com.cloudera.demo.metastore.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="TBLS")
@Data
public class HMSTable {

    @Id
    @Column(name="TBL_ID")
    private Long id;

    @Column(name="TBL_NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name="DB_ID")
    private HMSDatabase database;

}
