package com.wx.employment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.employment.PO.DeliveredResume;
import com.wx.employment.PO.Resume;
import com.wx.employment.VO.param.PageParam;
import com.wx.employment.VO.param.ResumeParam;
import com.wx.employment.common.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * 只需要查看和增加的功能，由deliver控制器调用
 * @author wbl
 */
public interface ResumeDeliveredService {
    /**
     * 在一个投递关系中要查看已经投递出去的简历的内容
     * @param drid
     * @param request
     * @return
     */
    public Result<DeliveredResume> getDeliveredResumeByDrid(int drid, HttpServletRequest request);

    /**
     * 并且只会被DeliverService调用，不需要验证登录情况了，并且返回drid方便DeliverService建立投递关系
     * @param resume
     * @param request
     * @return
     */
    public Integer addResume(Resume resume, HttpServletRequest request);

}
