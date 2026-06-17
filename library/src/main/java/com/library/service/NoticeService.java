package com.library.service;
import com.library.entity.Notice;
import com.library.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoticeService {
    @Autowired NoticeMapper m;
    public List<Notice> listAll(){return m.listAll();}
    public Notice getById(Integer id){return m.getById(id);}
    public boolean add(Notice n){return m.insert(n)>0;}
    public boolean update(Notice n){return m.update(n)>0;}
    public boolean delete(Integer id){return m.delete(id)>0;}
}