package com.redncs.parcelhistoryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redncs.parcelhistoryapi.model.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

}
