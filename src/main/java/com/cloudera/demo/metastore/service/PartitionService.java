package com.cloudera.demo.metastore.service;

import com.cloudera.demo.metastore.domain.HMSPartition;
import com.cloudera.demo.metastore.dto.PartitionColumnDTO;
import com.cloudera.demo.metastore.dto.PartitionDTO;
import com.cloudera.demo.metastore.dto.TableDTO;
import com.cloudera.demo.metastore.repository.HMSPartitionRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PartitionService {

    private final HMSPartitionRepository partitionRepository;

    public PartitionService(HMSPartitionRepository partitionRepository) {
        this.partitionRepository = partitionRepository;
    }

    public TableDTO findPartitions(String databaseName, String tableName) {
        Set<HMSPartition> partitions = partitionRepository.findAllByDatabaseAndTable(databaseName, tableName);
        return getTableDTO(databaseName, tableName, partitions);
    }

    public TableDTO findPartitions(String databaseName, String tableName, String partition) {
        Set<HMSPartition> partitions = partitionRepository.findAllByDatabaseAndTable(databaseName, tableName, partition);
        return getTableDTO(databaseName, tableName, partitions);
    }

    private TableDTO getTableDTO(String databaseName, String tableName, Set<HMSPartition> partitions) {
        List<PartitionDTO> partitionsList = partitions.stream()
                .map(p -> PartitionDTO.builder()
                        .name(p.getName())
                        .values(getPartitionColumns(p))
                        .build())
                .collect(Collectors.toList());
        TableDTO table = TableDTO.builder()
                .database(databaseName)
                .table(tableName)
                .partitions(partitionsList)
                .build();
        return table;
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
