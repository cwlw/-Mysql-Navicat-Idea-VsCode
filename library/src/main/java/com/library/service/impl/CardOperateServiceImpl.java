package com.library.service.impl;

import com.library.entity.Cardrec;
import com.library.entity.Libcard;
import com.library.entity.Operationlog;
import com.library.entity.Student;
import com.library.mapper.CardrecMapper;
import com.library.mapper.LibcardMapper;
import com.library.mapper.OperationlogMapper;
import com.library.mapper.StudentMapper;
import com.library.service.CardOperateService;
import com.library.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CardOperateServiceImpl implements CardOperateService {

    @Autowired
    private LibcardMapper libcardMapper;
    @Autowired
    private CardrecMapper cardrecMapper;
    @Autowired
    private OperationlogMapper operationlogMapper;
    @Autowired
    private StudentMapper studentMapper;

    private final AtomicInteger serialNum = new AtomicInteger(1);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private String getIp() {
        return "127.0.0.1";
    }

    private Integer getLoginAdminId() {
        return 1;
    }

    // 操作编码：1新办 2挂失 3补办 4注销
    private Integer getCode(String op) {
        return switch (op) {
            case "apply" -> 1;
            case "loss" -> 2;
            case "reissue" -> 3;
            case "cancel" -> 4;
            default -> 0;
        };
    }

    // 生成标准流水号 CR+日期+8位数字
    private String generateSerNum() {
        String date = LocalDateTime.now().format(formatter);
        int num = serialNum.getAndIncrement();
        return "CR" + date + String.format("%08d", num);
    }

    /** 新办借阅卡 */
    @Override
    @Transactional
    public Result applyCard(String sno, String cardNo) {
        Libcard exist = libcardMapper.getBySno(sno);
        if (exist != null) {
            return Result.fail("该学生已办理借阅卡，禁止重复办理");
        }
        Student stu = studentMapper.getStudentWithCardBySno(sno);
        if (stu == null) {
            return Result.fail("未查询到该学生信息");
        }

        Libcard newCard = new Libcard();
        newCard.setCardNo(cardNo);
        newCard.setSno(sno);
        newCard.setSname(stu.getUsername());
        newCard.setType(stu.getType());
        newCard.setCollage(stu.getCollage());
        newCard.setMajor(stu.getMajor());
        newCard.setBirth(stu.getBirth());
        newCard.setOriginPlace(stu.getOriginPlace());
        newCard.setCardStatus(getCode("apply"));
        newCard.setTimes(3);
        libcardMapper.insert(newCard);

        LocalDateTime nowLdt = LocalDateTime.now();
        Date nowDate = new Date();
        String serNum = generateSerNum();

        Cardrec rec = new Cardrec();
        rec.setSerNum(serNum);
        rec.setSno(sno);
        rec.setOriginCardNo("");
        rec.setNewCardNo(cardNo);
        rec.setOpType(getCode("apply"));
        rec.setOpTime(nowLdt);
        cardrecMapper.insert(rec);

        Operationlog log = new Operationlog();
        log.setAdminId(getLoginAdminId());
        log.setOperateTime(nowDate);
        log.setOperateType("办理借阅卡");
        log.setContent("为学号" + sno + "新办借阅卡，卡号：" + cardNo);
        log.setIp(getIp());
        operationlogMapper.insert(log);

        return Result.success(null);
    }

    /** 挂失借阅卡 */
    @Override
    @Transactional
    public Result lossCard(String sno, String cardNo) {
        Libcard libcard = libcardMapper.getById(cardNo);
        if (libcard == null) return Result.fail("未查询到该借阅卡");
        // 统一转字符串对比，解决前端字符串、后端数字不匹配
        String status = String.valueOf(libcard.getCardStatus());
        if ("2".equals(status)) return Result.fail("该卡片已挂失，无需重复操作");
        if ("4".equals(status)) return Result.fail("已注销卡片无法挂失");

        libcard.setCardStatus(getCode("loss"));
        libcardMapper.update(libcard);

        LocalDateTime nowLdt = LocalDateTime.now();
        Date nowDate = new Date();
        String serNum = generateSerNum();

        Cardrec rec = new Cardrec();
        rec.setSerNum(serNum);
        rec.setSno(sno);
        rec.setOriginCardNo(cardNo);
        rec.setNewCardNo("");
        rec.setOpType(getCode("loss"));
        rec.setOpTime(nowLdt);
        cardrecMapper.insert(rec);

        Operationlog log = new Operationlog();
        log.setAdminId(getLoginAdminId());
        log.setOperateTime(nowDate);
        log.setOperateType("挂失借阅卡");
        log.setContent("为学号" + sno + "挂失卡号：" + cardNo);
        log.setIp(getIp());
        operationlogMapper.insert(log);

        return Result.success(null);
    }

    /** 补办借阅卡（重点修复状态判断） */
    @Override
    @Transactional
    public Result reissueCard(String sno, String originCardNo, String newCardNo) {
        Libcard oldCard = libcardMapper.getById(originCardNo);
        if (oldCard == null) return Result.fail("原借阅卡不存在");
        // 核心修复：数字转字符串，和前端"2"匹配
        String status = String.valueOf(oldCard.getCardStatus());
        if (!"2".equals(status)) {
            return Result.fail("仅挂失状态卡片支持补办");
        }

        Student stu = studentMapper.getStudentWithCardBySno(sno);
        Libcard newCard = new Libcard();
        newCard.setCardNo(newCardNo);
        newCard.setSno(sno);
        newCard.setSname(oldCard.getSname());
        newCard.setType(stu.getType());
        newCard.setCollage(stu.getCollage());
        newCard.setMajor(stu.getMajor());
        newCard.setBirth(stu.getBirth());
        newCard.setOriginPlace(stu.getOriginPlace());
        newCard.setCardStatus(getCode("reissue"));
        newCard.setTimes(3);
        libcardMapper.insert(newCard);

        LocalDateTime nowLdt = LocalDateTime.now();
        Date nowDate = new Date();
        String serNum = generateSerNum();

        Cardrec rec = new Cardrec();
        rec.setSerNum(serNum);
        rec.setSno(sno);
        rec.setOriginCardNo(originCardNo);
        rec.setNewCardNo(newCardNo);
        rec.setOpType(getCode("reissue"));
        rec.setOpTime(nowLdt);
        cardrecMapper.insert(rec);

        Operationlog log = new Operationlog();
        log.setAdminId(getLoginAdminId());
        log.setOperateTime(nowDate);
        log.setOperateType("补办借阅卡");
        log.setContent("学号" + sno + "补办，原卡" + originCardNo + "，新卡" + newCardNo);
        log.setIp(getIp());
        operationlogMapper.insert(log);

        return Result.success(null);
    }

    /** 注销借阅卡 */
    @Override
    @Transactional
    public Result cancelCard(String sno, String cardNo) {
        Libcard libcard = libcardMapper.getById(cardNo);
        if (libcard == null) return Result.fail("未查询到该借阅卡");
        String status = String.valueOf(libcard.getCardStatus());
        if ("4".equals(status)) return Result.fail("卡片已注销，不可重复操作");

        libcard.setCardStatus(getCode("cancel"));
        libcardMapper.update(libcard);

        LocalDateTime nowLdt = LocalDateTime.now();
        Date nowDate = new Date();
        String serNum = generateSerNum();

        Cardrec rec = new Cardrec();
        rec.setSerNum(serNum);
        rec.setSno(sno);
        rec.setOriginCardNo(cardNo);
        rec.setNewCardNo("");
        rec.setOpType(getCode("cancel"));
        rec.setOpTime(nowLdt);
        cardrecMapper.insert(rec);

        Operationlog log = new Operationlog();
        log.setAdminId(getLoginAdminId());
        log.setOperateTime(nowDate);
        log.setOperateType("注销借阅卡");
        log.setContent("为学号" + sno + "注销卡号：" + cardNo);
        log.setIp(getIp());
        operationlogMapper.insert(log);

        return Result.success(null);
    }
}