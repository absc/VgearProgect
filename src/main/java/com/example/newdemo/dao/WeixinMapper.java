package com.example.newdemo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface WeixinMapper {
    @Select("SELECT * FROM wxid WHERE id = #{id}")
    Weixin findUserByToken(@Param("id") int id);


}
