package com.library.mapper;
import com.library.entity.Ruleconfig;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface RuleconfigMapper {
    @Select("select * from ruleconfig") List<Ruleconfig> listAll();
    @Select("select * from ruleconfig where id=#{id}") Ruleconfig getById(Integer id);
    @Insert("insert into ruleconfig(ruleName,ruleValue,intro) values(#{ruleName},#{ruleValue},#{intro})")
    int insert(Ruleconfig r);
    @Update("update ruleconfig set ruleName=#{ruleName},ruleValue=#{ruleValue},intro=#{intro} where id=#{id}")
    int update(Ruleconfig r);
    @Delete("delete from ruleconfig where id=#{id}") int delete(Integer id);
}