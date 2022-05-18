package com.cloudera.metastore.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="TBLS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
