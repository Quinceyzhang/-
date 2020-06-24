package com.luckyframe;

import java.net.InetSocketAddress;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.luckyframe.common.netty.NettyServer;

/**
 * @author lyb
 * @date 2020年6月24日
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class,FlywayAutoConfiguration.class })
@MapperScan("com.luckyframe.project.*.*.mapper")
public class LuckyFrameWebApplication  implements CommandLineRunner
{

    @Value("${netty.port}")
    private int port;

    @Value("${netty.url}")
    private String url;

    @Autowired
    private NettyServer server;

    @Override
    public void run(String... args) throws Exception {
        InetSocketAddress address = new InetSocketAddress(url,port);
        System.out.println("服务端启动成功："+url+":"+port);
        server.start(address);
    }
    
    public static void main(String[] args)
    {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(LuckyFrameWebApplication.class, args);
        System.out.println("项目启动成功......");
    }

}