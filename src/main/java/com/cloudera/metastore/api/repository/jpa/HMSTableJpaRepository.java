package com.cloudera.metastore.api.repository.jpa;

import com.cloudera.metastore.api.domain.HMSTable;
import com.cloudera.metastore.api.repository.HMSTableRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Profile("jpa")
@Repository
public interface HMSTableJpaRepository extends JpaRepository<HMSTable, Long>, HMSTableRepository {

    @Query("from HMSTable t where t.database.name=?1 and t.name=?2")
    HMSTable findAllByDatabaseAndTable(String databaseName, String tableName);

}
