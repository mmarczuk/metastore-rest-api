package com.cloudera.metastore.api.repository;

import com.cloudera.metastore.api.domain.HMSTable;
import org.springframework.cache.annotation.Cacheable;

@Cacheable("tables")
public interface HMSTableRepository {

    HMSTable findAllByDatabaseAndTable(String databaseName, String tableName);

}
