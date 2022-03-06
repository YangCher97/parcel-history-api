package com.redncs.parcelhistoryapi.model;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private long id;

	private String parcelId;
	private String lrkReId;
	private Long ownerId;
	private double buildingValue;
	private double landValue;
	private double totalValue;
	private double saleAmount;
	private Date saleDate;
	private String saleValidity;
	private String deedBk;
	private String deedPg;
	private OffsetDateTime createdDate;
	private String createdBy;
	private OffsetDateTime modifiedDate;
	private String modifiedBy;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sale other = (Sale) obj;
		return Double.doubleToLongBits(buildingValue) == Double.doubleToLongBits(other.buildingValue)
				&& Objects.equals(deedBk, other.deedBk) && Objects.equals(deedPg, other.deedPg)
				&& Double.doubleToLongBits(landValue) == Double.doubleToLongBits(other.landValue)
				&& Objects.equals(lrkReId, other.lrkReId) && Objects.equals(parcelId, other.parcelId)
				&& Double.doubleToLongBits(saleAmount) == Double.doubleToLongBits(other.saleAmount)
				&& Objects.equals(saleValidity, other.saleValidity)
				&& Double.doubleToLongBits(totalValue) == Double.doubleToLongBits(other.totalValue);
	}
	@Override
	public int hashCode() {
		return Objects.hash(buildingValue, deedBk, deedPg, landValue, lrkReId, parcelId, saleAmount, saleValidity,
				totalValue);
	}

	
}
