package com.cloudera.metastore.api.repository.jpa;

import com.cloudera.metastore.api.domain.HMSDatabase;
import com.cloudera.metastore.api.repository.HMSDatabaseRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Profile("jpa")
@Repository
public interface HMSDatabaseJpaRepository extends JpaRepository<HMSDatabase, Long>, HMSDatabaseRepository {

}
