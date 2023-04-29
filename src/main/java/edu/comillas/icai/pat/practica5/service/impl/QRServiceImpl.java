/*******************************************************************************
 * Copyright (c) 2023 Universidad de Comillas ICAI.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the company
 * which accompanies this distribution
 *
 * Contributors:
 *     Universidad de Comillas - ICAI
 *******************************************************************************/


package edu.comillas.icai.pat.practica5.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import edu.comillas.icai.pat.practica5.dto.QRImage;
import edu.comillas.icai.pat.practica5.repository.QRRepository;
import edu.comillas.icai.pat.practica5.service.QRService;
import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class QRServiceImpl implements QRService {
    @Autowired
    RestTemplate restTemplate;

    @Override
    public byte[] generarQR(String data, String size) throws Exception {
        try {
            String queryParams = "?size=" + size + "&data=" + data;
            String url = "https://api.qrserver.com/v1/create-qr-code/" + queryParams;

            log.info("Calling QR server API with URL: {}", url);
            ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);
            log.info("QR server API call successful with status code: {}", response.getStatusCode().value());
            return response.getBody();
        } catch (RestClientException e) {
            log.error("Error calling QR server API: {}", e.getMessage());
            if (data == null || data.isEmpty()||size == null || size.isEmpty() || !size.matches("\\d+x\\d+")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error calling QR server API (400): " + e.getMessage(), e);
            }else{
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error calling QR server API (500): " + e.getMessage(), e);
            }
        }
    }

    @Autowired
	QRRepository qrRepository;

    @Override
    public void guardarQR(String data, String size, byte[] image) throws Exception {
        try {
            qrRepository.guardar(data, size, image);
            log.info("QR info save successful");
        } catch (Exception e) {
            log.error("Failed to save QR code: : {}", e.getMessage());
            if (data == null || data.isEmpty()||size == null || size.isEmpty()||image == null || !size.matches("\\d+x\\d+") || image.length == 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to save QR code (400): " + e.getMessage(), e);
            }else{
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save QR code (500): " + e.getMessage(), e);
            }
        }
    }

    @Override
    @Transactional
    public QRImage ejecutarOperacion(String data, String size) throws Exception{
        byte[] qrImage = null;
        try {
            qrImage = this.generarQR(data, size);
            this.guardarQR(data, size, qrImage);
            QRImage qrImageDto = new QRImage(data,size,qrImage);
            log.info("Transactional operation successful");
            return qrImageDto;
        } catch (Exception e) {
            log.error("Error during ejecutarOperacion(): {}", e.getMessage());
            if (data == null || data.isEmpty()||size == null || size.isEmpty()||!size.matches("\\d+x\\d+")||qrImage == null || qrImage.length == 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to save QR code (400): " + e.getMessage(), e);
            }else{
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save QR code (500): " + e.getMessage(), e);
            }
        }
    }
   
}

