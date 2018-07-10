package com.wenkubao.service;

import com.wenkubao.entity.Userecord;

public interface UserrecordService {

    void insertRecord(Userecord userecord);

    int getUseCount(String ip,String usedate);

}
