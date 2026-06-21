package com.library.service.impl;
import com.library.dto.PasswordDTO;
import com.library.dto.StudentUpdateDTO;
import com.library.entity.Libcard;
import com.library.entity.Student;
import com.library.service.BorrowrecService;
import com.library.service.LibcardService;
import com.library.service.PayrecService;
import com.library.service.SimpleProfileService;
import com.library.service.StudentService;
import com.library.util.Result;
import com.library.vo.BorrowVo;
import com.library.vo.FineVo;
import com.library.vo.StudentProfileVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class SimpleProfileServiceImpl implements SimpleProfileService {

    @Resource
    private StudentService studentService;
    @Resource
    private LibcardService libcardService;
    @Resource
    private BorrowrecService borrowrecService;
    @Resource
    private PayrecService payrecService;

    @Override
    public StudentProfileVO getStudentProfile(String sno) {
        StudentProfileVO vo = new StudentProfileVO();
        Student student = studentService.getBySno(sno);
        vo.setStudent(student);

        Libcard libcard = libcardService.getCardEntityBySno(sno);
        vo.setLibcard(libcard);

        List<BorrowVo> currentBorrow = borrowrecService.getBorrowListBySno(sno);
        vo.setCurrentBorrowList(currentBorrow);
        vo.setCurrentBorrowCount(currentBorrow.size());

        BigDecimal unpaidFine = borrowrecService.getUnpaidFineTotalBySno(sno);
        vo.setTotalUnpaidFine(unpaidFine == null ? BigDecimal.ZERO : unpaidFine);
        return vo;
    }

    @Override
    public void updateStudentInfo(StudentUpdateDTO dto) {
        studentService.updateStudent(dto);
    }

    @Override
    public Result<String> updatePassword(PasswordDTO dto) {
        if (!dto.getNewPwd().equals(dto.getConfirmPwd())) {
            return Result.fail("两次输入新密码不一致");
        }
        return studentService.updatePwd(dto.getAccountId(), dto);
    }

    @Override
    public List<BorrowVo> getBorrowHistory(String sno, Integer page, Integer size) {
        return borrowrecService.getBorrowHistoryBySno(sno, page, size);
    }

    @Override
    public int getBorrowTotal(String sno) {
        return borrowrecService.getBorrowTotalCount(sno);
    }

    @Override
    public List<FineVo> getFineRecord(String sno, Integer page, Integer size) {
        return payrecService.getPayRecordBySno(sno, page, size);
    }

    @Override
    public int getFineTotal(String sno) {
        return payrecService.getPayTotalCount(sno);
    }
}