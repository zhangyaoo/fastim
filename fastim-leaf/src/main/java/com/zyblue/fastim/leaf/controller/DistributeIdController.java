package com.zyblue.fastim.leaf.controller;

import com.zyblue.fastim.common.url.UrlConstant;
import com.zyblue.fastim.common.pojo.BaseResponse;
import com.zyblue.fastim.leaf.service.LeafService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author zhangy75
 */
@Controller
@RequestMapping("/")
public class DistributeIdController {
    private final static Logger logger = LoggerFactory.getLogger(DistributeIdController.class);

    @Resource
    private LeafService leafService;

    @RequestMapping(value = UrlConstant.Leaf.GET_DISTRIBUTE_ID, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Long> getDistributeId(){
        Long id = leafService.getDistributedId();
        logger.info("id:{}", id);
        return new BaseResponse<>(200, "success", id);
    }
}
