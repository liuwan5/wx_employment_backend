package com.wx.employment.VO.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wbl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeParam {
    private String resumeName;

    private String name;

    private Integer sex;

    private Integer age;

    private String phone;

    private String email;

    private String education;

    private String experience;
}
