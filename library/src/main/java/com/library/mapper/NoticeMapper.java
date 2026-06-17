package com.library.mapper;
import com.library.entity.Notice;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface NoticeMapper {
    @Select("select * from notice") List<Notice> listAll();
    @Select("select * from notice where id=#{id}") Notice getById(Integer id);
    @Insert("insert into notice(topic,content,author,createDate) values(#{topic},#{content},#{author},#{createDate})")
    int insert(Notice n);
    @Update("update notice set topic=#{topic},content=#{content},author=#{author},createDate=#{createDate} where id=#{id}")
    int update(Notice n);
    @Delete("delete from notice where id=#{id}") int delete(Integer id);
}