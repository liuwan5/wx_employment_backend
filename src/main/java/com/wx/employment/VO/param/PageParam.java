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
public class PageParam {

    int currentPage;

    int pageSize;
}
