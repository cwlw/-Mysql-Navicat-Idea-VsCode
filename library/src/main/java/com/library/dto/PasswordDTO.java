package com.library.dto;

import lombok.Data;

@Data
public class PasswordDTO {
    private String accountId;
    private String oldPwd;
    private String newPwd;
    private String confirmPwd;
}