package com.cloudservice.myservice.ui;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSaveRequest {

    @Size(min=6, max=20)
    @NotBlank
    private String loginId;

    @Size(min=8, max=20)
    @NotBlank
    private String password;

}
