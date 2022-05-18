package com.cloudera.metastore.api.repository.jpa;

import com.cloudera.metastore.api.domain.HMSPartition;
import com.cloudera.metastore.api.repository.HMSPartitionRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("jpa")
@Repository
public interface HMSPartitionJpaRepository extends JpaRepository<HMSPartition, Long>, HMSPartitionRepository {

    @Query("from HMSPartition p where p.table.database.name=?1 and p.table.name=?2")
    List<HMSPartition> findAllByDatabaseAndTable(String databaseName, String tableName);

    @Query("from HMSPartition p where p.table.database.name=?1 and p.table.name=?2 and p.name=?3")
    List<HMSPartition> findAllByDatabaseAndTableAndPartition(String databaseName, String tableName, String partition);

    @Query("from HMSPartition p1 where p1.table.database.name=?1 and p1.table.name=?2 and p1.name=" +
            "(select max(p2.name) from HMSPartition p2 where p2.table.database.name=?1 and p2.table.name=?2)")
    HMSPartition findLastPartitionByDatabaseAndTable(String databaseName, String tableName);

}
