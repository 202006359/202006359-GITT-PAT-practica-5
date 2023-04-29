/*******************************************************************************
 * Copyright (c) 2023 Universidad de Comillas ICAI.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the company
 * which accompanies this distribution
 *
 * Contributors:
 *     Universidad de Comillas - ICAI
 *******************************************************************************/

package edu.comillas.icai.pat.practica5.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.comillas.icai.pat.practica5.dto.QRImage;
import edu.comillas.icai.pat.practica5.service.QRService;

@RestController
@RequestMapping("/icai")
@Slf4j
public class QRController implements HealthIndicator{

    @Autowired
    QRService qrService;

    @CrossOrigin(origins = {"http://127.0.0.1:5500/","https://202006359.github.io"}) //Use this to work in local and in github pages
    @GetMapping("/CreateQR")
    public ResponseEntity<byte[]> createQR(@RequestParam(required = true) String size, @RequestParam(required = true) String data) {
        log.info("Received request to create QR code with size {} and data {}", size, data);

        try {
            if (data == null || data.isEmpty()||size == null || size.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
            }
            QRImage qrImage = qrService.ejecutarOperacion(data, size); 
            log.info("Successfully generated and saved QR code");
            return new ResponseEntity<>(qrImage.getImage(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error generating QR code: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("CreateQR/health")
    @Override
    public Health health() { //Para ver si el servicio está activo
        try {
            qrService.generarQR("160x160", "test");
            return Health.up().build();
        } catch (Exception e) {
            return Health.down().withDetail("error", e.getMessage()).build();
        }
    }


}
