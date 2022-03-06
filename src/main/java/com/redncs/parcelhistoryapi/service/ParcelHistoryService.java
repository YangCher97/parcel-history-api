package com.redncs.parcelhistoryapi.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.redncs.parcelhistoryapi.model.Owner;
import com.redncs.parcelhistoryapi.model.Parcel;
import com.redncs.parcelhistoryapi.model.ParcelHistory;
import com.redncs.parcelhistoryapi.model.Sale;
import com.redncs.parcelhistoryapi.repository.OwnerRepository;
import com.redncs.parcelhistoryapi.repository.ParcelRepository;
import com.redncs.parcelhistoryapi.repository.SaleRepository;
import com.redncs.parcelhistoryapi.utility.FileUtility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ParcelHistoryService {

	@Autowired
	private ParcelRepository parcelRepository;
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	@Autowired
	private SaleRepository saleRepository;
	
	@Autowired
	private FileUtility fileUtility;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void processParcelHistoryCsvFile(MultipartFile file) {
		try {
			
			// Read everything from the CSV File into a list of objects
			List<ParcelHistory> parcelHistorylist = fileUtility.read(ParcelHistory.class, file.getInputStream());
			
			// Parse the objects and get the sub lists
			List<Sale> saleList = new ArrayList<>();
			List<Parcel> parcelList = new ArrayList<>();
			List<Owner> ownerList = new ArrayList<>();
			
			List<Sale> existingSaleList = saleRepository.findAll();
			List<Owner> existingOwnerList = ownerRepository.findAll();
			List<Parcel> existingParcelList = parcelRepository.findAll();
			
			fileUtility.populateLists(parcelHistorylist, saleList, parcelList, ownerList, existingSaleList, existingOwnerList, existingParcelList);
			
			// Save for each different list			
			saleRepository.saveAll(saleList);
			ownerRepository.saveAll(ownerList);
			parcelRepository.saveAll(parcelList);
			
		} catch (Exception e) {
			log.error(this.getClass().getName(), "Exception occurred when trying to process file. Exception : {}", e);
		}
	}
}
