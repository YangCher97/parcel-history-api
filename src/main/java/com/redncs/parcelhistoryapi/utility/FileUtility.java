package com.redncs.parcelhistoryapi.utility;

import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.redncs.parcelhistoryapi.model.Owner;
import com.redncs.parcelhistoryapi.model.Parcel;
import com.redncs.parcelhistoryapi.model.ParcelHistory;
import com.redncs.parcelhistoryapi.model.Sale;

@Component
public class FileUtility {

	private final CsvMapper mapper = new CsvMapper();

	/**
	 * Reads the csv files input stream into a generic class.
	 * 
	 * @param <T>
	 * @param clazz
	 * @param stream
	 * 
	 * @return
	 * 
	 * @throws IOException
	 */
	public <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException {
		mapper.enable(CsvParser.Feature.TRIM_SPACES);

		CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true);
		ObjectReader reader = mapper.readerFor(clazz).with(schema);
		return reader.<T>readValues(stream).readAll();
	}

	/**
	 * Populates the list of objects passed by reference.
	 * 
	 * @param parcelHistorylist
	 * @param saleList
	 * @param parcelList
	 * @param ownerList
	 * @param existingSaleList
	 * @param existingParcelList
	 * @param existingOwnerList
	 * 
	 */
	public void populateLists(List<ParcelHistory> parcelHistorylist, List<Sale> saleList, List<Parcel> parcelList,
			List<Owner> ownerList, List<Sale> existingSaleList, List<Owner> existingOwnerList,
			List<Parcel> existingParcelList) {

		// Assemble Maps for quick lookup to not create duplicates
		Map<String, String> parcelMap = this.assembleParcelMap(existingParcelList);
		Map<Owner, String> ownerMap = this.assembleOwnerMap(existingOwnerList);
		Map<Sale, String> saleMap = this.assembleSaleMap(existingSaleList);

		// Assemble the objects
		for (ParcelHistory parcelHistory : parcelHistorylist) {

			this.assembleSale(parcelHistory, saleList, saleMap);
			this.assembleParcel(parcelHistory, parcelList, parcelMap);
			this.assembleOwner(parcelHistory, ownerList, ownerMap);

		}

	}

	/**
	 * Assemble Sale Map.
	 * 
	 * @param existingSaleList
	 * 
	 * @return
	 */
	private Map<Sale, String> assembleSaleMap(List<Sale> existingSaleList) {
		Map<Sale, String> saleMap = new HashMap<>();

		for (Sale sale : existingSaleList) {
			saleMap.put(sale, "Exists!");
		}

		return saleMap;
	}

	/**
	 * Assemble the owner map.
	 * 
	 * @param existingOwnerList
	 * 
	 * @return
	 */
	private Map<Owner, String> assembleOwnerMap(List<Owner> existingOwnerList) {
		Map<Owner, String> ownerMap = new HashMap<>();

		for (Owner owner : existingOwnerList) {

//			Owner ownerMatcher = Owner.builder().address(owner.getAddress()).city(owner.getCity()).name(owner.getName())
//					.state(owner.getState()).zip(owner.getZip()).build();

			ownerMap.put(owner, "Exists!");
		}

		return ownerMap;
	}

	/**
	 * Assembles the parcel map.
	 * 
	 * @param existingParcelList
	 * 
	 * @return
	 */
	private Map<String, String> assembleParcelMap(List<Parcel> existingParcelList) {
		Map<String, String> parcelMap = new HashMap<>();

		for (Parcel parcel : existingParcelList) {
			parcelMap.put(parcel.getId(), "Exists!");
		}

		return parcelMap;
	}

	/**
	 * Assemble the sale object and add to the list.
	 * 
	 * @param parcelHistory
	 * @param saleList
	 * @param saleMap
	 */
	private void assembleSale(ParcelHistory parcelHistory, List<Sale> saleList, Map<Sale, String> saleMap) {

		Sale sale = Sale.builder().buildingValue(parcelHistory.getBuildingValue()).createdBy("LoadApi")
				.createdDate(OffsetDateTime.now()).deedBk(parcelHistory.getDeedBk()).deedPg(parcelHistory.getDeedPg())
				.landValue(parcelHistory.getLandValue()).lrkReId(parcelHistory.getLrkReId()).ownerId(null)
				.parcelId(StringUtils.strip(parcelHistory.getParcelId())).saleAmount(parcelHistory.getSaleAmount())
				.saleDate(parcelHistory.getSaleDate()).saleValidity(parcelHistory.getSaleValidity())
				.totalValue(parcelHistory.getTotalValue()).build();

		if (saleMap.get(sale) == null) {
			saleMap.put(sale, "Exists");
			saleList.add(sale);
		}
	}

	/**
	 * Assemble the Parcel object and add to the list.
	 * 
	 * @param parcelHistory
	 * @param parcelList
	 * @param parcelMap
	 */
	private void assembleParcel(ParcelHistory parcelHistory, List<Parcel> parcelList, Map<String, String> parcelMap) {

		// Only add new parcels
		if (parcelHistory.getParcelId() != null && parcelMap.get(parcelHistory.getParcelId()) == null) {
			Parcel parcel = Parcel.builder().acreage(parcelHistory.getAcreage())
					.address(parcelHistory.getParcelAddress()).city(parcelHistory.getParcelCity())
					.id(parcelHistory.getParcelId()).subdivision(parcelHistory.getSubdivision())
					.zip(parcelHistory.getParcelZip()).zoning(parcelHistory.getZoning())
					.zoningDist(parcelHistory.getZoningDist()).build();

			parcelMap.put(parcel.getId(), "Exists!");
			
			parcelList.add(parcel);
		}
	}

	/**
	 * Assemble the Owner object and add to the list.
	 * 
	 * @param parcelHistory
	 * @param ownerList
	 * @param ownerMap
	 */
	private void assembleOwner(ParcelHistory parcelHistory, List<Owner> ownerList, Map<Owner, String> ownerMap) {

		Owner owner = Owner.builder().address(parcelHistory.getOwnerAddress()).city(parcelHistory.getOwnerCity())
				.name(parcelHistory.getOwner()).state(parcelHistory.getOwnerState()).zip(parcelHistory.getOwnerZip())
				.build();

		// Only add new owner
		if (ownerMap.get(owner) == null) {
			ownerMap.put(owner, "Exists!");
			ownerList.add(owner);
		}
	}
}
