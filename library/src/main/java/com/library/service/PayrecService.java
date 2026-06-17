package com.library.service;
import com.library.entity.Payrec;
import com.library.mapper.PayrecMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PayrecService {
    @Autowired PayrecMapper m;
    public List<Payrec> listAll(){return m.listAll();}
    public Payrec getById(Integer id){return m.getById(id);}
    public boolean add(Payrec p){return m.insert(p)>0;}
    public boolean update(Payrec p){return m.update(p)>0;}
    public boolean delete(Integer id){return m.delete(id)>0;}
}