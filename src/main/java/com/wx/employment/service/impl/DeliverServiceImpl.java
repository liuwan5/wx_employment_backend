package com.wx.employment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.employment.DAO.DeliverRelationDao;
import com.wx.employment.DAO.JobDao;
import com.wx.employment.DAO.ResumeDao;
import com.wx.employment.PO.*;
import com.wx.employment.VO.param.DeliverParam;
import com.wx.employment.VO.param.PageParam;
import com.wx.employment.common.Result;
import com.wx.employment.service.DeliverService;
import com.wx.employment.service.ResumeDeliveredService;
import com.wx.employment.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wbl
 */
@Slf4j
@Service
public class DeliverServiceImpl implements DeliverService {

    @Autowired
    DeliverRelationDao deliverRelationDao;

    @Autowired
    JobDao jobDao;

    @Autowired
    ResumeDao resumeDao;

    @Autowired
    ResumeDeliveredService resumeDeliveredService;

    @Override
    public Result deliverResume(DeliverParam deliverParam, HttpServletRequest request) {
        try{
            User nowUser = JwtUtil.getNowUser(request);
            // 登录确认
            if (nowUser == null) {
                return Result.noLogin();
            }
            // 如果这个工作已经投递过简历了，那么禁止投递，返回简历投递失败
            LambdaQueryWrapper<DeliverRelation> query = new LambdaQueryWrapper<>();
            query.eq(DeliverRelation::getJid, deliverParam.getJid())
                    .eq(DeliverRelation::getEmployeeId, nowUser.getUid());
            DeliverRelation oldDeliverRelation = deliverRelationDao.getOne(query);
            if(oldDeliverRelation != null){
                return Result.executeFailure("你已经投递过简历，请不要重复投递简历");
            }
            // 在已投递的简历中增加这个简历
            Resume resume = resumeDao.getById(deliverParam.getRid());
            int drid = resumeDeliveredService.addResume(resume,request);
            // 建立投递关系
            DeliverRelation deliverRelation = new DeliverRelation();
            deliverRelation.setDrid(drid);
            deliverRelation.setJid(deliverParam.getJid());
            deliverRelation.setEmployeeId(nowUser.getUid());
            int employerId = jobDao.getById(deliverParam.getJid()).getCreatedUserId();
            deliverRelation.setEmployerId(employerId);
            deliverRelation.setStatus(0);
            deliverRelationDao.save(deliverRelation);
            return Result.executeSuccess("简历投递成功");
        }catch (Exception e){
            log.error(e.toString());
            e.printStackTrace();
            return Result.executeFailure("简历投递失败");
        }
    }

    @Override
    public Result<IPage<DeliverRelation>> getDeliveredJobByUid(PageParam param, HttpServletRequest request) {
        try{
            User nowUser = JwtUtil.getNowUser(request);
            // 登录确认
            if (nowUser == null) {
                return Result.noLogin();
            }
            LambdaQueryWrapper<DeliverRelation> deliverQuery = new LambdaQueryWrapper<>();
            deliverQuery.eq(DeliverRelation::getEmployeeId, nowUser.getUid());
            Page<DeliverRelation> producePage = new Page<>(param.getCurrentPage(), param.getPageSize());
            IPage<DeliverRelation> page = deliverRelationDao.page(producePage, deliverQuery);
            return Result.getSuccess(page);
        }catch (Exception e){
            log.error(e.toString());
            e.printStackTrace();
            return Result.getFailure();
        }
    }

    @Override
    public Result<IPage<DeliverRelation>> getDeliveredResumeByJid(int jid, PageParam param, HttpServletRequest request) {
        try{
            User nowUser = JwtUtil.getNowUser(request);
            // 登录确认
            if (nowUser == null) {
                return Result.noLogin();
            }
            LambdaQueryWrapper<DeliverRelation> deliverQuery = new LambdaQueryWrapper<>();
            deliverQuery.eq(DeliverRelation::getJid, jid);
            Page<DeliverRelation> producePage = new Page<>(param.getCurrentPage(), param.getPageSize());
            IPage<DeliverRelation> page = deliverRelationDao.page(producePage, deliverQuery);
            return Result.getSuccess(page);
        }catch (Exception e){
            log.error(e.toString());
            e.printStackTrace();
            return Result.getFailure();
        }
    }

    @Override
    public Result acceptResume(int did, HttpServletRequest request) {
        try{
            User nowUser = JwtUtil.getNowUser(request);
            // 登录确认
            if (nowUser == null) {
                return Result.noLogin();
            }
            LambdaUpdateWrapper<DeliverRelation> deliverUpdate = new LambdaUpdateWrapper<>();
            // 找到这个投递关系并通过
            deliverUpdate.eq(DeliverRelation::getDid, did)
                    .set(DeliverRelation::getStatus, 1);
            deliverRelationDao.update(null, deliverUpdate);
            return Result.executeSuccess("已成功录用");
        }catch (Exception e){
            log.error(e.toString());
            e.printStackTrace();
            return Result.executeFailure("录用失败，请重试");
        }
    }

    @Override
    public Result refuseResume(int did, HttpServletRequest request) {
        try{
            User nowUser = JwtUtil.getNowUser(request);
            // 登录确认
            if (nowUser == null) {
                return Result.noLogin();
            }
            LambdaUpdateWrapper<DeliverRelation> deliverUpdate = new LambdaUpdateWrapper<>();
            // 找到这个投递关系并通过
            deliverUpdate.eq(DeliverRelation::getDid, did)
                    .set(DeliverRelation::getStatus, 2);
            deliverRelationDao.update(null, deliverUpdate);
            return Result.executeSuccess("已拒绝录用");
        }catch (Exception e){
            log.error(e.toString());
            e.printStackTrace();
            return Result.executeFailure("拒绝录用失败，请重试");
        }
    }
}
