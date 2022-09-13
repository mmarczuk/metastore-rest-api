package com.cloudera.metastore.api.repository.jdbc;

import com.cloudera.metastore.api.domain.HMSDatabase;
import com.cloudera.metastore.api.domain.HMSPartition;
import com.cloudera.metastore.api.domain.HMSTable;
import com.cloudera.metastore.api.repository.HMSPartitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Profile("jdbc")
@Repository
@RequiredArgsConstructor
public class HMSPartitionJdbcRepository implements HMSPartitionRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<HMSPartition> findAllByDatabaseAndTable(String databaseName, String tableName) {
        return jdbcTemplate.query(
                "SELECT d.name, t.tbl_id, t.tbl_name, p.part_name\n" +
                        " FROM partitions p\n" +
                        " INNER JOIN tbls t ON p.tbl_id = t.tbl_id\n" +
                        " INNER JOIN dbs d ON p.db_id = d.db_id\n" +
                        " WHERE d.name = ? AND t.tbl_name = ?",
                (rs, rowNum) -> createPartition(rs),
                new Object[]{databaseName, tableName});
    }

    public List<HMSPartition> findAllByDatabaseAndTableAndPartition(String databaseName, String tableName, String partition) {
        return jdbcTemplate.query(
                "SELECT d.name, t.tbl_id, t.tbl_name, p.part_name\n" +
                        " FROM partitions p\n" +
                        " INNER JOIN tbls t ON p.tbl_id = t.tbl_id\n" +
                        " INNER JOIN dbs d ON p.db_id = d.db_id\n" +
                        " WHERE d.name = ? AND t.tbl_name = ? AND p.name = ?",
                (rs, rowNum) -> createPartition(rs),
                new Object[]{databaseName, tableName, partition});
    }

    public HMSPartition findLastPartitionByDatabaseAndTable(String databaseName, String tableName) {
        return jdbcTemplate.queryForObject(
                "SELECT d1.name, t1.tbl_id, t1.tbl_name, p1.part_name\n" +
                        " FROM partitions p1\n" +
                        " INNER JOIN tbls t1 ON p1.tbl_id = t1.tbl_id\n" +
                        " INNER JOIN dbs d1 ON p1.db_id = d1.db_id\n" +
                        " WHERE d1.name = ? AND t1.tbl_name = ? AND p1.part_name = \n" +
                        "(SELECT max(p2.part_name) FROM partitions p2\n" +
                        " INNER JOIN tbls t2 ON p2.tbl_id = t2.tbl_id\n" +
                        " INNER JOIN dbs d2 ON p2.db_id = d2.db_id\n" +
                        " WHERE d2.name = ? AND t2.tbl_name = ?)",
                (rs, rowNum) -> createPartition(rs),
                new Object[]{databaseName, tableName, databaseName, tableName});
    }

    private HMSPartition createPartition(ResultSet rs) throws SQLException {
        return HMSPartition.builder()
                .table(HMSTable.builder()
                        .database(HMSDatabase.builder()
                                .name(rs.getString(1))
                                .build()
                        )
                        .id(rs.getLong(2))
                        .name(rs.getString(3))
                        .build()
                )
                .name(rs.getString(4))
                .build();
    }

}
