package com.cloudera.demo.metastore.controller;

import com.cloudera.demo.metastore.domain.HMSTable;
import com.cloudera.demo.metastore.dto.TableDTO;
import com.cloudera.demo.metastore.service.DatabaseService;
import com.cloudera.demo.metastore.service.PartitionService;
import com.cloudera.demo.metastore.service.TableService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TableController {

    private final DatabaseService databaseService;
    private final TableService tableService;
    private final PartitionService partitionService;

    public TableController(
            DatabaseService databaseService,
            TableService tableService,
            PartitionService partitionService) {
        this.databaseService = databaseService;
        this.tableService = tableService;
        this.partitionService = partitionService;
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
