package com.fantasy.tbs.service.impl;

import com.fantasy.tbs.repository.TimeBookingRepository;
import com.fantasy.tbs.service.BookingPrompterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class BookingPrompterServiceImpl implements BookingPrompterService {

    // TODO: Find a graceful way to get everyone's name.
    private static final List<String> PERSONAL_NUMBER_LIST = new ArrayList<>();

    private final TimeBookingRepository timeBookingRepository;

    @Autowired
    public BookingPrompterServiceImpl(TimeBookingRepository timeBookingRepository) {
        this.timeBookingRepository = timeBookingRepository;
    }

    @Override
    public void checkForgotBook() {
        List<String> personForgot = new ArrayList<>();
        for (var personalNumber : PERSONAL_NUMBER_LIST) {
            if (checkIsEmployeeForgotBook(personalNumber)) {
                personForgot.add(personalNumber);
            }
        }
        if (personForgot.size() > 0) {
            //TODO: Call a transporting method here, communicate to the client.
        }
    }

    private boolean checkIsEmployeeForgotBook(String personalNumber) {
        ZonedDateTime nowTime = ZonedDateTime.now();
        ZonedDateTime thisDayZeroClock = getZeroClock(nowTime);
        List<ZonedDateTime> times = timeBookingRepository.findByPersonalNumberAndBookingBetween(personalNumber, thisDayZeroClock, nowTime);
        return (times.size() < 1);
    }

    // TODO: Move this method to util package
    private ZonedDateTime getZeroClock(ZonedDateTime dateTime) {
        int yearNumber = dateTime.getYear();
        int monthNumber = dateTime.getMonthValue();
        int dayOfMonth = dateTime.getDayOfMonth();
        return ZonedDateTime.of(yearNumber, monthNumber, dayOfMonth, 0, 0, 0, 0, dateTime.getZone());
    }

}
