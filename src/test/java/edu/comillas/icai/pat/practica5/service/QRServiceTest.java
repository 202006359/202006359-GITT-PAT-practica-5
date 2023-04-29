package edu.comillas.icai.pat.practica5.service;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import edu.comillas.icai.pat.practica5.dao.QRDao;
import edu.comillas.icai.pat.practica5.dto.QRImage;
import edu.comillas.icai.pat.practica5.repository.QRRepository;
import static org.mockito.Mockito.*;



@RunWith(SpringRunner.class)
@SpringBootTest
public class QRServiceTest{
    
    @Autowired
    private QRService qrService;
    
    @Autowired
    private QRRepository qrRepository;
    
    @Test
    public void testGenerarQR() throws Exception {
        byte[] qrCode = qrService.generarQR("Maven", "200x200");
        assertNotNull(qrCode);
    }
    
    @Test
    public void testGuardarQR() throws Exception {
        byte[] qrCode = qrService.generarQR("Spring Boot", "250x250");
        qrService.guardarQR("test", "250x250", qrCode);
        
        QRDao storedQR = qrRepository.findByName("test");
        assertNotNull(storedQR);
        assertEquals("test", storedQR.getName());
        assertEquals("250x250", storedQR.getSize());
        assertArrayEquals(qrCode, storedQR.getImage());
    }
    
    //Code 200
    @Test
    public void testEjecutarOperacion() throws Exception {
        QRImage qrImage = qrService.ejecutarOperacion("JUnit", "300x300");
        assertNotNull(qrImage);
        assertEquals("JUnit", qrImage.getData());
        assertEquals("300x300", qrImage.getSize());
        assertNotNull(qrImage.getImage());
    }

    //Error 400
    @Test
    public void testEjecutarOperacionBadRequest() throws Exception {
        QRService mock = org.mockito.Mockito.mock(QRService.class);
        when(mock.ejecutarOperacion(anyString(), anyString()))
            .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create and save QR code (400): " ));
        try {
            mock.ejecutarOperacion("", "");
            fail("Expected ResponseStatusException");
        } catch (ResponseStatusException ex) {
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        }
    }

    //Error 500
    @Test
    public void testEjecutarOperacionInternalServerError() throws Exception {
        String datos = "Hola";
        String tamano = "300x300";
        QRService mock = org.mockito.Mockito.mock(QRService.class);
        when(mock.ejecutarOperacion(datos, tamano))
            .thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create and save QR code (500): " ));
        try {    
            mock.ejecutarOperacion(datos, tamano);
            fail("Expected ResponseStatusException");
        } catch (ResponseStatusException ex) {
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatusCode());
        }
    }


}
