package com.wx.employment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.employment.PO.Job;
import com.wx.employment.VO.param.JobParam;
import com.wx.employment.VO.param.JobsConditionParam;
import com.wx.employment.VO.param.PageParam;
import com.wx.employment.common.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wbl
 */
public interface JobService {
    /**
     * 根据条件查询相应的job，如果不需要这个条件，那么在RequestBody中不需要写
     * 只需要登录即可，不需要权限
     * @param jobsConditionParam
     * @param request
     * @return
     */
    public Result<IPage<Job>> getJobsPageByCondition(JobsConditionParam jobsConditionParam, HttpServletRequest request);

    /**
     * 根据当前的employer获取他发布过的job，需要有当前页和页大小的参数
     * @param pageParam
     * @param request
     * @return
     */
    public Result<IPage<Job>> getJobsByUid(PageParam pageParam, HttpServletRequest request);

    /**
     * 根据jid获取这个job的信息
     * @param jid
     * @param request
     * @return
     */
    public Result<Job> getJobsByJid(int jid, HttpServletRequest request);

    /**
     * 增加一个job，使用jobParam，传递需要用户书写的参数，其他的自动生成
     * @param jobParam
     * @param request
     * @return
     */
    public Result addJob(JobParam jobParam, HttpServletRequest request);

    /**
     * 根据jid删除job
     * @param jid
     * @param request
     * @return
     */
    public Result delJob(int jid, HttpServletRequest request);

    /**
     * 在用户修改完信息后将整个job表单重新提交给后端进行更新
     * @param job
     * @param request
     * @return
     */
    public Result updJob(Job job, HttpServletRequest request);
}
