package com.library.mapper;
import com.library.entity.Borrowrec;
import com.library.vo.BorrowVo;
import com.library.vo.FineVo;
import org.apache.ibatis.annotations.*;
import java.math.BigDecimal;
import java.util.List;
@Mapper
public interface BorrowrecMapper {
    @Select("select * from borrowrec") List<Borrowrec> listAll();
    @Select("select * from borrowrec where id=#{id}") Borrowrec getById(Integer id);
    @Insert("insert into borrowrec(serNum,sno,barCode,borDate,retDate,realRetDate,retStatus,oddDays,fineMoney,fineStatus,paySerNum) values(#{serNum},#{sno},#{barCode},#{borDate},#{retDate},#{realRetDate},#{retStatus},#{oddDays},#{fineMoney},#{fineStatus},#{paySerNum})")
    int insert(Borrowrec b);
    @Update("update borrowrec set serNum=#{serNum},sno=#{sno},barCode=#{barCode},borDate=#{borDate},retDate=#{retDate},realRetDate=#{realRetDate},retStatus=#{retStatus},oddDays=#{oddDays},fineMoney=#{fineMoney},fineStatus=#{fineStatus},paySerNum=#{paySerNum} where id=#{id}")
    int update(Borrowrec b);
    @Delete("delete from borrowrec where id=#{id}") int delete(Integer id);

    @Select("""
        SELECT br.id, bc.barCode barcode, b.bname name, br.borDate borrowDate, br.retDate dueDate, br.realRetDate realRetDate,
        CASE br.retStatus WHEN 0 THEN '未归还' WHEN 1 THEN '已归还' WHEN 2 THEN '超时还' ELSE '未知' END status
        FROM borrowrec br
        LEFT JOIN bookcopy bc ON br.barCode = bc.barCode
        LEFT JOIN book b ON bc.ISBN = b.ISBN
        WHERE br.sno = #{sno} AND br.retStatus = 0
        ORDER BY br.borDate DESC
        """)
    List<BorrowVo> selectBorrowListBySno(@Param("sno") String sno);

    @Select("""
        SELECT br.id, b.bname name, br.fineMoney fine,
        CASE br.fineStatus WHEN 0 THEN '无需缴纳' WHEN 1 THEN '已结清' WHEN 2 THEN '未缴纳' END status
        FROM borrowrec br
        LEFT JOIN bookcopy bc ON br.barCode = bc.barCode
        LEFT JOIN book b ON bc.ISBN = b.ISBN
        WHERE br.sno = #{sno}
          AND br.retStatus = 2
          AND br.fineStatus = 2
        """)
    List<FineVo> selectUnpaidFineBySno(@Param("sno") String sno);

    // 批量更新罚款状态，新增paySerNum字段，3个入参
    @Update("<script>" +
            "UPDATE borrowrec SET fineStatus = 1, paySerNum = #{paySerNum} WHERE sno = #{sno} AND id IN " +
            "<foreach collection='ids' item='item' open='(' separator=',' close=')'>#{item}</foreach>" +
            "</script>")
    void batchUpdateFineStatus(
            @Param("sno") String sno,
            @Param("ids") List<Integer> ids,
            @Param("paySerNum") String paySerNum
    );

    @Select("""
        SELECT 
            br.id,
            bc.barCode AS barcode,
            b.bname AS name,
            DATE_FORMAT(br.borDate,'%Y-%m-%d %H:%i:%s') AS borrowDate,
            DATE_FORMAT(br.retDate,'%Y-%m-%d %H:%i:%s') AS dueDate,
            DATE_FORMAT(br.realRetDate,'%Y-%m-%d %H:%i:%s') AS realRetDate,
            CASE br.retStatus 
                WHEN 0 THEN '未归还' 
                WHEN 1 THEN '已归还' 
                WHEN 2 THEN '超时还' 
                ELSE '未知' 
            END AS status
        FROM borrowrec br
        LEFT JOIN bookcopy bc ON br.barCode = bc.barCode
        LEFT JOIN book b ON bc.isbn = b.isbn
        WHERE br.sno = #{sno}
        ORDER BY br.borDate DESC
        LIMIT #{offset},#{size}
    """)
    List<BorrowVo> selectBorrowHistoryPageBySno(
            @Param("sno") String sno,
            @Param("offset") int offset,
            @Param("size") int size
    );

    @Select("""
        SELECT IFNULL(SUM(br.fineMoney),0)
        FROM borrowrec br
        WHERE br.sno = #{sno} AND br.retStatus = 2 AND br.fineStatus = 2 AND NOW() > br.retDate
    """)
    BigDecimal sumUnpaidFineBySno(@Param("sno") String sno);

    @Select("SELECT COUNT(*) FROM borrowrec br WHERE br.sno = #{sno}")
    int countAllBorrowBySno(@Param("sno") String sno);

    // 仅新增这条，查询当前学生最大paySerNum，其余全部不动
    @Select("SELECT MAX(paySerNum) FROM borrowrec WHERE sno = #{sno}")
    String selectMaxPaySerNum(@Param("sno") String sno);
}