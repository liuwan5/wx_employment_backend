package com.wx.employment.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.employment.PO.DeliverRelation;
import com.wx.employment.VO.param.DeliverParam;
import com.wx.employment.VO.param.PageParam;
import com.wx.employment.common.Result;
import com.wx.employment.service.DeliverService;
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
public class DeliverController {

    @Autowired
    DeliverService deliverService;

    @PostMapping("/employee/Deliver")
    public Result deliver(@RequestBody DeliverParam deliverParam, HttpServletRequest request){
        return deliverService.deliverResume(deliverParam, request);
    }

    @PostMapping("/employee/getDeliveredJob")
    public Result<IPage<DeliverRelation>> getDeliveredJobByUid(@RequestBody PageParam pageParam, HttpServletRequest request){
        return deliverService.getDeliveredJobByUid(pageParam, request);
    }

    @PostMapping("/employer/getOneJobDeliveredResume")
    public Result<IPage<DeliverRelation>> getDeliveredResumeByJid(@RequestParam int jid, @RequestBody PageParam pageParam, HttpServletRequest request){
        return deliverService.getDeliveredResumeByJid(jid, pageParam, request);
    }

    @PostMapping("/employer/accept")
    public Result accept(@RequestParam int did, HttpServletRequest request){
        return deliverService.acceptResume(did, request);
    }

    @PostMapping("/employer/refuse")
    public Result refuse(@RequestParam int did, HttpServletRequest request){
        return deliverService.refuseResume(did, request);
    }
}
