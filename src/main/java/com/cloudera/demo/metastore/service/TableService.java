package com.cloudera.demo.metastore.service;

import com.cloudera.demo.metastore.domain.HMSTable;
import com.cloudera.demo.metastore.repository.HMSTableRepository;
import org.springframework.stereotype.Service;

@Service
public class TableService {

    private final HMSTableRepository tableRepository;

    public TableService(HMSTableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public HMSTable findTable(String databaseName, String tableName) {
        return tableRepository.findAllByDatabaseAndTable(databaseName, tableName);
    }
}
