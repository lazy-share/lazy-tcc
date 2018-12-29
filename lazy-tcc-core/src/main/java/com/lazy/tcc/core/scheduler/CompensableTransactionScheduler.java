package com.lazy.tcc.core.scheduler;

import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;
import com.lazy.tcc.core.scheduler.job.CompensableTransactionJob;
import com.lazy.tcc.core.spi.SpiConfiguration;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * <p>
 * CompensableTransactionScheduler Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/18.
 */
public class CompensableTransactionScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompensableTransactionScheduler.class);


    public CompensableTransactionScheduler init() {

        try {

            SchedulerFactory schedulerFactory = new StdSchedulerFactory();

            Scheduler scheduler = schedulerFactory.getScheduler();

            scheduler.start();


            JobDetail job = newJob(CompensableTransactionJob.class)

                    .withIdentity("compensableTransactionJob", "compensableTransactionGroup")

                    .build();


            Trigger trigger = newTrigger()

                    .withIdentity("compensableTransactionTrigger", "compensableTransactionGroup")

                    .startNow()

                    .withSchedule(simpleSchedule()

                            .withIntervalInMinutes(SpiConfiguration.getInstance().getCompensationMinuteInterval())

                            .repeatForever())

                    .build();


            scheduler.scheduleJob(job, trigger);

        } catch (Exception ex) {

            LOGGER.error("CompensableTransactionScheduler Exception: ", ex);
            throw new RuntimeException("CompensableTransactionScheduler Exception: ", ex);
        }

        return this;
    }

}
