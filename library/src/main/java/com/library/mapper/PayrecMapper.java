package com.library.mapper;
import com.library.entity.Payrec;
import com.library.vo.FineVo;
import org.apache.ibatis.annotations.*;
import java.util.List;
@Mapper
public interface PayrecMapper {
    @Insert("insert into payrec(serNum,sno,payAmount,payDate) values(#{serNum},#{sno},#{payAmount},#{payDate})")
    int insert(Payrec payrec);

    @Select("SELECT * FROM payrec WHERE id = #{id}")
    Payrec selectById(@Param("id") Integer id);

    @Update("UPDATE payrec SET serNum=#{serNum},sno=#{sno},payAmount=#{payAmount},payDate=#{payDate} WHERE id=#{id}")
    int update(Payrec payrec);

    @Select("""
        SELECT pr.serNum, pr.payAmount fine, pr.payDate, b.bname name
        FROM payrec pr
        LEFT JOIN borrowrec b ON pr.serNum = b.paySerNum
        WHERE pr.sno = #{sno}
        ORDER BY pr.payDate DESC
        LIMIT #{offset},#{size}
        """)
    List<FineVo> selectPayPage(@Param("sno") String sno, @Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM payrec WHERE sno = #{sno}")
    int countPayBySno(@Param("sno") String sno);
}