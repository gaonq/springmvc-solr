package com.corpautohome.service.impl;

import com.corpautohome.dao.CityMapper;
import com.corpautohome.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Created by gaonq on 2017/3/3.
 */
@Service("cityService")
public class CityServiceImpl implements ICityService{
    @Autowired
    private CityMapper cityDao;
    @Override
    public Integer getList() throws Exception {
        return cityDao.selectAll().size();
    }
}
