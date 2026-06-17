package com.library.service;
import com.library.entity.Borrowrec;
import com.library.mapper.BorrowrecMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BorrowrecService {
    @Autowired BorrowrecMapper m;
    public List<Borrowrec> listAll(){return m.listAll();}
    public Borrowrec getById(Integer id){return m.getById(id);}
    public boolean add(Borrowrec b){return m.insert(b)>0;}
    public boolean update(Borrowrec b){return m.update(b)>0;}
    public boolean delete(Integer id){return m.delete(id)>0;}
}