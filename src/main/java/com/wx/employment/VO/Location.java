package com.wx.employment.VO;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
public class Location {

    private String location;

    private BigDecimal longitude;

    private BigDecimal latitude;

    public boolean isValid(){return StringUtils.isNotEmpty(location)&&longitude!=null&&latitude!=null;}
}
