package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 配置spring 和junit整合 junit启动时加载IOC容器
 * spring-test junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDAOTest {
    @Resource
    private SeckillDAO seckillDAO;

    /**
     * 1000元秒IPhone
     * Seckill{seckillId=1000, name='1000元秒IPhone', number=100, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Sun Nov 01 00:00:00 CST 2015, createTime=Mon Apr 09 10:41:22 CST 2018}
     */
    @Test
    public void queryById() {
        Long id = 1000L;
        Seckill seckill = seckillDAO.queryById(id);
        System.out.print(seckill.getName());
        System.out.print(seckill.toString());

    }

    /**
     * 1000元秒IPhone
     * 500元秒IPad2
     * 300元秒小米4
     * 200元秒红米note
     */
    @Test
    public void queryAll() {
        List<Seckill> seckills = seckillDAO.queryAll(0,100);
        for(Seckill seckill:seckills){
            System.out.println(seckill.getName());
        }

    }

    @Test
    public void reduceNumber() {
        Date date = new Date();
        int res = seckillDAO.reduceNumber(1000L, date);
        System.out.println(res);
    }
}