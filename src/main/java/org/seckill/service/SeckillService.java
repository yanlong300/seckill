package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在使用者的角度设计接口
 * 方法定义粒度，参数，返回类型 return类型友好/异常
 *
 */
public interface SeckillService {

    List<Seckill> getSeckillList();

    /**
     * 查询秒杀商品
     * @param seckill_id 商品ID
     * @return
     */
    Seckill getById(Long seckill_id);

    /**
     * 秒杀开启是输出秒杀接口地址
     * 否则输出系统时间和秒杀时间
     * @param seckillId 秒杀商品
     */
    Exposer exportSeckillUrl(Long seckillId);

    /**
     * 执行秒杀操作（spring 事务）
     * @param seckillId 商品ID
     * @param userPhone 用户电话号码
     * @param md5 验证
     */
    SeckillExecution executeSeckill(Long seckillId, Long userPhone, String md5)throws SeckillException,RepeatKillException,SeckillCloseException;

    /**
     * 执行秒杀操作（存储过程）
     * @param seckillId 商品ID
     * @param userPhone 用户电话号码
     * @param md5 验证
     */
    SeckillExecution executeSeckillProducer(Long seckillId, Long userPhone, String md5);
}

