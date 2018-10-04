package demo.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.CronScheduleBuilder.cronSchedule;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.Date;

public class Program {
    //private static final Logger _log = LoggerFactory.getLogger(Program.class);

    public static void main( String[] args )
    {
        try
        {
            jobWithSimpleTriggerDemo();

            //jobWithCronTriggerDemo();

            //jobWithManyTriggerDemo();
        }
        catch (Exception e)
        {

        }
    }

    // 演示简单触发器的使用
    private static void jobWithSimpleTriggerDemo() throws Exception {
        // 从工厂中获取一个调度器实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 创建一个任务
        JobDetail job = newJob(SimpleJob.class).withIdentity("任务名称1", "任务组名").build();
        // 创建一个简单触发器，每隔5秒执行一次
        Trigger simpleTrigger = newTrigger()
                .withIdentity("触发器名称1", "触发器组名")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever())
                .build();

        // 把任务、简单触发器加入调度器
        scheduler.scheduleJob(job, simpleTrigger);

        // 开启调度器
        scheduler.start();
        //_log.info("------- 已开始计划 -----------------");
        System.out.println("------- 已开始计划 -----------------");

        //_log.info("------- 正在等待20秒 -------------");
        System.out.println("------- 正在等待20秒 -------------");
        try {
            Thread.sleep(20L * 1000L);
        } catch (Exception e) {
        }

        scheduler.shutdown(true);
        //_log.info("------- 已关闭计划 -----------------");
        System.out.println("------- 已关闭计划 -------------");
    }

    // 演示Cron触发器的使用
    private static void jobWithCronTriggerDemo() throws Exception {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 创建【任务21】
        JobDetail job = newJob(SimpleJob.class).withIdentity("任务名称21", "任务组名").build();
        // 创建一个Cron触发器，每隔10秒执行一次，触发的时刻：02s、12s、22s、32s、42s、52s
        CronTrigger cronTrigger = newTrigger().withIdentity("触发器名称21", "触发器组名").withSchedule(cronSchedule("2/10 * * ? * *"))
                .build();

        // 把任务、Cron触发器加入调度器
        Date ft = scheduler.scheduleJob(job, cronTrigger);
        //_log.info(jft + "时计划执行【" + job.getKey() + "】，且基于【" + cronTrigger.getCronExpression() + "】Cron表达式重复执行。");
        System.out.println(ft + "时计划执行【" + job.getKey() + "】，且基于【" + cronTrigger.getCronExpression() + "】Cron表达式重复执行。");

        // 创建【任务22】
        job = newJob(SimpleJob.class).withIdentity("任务名称22", "任务组名").build();
        // 创建一个Cron触发器，每隔1分钟的第15秒执行一次
        cronTrigger = newTrigger().withIdentity("触发器名称22", "触发器组名").withSchedule(cronSchedule("15 0/2 * * * ?")).build();

        ft = scheduler.scheduleJob(job, cronTrigger);
        //_log.info(jft + "时计划执行【" + job.getKey() + "】，且基于【" + cronTrigger.getCronExpression() + "】Cron表达式重复执行。");
        System.out.println(ft + "时计划执行【" + job.getKey() + "】，且基于【" + cronTrigger.getCronExpression() + "】Cron表达式重复执行。");

        // 开启调度器
        scheduler.start();
        //_log.info("------- 已开始计划 -----------------");
        System.out.println("------- 已开始计划 -----------------");

        //_log.info("------- 正在等待5分钟 -------------");
        System.out.println("------- 正在等待5分钟 -------------");
        try {
            Thread.sleep(300L * 1000L);
        } catch (Exception e) {
        }

        scheduler.shutdown(true);
        //_log.info("------- 已关闭计划 -----------------");
        System.out.println("------- 已关闭计划 -------------");

        SchedulerMetaData metaData = scheduler.getMetaData();
        //_log.info(String.format("%n共执行了%d个任务。", metaData.getNumberOfJobsExecuted()));
        System.out.printf("共执行了%d个任务。%n", metaData.getNumberOfJobsExecuted());
    }

    // 演示一个任务多触发器的使用
    private static void jobWithManyTriggerDemo() throws Exception
    {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 创建【任务3】
        JobDetail job = newJob(SimpleJob.class).withIdentity("任务名称3", "任务组名").build();
        // 创建一个简单触发器，每隔5秒执行一次
        Trigger simpleTrigger = newTrigger()
                .withIdentity("触发器名称3", "触发器组名")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever())
                .build();

        // 把任务、简单触发器加入调度器
        scheduler.scheduleJob(job, simpleTrigger);

        // 创建一个为任务“job”服务的Cron触发器，每隔10秒执行一次
        CronTrigger cronTrigger = newTrigger().withIdentity("触发器名称4", "触发器组名")
                .withSchedule(cronSchedule("/10 * * ? * *")).forJob(job)
                .build();

        // 把Cron触发器加入调度器
        scheduler.scheduleJob(cronTrigger);

        // 开启调度器
        scheduler.start();
        //_log.info("------- 已开始计划 -----------------");
        System.out.println("------- 已开始计划 -----------------");

        //_log.info("------- 正在等待20秒 -------------");
        System.out.println("------- 正在等待20秒 -------------");
        try {
            Thread.sleep(20L * 1000L);
        } catch (Exception e) {
        }

        scheduler.shutdown(true);
        //_log.info("------- 已关闭计划 -----------------");
        System.out.println("------- 已关闭计划 -------------");

        SchedulerMetaData metaData = scheduler.getMetaData();
        //_log.info(String.format("%n共执行了%d个任务。", metaData.getNumberOfJobsExecuted()));
        System.out.printf("共执行了%d个任务。%n", metaData.getNumberOfJobsExecuted());
    }
}
