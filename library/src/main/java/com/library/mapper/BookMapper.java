package com.library.mapper;
import com.library.entity.Book;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface BookMapper {
    @Select("select * from book") List<Book> listAll();
    @Select("select * from book where id=#{id}") Book getById(Integer id);
    @Insert("insert into book(ISBN,bname,author,publisher,introduction,pubDate,clcNum,bookStatus) values(#{ISBN},#{bname},#{author},#{publisher},#{introduction},#{pubDate},#{clcNum},#{bookStatus})")
    int insert(Book b);
    @Update("update book set ISBN=#{ISBN},bname=#{bname},author=#{author},publisher=#{publisher},introduction=#{introduction},pubDate=#{pubDate},clcNum=#{clcNum},bookStatus=#{bookStatus} where id=#{id}")
    int update(Book b);
    @Delete("delete from book where id=#{id}") int delete(Integer id);
}