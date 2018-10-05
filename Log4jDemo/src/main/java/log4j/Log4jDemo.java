package log4j;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

public class Log4jDemo {
    private static final Logger logger = LogManager.getLogger("RollingRandomAccessFileLogger");

    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/log4j2.xml");
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        final ConfigurationSource source = new ConfigurationSource(in);
        Configurator.initialize(null, source);

        for(int i = 0; i < 50000; i++) {
            logger.trace("trace日志");
            logger.debug("调试信息的日志");
            logger.info("一般信息的日志");
            logger.warn("警告信息的日志");
            logger.error("错误信息的日志", new Exception("发生了一个异常"));
            logger.fatal("致命错误的日志", new Exception("发生了一个致命错误"));
        }

        try {
            Thread.sleep(1000 * 61);
        } catch (InterruptedException e) {}

        logger.trace("trace日志");
        logger.debug("调试信息的日志");
        logger.info("一般信息的日志");
        logger.warn("警告信息的日志");
        logger.error("错误信息的日志", new Exception("发生了一个异常"));
        logger.fatal("致命错误的日志", new Exception("发生了一个致命错误"));

        System.out.println("日志记录完毕。");
    }
}
