package org.example.Services;

import de.jollyday.HolidayManager;
import org.example.exeptions.CustomDateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class CalculateServiceTest {
    @Mock
    private HolidayManager holidayManager;

    @InjectMocks
    private CalculateService calculateService;

    @Test
    public void simpleCalculate(){
        String res = calculateService.simpleCalculateVacationPay(60000.00, 10);
        Assertions.assertEquals("20477,82", res);
    }

    @Test
    public void accurateCalculate() throws CustomDateException {
        LocalDate startDate = LocalDate.parse("2024-09-01");
        LocalDate endDate = LocalDate.parse("2024-09-10");
        String res = calculateService.accurateCalculateVacationPay(60000.00,
                                                                 10,
                                                                  startDate,
                                                                  endDate);
        Assertions.assertEquals("14334,47", res);
    }

    @Test
    public void accurateCalculateThrowCustomDateExceptionInvalidStartDate() throws CustomDateException {
        LocalDate startDate = LocalDate.parse("2024-09-11");
        LocalDate endDate = LocalDate.parse("2024-09-10");
        Assertions.assertThrows(CustomDateException.class, () ->
                calculateService.accurateCalculateVacationPay(60000.00, 10, startDate, endDate));
    }

    @Test
    public void accurateCalculateThrowCustomDateExceptionInvalidPeriod() throws CustomDateException {
        LocalDate startDate = LocalDate.parse("2024-09-01");
        LocalDate endDate = LocalDate.parse("2024-09-10");
        Assertions.assertThrows(CustomDateException.class, () ->
                calculateService.accurateCalculateVacationPay(60000.00, 33, startDate, endDate));
    }
}
