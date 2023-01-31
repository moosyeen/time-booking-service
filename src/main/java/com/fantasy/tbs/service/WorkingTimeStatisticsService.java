package com.fantasy.tbs.service;

import java.time.ZonedDateTime;

public interface WorkingTimeStatisticsService {

    /**
     * Get an employee total working hours in the assigned date.
     * @param personalNumber The personalNumber of an employee.
     * @param dateTime The date of the query.
     * @return The employee working hours the day assigned by the client.
     */
    double getEmployeeWorkingHours(String personalNumber, ZonedDateTime dateTime);

    /**
     * Get an employee total working hours in the assigned time range.
     * @param personalNumber The personalNumber of an employee.
     * @param startDate The start date of the time range.
     * @param endDate The end date of the time range.
     * @return The employee total working hours in the range assigned by the client.
     */
    double getEmployeeWorkingHours(String personalNumber, ZonedDateTime startDate, ZonedDateTime endDate);

}
