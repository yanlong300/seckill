package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDAOTest {
    @Resource
    private SuccessKilledDAO successKilledDAO;

    @Test
    public void insertSuccessKilled() {
        Long id = 1001L;
        Long phone = 13999999999L;
        int insertCount = successKilledDAO.insertSuccessKilled(id, phone);
        System.out.println(insertCount);
    }

    @Test
    public void querybyIdwidthSeckill() {
        Long id = 1001L;
        Long phone = 13999999999L;
        SuccessKilled successKilled = successKilledDAO.querybyIdwidthSeckill(id, phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}