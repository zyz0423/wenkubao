package com.wenkubao.service;

import com.wenkubao.dao.UserecordMapper;
import com.wenkubao.entity.Userecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserrecordServiceImpl implements UserrecordService {

    @Autowired
    private UserecordMapper userecordMapper;

    @Override
    public void insertRecord(Userecord userecord) {
        userecordMapper.insert(userecord);
    }

    @Override
    public int getUseCount(String ip, String usedate) {
        return userecordMapper.getUseCountByIp(ip,usedate);
    }
}
