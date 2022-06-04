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
public class JobsConditionParam {

    // 关键词

    private String keyword;

    // 工作时间范围

    private Integer durationStart;

    private Integer durationEnd;

    private Integer durationType = -1;

    // 薪资范围

    private Integer salaryStart;

    private Integer salaryEnd;

    private Integer salaryType = -1;

    //意向地点

    private BigDecimal longitude;

    private BigDecimal latitude;

    // 范围，默认目标地点的三公里内

    private double distance = 5;

    // 分页情况，默认第1页，页的大小为5

    private Integer currentPage = 1;

    private Integer pageSize = 5;
}
