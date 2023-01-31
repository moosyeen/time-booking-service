package com.fantasy.tbs.bootstrap;


import com.fantasy.tbs.task.BookingPrompterTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationBootstrap {

    private static final Logger log = LoggerFactory.getLogger(ApplicationBootstrap.class);

    private final BookingPrompterTask bookingPrompterTask;

    @Autowired
    public ApplicationBootstrap(BookingPrompterTask bookingPrompterTask) {
        this.bookingPrompterTask = bookingPrompterTask;
    }

    public void initial() {
        initialMethods();
        startThreads();
        log.info("ServerBootstrap initial successfully!");
    }

    private void initialMethods() {
    }

    private void startThreads() {
        bookingPrompterTask.start();
    }

}
