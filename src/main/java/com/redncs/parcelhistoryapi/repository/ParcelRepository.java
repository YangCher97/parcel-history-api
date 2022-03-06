package com.redncs.parcelhistoryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redncs.parcelhistoryapi.model.Parcel;

public interface ParcelRepository extends JpaRepository<Parcel, String> {

}
