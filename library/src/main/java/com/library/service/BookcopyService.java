package com.library.service;
import com.library.entity.Bookcopy;
import com.library.mapper.BookcopyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookcopyService {
    @Autowired BookcopyMapper m;
    public List<Bookcopy> listAll(){return m.listAll();}
    public Bookcopy getById(String barCode){return m.getById(barCode);}
    public boolean add(Bookcopy c){return m.insert(c)>0;}
    public boolean update(Bookcopy c){return m.update(c)>0;}
    public boolean delete(String barCode){return m.delete(barCode)>0;}
}