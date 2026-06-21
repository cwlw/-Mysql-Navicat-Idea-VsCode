package com.library.mapper;
import com.library.dto.BookCopyDTO;
import com.library.entity.Bookcopy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import java.util.List;

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

    // 根据ISBN查询所有【可借】副本 status=1代表可借（已删除@Param注解）
    @Select("select barCode,ISBN,place,status,oldStatus,location,nowLocation from bookcopy where ISBN=#{ISBN} and status=1")
    List<Bookcopy> listAvailableByIsbn(String ISBN);

    // ===================== 采编新增接口 =====================
    /**
     * 根据ISBN查询该书所有副本（编辑图书弹窗展示）
     */
    @Select("select barCode,ISBN,place,status,oldStatus,location,nowLocation from bookcopy where ISBN=#{isbn}")
    List<BookCopyDTO> listCopyDtoByIsbn(@Param("isbn") String isbn);

    /**
     * 注销副本：oldStatus保存原状态，status改为2（注销/已借出）
     */
    @Update("update bookcopy set oldStatus = status, status = 2 where barCode = #{barCode}")
    int cancelCopy(@Param("barCode") String barCode);

    /**
     * 图书下架：将该ISBN下所有副本状态改为3（下架），oldStatus存原状态
     */
    @Update("update bookcopy set oldStatus = status, status = 3 where ISBN = #{isbn}")
    int downAllCopyByIsbn(@Param("isbn") String isbn);
}