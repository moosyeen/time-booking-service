package com.fantasy.tbs.repository;

import com.fantasy.tbs.domain.TimeBooking;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data SQL repository for the TimeBooking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeBookingRepository extends JpaRepository<TimeBooking, Long> {


    @Query(value = "select booking from time_booking where personal_number = :number and booking between :startTime and :endTime order by booking desc", nativeQuery = true)
    List<ZonedDateTime> findByPersonalNumberAndBookingBetween(@Param("number") String personalNumber,
                                                              @Param("startTime") ZonedDateTime startTime,
                                                              @Param("endTime") ZonedDateTime endTime);

}
