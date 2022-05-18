package com.cloudera.metastore.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PartitionDTO {
    private String name;
    private List<PartitionColumnDTO> values;
}
