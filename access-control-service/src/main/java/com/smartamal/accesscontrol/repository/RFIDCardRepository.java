package com.smartamal.accesscontrol.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartamal.accesscontrol.model.RFIDCard;

@Repository
public interface RFIDCardRepository
        extends JpaRepository<RFIDCard, Long> {

    Optional<RFIDCard> findByUid(String uid);

}