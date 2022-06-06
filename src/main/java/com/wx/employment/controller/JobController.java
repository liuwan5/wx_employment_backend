package com.wx.employment.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.wx.employment.PO.Job;
import com.wx.employment.VO.param.JobParam;
import com.wx.employment.VO.param.JobsConditionParam;
import com.wx.employment.VO.param.PageParam;
import com.wx.employment.common.Result;
import com.wx.employment.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wbl
 * @since 2022-06-03
 */
@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    JobService jobService;

    @PostMapping("/getJobs")
    public Result<IPage<Job>> getJobs(@RequestBody JobsConditionParam jobsConditionParam, HttpServletRequest request){
        return jobService.getJobsPageByCondition(jobsConditionParam, request);
    }

    @PostMapping("/getEmployerJobs")
    public Result<IPage<Job>> getEmployerJobs(@RequestBody PageParam pageParam, HttpServletRequest request){
        return jobService.getJobsByUid(pageParam, request);
    }

    @GetMapping("/getJob")
    public Result getJob(@RequestParam int jid, HttpServletRequest request){
        return jobService.getJobsByJid(jid, request);
    }

    @PostMapping("/add")
    public Result addJob(@RequestBody JobParam jobParam, HttpServletRequest request){
        return jobService.addJob(jobParam, request);
    }

    @GetMapping("/del")
    public Result delJob(@RequestParam int jid, HttpServletRequest request){
        return jobService.delJob(jid, request);
    }

    @PostMapping("/upd")
    public Result updJob(@RequestBody Job job, HttpServletRequest request){
        return jobService.updJob(job, request);
    }
}
