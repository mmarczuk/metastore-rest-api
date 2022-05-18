package com.cloudera.metastore.api.repository;

import com.cloudera.metastore.api.domain.HMSPartition;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface HMSPartitionRepository {

    @Cacheable("findAllByDatabaseAndTableAndPartition")
    List<HMSPartition> findAllByDatabaseAndTable(String databaseName, String tableName);

    @Cacheable("findAllByDatabaseAndTableAndPartition")
    List<HMSPartition> findAllByDatabaseAndTableAndPartition(String databaseName, String tableName, String partition);

    @Cacheable("findLastPartitionByDatabaseAndTable")
    HMSPartition findLastPartitionByDatabaseAndTable(String databaseName, String tableName);

}
