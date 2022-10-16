package com.example.quartztest.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface JobDao {

    @Select("select API from TESTAPI where ID=#{id}")
    String getApi(long id);
}
