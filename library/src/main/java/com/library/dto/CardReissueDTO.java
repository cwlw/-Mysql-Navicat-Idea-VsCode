package com.library.dto;
import lombok.Data;

@Data
public class CardReissueDTO {
    private String sno;
    private String originCardNo;
    private String newCardNo;
}