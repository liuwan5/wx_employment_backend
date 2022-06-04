package com.wx.employment.VO.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author wbl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobParam {
    private String name;

    private Integer headcount;

    private Integer duration;

    private Integer durationType;

    private Integer salary;

    private Integer salaryType;

    private String location;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String description;
}
