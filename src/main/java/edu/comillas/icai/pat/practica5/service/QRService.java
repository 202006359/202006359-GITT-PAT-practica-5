/*******************************************************************************
 * Copyright (c) 2023 Universidad de Comillas ICAI.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the company
 * which accompanies this distribution
 *
 * Contributors:
 *     Universidad de Comillas - ICAI
 *******************************************************************************/


package edu.comillas.icai.pat.practica5.service;


import edu.comillas.icai.pat.practica5.dto.QRImage;

public interface QRService {
  byte[] generarQR(String data, String size) throws Exception;
  void guardarQR(String data, String size, byte[] image) throws Exception;
  QRImage ejecutarOperacion(String data, String size) throws Exception;
}


 