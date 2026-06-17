package com.library.mapper;

import com.library.entity.Cardrec;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface CardrecMapper {

    @Select("select * from cardrec")
    List<Cardrec> listAll();

    @Select("select * from cardrec where id = #{id}")
    Cardrec getById(Long id);

    @Insert("INSERT INTO cardrec(serNum,sno,originCardNo,newCardNo,opType,opTime) " +
            "VALUES(#{serNum},#{sno},#{originCardNo},#{newCardNo},#{opType},#{opTime})")
    int insert(Cardrec rec);
}