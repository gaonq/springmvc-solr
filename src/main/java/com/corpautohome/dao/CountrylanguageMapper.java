package com.corpautohome.dao;

import com.corpautohome.entity.Countrylanguage;
import com.corpautohome.entity.CountrylanguageExample;
import com.corpautohome.entity.CountrylanguageKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CountrylanguageMapper {
    int countByExample(CountrylanguageExample example);

    int deleteByExample(CountrylanguageExample example);

    int deleteByPrimaryKey(CountrylanguageKey key);

    int insert(Countrylanguage record);

    int insertSelective(Countrylanguage record);

    List<Countrylanguage> selectByExample(CountrylanguageExample example);

    Countrylanguage selectByPrimaryKey(CountrylanguageKey key);

    int updateByExampleSelective(@Param("record") Countrylanguage record, @Param("example") CountrylanguageExample example);

    int updateByExample(@Param("record") Countrylanguage record, @Param("example") CountrylanguageExample example);

    int updateByPrimaryKeySelective(Countrylanguage record);

    int updateByPrimaryKey(Countrylanguage record);
}