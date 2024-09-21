package org.example.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.services.CalculateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CalculateController.class)
public class CalculateControllerTest {
    @MockBean
    private CalculateService calculateService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void simpleCalculate() throws Exception {
        double avgSalary = 60000;
        int countVacationDays = 10;

        mockMvc.perform(get("/calculacte")
                        .param("avgSalary", String.valueOf(avgSalary))
                        .param("countVacationDays", String.valueOf(countVacationDays)))
                .andExpect(status().isOk());
    }

    @Test
    public void simpleCalculateWithViolationException() throws Exception {
        double avgSalary = -60000;
        int countVacationDays = 10;

        mockMvc.perform(get("/calculacte")
                        .param("avgSalary", String.valueOf(avgSalary))
                        .param("countVacationDays", String.valueOf(countVacationDays)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void accurateCalculate() throws Exception {
        double avgSalary = 60000;
        int countVacationDays = 10;

        mockMvc.perform(get("/calculacte")
                        .param("avgSalary", String.valueOf(avgSalary))
                        .param("countVacationDays", String.valueOf(countVacationDays))
                        .param("startDate", "01-09-2024")
                        .param("endDate", "10-09-2024"))
                .andExpect(status().isOk());
    }

    @Test
    public void accurateCalculateWithViolationException() throws Exception {
        double avgSalary = 60000;
        int countVacationDays = -10;

        mockMvc.perform(get("/calculacte")
                        .param("avgSalary", String.valueOf(avgSalary))
                        .param("countVacationDays", String.valueOf(countVacationDays))
                        .param("startDate", "01-09-2024")
                        .param("endDate", "10-09-2024"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void accurateCalculateWithMissingServletRequestParameterException() throws Exception {
        Double avgSalary = null;
        int countVacationDays = 10;

        mockMvc.perform(get("/calculacte")
                        .param("avgSalary", String.valueOf(avgSalary))
                        .param("countVacationDays", String.valueOf(countVacationDays))
                        .param("startDate", "01-09-2024")
                        .param("endDate", "10-09-2024"))
                .andExpect(status().isBadRequest());
    }

}
