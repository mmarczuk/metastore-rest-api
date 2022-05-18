package com.cloudera.metastore.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="PARTITIONS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
