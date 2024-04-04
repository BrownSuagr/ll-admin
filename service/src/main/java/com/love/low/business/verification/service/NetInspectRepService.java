package com.love.low.business.verification.service;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.love.low.model.vo.CommonVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @name: NetInspectRepService
 * @description: 生成网络核查业务接口层
 * @author: BrownSugar
 * @date: 2024-03-25 08:15:00
 * @version: 1.0.0
 **/
public interface NetInspectRepService {

    /**
     * @description: 生成网络核查组报告
     * @param: [companyName, response]
     * @author: BrownSugar
     * @date: 2024-03-25 08:15:08
     **/
    void create(List<String> companyNameList, HttpServletResponse response);

    /**
     * @return {@code List<CommonVO> }
     * @description: 公司名称搜索联想
     * @param: [companyName]
     * @author: BrownSugar
     * @date: 2024-04-03 10:50:48
     **/
    List<CommonVO> companyNameList(String companyName);
}
