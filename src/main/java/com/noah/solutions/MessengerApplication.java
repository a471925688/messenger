package com.noah.solutions;

import com.noah.solutions.common.ProjectCache;
import com.noah.solutions.system.service.TransferStationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.annotation.Resource;

@SpringBootApplication
public class MessengerApplication  extends SpringBootServletInitializer implements CommandLineRunner {
    @Resource
    private TransferStationService transferStationService;
    public static void main(String[] args) {
        SpringApplication.run(MessengerApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        ProjectCache.setTransferStation(transferStationService.getInfo());//珠海中轉站地址緩存

    }
}
