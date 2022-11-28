package com.evol.pichincha.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.evol.pichincha.entity.Audit;

@Repository
public interface AuditRepository  extends R2dbcRepository<Audit, Long>{

}
