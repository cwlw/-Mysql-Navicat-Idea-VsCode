package com.library.service;
import com.library.entity.Ruleconfig;
import com.library.mapper.RuleconfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RuleconfigService {
    @Autowired RuleconfigMapper m;
    public List<Ruleconfig> listAll(){return m.listAll();}
    public Ruleconfig getById(Integer id){return m.getById(id);}
    public boolean add(Ruleconfig r){return m.insert(r)>0;}
    public boolean update(Ruleconfig r){return m.update(r)>0;}
    public boolean delete(Integer id){return m.delete(id)>0;}
}