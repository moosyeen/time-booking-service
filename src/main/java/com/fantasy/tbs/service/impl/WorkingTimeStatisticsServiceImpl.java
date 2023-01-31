package com.fantasy.tbs.service.impl;

import com.fantasy.tbs.repository.TimeBookingRepository;
import com.fantasy.tbs.service.WorkingTimeStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class WorkingTimeStatisticsServiceImpl implements WorkingTimeStatisticsService {

    private final Logger log = LoggerFactory.getLogger(WorkingTimeStatisticsServiceImpl.class);

    private final TimeBookingRepository timeBookingRepository;

    public WorkingTimeStatisticsServiceImpl(TimeBookingRepository timeBookingRepository) {
        this.timeBookingRepository = timeBookingRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public double getEmployeeWorkingHours(String personalNumber, ZonedDateTime dateTime) {
        log.debug("Request to get employee: " + personalNumber + " working hours on " + dateTime.toString() + ".");
        ZonedDateTime thisDay = getZeroClock(dateTime);
        ZonedDateTime nextDay = getZeroClock(dateTime.plusDays(1));
        List<ZonedDateTime> bookingTimeList = timeBookingRepository.findByPersonalNumberAndBookingBetween(personalNumber, thisDay, nextDay);
        if (bookingTimeList.size() >= 2) {
            return zonedDateTimeDifference(bookingTimeList.get(0), bookingTimeList.get(bookingTimeList.size() - 1));
        } else {
            return -1;
        }
    }

    @Override
    public double getEmployeeWorkingHours(String personalNumber, ZonedDateTime startDate, ZonedDateTime endDate) {
        log.debug("Request to get employee: " + personalNumber + " working hours in " + startDate.toString() + " and " + endDate.toString() + ".");
        double totalHours = 0d;
        ZonedDateTime startDateZeroClock = getZeroClock(startDate);
        ZonedDateTime endDateZeroClock = getZeroClock(endDate);
        List<ZonedDateTime> bookingTimeList = timeBookingRepository.findByPersonalNumberAndBookingBetween(personalNumber, startDateZeroClock, endDateZeroClock);
        if (bookingTimeList.size() >= 2) {
            ZonedDateTime previous;
            ZonedDateTime current;
            previous = bookingTimeList.get(0);
            for (int i = 1; i < bookingTimeList.size() - 1; i++) {
                current = bookingTimeList.get(i);
                // avoid employee forgot to book time
                if (previous.getDayOfYear() != current.getDayOfYear()){
                    previous = current;
                    continue;
                }
                if (bookingTimeList.get(i+1).getDayOfYear() != current.getDayOfYear()){
                    totalHours = totalHours + zonedDateTimeDifference(previous, current);
                    previous = current;
                }
            }
            return totalHours;
        } else {
            return -1;
        }
    }

    private ZonedDateTime getZeroClock(ZonedDateTime dateTime) {
        int yearNumber = dateTime.getYear();
        int monthNumber = dateTime.getMonthValue();
        int dayOfMonth = dateTime.getDayOfMonth();
        return ZonedDateTime.of(yearNumber, monthNumber, dayOfMonth, 0, 0, 0, 0, dateTime.getZone());
    }

    private double zonedDateTimeDifference(ZonedDateTime zdt1, ZonedDateTime zdt2) {
        return (Math.abs(ChronoUnit.SECONDS.between(zdt1, zdt2))) / 3600d;
    }



}
