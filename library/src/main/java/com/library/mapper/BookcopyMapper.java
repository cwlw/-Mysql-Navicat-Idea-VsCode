package com.library.mapper;
import com.library.entity.Bookcopy;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface BookcopyMapper {
    // 查询全部图书副本
    @Select("select barCode,ISBN,place,status,oldStatus,location,nowLocation from bookcopy")
    List<Bookcopy> listAll();

    // 根据条码主键查询单条
    @Select("select barCode,ISBN,place,status,oldStatus,location,nowLocation from bookcopy where barCode=#{barCode}")
    Bookcopy getById(String barCode);

    // 新增副本，完整7字段插入
    @Insert("insert into bookcopy(barCode,ISBN,place,status,oldStatus,location,nowLocation) " +
            "values(#{barCode},#{ISBN},#{place},#{status},#{oldStatus},#{location},#{nowLocation})")
    int insert(Bookcopy c);

    // 修改副本，全部可编辑字段更新
    @Update("update bookcopy set ISBN=#{ISBN},place=#{place},status=#{status},oldStatus=#{oldStatus}," +
            "location=#{location},nowLocation=#{nowLocation} where barCode=#{barCode}")
    int update(Bookcopy c);

    // 根据条码删除
    @Delete("delete from bookcopy where barCode=#{barCode}")
    int delete(String barCode);
}