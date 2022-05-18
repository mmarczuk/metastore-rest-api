package com.cloudera.metastore.api.service;

import com.cloudera.metastore.api.domain.HMSPartition;
import com.cloudera.metastore.api.dto.PartitionColumnDTO;
import com.cloudera.metastore.api.dto.PartitionDTO;
import com.cloudera.metastore.api.dto.TableDTO;
import com.cloudera.metastore.api.repository.HMSPartitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PartitionService {

    private final HMSPartitionRepository partitionRepository;

    public TableDTO findPartitions(String databaseName, String tableName) {
        List<HMSPartition> partitions = partitionRepository.findAllByDatabaseAndTable(databaseName, tableName);
        return getTableDTO(databaseName, tableName, partitions);
    }

    public TableDTO findPartitions(String databaseName, String tableName, String partition) {
        List<HMSPartition> partitions = partitionRepository.findAllByDatabaseAndTableAndPartition(databaseName, tableName, partition);
        return getTableDTO(databaseName, tableName, partitions);
    }

    public TableDTO findLatestPartition(String databaseName, String tableName) {
        HMSPartition partition = partitionRepository.findLastPartitionByDatabaseAndTable(databaseName, tableName);
        return getTableDTO(databaseName, tableName, Stream.of(partition).collect(Collectors.toList()));
    }

    private TableDTO getTableDTO(String databaseName, String tableName, List<HMSPartition> partitions) {
        List<PartitionDTO> partitionsList = partitions.stream()
                .map(p -> PartitionDTO.builder()
                        .name(p.getName())
                        .values(getPartitionColumns(p))
                        .build())
                .collect(Collectors.toList());
        return TableDTO.builder()
                .database(databaseName)
                .table(tableName)
                .partitions(partitionsList)
                .build();
    }

    private List<PartitionColumnDTO> getPartitionColumns(HMSPartition p) {
        String[] parts = p.getName().split("=");
        PartitionColumnDTO col = PartitionColumnDTO.builder()
                .columnName(parts[0])
                .columnValue(parts[1])
                .build();
        return Collections.singletonList(col);
    }

}
