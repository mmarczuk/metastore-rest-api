package com.cloudera.demo.metastore.repository;

import com.cloudera.demo.metastore.domain.HMSTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HMSTableRepository extends JpaRepository<HMSTable, Long> {

    @Query("from HMSTable t where t.database.name=?1 and t.name=?2")
    HMSTable findAllByDatabaseAndTable(String databaseName, String tableName);

}
