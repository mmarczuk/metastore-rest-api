package com.cloudera.demo.metastore.repository;

import com.cloudera.demo.metastore.domain.HMSPartition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface HMSPartitionRepository extends JpaRepository<HMSPartition, Long> {

    @Query("from HMSPartition p where p.table.database.name=?1 and p.table.name=?2")
    Set<HMSPartition> findAllByDatabaseAndTable(String databaseName, String tableName);

    @Query("from HMSPartition p where p.table.database.name=?1 and p.table.name=?2 and p.name=?3")
    Set<HMSPartition> findAllByDatabaseAndTable(String databaseName, String tableName, String partition);

}
