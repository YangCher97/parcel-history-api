package com.redncs.parcelhistoryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redncs.parcelhistoryapi.model.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

}
