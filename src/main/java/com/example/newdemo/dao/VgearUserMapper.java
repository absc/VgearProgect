package com.example.newdemo.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface VgearUserMapper {
    @Select("SELECT * FROM vgearuser WHERE wxopenid = #{wxopenid}")
    VgearUser findUserByToken(@Param("wxopenid") String wxopenid);

    @Select("SELECT * FROM vgearuser WHERE id = #{id}")
    VgearUser findUserById(@Param("id") String id);

    @Insert("insert into vgearuser(wxopenid, wxnickname, wxsex, wxprovince, wxcity, wxcountry, wxheadimgurl,phonenum ) VALUES(#{wxopenid}, #{wxnickname}, #{wxsex},#{wxprovince},#{wxcity},#{wxcountry},#{wxheadimgurl},#{phonenum})")
    int insert(   @Param("wxopenid") String wxopenid, @Param("wxnickname") String wxnickname,@Param("wxsex") Integer wxsex,@Param("wxprovince") String wxprovince
               ,@Param("wxcity") String wxcity,@Param("wxcountry") String wxcountry, @Param("wxheadimgurl") String wxheadimgurl
               ,@Param("phonenum") Integer phonenum);



    @Update("UPDATE users SET userName=#{userName},nick_name=#{nickName} WHERE id =#{id}")
    void update(VgearUser user);

}
