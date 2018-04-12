package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

public interface SuccessKilledDAO {
    /**
     * 插入购买明细 可过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId") Long seckillId,@Param("userPhone") Long userPhone);

    /**
     * 查询秒杀详情
     * @param seckillId 商品号
     * @return
     */
    SuccessKilled querybyIdwidthSeckill(@Param("seckillId") Long seckillId,@Param("userPhone") Long userPhone);

}
