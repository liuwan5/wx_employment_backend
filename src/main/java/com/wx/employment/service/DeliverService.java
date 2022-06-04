package com.wx.employment.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.employment.PO.DeliverRelation;
import com.wx.employment.PO.DeliveredResume;
import com.wx.employment.PO.Job;
import com.wx.employment.VO.param.DeliverParam;
import com.wx.employment.VO.param.PageParam;
import com.wx.employment.common.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理投递简历和录取
 * @author wbl
 */
public interface DeliverService {
    /**
     * employee投递简历，简历和招聘信息建立关系
     * @param deliverParam
     * @param request
     * @return
     */
    public Result deliverResume(DeliverParam deliverParam, HttpServletRequest request);

    /**
     * 获取当前employee已经投递的jobs
     * @param pageParam
     * @param request
     * @return
     */
    public Result<IPage<DeliverRelation>> getDeliveredJobByUid(PageParam pageParam, HttpServletRequest request);

    /**
     * employer先通过JobService查看他的发布的所有job，点击job通过这个方法查看这个job收到的简历
     * @param jid
     * @param pageParam
     * @param request
     * @return
     */
    public Result<IPage<DeliverRelation>> getDeliveredResumeByJid(int jid, PageParam pageParam, HttpServletRequest request);

    /**
     * 调用上面的方法查看后，可以选择接受这个简历，即录用
     * @param did
     * @param request
     * @return
     */
    public Result acceptResume(int did, HttpServletRequest request);

    /**
     * 调用上面的方法查看后，可以选择拒绝这个简历，即拒绝录用
     * @param did
     * @param request
     * @return
     */
    public Result refuseResume(int did, HttpServletRequest request);
}
