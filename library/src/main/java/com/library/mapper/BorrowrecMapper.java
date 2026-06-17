package com.library.mapper;
import com.library.entity.Borrowrec;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface BorrowrecMapper {
    @Select("select * from borrowrec") List<Borrowrec> listAll();
    @Select("select * from borrowrec where id=#{id}") Borrowrec getById(Integer id);
    @Insert("insert into borrowrec(serNum,sno,barCode,borDate,retDate,realRetDate,retStatus,oddDays,fineMoney,fineStatus,paySerNum) values(#{serNum},#{sno},#{barCode},#{borDate},#{retDate},#{realRetDate},#{retStatus},#{oddDays},#{fineMoney},#{fineStatus},#{paySerNum})")
    int insert(Borrowrec b);
    @Update("update borrowrec set serNum=#{serNum},sno=#{sno},barCode=#{barCode},borDate=#{borDate},retDate=#{retDate},realRetDate=#{realRetDate},retStatus=#{retStatus},oddDays=#{oddDays},fineMoney=#{fineMoney},fineStatus=#{fineStatus},paySerNum=#{paySerNum} where id=#{id}")
    int update(Borrowrec b);
    @Delete("delete from borrowrec where id=#{id}") int delete(Integer id);
}