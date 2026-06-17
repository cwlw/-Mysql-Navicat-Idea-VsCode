package com.library.service;
import com.library.entity.Role;
import com.library.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleService {
    @Autowired RoleMapper m;
    public List<Role> listAll(){return m.listAll();}
    public Role getById(String id){return m.getById(id);}
    public boolean add(Role r){return m.insert(r)>0;}
    public boolean update(Role r){return m.update(r)>0;}
    public boolean delete(String id){return m.delete(id)>0;}
}