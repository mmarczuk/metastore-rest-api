package com.cloudera.metastore.api.repository;

import org.springframework.cache.annotation.Cacheable;

@Cacheable("databases")
public interface HMSDatabaseRepository {
}
