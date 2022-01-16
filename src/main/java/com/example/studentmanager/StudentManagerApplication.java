package com.example.studentmanager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;


@SpringBootApplication

public class StudentManagerApplication  {


    public static void main(String[] args) {
       SpringApplication.run(StudentManagerApplication.class, args);
    }


//    @Scheduled(fixedRate = 1000L)
//    void someJob() throws InterruptedException{
//        System.out.println("thực hiện trong thời gian nhất định " + new Date());
//    }
//
//    @Scheduled(cron = "15 * * * * ?")
//    public void scheduleTaskWithCronExpression() {
//        System.out.println("Sử dụng cron với thời gian vào giây thứ 15 của mỗi phút");
//    }
//
//    @Scheduled(fixedRate = 2000, initialDelay = 20000)
//    public void scheduleTaskWithInitialDelay() {
//        System.out.println("thực hiện thông qua khoảng thời gian " + new Date());
//    }

}

