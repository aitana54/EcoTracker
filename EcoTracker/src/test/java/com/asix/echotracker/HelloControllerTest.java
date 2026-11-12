package com.asix.echotracker;

import com.asix.ecotracker.HelloController;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HelloControllerTest {
    HelloController controller = new HelloController();
    @Test
    void testConnectToDatabase() {

        try (Connection conn = controller.connect_to_bd()) {
            assertNotNull(conn, "La conexión no debe ser null");
            assertFalse(conn.isClosed(), "La conexión debe estar abierta");
        } catch (SQLException e) {
            fail("No se pudo conectar a la base de datos: " + e.getMessage());
        }
    }
    @Test
    void testGenerateCO2ChartHtml_NotNullOrEmpty() {
        String html = controller.generateCO2ChartHtml();

        assertNotNull(html, "El HTML generado no debe ser null");
        assertFalse(html.isEmpty(), "El HTML generado no debe estar vacío");
    }

    @Test
    void testGenerateCO2ChartHtml_ContainsExpectedData() {
        String html = controller.generateCO2ChartHtml();

        assertTrue(html.contains("Ciclisme"), "Debe contener 'Ciclisme'");
        assertTrue(html.contains("0.24"), "Debe contener el valor '0.24'");
        assertTrue(html.contains("Reciclar 1 kg de paper"), "Debe contener la actividad 'Reciclar 1 kg de paper'");
        assertTrue(html.contains("CO₂ estalviat"), "Debe contener el encabezado 'CO₂ estalviat'");
    }
}