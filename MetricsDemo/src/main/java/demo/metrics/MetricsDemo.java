package demo.metrics;

import java.io.File;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.time.StopWatch;

import com.codahale.metrics.*;

public class MetricsDemo {
    static final MetricRegistry metrics = new MetricRegistry();
    static final Histogram searchFlightTime = metrics.histogram("MetricsDemo.SearchFlightTime");

    public static void main( String[] args )
    {
        startConsoleReport();
        startCSVReport();

        while(true)
        {
            searchFlight();
            creatOrder();
        }
    }

    static void searchFlight()
    {
        StopWatch stopwatch = StopWatch.createStarted();
        // 模拟关于航班查询的业务逻辑代码
        try {
            Random random = new Random(System.currentTimeMillis() & 0x0000FFFF);
            long n = random.nextInt(1000);
            Thread.sleep(n);
        }
        catch(InterruptedException e)
        {
        }
        stopwatch.stop();

        // 统计航班搜索耗时
        searchFlightTime.update(stopwatch.getTime(TimeUnit.MILLISECONDS));
    }

    static void creatOrder()
    {
        long n = 0;

        try
        {
            // 模拟关于下单的业务逻辑代码
            try {
                Random random = new Random(System.currentTimeMillis() & 0x0000FFFF);
                n = random.nextInt(1000);
                Thread.sleep(n);
            }
            catch(InterruptedException e)
            {
            }
            if (n % 7 == 0)
            {
                throw new Exception();
            }

            // 统计成功下单量
            Meter orderCount = metrics.meter("MetricsDemo.OrderCount");
            orderCount.mark();

            // 统计下单金额
            if (n % 2 == 1)
            {
                Meter orderMoneyCount = metrics.meter("MetricsDemo.BuyerA.OrderMoneyCount");
                orderMoneyCount.mark(n);
            }
            else
            {
                Meter orderMoneyCount = metrics.meter("MetricsDemo.BuyerB.OrderMoneyCount");
                orderMoneyCount.mark(n);
            }
        }
        catch (Exception e)
        {
            // 统计失败下单量
            Meter orderErrorCount = metrics.meter("MetricsDemo.OrderErrorCount");
            orderErrorCount.mark();
        }
    }

    static void startConsoleReport() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    static void startCSVReport() {
        CsvReporter reporter = CsvReporter.forRegistry(metrics)
                .formatFor(Locale.US)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build(new File("D:/data/"));
        reporter.start(1, TimeUnit.SECONDS);
    }
}
