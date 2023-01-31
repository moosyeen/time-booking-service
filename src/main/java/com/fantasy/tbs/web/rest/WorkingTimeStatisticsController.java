package com.fantasy.tbs.web.rest;

import com.fantasy.tbs.service.WorkingTimeStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api")
public class WorkingTimeStatisticsController {

    private final Logger log = LoggerFactory.getLogger(WorkingTimeStatisticsController.class);

    private final WorkingTimeStatisticsService workingTimeStatisticsService;

    public WorkingTimeStatisticsController(WorkingTimeStatisticsService workingTimeStatisticsService) {
        this.workingTimeStatisticsService = workingTimeStatisticsService;
    }

    /**
     * Get an employee total working hours in the assigned date.
     * @param personalNumber employee personal number
     * @param date assigned date
     */
    @GetMapping("/employee-working-hours/{personalNumber}/{date}")
    public ResponseEntity<String> getEmployeeWorkingHoursWithAssignedDate(@PathVariable("personalNumber") String personalNumber,
                                                                          @PathVariable("date") ZonedDateTime date){
        log.debug("REST request to get employee: " + personalNumber + " working hours on " + date.toString() + ".");
        double hours = workingTimeStatisticsService.getEmployeeWorkingHours(personalNumber, date);
        if (hours + 1 < 0.00000001) {
            String exceptionString = "No valid record found with the personalNumber: " + personalNumber;
            return ResponseEntity.ok(exceptionString);
        } else {
            return ResponseEntity.ok(hours+"");
        }
    }

    /**
     * Get an employee total working hours in the assigned time range.
     * @param personalNumber employee personal number
     * @param startDate start time
     * @param endDate end time
     */
    @GetMapping("/employee-working-hours/{personalNumber}/{startDate}/{endDate}")
    public ResponseEntity<String> getEmployeeWorkingHoursWithDateRange(@PathVariable("personalNumber") String personalNumber,
                                                                       @PathVariable("startDate") ZonedDateTime startDate,
                                                                       @PathVariable("endDate") ZonedDateTime endDate){
        log.debug("REST request to get employee: " + personalNumber + " working hours in " + startDate.toString() + " and " + endDate.toString() + ".");
        double hours = workingTimeStatisticsService.getEmployeeWorkingHours(personalNumber, startDate, endDate);
        if (hours + 1 < 0.00000001) {
            String exceptionString = "No valid record found with the personalNumber: " + personalNumber;
            return ResponseEntity.ok(exceptionString);
        } else {
            return ResponseEntity.ok(hours+"");
        }
    }

}
