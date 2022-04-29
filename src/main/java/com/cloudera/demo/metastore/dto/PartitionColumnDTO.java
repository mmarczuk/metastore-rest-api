package com.cloudera.demo.metastore.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PartitionColumnDTO {
    private String columnName;
    private String columnValue;
}
