package com.xiaobawang.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xiaobawang.springcloud.entities.Dept;
import com.xiaobawang.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class DeptController
{
    @Autowired
    private DeptService service = null;

    @RequestMapping(value = "/dept/add", method = RequestMethod.POST)
    public boolean add(@RequestBody Dept dept)
    {
        return service.add(dept);
    }

    @HystrixCommand(fallbackMethod = "processHystrix_Get") //一旦调用服务器方法失败并抛出错误信息后，会自动调用其中声明的方法
    @RequestMapping(value = "/dept/get/{id}", method = RequestMethod.GET)
    public Dept get(@PathVariable("id") Long id)
    {
        Dept dept = this.service.get(id);
        if (dept == null){
            throw new RuntimeException(" 该ID " + id + " 没有对应的信息 ");
        }
        return dept;
    }

    public Dept processHystrix_Get(@PathVariable("id") Long id){
        return new Dept().setDeptno(id).setDname(" 该ID " + id + " 没有对应的信息,null -- @HystrixCommand ")
                .setDb_source("no this database in MySql");

    }

    @RequestMapping(value = "/dept/list", method = RequestMethod.GET)
    public List<Dept> list()
    {
        return service.list();
    }

}
