package com.library.mapper;

import com.library.entity.Libcard;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

public interface LibcardMapper {

    @Select("select * from libcard")
    List<Libcard> listAll();

    // 原getById改名为getByCardNo，适配借书校验逻辑
    @Select("select * from libcard where cardNo = #{cardNo}")
    Libcard getByCardNo(@Param("cardNo") String cardNo);

    @Insert("INSERT INTO libcard(cardNo,sno,sname,type,collage,major,birth,originPlace,cardStatus,times) " +
            "VALUES(#{cardNo},#{sno},#{sname},#{type},#{collage},#{major},#{birth},#{originPlace},#{cardStatus},#{times})")
    int insert(Libcard libcard);

    @Update("UPDATE libcard SET cardNo=#{cardNo},sno=#{sno},sname=#{sname},type=#{type},collage=#{collage},major=#{major},birth=#{birth},originPlace=#{originPlace},cardStatus=#{cardStatus},times=#{times} WHERE cardNo=#{cardNo}")
    int update(Libcard libcard);

    @Select("delete from libcard where cardNo = #{cardNo}")
    int delete(@Param("cardNo") String cardNo);

    @Select("select * from libcard where sno = #{sno}")
    Libcard getBySno(@Param("sno") String sno);

    @Select("select MAX(cardNo) from libcard")
    String selectMaxCardNo();
}