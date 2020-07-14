package com.springboot.chapter15.dao;

import org.apache.ibatis.annotations.Mapper;

import com.springboot.chapter15.pojo.PurchaseRecordPo;

/**** imports ****/
@Mapper
public interface PurchaseRecordDao {
    public int insertPurchaseRecord(PurchaseRecordPo pr);
}