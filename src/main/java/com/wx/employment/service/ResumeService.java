package com.wx.employment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.wx.employment.PO.Job;
import com.wx.employment.PO.Resume;
import com.wx.employment.VO.param.PageParam;
import com.wx.employment.VO.param.ResumeParam;
import com.wx.employment.common.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wbl
 */
public interface ResumeService {
    /**
     * 获取当前用户的所有简历，只有employee才有简历，需要有当前页和页大小的参数
     * @param pageParam
     * @param request
     * @return
     */
    public Result<IPage<Resume>> getUserResume(PageParam pageParam, HttpServletRequest request);

    /**
     * 根据uid获取简历的list
     * @param uid
     * @param request
     * @return
     */
    public Result<List<Resume>> getResumeByUid(int uid, HttpServletRequest request);

    /**
     * 当前用户添加一个新的简历
     * @param resumeParam
     * @param request
     * @return
     */
    public Result addResume(ResumeParam resumeParam, HttpServletRequest request);

    /**
     * 当前用户删除rid的简历，删除这个简历不影响已经投递出去的简历
     * @param rid
     * @param request
     * @return
     */
    public Result delResume(int rid, HttpServletRequest request);

    /**
     * 根据get得到的简历修改，将修改后的resume直接整个提交给后端修改
     * @param resume
     * @param request
     * @return
     */
    public Result updResume(Resume resume, HttpServletRequest request);
}
