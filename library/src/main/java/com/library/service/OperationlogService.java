package com.library.service;
import com.library.entity.Operationlog;
import com.library.mapper.OperationlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OperationlogService {
    @Autowired OperationlogMapper m;
    public List<Operationlog> listAll(){return m.listAll();}
    public Operationlog getById(Integer id){return m.getById(id);}
    public boolean add(Operationlog o){return m.insert(o)>0;}
    public boolean update(Operationlog o){return m.update(o)>0;}
    public boolean delete(Integer id){return m.delete(id)>0;}
}