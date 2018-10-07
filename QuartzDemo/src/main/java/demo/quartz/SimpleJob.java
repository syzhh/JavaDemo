package demo.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SimpleJob implements Job {
    public SimpleJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
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
