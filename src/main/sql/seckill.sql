-- 秒杀执行存储过程

DELIMITER $$ -- console;转换为$$

-- 定义存储过程
-- 参数 in 输入参数 out输出参数
-- row_count() 返回上一条修改sql影响行数
-- row_count() 0 影响未修改 >0修改行数 <0表示为执行修改sql
CREATE PROCEDURE `seckill`.`execute_seckill`
  (in v_seckill_id BIGINT,in v_seckill_phone BIGINT,
    in v_seckill_time TIMESTAMP ,out r_result int)
  BEGIN
    DECLARE insert_count int DEFAULT 0;
    START TRANSACTION ;
    INSERT IGNORE INTO success_killed
      (seckill_id, user_phone,create_time)
    VALUES
      (v_seckill_id,v_seckill_phone,v_seckill_time);
    SELECT row_count() INTO insert_count;
    IF (insert_count = 0)THEN
      ROLLBACK;
      SET r_result = -1;
    ELSEIF (insert_count < 0) THEN
      ROLLBACK ;
      SET r_result = -2;
    ELSE
      UPDATE seckill SET
        number = number -1
      WHERE seckill_id = v_seckill_id
      AND  end_time > v_seckill_time
      and start_time < v_seckill_time
      and number > 0;
      SELECT row_count() INTO insert_count;
      IF (insert_count = 0) THEN
        ROLLBACK ;
        SET r_result = 0;
      ELSEIF (insert_count < 0)THEN
        ROLLBACK ;
        SET r_result = -2;
      ELSE
        COMMIT ;
        SET r_result = 1;
      END IF ;
    END IF;

END;
$$

DELIMITER ;
SET @r_result = -3;
SELECT @r_result;
CALL execute_seckill(1003,12999999999,now(),@r_result);
SELECT @r_result;




