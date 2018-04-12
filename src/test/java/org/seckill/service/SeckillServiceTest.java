package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<Seckill> seckills = seckillService.getSeckillList();
        logger.info("list={}", seckills);
    }


    @Test
    public void getById() {
        Long id = 1000L;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}", seckill);
    }

    @Test
    public void exportSeckillUrl() {
        Long id = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}", exposer);
        /**
         * exposer=Exposer{
         * exposed=true,
         * md5='d7618a0c5aa11bd33866d610f4726c8a',
         * seckillId=1000,
         * now=0, start=0, end=0}
         */
    }

    @Test
    public void executeSeckill() {
        try {
            Long id = 1000L;
            Long phone = 13999999992L;
            String md5 = "d7618a0c5aa11bd33866d610f4726c8a";
            SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
            logger.info("seckillExcution={}", seckillExecution);
        } catch (SeckillCloseException e) {
            logger.error(e.getMessage());
        } catch (RepeatKillException e) {
            logger.error(e.getMessage());
        }

        //seckillExcution=SeckillExcution{seckillId=1000, state=1, stateInfo='秒杀成功', successKilled=SuccessKilled{seckillId=1000, userPhone=13999999992, state=1, createTime=Tue Apr 10 10:19:05 CST 2018, seckill=Seckill{seckillId=1000, name='1000元秒IPhone', number=98, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Thu Nov 01 00:00:00 CST 2018, createTime=Mon Apr 09 10:41:22 CST 2018}}}
    }

    @Test
    public void seckillLogic() {
        Long id = 1001L;
        Long phone = 13999999993L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.getExposed()) {
            String md5 = exposer.getMd5();
            logger.info("exposer={}", exposer);
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
                logger.info("seckillExecution={}", seckillExecution);
            } catch (SeckillCloseException e) {
                logger.error(e.getMessage());
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.warn("exposer={}", exposer);
        }
    }

    @Test
    public void executeSeckillProcedure(){
        Long id = 1001L;
        Long phone = 13999999992L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if(exposer.getExposed()){
            String md5 = exposer.getMd5();
            SeckillExecution seckillExecution = seckillService.executeSeckillProducer(exposer.getSeckillId(), phone, md5);
            logger.info(seckillExecution.getStateInfo());
        }else {
            logger.info("exposer="+exposer);
        }

    }
}