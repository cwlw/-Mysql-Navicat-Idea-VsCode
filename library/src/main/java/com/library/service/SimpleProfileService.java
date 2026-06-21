package com.library.service;

import com.library.dto.PasswordDTO;
import com.library.dto.StudentUpdateDTO;
import com.library.util.Result;
import com.library.vo.BorrowVo;
import com.library.vo.FineVo;
import com.library.vo.StudentProfileVO;
import java.util.List;

public interface SimpleProfileService {
    StudentProfileVO getStudentProfile(String sno);

    void updateStudentInfo(StudentUpdateDTO dto);

    Result<String> updatePassword(PasswordDTO dto);

    List<BorrowVo> getBorrowHistory(String sno, Integer page, Integer size);
    int getBorrowTotal(String sno);

    List<FineVo> getFineRecord(String sno, Integer page, Integer size);
    int getFineTotal(String sno);
}