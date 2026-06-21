package com.library.dto;

import lombok.Data;
import java.util.Date;

@Data
public class StudentUpdateDTO {
    private String sno;
    private String username;
    private Date birth;
    private String originPlace;
}