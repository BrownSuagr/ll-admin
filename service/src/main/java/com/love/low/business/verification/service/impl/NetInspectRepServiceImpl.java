package com.love.low.business.verification.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.love.low.asynchronous.AsyncService;
import com.love.low.business.verification.service.NetInspectRepService;
import com.love.low.component.EnvironmentChecker;
import com.love.low.enmus.ExcelTemplate;
import com.love.low.entity.Company;
import com.love.low.entity.NetInspectRep;
import com.love.low.mapper.CompanyMapper;
import com.love.low.mapper.NetInspectRepMapper;
import com.love.low.model.dto.ExcelExportDTO;
import com.love.low.model.dto.NetInspectRepDTO;
import com.love.low.model.vo.CommonVO;
import com.love.low.service.ExcelService;
import lombok.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import static com.love.low.enmus.DataStatusEnum.VALID;


/**
 * @name: NetInspectRepServiceImpl
 * @description: 网络核查业务层实现
 * @author: BrownSugar
 * @date: 2024-03-25 08:15:58
 * @version: 1.0.0
 * @see NetInspectRepService
 **/
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class NetInspectRepServiceImpl implements NetInspectRepService {

    private static final Logger LOG = LoggerFactory.getLogger(NetInspectRepServiceImpl.class);
    final AsyncService asyncService;
    final ExcelService excelService;
    final CompanyMapper companyMapper;
    final EnvironmentChecker environmentChecker;
    final NetInspectRepMapper netInspectRepMapper;

    /**
     * @description: 生成网络核查组报告
     * @param: [companyName, response]
     * @author: BrownSugar
     * @date: 2024-03-25 08:16:15
     **/
    @Override
    public void create(List<String> companyNameList, HttpServletResponse response) {
        LOG.info("Interface：生成网络核查组报告 MethodName：create Params companyNameList:{}", companyNameList);
        List<ExcelExportDTO> dataList = Lists.newArrayList();

        // 循环获取数据
        LambdaQueryWrapper<NetInspectRep> qw = new LambdaQueryWrapper<>();
        qw.eq(NetInspectRep::getValidFlag, VALID.getCode()).orderByAsc(NetInspectRep::getId);
        List<NetInspectRep> netInspectRepList = netInspectRepMapper.selectList(qw);

        companyNameList.forEach(companyName ->{
            LinkedList<NetInspectRepDTO> list = Lists.newLinkedList();

            netInspectRepList.forEach(net ->{
                NetInspectRepDTO dto = new NetInspectRepDTO();
                BeanUtil.copyProperties(net, dto);
                dto.setSubject(companyName);
                dto.setCheckDate(DateUtil.format(DateUtil.date(), DatePattern.NORM_DATE_PATTERN));

//            Boolean flag = this.saveScreenCaptureFile(dto.getSiteUrl());
//            dto.setResultNum(flag ? "1" : "0");
                dto.setResultNum("0");
                list.add(dto);
            });

            ExcelExportDTO<NetInspectRepDTO> dto = new ExcelExportDTO();
            dto.setIndex(0);
            dto.setName(companyName);
            dto.setList(list);

            dataList.add(dto);
        });

        LOG.warn("查询结果:{}", JSONUtil.toJsonStr(dataList));
        //excelService.exportExcel(ExcelTemplate.ExtendedWarranty, dataList, response);
        excelService.exportExcel(ExcelTemplate.ExtendedWarranty, "", dataList);
    }

    /**
     * @return {@code List<CommonVO> }
     * @description: 公司名称搜索联想
     * @param: [companyName]
     * @author: BrownSugar
     * @date: 2024-04-03 10:50:52
     **/
    @Override
    public List<CommonVO> companyNameList(String companyName) {
        LOG.info("Interface：公司名称列表 MethodName：companyNameList Params companyName;{}", companyName);
        LambdaQueryWrapper<Company> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Company::getName, companyName).eq(Company::getValidFlag, VALID.getCode());
        List<Company> companyList = companyMapper.selectList(queryWrapper);

        return BeanUtil.copyToList(companyList, CommonVO.class)
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }


    /**
     * @return {@code Boolean }
     * @description: 获取目标截屏并返回文件名
     * @param: [url]
     * @author: BrownSugar
     * @date: 2024-04-01 05:30:20
     **/
    @Override
    public Boolean saveScreenCaptureFile(String url, String companyPath, String siteName) {
        final TimeInterval timer = new TimeInterval();
        if(environmentChecker.isDevEnvironment()){
            System.setProperty("webdriver.chrome.driver", "src/main/driver/mac_chromedriver");
        }else {
            System.setProperty("webdriver.chrome.driver", "src/main/driver/linux64_chromedriver");
        }

        ChromeOptions options = new ChromeOptions();
        /**
          * 这里是设置要执行的命令
          * --headless: 不提供可视化页面（无头模式）
          * --disable-gpu: 禁用GPU加速
          * --window-size: 修改截图页面的尺寸 "--window-size=1920,1200"
          * --ignore-certificate-errors:
          */
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1920", "--ignore-certificate-errors");
        options.addArguments("--disable-dev-shm-usage", "--no-sandbox");
        ChromeDriver driver = new ChromeDriver(options);

        // 1.加载chromedriver驱动
        //LOG.info("加载驱动：{}", driver);
        LOG.warn("网站名：{} 保存路径：{} 网址：{} ", siteName, companyPath, url);
        // 2.指定截屏页面
        driver.get(url);
        Long width = (Long)driver.executeScript("return document.documentElement.scrollWidth");
        Long height =(Long) driver.executeScript("return document.documentElement.scrollHeight");
        driver.manage().window().setSize(new Dimension(width.intValue(), height.intValue()));

        // 3.页面等待渲染时长，如果你的页面需要动态渲染数据的话一定要留出页面渲染的时间，单位默认是秒
        new WebDriverWait(driver, 5);
        // 4.获取到截图的文件字节
        byte[] byteArray = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(byteArray));
            // 确保image不为null，即字节数组确实是一个图片的有效表示
            if (Objects.nonNull(image)) {
                // 将BufferedImage保存为文件
                String filePath = companyPath + siteName + ".png";
//                File outputFile = new File("./documents/screenshot-2.png");
                File outputFile = new File(filePath);
                if (!outputFile.exists()) {
                    boolean result = outputFile.mkdirs();
                    if (result) {
                        LOG.warn("目录创建成功：{}" , outputFile.getAbsolutePath());
                    } else {
                        LOG.warn("目录创建失败。");
                    }
                }
                ImageIO.write(image, "png", outputFile);
            } else {
                LOG.info("字节数组不是有效的图片数据");
                return Boolean.FALSE;
            }
        } catch (IOException e) {
            LOG.info("截图文件获取异常: {}", e.getMessage());
            return Boolean.FALSE;
        }

        LOG.info("网址:[{}] 抓取耗时 {} ms",url, timer.intervalMs());
        return Boolean.TRUE;
    }
}
