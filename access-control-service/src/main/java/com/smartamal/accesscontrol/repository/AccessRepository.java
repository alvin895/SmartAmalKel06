package com.smartamal.accesscontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartamal.accesscontrol.model.AccessLog;

@Repository
public interface AccessRepository
        extends JpaRepository<AccessLog, Long> {

}