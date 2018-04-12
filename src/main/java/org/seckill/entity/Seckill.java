package org.seckill.entity;

import java.util.Date;
import java.util.Objects;

public class Seckill {

    private long seckillId;

    private String  name;

    private int number;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Seckill{" +
                "seckillId=" + seckillId +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Seckill seckill = (Seckill) o;
        return seckillId == seckill.seckillId &&
                number == seckill.number &&
                Objects.equals(name, seckill.name) &&
                Objects.equals(startTime, seckill.startTime) &&
                Objects.equals(endTime, seckill.endTime) &&
                Objects.equals(createTime, seckill.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(seckillId, name, number, startTime, endTime, createTime);
    }
}
