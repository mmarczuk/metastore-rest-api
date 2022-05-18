package com.cloudera.metastore.api.service;

import com.cloudera.metastore.api.domain.HMSTable;
import com.cloudera.metastore.api.repository.HMSTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TableService {

    private final HMSTableRepository tableRepository;

    public HMSTable findTable(String databaseName, String tableName) {
        return tableRepository.findAllByDatabaseAndTable(databaseName, tableName);
    }
}
