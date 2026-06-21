package com.library.vo;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class FineVo {
    private Integer id;
    private String name;
    private BigDecimal fine;
    private String status;
}