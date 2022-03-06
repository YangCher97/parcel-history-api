package com.redncs.parcelhistoryapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;


//@JsonInclude(JsonInclude.Include.NON_NULL)

//@ToString
//@JsonIgnoreProperties(ignoreUnknown = true)

//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
@Data
@Entity
public class ParcelHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private long id;

	private String parcelId;
	private String lrkReId;
	private String parcelAddress;
	private String parcelCity;
	private String parcelZip;
	private String owner;
	private String ownerAddress;
	private String ownerCity;
	private String ownerState;
	private String ownerZip;
	private double buildingValue;
	private double landValue;
	private double totalValue;
	private double acreage;
	private String subdivision;
	private String zoningDist;
	private String zoning;
	private double saleAmount;
	private Date saleDate;
	private String saleValidity;
	private String deedBk;
	private String deedPg;
}
