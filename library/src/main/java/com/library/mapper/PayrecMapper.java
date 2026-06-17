package com.library.mapper;
import com.library.entity.Payrec;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface PayrecMapper {
    @Select("select * from payrec") List<Payrec> listAll();
    @Select("select * from payrec where id=#{id}") Payrec getById(Integer id);
    @Insert("insert into payrec(serNum,sno,payAmount,payDate) values(#{serNum},#{sno},#{payAmount},#{payDate})")
    int insert(Payrec p);
    @Update("update payrec set serNum=#{serNum},sno=#{sno},payAmount=#{payAmount},payDate=#{payDate} where id=#{id}")
    int update(Payrec p);
    @Delete("delete from payrec where id=#{id}") int delete(Integer id);
}