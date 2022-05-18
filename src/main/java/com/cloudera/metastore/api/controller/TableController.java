package com.cloudera.metastore.api.controller;

import com.cloudera.metastore.api.dto.TableDTO;
import com.cloudera.metastore.api.service.DatabaseService;
import com.cloudera.metastore.api.service.PartitionService;
import com.cloudera.metastore.api.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TableController {

    private final DatabaseService databaseService;
    private final TableService tableService;
    private final PartitionService partitionService;

    @GetMapping("/templeton/v1/ddl/database/{databaseName}/table/{tableName}/lastPartition")
    public TableDTO findLastPartition(
            @PathVariable String databaseName,
            @PathVariable String tableName) {
        return partitionService.findLatestPartition(databaseName, tableName);
    }

    @GetMapping("/templeton/v1/ddl/database/{databaseName}/table/{tableName}/partition")
    public TableDTO findPartitions(
            @PathVariable String databaseName,
            @PathVariable String tableName) {
        return partitionService.findPartitions(databaseName, tableName);
    }

    @GetMapping("/templeton/v1/ddl/database/{databaseName}/table/{tableName}/partition/{partition}")
    public TableDTO findPartition(
            @PathVariable String databaseName,
            @PathVariable String tableName,
            @PathVariable String partition) {
        return partitionService.findPartitions(databaseName, tableName, partition);
    }

}
