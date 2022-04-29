package com.cloudera.demo.metastore.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="PARTITIONS")
@Data
public class HMSPartition {

    @Id
    @Column(name="PART_ID")
    private Long id;

    @Column(name="PART_NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name="TBL_ID")
    private HMSTable table;

}
