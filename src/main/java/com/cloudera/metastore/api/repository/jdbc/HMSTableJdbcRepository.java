package com.cloudera.metastore.api.repository.jdbc;

import com.cloudera.metastore.api.domain.HMSDatabase;
import com.cloudera.metastore.api.domain.HMSTable;
import com.cloudera.metastore.api.repository.HMSTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Profile("jdbc")
@Repository
@RequiredArgsConstructor
public class HMSTableJdbcRepository implements HMSTableRepository {

    private final JdbcTemplate jdbcTemplate;

    public HMSTable findAllByDatabaseAndTable(String databaseName, String tableName) {
        return jdbcTemplate.queryForObject(
                "SELECT d.name, t.tbl_id, t.tbl_name\n" +
                        " FROM tbls t ON p.tbl_id = t.tbl_id\n" +
                        " INNER JOIN dbs d ON p.db_id = d.db_id\n" +
                        " WHERE d.name = ? AND t.tbl_name = ?",
                (rs, rowNum) -> createTable(rs),
                new Object[]{databaseName, tableName});
    }

    private HMSTable createTable(ResultSet rs) throws SQLException {
        return HMSTable.builder()
                .id(rs.getLong(1))
                .database(
                        HMSDatabase.builder()
                                .name(rs.getString(2))
                                .build()
                )
                .name(rs.getString(3))
                .build();
    }

}
