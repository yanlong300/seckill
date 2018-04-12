-- V1.0版本
-- 创建数据库
CREATE DATABASE seckill;

-- 使用数据库
USE  seckill;

-- 创建表

CREATE TABLE seckill(
  `seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存Id',
  `name` varchar(120) NOT NULL COMMENT '商品名称',
  `number` INT NOT NULL COMMENT '库存数量',
  `start_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`),
  KEY idx_start_time(`start_time`),
  KEY idx_end_time(`end_time`),
  KEY idx_create_time(`create_time`)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';
-- 添加数据

INSERT INTO
  seckill(name,number,start_time,end_time)
VALUES
  ('1000元秒IPhone',100,'2015-11-01 00:00:00','2015-11-01 00:00:00'),
  ('500元秒IPad2',200,'2015-11-01 00:00:00','2015-11-01 00:00:00'),
  ('300元秒小米4',300,'2015-11-01 00:00:00','2015-11-01 00:00:00'),
  ('200元秒红米note',400,'2015-11-01 00:00:00','2015-11-01 00:00:00');


-- 秒杀详情表

CREATE TABLE success_killed(
  `seckill_id` BIGINT NOT NULL  COMMENT '秒杀商品id',
  `user_phone` BIGINT NOT NULL COMMENT '秒杀用户手机号',
  `state` TINYINT NOT NULL  DEFAULT  -1 COMMENT '状态提示 ：-1 无效 0成功 1已付款',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`,`user_phone`),
  KEY idx_create_time(`create_time`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='秒杀明细表';

