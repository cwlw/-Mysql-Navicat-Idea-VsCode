package com.library.mapper;
import com.library.dto.BookDTO;
import com.library.entity.Book;
import com.library.vo.BookQueryVO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import com.github.pagehelper.Page;
import java.util.List;

public interface BookMapper {
    @Select("select * from book")
    List<Book> listAll();

    @Select("select * from book where id=#{id}")
    Book getById(Integer id);

    @Insert("insert into book(ISBN,bname,author,publisher,introduction,pubDate,clcNum,bookStatus) values(#{ISBN},#{bname},#{author},#{publisher},#{introduction},#{pubDate},#{clcNum},#{bookStatus})")
    int insert(Book b);

    @Update("update book set ISBN=#{ISBN},bname=#{bname},author=#{author},publisher=#{publisher},introduction=#{introduction},pubDate=#{pubDate},clcNum=#{clcNum},bookStatus=#{bookStatus} where id=#{id}")
    int update(Book b);

    @Delete("delete from book where id=#{id}")
    int delete(Integer id);

    @Select("select * from book where ISBN=#{ISBN}")
    Book getByIsbn(String ISBN);

    // 采编分页查询（无@Param注解，和xml匹配）
    Page<BookDTO> searchBookPage(BookQueryVO vo);

    @Update("update book set bookStatus = #{status} where id = #{bookId}")
    int updateBookStatus(Integer bookId, Integer status);
}