package com.library.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.library.dto.BookCopyDTO;
import com.library.dto.BookDTO;
import com.library.entity.Book;
import com.library.mapper.BookMapper;
import com.library.mapper.BookcopyMapper;
import com.library.vo.BookQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookcopyMapper bookcopyMapper;

    // 原有基础方法
    public List<Book> listAll() {
        return bookMapper.listAll();
    }

    public Book getById(Integer id) {
        return bookMapper.getById(id);
    }

    public boolean add(Book b) {
        // 新增图书默认状态1：可借（数据库规范1=可借）
        if (b.getBookStatus() == null) {
            b.setBookStatus(1);
        }
        return bookMapper.insert(b) > 0;
    }

    public boolean update(Book b) {
        return bookMapper.update(b) > 0;
    }

    public boolean delete(Integer id) {
        return bookMapper.delete(id) > 0;
    }

    public Book getByIsbn(String isbn) {
        return bookMapper.getByIsbn(isbn);
    }

    // ===================== 采编新书入库新增业务方法 =====================
    /**
     * 分页模糊查询图书列表
     */
    public Page<BookDTO> pageSearch(BookQueryVO vo) {
        // 分页参数校验
        Integer pageNum = vo.getPageNum() == null ? 1 : vo.getPageNum();
        Integer pageSize = vo.getPageSize() == null ? 10 : vo.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        return bookMapper.searchBookPage(vo);
    }

    /**
     * 图书下架：修改图书总状态为3 + 所有副本改为3
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean downBook(Integer bookId) {
        Book book = bookMapper.getById(bookId);
        if (book == null) {
            return false;
        }
        // 数据库规范：3=下架
        int updateBook = bookMapper.updateBookStatus(bookId, 3);
        // 批量下架该书所有副本
        bookcopyMapper.downAllCopyByIsbn(book.getISBN());
        // 修正：判断大于0返回布尔，不再直接返回int
        return updateBook > 0;
    }

    /**
     * 根据ISBN查询该书全部副本（编辑弹窗展示）
     */
    public List<BookCopyDTO> getCopyListByIsbn(String isbn) {
        return bookcopyMapper.listCopyDtoByIsbn(isbn);
    }
}