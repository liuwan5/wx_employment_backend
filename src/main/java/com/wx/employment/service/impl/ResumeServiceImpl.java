package com.wx.employment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.employment.DAO.ResumeDao;
import com.wx.employment.PO.Job;
import com.wx.employment.PO.Resume;
import com.wx.employment.PO.User;
import com.wx.employment.VO.param.PageParam;
import com.wx.employment.VO.param.ResumeParam;
import com.wx.employment.common.Result;
import com.wx.employment.service.ResumeService;
import com.wx.employment.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wbl
 */
@Slf4j
@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    ResumeDao resumeDao;

    @Override
    public Result<IPage<Resume>> getUserResume(PageParam param, HttpServletRequest request) {
        try{
            User nowUser = JwtUtil.getNowUser(request);
            // 登录确认
            if (nowUser == null) {
                return Result.noLogin();
            }
            LambdaQueryWrapper<Resume> resumeQuery = new LambdaQueryWrapper<>();
            resumeQuery.eq(Resume::getUid, nowUser.getUid());
            Page<Resume> producePage = new Page<>(param.getCurrentPage(), param.getPageSize());
            IPage<Resume> page = resumeDao.page(producePage, resumeQuery);
            return Result.getSuccess(page);
        }catch (Exception e){
            log.error(e.toString());
            e.printStackTrace();
            return Result.getFailure();
        }
    }

    @Override
    public Result<List<Resume>> getResumeByUid(int uid, HttpServletRequest request) {
        try{
            LambdaQueryWrapper<Resume> resumeQuery = new LambdaQueryWrapper<>();
            resumeQuery.eq(Resume::getUid, uid);
            List<Resume> resumes = resumeDao.list(resumeQuery);
            return Result.getSuccess(resumes);
        }catch (Exception e){
            log.error(e.toString());
            e.printStackTrace();
            return Result.getFailure();
        }
    }

    @Override
    public Result addResume(ResumeParam resumeParam, HttpServletRequest request) {
        try{
            User nowUser = JwtUtil.getNowUser(request);
            // 登录确认
            if (nowUser == null) {
                return Result.noLogin();
            }
            Resume resume = new Resume();
            resume.setResumeName(resumeParam.getResumeName())
                    .setUid(nowUser.getUid())
                    .setName(resumeParam.getName())
                    .setSex(resumeParam.getSex())
                    .setAge(resumeParam.getAge())
                    .setPhone(resumeParam.getPhone())
                    .setEmail(resumeParam.getEmail())
                    .setEducation(resumeParam.getEducation())
                    .setExperience(resumeParam.getExperience());
            resumeDao.save(resume);
            return Result.insertSuccess();
        }catch (Exception e){
            log.error(e.toString());
            e.printStackTrace();
            return Result.insertFailure();
        }
    }

    @Override
    public Result delResume(int rid, HttpServletRequest request) {
        try{
            User nowUser = JwtUtil.getNowUser(request);
            // 登录确认
            if (nowUser == null) {
                return Result.noLogin();
            }
            resumeDao.remove(new LambdaQueryWrapper<Resume>().eq(Resume::getRid, rid));
            return Result.delSuccess();
        }catch (Exception e){
            log.error(e.toString());
            e.printStackTrace();
            return Result.delFailure();
        }
    }

    @Override
    public Result updResume(Resume resume, HttpServletRequest request) {
        try{
            User nowUser = JwtUtil.getNowUser(request);
            // 登录确认
            if (nowUser == null) {
                return Result.noLogin();
            }
            LambdaUpdateWrapper<Resume> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(Resume::getRid, resume.getRid());
            resumeDao.update(resume, lambdaUpdateWrapper);
            return Result.updSuccess();
        }catch (Exception e){
            log.error(e.toString());
            e.printStackTrace();
            return Result.updFailure();
        }
    }
}
