package com.wx.employment.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class DeliveredResume implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "drid", type = IdType.AUTO)
    private Integer drid;

    private String resumeName;

    private Integer uid;

    private String name;

    private Integer sex;

    private Integer age;

    private String phone;

    private String email;

    private String education;

    private String experience;


}
