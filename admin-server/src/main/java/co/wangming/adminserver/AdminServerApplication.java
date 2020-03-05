package co.wangming.adminserver;

import co.wangming.adminserver.config.HttpTraceLogFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 程序启动入口
 *
 * @ServletComponentScan 使用该注解是为了扫描到自定义的Filter 例如({@link HttpTraceLogFilter})
 * <p>
 * Created By WangMing On 2020-03-02
 */
@ServletComponentScan("co.wangming.adminserver")
@SpringBootApplication
public class AdminServerApplication {

    private static final Logger logger = LoggerFactory.getLogger(AdminServerApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(AdminServerApplication.class, args);

        logger.info("服务器启动成功");
    }

}
