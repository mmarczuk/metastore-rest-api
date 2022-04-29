package com.cloudera.demo.metastore.repository;

import com.cloudera.demo.metastore.domain.HMSDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HMSDatabaseRepository extends JpaRepository<HMSDatabase, Long> {

}
