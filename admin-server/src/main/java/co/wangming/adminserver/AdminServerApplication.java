package co.wangming.adminserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdminServerApplication {

    private static final Logger logger = LoggerFactory.getLogger(AdminServerApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(AdminServerApplication.class, args);

        logger.info("服务器启动成功");
    }

}
