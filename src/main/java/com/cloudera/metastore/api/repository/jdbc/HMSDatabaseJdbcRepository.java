package com.cloudera.metastore.api.repository.jdbc;

import com.cloudera.metastore.api.repository.HMSDatabaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Profile("jdbc")
@Repository
@RequiredArgsConstructor
public class HMSDatabaseJdbcRepository implements HMSDatabaseRepository {

    private final JdbcTemplate jdbcTemplate;

}
