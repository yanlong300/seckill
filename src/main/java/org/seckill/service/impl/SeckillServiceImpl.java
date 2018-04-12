package org.seckill.service.impl;

import org.apache.commons.collections.MapUtils;
import org.seckill.dao.SeckillDAO;
import org.seckill.dao.SuccessKilledDAO;
import org.seckill.dao.cache.RedisDAO;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDAO seckillDAO;
    @Autowired
    private SuccessKilledDAO successKilledDAO;
    @Autowired
    private RedisDAO redisDAO;

    /**
     * 用于混淆md5
     */
    private final String salt = "AWEjdajowhjWjd9008312jO)(*#@&95-0348534@#";


    @Override
    public List<Seckill> getSeckillList() {
        return seckillDAO.queryAll(0,4);
    }

    @Override
    public Seckill getById(Long seckillId) {
        return seckillDAO.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(Long seckillId) {
        //优化秒杀接口暴露
        //1.访问redis
        Seckill seckill = redisDAO.getSecKill(seckillId);
        if(seckill == null){
            seckill = seckillDAO.queryById(seckillId);
            if(seckill == null){
                return new Exposer(false,seckillId);
            }else {
                redisDAO.putSeckill(seckill);
            }

        }

        long startTime = seckill.getStartTime().getTime();
        long endTime = seckill.getEndTime().getTime();
        long nowTime = System.currentTimeMillis();
        if(nowTime < startTime || nowTime > endTime){
            return new Exposer(false,seckillId,nowTime,startTime,endTime);
        }else {
            String md5 = getMD5(seckillId);
            return new Exposer(true,md5,seckillId);
        }
    }

    /**
     * 1.开发团队达成一致约定
     * 2.保证事务方法执行时间尽可能短 不要穿插其他网络操作 RPC/HTTP请求
     * 3.不是所有的方法都需要事务
     * @param seckillId 商品ID
     * @param userPhone 用户电话号码
     * @param md5 验证
     * @return 秒杀结果
     * @throws SeckillException 秒杀异常
     * @throws RepeatKillException 重复秒杀异常
     * @throws SeckillCloseException 秒杀关闭异常
     */
    @Override
    @Transactional
    public SeckillExecution executeSeckill(Long seckillId, Long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        try {
             if(md5 == null || !md5.equals(getMD5(seckillId))){
                throw new SeckillException("seckill date Rewrite");
            }

            //记录购买行为
            /**
             * 先插入后更新可以减少部分GC与网络延迟。
             */
            int insertCount = successKilledDAO.insertSuccessKilled(seckillId, userPhone);
            if(insertCount <= 0){
                throw new RepeatKillException("seckill repeated");
            }else{
                int updateCount = seckillDAO.reduceNumber(seckillId,new Date());
                if(updateCount <= 0){
                    //没有更新记录
                    throw new SeckillCloseException("seckill is closed");
                }else{
                    SuccessKilled successKilled = successKilledDAO.querybyIdwidthSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS,successKilled);
                }
            }



        }catch (SeckillCloseException | RepeatKillException e1){
            throw e1;
        } catch(Exception e){
            //将所谓异常转换为运行期异常，一旦有问题立即回滚
            logger.error(e.getMessage(),e);
            throw new SeckillException("seckill inner error:"+e.getMessage());
        }
    }

    @Override
    public SeckillExecution executeSeckillProducer(Long seckillId, Long userPhone, String md5) {
        if(md5 == null || !md5.equals(getMD5(seckillId))){
            return new SeckillExecution(seckillId, SeckillStatEnum.DATA_REWRITE);
        }
        Date killTime = new Date();
        Map<String,Object > map = new HashMap<>(4);
        map.put("seckillId",seckillId);
        map.put("phone",userPhone);
        map.put("killTime",killTime);
        map.put("result",null);
        try {
            seckillDAO.killByProcedure(map);
            int result = MapUtils.getInteger(map,"result",-2);
            if(result == 1){
                SuccessKilled sk = successKilledDAO.querybyIdwidthSeckill(seckillId, userPhone);
                return new SeckillExecution(seckillId,SeckillStatEnum.SUCCESS,sk);
            }else{
                return new SeckillExecution(seckillId,SeckillStatEnum.stateOf(result));
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new SeckillExecution(seckillId,SeckillStatEnum.INNER_ERROR);
        }
    }

    private String getMD5(Long seckillId){
        String base = seckillId +"/"+salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
