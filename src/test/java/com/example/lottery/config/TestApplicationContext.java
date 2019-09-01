package com.example.lottery.config;

import com.example.lottery.LotteryApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Import(LotteryApplication.class)
@Profile("test")
public class TestApplicationContext {

}
