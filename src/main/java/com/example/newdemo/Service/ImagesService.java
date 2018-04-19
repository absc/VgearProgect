package com.example.newdemo.Service;

import com.example.newdemo.bean.Images;
import com.example.newdemo.bean.ReciveMessage;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface ImagesService {
    @Insert("insert into images(uid, path, time) VALUES(#{uid}, #{path},#{time})")
    int insert( @Param("uid") int uid, @Param("path") String path, @Param("time") String time);


    @Delete("delete from images where uid =#{uid}")
    void delete(@Param("uid") String uid);

    @Select("SELECT * FROM images where uid=#{uid}")
    List<Images> select(@Param("uid") String uid);
}
