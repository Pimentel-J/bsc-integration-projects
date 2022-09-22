/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.util.ArrayList;
import java.util.List;
import lapr.project.data.InvoiceLineDB;
import lapr.project.model.InvoiceLine;
import lapr.project.model.Park;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 *
 */
public class InvoiceLineControllerTest {

    @InjectMocks
    private InvoiceLineController controller;

    @Mock
    private InvoiceLineDB mockedInvoiceDB;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new InvoiceLineController();
        controller.setInvoiceLineDB(mockedInvoiceDB);

    }

    /**
     * Test of GetInvoiceByUserName method.
     */
    @Test
    public void testGetInvoiceByUserName() {
        System.out.println("GetInvoiceByUserName");
        InvoiceLine invoice = new InvoiceLine(1, "Joao", 15, 15, 1, 10, 1);
        List<InvoiceLine> result = new ArrayList<>();
        result.add(invoice);

        when(mockedInvoiceDB.getInvoicesByUsername(any(String.class))).thenReturn(result);
        assertEquals(controller.getInvoicesByUsername("Joao").get(0).equals(invoice), true);
    }
}
