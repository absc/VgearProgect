package com.example.newdemo.dao;


import com.example.newdemo.bean.ReciveMessage;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ReciveMessageMapper {

    @Select("SELECT * FROM chatrecord")
    List<ReciveMessage> seletAll();

    @Insert("insert into chatrecord(type, message, time, nickname, headpic, imageOne, money) VALUES(#{type}, #{message}, #{time},#{nickname},#{headpic},#{imageone},#{money})")
    int insert(@Param("type") int type, @Param("message") String message,@Param("time") String time,@Param("nickname") String nickname
            ,@Param("headpic") String headpic,@Param("imageone") String imageone,@Param("money") String money);

    @Delete("delete from chatrecord where 1 order by id limit 1")
    void delete();
}
