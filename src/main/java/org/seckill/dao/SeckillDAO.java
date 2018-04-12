package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SeckillDAO {
    /**
     * 减库存
     * @param seckillId 减库存id
     * @param killTime 减库存时间
     * @return
     */
    int reduceNumber(@Param("seckillId") Long seckillId,@Param("killTime") Date killTime);

    /**
     * 依旧商品id查询秒杀商品
     * @param seckillId 商品id
     * @return
     */
    Seckill queryById(Long seckillId);

    /**
     * 依据偏移量查询秒杀商品
     * 利用注解保留形参
     * @param offset 第几页
     * @param limit 几条
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);
    /**
     * 存储过程秒杀
     */
    void killByProcedure(Map<String,Object> pareMap);

}
