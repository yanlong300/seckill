package org.seckill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDAO;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDAOTest {
    private Long id =1001L;
    @Autowired
    private RedisDAO redisDAO;
    @Autowired
    private SeckillDAO seckillDAO;

    @Test
    public void testSecKill() {

        Seckill seckill = redisDAO.getSecKill(id);
        if(seckill == null){
            seckill = seckillDAO.queryById(id);
            if(seckill != null){
                String res = redisDAO.putSeckill(seckill);
                System.out.println("res="+res);
                seckill = redisDAO.getSecKill(id);
                System.out.println(seckill);
            }

        }
    }

}