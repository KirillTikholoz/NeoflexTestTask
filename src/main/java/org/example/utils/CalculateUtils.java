package org.example.utils;

import de.jollyday.HolidayManager;
import lombok.RequiredArgsConstructor;
import org.example.exeptions.CustomDateException;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;

@Component
@RequiredArgsConstructor
public class CalculateUtils {

    private final HolidayManager holidayManager;

    private Integer calculateCountWorkingDays(LocalDate startDate, LocalDate endDate) {
        LocalDate currentDate = startDate;
        Integer countWorkingDays = 0;

        while (!currentDate.isAfter(endDate)) {
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
            if (!holidayManager.isHoliday(currentDate) &&
                    dayOfWeek != DayOfWeek.SATURDAY &&
                    dayOfWeek != DayOfWeek.SUNDAY) {
                countWorkingDays++;
            }

            currentDate = currentDate.plusDays(1);
        }

        return countWorkingDays;
    }

    public String accurateCalculateVacationPay(Double avgSalary,
                                               Integer countVacationDays,
                                               LocalDate startDate,
                                               LocalDate endDate) throws CustomDateException {

        if (startDate.isAfter(endDate)) {
            throw new CustomDateException("The start date of the period is later than the end date of the period");
        }

        if (Period.between(startDate, endDate).getDays() + 1 != countVacationDays) {
            throw new CustomDateException("countVacationDays does not match the period between startDate and endDate");
        }

        Integer countWorkingDays = calculateCountWorkingDays(startDate, endDate);
        Double res = (avgSalary / 29.3) * countWorkingDays;

        DecimalFormat df = new DecimalFormat("#.##");

        return df.format(res);
    }

    public String simpleCalculateVacationPay(Double avgSalary, Integer countVacationDays) {
        Double res = (avgSalary / 29.3) * countVacationDays;
        DecimalFormat df = new DecimalFormat("#.##");

        return df.format(res);
    }
}
