package com.cloudera.metastore.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TableDTO {
    private String database;
    private String table;
    private List<PartitionDTO> partitions;
}
