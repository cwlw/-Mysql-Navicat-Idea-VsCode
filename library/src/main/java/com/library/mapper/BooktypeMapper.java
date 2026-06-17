package com.library.mapper;
import com.library.entity.Booktype;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface BooktypeMapper {
    @Select("select * from booktype") List<Booktype> listAll();
    @Select("select * from booktype where id=#{id}") Booktype getById(Integer id);
    @Insert("insert into booktype(typeName,intro) values(#{typeName},#{intro})") int insert(Booktype t);
    @Update("update booktype set typeName=#{typeName},intro=#{intro} where id=#{id}") int update(Booktype t);
    @Delete("delete from booktype where id=#{id}") int delete(Integer id);
}