package com.redncs.parcelhistoryapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.redncs.parcelhistoryapi.service.ParcelHistoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/parcelhistory")
@Slf4j
public class ParcelHistoryApiController {

	@Autowired
	private ParcelHistoryService parcelHistoryService;
	
	@PostMapping(value = "/load", consumes = "multipart/form-data")
	public String load(@RequestParam("file") MultipartFile file) {

		try {
			log.info("Received file to load.");
			
			parcelHistoryService.processParcelHistoryCsvFile(file);

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return "Unsuccessful attemp to load file.";
		}

		return "File loaded successfully.";
	}
}
