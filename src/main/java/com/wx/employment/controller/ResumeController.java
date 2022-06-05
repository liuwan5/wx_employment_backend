package com.wx.employment.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.employment.PO.Job;
import com.wx.employment.PO.Resume;
import com.wx.employment.VO.param.JobParam;
import com.wx.employment.VO.param.JobsConditionParam;
import com.wx.employment.VO.param.PageParam;
import com.wx.employment.VO.param.ResumeParam;
import com.wx.employment.common.Result;
import com.wx.employment.service.JobService;
import com.wx.employment.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wbl
 * @since 2022-06-03
 */
@RestController
@RequestMapping("/resume")
public class ResumeController {
    @Autowired
    ResumeService resumeService;

    @PostMapping("/getResumes")
    public Result<IPage<Resume>> getResumes(@RequestBody PageParam pageParam, HttpServletRequest request){
        return resumeService.getUserResume(pageParam, request);
    }

    @GetMapping("/getResumesByUid")
    public Result<List<Resume>> getResumesByUid(@RequestParam int uid, HttpServletRequest request){
        return resumeService.getResumeByUid(uid, request);
    }

    @PostMapping("/add")
    public Result addResume(@RequestBody ResumeParam resumeParam, HttpServletRequest request){
        return resumeService.addResume(resumeParam, request);
    }

    @GetMapping("/del")
    public Result delResume(@RequestParam int rid, HttpServletRequest request){
        return resumeService.delResume(rid, request);
    }

    @PostMapping("/upd")
    public Result updJob(@RequestBody Resume resume, HttpServletRequest request){
        return resumeService.updResume(resume, request);
    }
}
