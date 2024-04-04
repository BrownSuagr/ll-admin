package com.love.low.business.verification.controller;

import com.love.low.business.verification.service.NetInspectRepService;
import com.love.low.model.vo.CommonVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/inspect")
@Api(value = "网络核查组报告管理",tags = {"工具管理-网络核查组报告管理"})
@RequiredArgsConstructor
public class NetInspectRepController {
    final NetInspectRepService netInspectRepService;

    @ApiOperation(value = "生成网络核查组报告", httpMethod = "POST", notes = "导出网络核查组报告Excel",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiImplicitParam(name = "companyNameList", value = "公司名称集合", paramType = "query", allowMultiple = true, dataType = "String", required = true)
    public void create(@ApiParam(value = "公司名称集合" , required = true, defaultValue = "华为,小米") @RequestParam(name ="companyNameList") List<String> companyNameList, HttpServletResponse response) {
        netInspectRepService.create(companyNameList, response);
    }

    @RequestMapping(value = "/company/comboBox/{companyName}", method = RequestMethod.GET)
    @ApiOperation("公司名称搜索联想")
    public List<CommonVO> companyNameList(@PathVariable("companyName") String companyName) {
        return netInspectRepService.companyNameList(companyName);
    }
}
