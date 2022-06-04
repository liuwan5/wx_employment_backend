package com.wx.employment.PO;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wbl
 * @since 2022-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "jid", type = IdType.AUTO)
    private Integer jid;

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

    private LocalDateTime createdTime;

    private Integer createdUserId;

    private Integer deliveredResumeCount;
}
