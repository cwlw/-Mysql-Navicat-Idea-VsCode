package com.library.mapper;

import com.library.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {

    @Select("SELECT * FROM admin WHERE username = #{username} AND password = #{password}")
    Admin login(@Param("username") String username,
                @Param("password") String password);

}