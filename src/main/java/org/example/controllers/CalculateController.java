package org.example.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtos.ResponseCalculateDto;
import org.example.exeptions.CustomDateException;
import org.example.utils.CalculateUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class CalculateController {
    private final CalculateUtils calculateUtils;

    @GetMapping(value = "/calculacte", params = {"avgSalary", "countVacationDays"})
    public ResponseEntity<ResponseCalculateDto> simpleCalculate(@RequestParam @Positive Double avgSalary,
                                                                @RequestParam @Positive Integer countVacationDays) {

        String vacationPay = calculateUtils.simpleCalculateVacationPay(avgSalary, countVacationDays);

        return ResponseEntity.ok(new ResponseCalculateDto(vacationPay));
    }

    @GetMapping(value = "/calculacte", params = {"avgSalary", "countVacationDays", "startDate", "endDate"})
    public ResponseEntity<ResponseCalculateDto> accurateCalculate(
            @RequestParam @Positive Double avgSalary,
            @RequestParam @Positive Integer countVacationDays,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate
            ) throws CustomDateException {

        String vacationPay = calculateUtils.accurateCalculateVacationPay(avgSalary,
                countVacationDays,
                startDate,
                endDate);

        return ResponseEntity.ok(new ResponseCalculateDto(vacationPay));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationException(ConstraintViolationException ex) {
        log.error(ex.getMessage());

        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach(cv -> errors.add(cv.getPropertyPath() + ": " + cv.getMessage()));

        Map<String, List<String>> result = new HashMap<>();
        result.put("errors", errors);

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, CustomDateException.class})
    public ResponseEntity<?> constraintOtherParameterException(Exception ex) {
        log.error(ex.getMessage());

        Map<String, String> result = new HashMap<>();
        result.put("error", ex.getMessage());

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
