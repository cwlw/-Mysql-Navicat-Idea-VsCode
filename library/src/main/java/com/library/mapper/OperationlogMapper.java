package com.library.mapper;
import com.library.entity.Operationlog;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface OperationlogMapper {
    @Select("select * from operationlog") List<Operationlog> listAll();
    @Select("select * from operationlog where id=#{id}") Operationlog getById(Integer id);
    @Insert("insert into operationlog(adminId,operateTime,operateType,content,ip) values(#{adminId},#{operateTime},#{operateType},#{content},#{ip})")
    int insert(Operationlog o);
    @Update("update operationlog set adminId=#{adminId},operateTime=#{operateTime},operateType=#{operateType},content=#{content},ip=#{ip} where id=#{id}")
    int update(Operationlog o);
    @Delete("delete from operationlog where id=#{id}") int delete(Integer id);
}