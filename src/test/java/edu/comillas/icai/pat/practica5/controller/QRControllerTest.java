package edu.comillas.icai.pat.practica5.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.comillas.icai.pat.practica5.dto.QRImage;
import edu.comillas.icai.pat.practica5.service.QRService;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;




@RunWith(MockitoJUnitRunner.class)
public class QRControllerTest {

    @Mock
    private QRService qrService;

    @InjectMocks
    private QRController qrController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(qrController).build();
    }

    //Code 200
    @Test
    public void testCreateQRSuccess() throws Exception {
        String data = "JUnit";
        String size = "300x300";
        byte[] qrImageBytes = new byte[] {0x00, 0x01, 0x02};
        QRImage qrImage = new QRImage(data, size, qrImageBytes);
        when(qrService.ejecutarOperacion(data, size)).thenReturn(qrImage);
        mockMvc.perform(get("/icai/CreateQR")
                .param("data", data)
                .param("size", size))
                .andExpect(status().isOk())
                .andExpect(content().bytes(qrImageBytes));
    }

    //Error 400
    @Test
    public void testCreateQREmptyParams() throws Exception {
        mockMvc.perform(get("/icai/CreateQR"))
                .andExpect(status().isBadRequest());
    }

    //Error 404
    @Test
    public void testQRResourceNotFound() throws Exception {
        mockMvc.perform(get("/BadEndPoint"))
                .andExpect(status().isNotFound());
    }

    //Error 500
    @Test
    public void testCreateQRInternalServerError() throws Exception {
        String data = "JUnit";
        String size = "300x300";
        when(qrService.ejecutarOperacion(data, size)).thenThrow(new Exception("Mock Exception"));
        mockMvc.perform(get("/icai/CreateQR")
                .param("data", data)
                .param("size", size))
                .andExpect(status().isInternalServerError());
    }

}

