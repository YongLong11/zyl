package com.ziroom.zyl.initLoad.commandLineRunner;

import org.springframework.boot.CommandLineRunner;

public class InitRunner implements CommandLineRunner {

    public void run(String... args) throws Exception{
        System.out.println("initRunner加载了");
    }
}
