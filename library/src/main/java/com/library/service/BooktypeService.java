package com.library.service;
import com.library.entity.Booktype;
import com.library.mapper.BooktypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BooktypeService {
    @Autowired BooktypeMapper m;
    public List<Booktype> listAll(){return m.listAll();}
    public Booktype getById(Integer id){return m.getById(id);}
    public boolean add(Booktype t){return m.insert(t)>0;}
    public boolean update(Booktype t){return m.update(t)>0;}
    public boolean delete(Integer id){return m.delete(id)>0;}
}