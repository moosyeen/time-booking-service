package com.fantasy.tbs.task;

import com.fantasy.tbs.service.BookingPrompterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class BookingPrompterTask {

    private static final int TIME_POINT = 13;

    private final Logger log = LoggerFactory.getLogger(BookingPrompterTask.class);

    private static final ScheduledExecutorService TIMER = Executors.newSingleThreadScheduledExecutor();

    private static final String THREAD_NAME = "Booking Prompter";

    private final BookingPrompterService bookingPrompterService;

    public BookingPrompterTask(BookingPrompterService bookingPrompterService) {
        this.bookingPrompterService = bookingPrompterService;
    }

    // TODO: Find a graceful way to check time point.
    public void start() {
        TIMER.scheduleAtFixedRate(() -> {
            Thread.currentThread().setName(THREAD_NAME);
            try {
                if (whetherExecute()) {
                    log.debug("Start to check booking");
                    bookingPrompterService.checkForgotBook();
                }
            } catch (Exception e) {
                // TODO: Use self-defined catching method.
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.HOURS);
    }

    // TODO: Find a graceful way to check time point.
    private boolean whetherExecute() {
        LocalDateTime ldt = LocalDateTime.now();
        return (ldt.getHour() == TIME_POINT);
    }

}
