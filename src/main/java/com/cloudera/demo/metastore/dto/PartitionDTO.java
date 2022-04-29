package com.cloudera.demo.metastore.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PartitionDTO {
    private String name;
    private List<PartitionColumnDTO> values;
}
