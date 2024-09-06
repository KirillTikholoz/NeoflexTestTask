package org.example.configs;

import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;
import de.jollyday.ManagerParameters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HolidayManagerConfig {
    @Bean
    public HolidayManager holidayManager() {
        return HolidayManager.getInstance(ManagerParameters.create(HolidayCalendar.RUSSIA));
    }
}
