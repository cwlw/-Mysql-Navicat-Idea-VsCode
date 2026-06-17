package com.library.service;
import com.library.entity.Book;
import com.library.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    @Autowired BookMapper m;
    public List<Book> listAll(){return m.listAll();}
    public Book getById(Integer id){return m.getById(id);}
    public boolean add(Book b){return m.insert(b)>0;}
    public boolean update(Book b){return m.update(b)>0;}
    public boolean delete(Integer id){return m.delete(id)>0;}
}