package com.cloudera.metastore.api.service;

import com.cloudera.metastore.api.repository.HMSDatabaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseService {

    private final HMSDatabaseRepository databaseRepository;

}
