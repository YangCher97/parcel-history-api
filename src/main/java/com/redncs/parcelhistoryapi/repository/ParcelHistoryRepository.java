package com.redncs.parcelhistoryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redncs.parcelhistoryapi.model.ParcelHistory;

public interface ParcelHistoryRepository extends JpaRepository<ParcelHistory, Long> {

}
