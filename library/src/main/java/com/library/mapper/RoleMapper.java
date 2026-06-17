package com.library.mapper;
import com.library.entity.Role;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface RoleMapper {
    @Select("select * from role") List<Role> listAll();
    @Select("select * from role where id=#{id}") Role getById(String id);
    @Insert("insert into role(id,role_name,remark) values(#{id},#{role_name},#{remark})") int insert(Role r);
    @Update("update role set role_name=#{role_name},remark=#{remark} where id=#{id}") int update(Role r);
    @Delete("delete from role where id=#{id}") int delete(String id);
}