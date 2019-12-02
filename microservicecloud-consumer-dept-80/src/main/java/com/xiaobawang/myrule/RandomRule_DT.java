package com.xiaobawang.myrule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;

public class RandomRule_DT extends AbstractLoadBalancerRule {

    private int currentIndex = 0;  //当前提供服务的机器
    private int total = 0; //被调用的总次数

    public Server choose(ILoadBalancer ilb, Object key){
        if (ilb == null){
            return null;
        }
        Server server = null;
        while (server == null){
            if (Thread.interrupted()){
                return null;
            }
            List<Server> upList = ilb.getReachableServers();
            List<Server> allServers = ilb.getAllServers();

            if (allServers.size() == 0) {
                return null;
            }

            //被调用次数小于预定值
            if (total < 5){
                server = upList.get(currentIndex);
                total ++;
            }else {
                total = 0;
                currentIndex ++;
                if (currentIndex >= upList.size()){
                    currentIndex = 0;
                }
            }
            //如果获取的server为null，中断线程
            if (server == null){
                Thread.yield();
                continue;
            }

            if (server.isAlive()){
                return server;
            }

            server = null;
            Thread.yield();
        }

        return server;

    }

    /**
     * 返回给哪个服务器
     * @param key
     * @return
     */
    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(),key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

}
