package demo.quartz;

import java.util.Date;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SimpleJob implements Job {

    //private static Logger _log = LoggerFactory.getLogger(SimpleJob.class);

    public SimpleJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        //_log.info(String.format("[%tT] 简单的任务处理中...%n", new Date()));
        System.out.printf("[%tT] 简单的任务处理中...%n", new Date());
        // 业务逻辑处理
        try {
            Thread.sleep(2000);
        }
        catch(InterruptedException e)
        {
        }
    }
}
