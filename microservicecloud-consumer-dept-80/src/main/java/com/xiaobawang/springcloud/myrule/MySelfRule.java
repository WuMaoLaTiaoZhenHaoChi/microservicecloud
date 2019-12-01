package com.xiaobawang.springcloud.myrule;


import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySelfRule {

    @Bean
    public IRule MyRule(){

        //return new RandomRule();//随机
        //return new RoundRobinRule();
        return new RandomRule_DT(); //自定义负载均衡
    }

}
