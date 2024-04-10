package com.love.low;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.love.low.business.verification.service.NetInspectRepService;
import com.love.low.enmus.ExcelTemplate;
import com.love.low.entity.NetInspectRep;
import com.love.low.mapper.NetInspectRepMapper;
import com.love.low.model.dto.ExcelExportDTO;
import com.love.low.model.dto.NetInspectRepDTO;
import com.love.low.service.ExcelService;
import nonapi.io.github.classgraph.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static com.love.low.enmus.DataStatusEnum.VALID;


@SpringBootTest
public class ServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceTest.class);


//    @Resource
//    private ScreenshotService screenshotService;
    @Resource
    private NetInspectRepMapper netInspectRepMapper;
    @Resource
    private ExcelService excelService;
    @Resource
    private NetInspectRepService netInspectRepService;

    @Test
    public void create() {
        List<String> companyNameList = List.of("华为", "字节跳动");

        if(CollUtil.isEmpty(companyNameList)){
            return;
        }
        LOG.info("Interface：生成网络核查组报告 MethodName：create Params companyNameList:{}", companyNameList);
        List<ExcelExportDTO> dataList = Lists.newArrayList();

        String path = "./documents/";

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

                String companyPath = path + companyName + "/";
                Boolean flag = netInspectRepService.saveScreenCaptureFile(dto.getSiteUrl(), companyPath , dto.getSiteName());
                dto.setResultNum(flag ? "1" : "0");
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
        excelService.exportExcel(ExcelTemplate.ExtendedWarranty, path, dataList);
    }





    @Test
    public void WebScreenshot(){


    }

    public static void main(String[] args) throws IOException {
        // 设置Chrome驱动路径
//        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");

        // /Users/BrownSugar/IDEAWorkPlace/loveLow/service/src/main/driver/mac_chromedriver
        // /Users/BrownSugar/IDEAWorkPlace/loveLow/       /src/main/driver/mac_chromedriver

        System.setProperty("webdriver.chrome.driver", "service/src/main/driver/mac_chromedriver");

        // 初始化Chrome浏览器实例
        ChromeOptions options = new ChromeOptions();
        //ssl证书支持
        options.setCapability("acceptSslCerts", true);
        //截屏支持
        options.setCapability("takesScreenshot", true);
        //css搜索支持
        options.setCapability("cssSelectorsEnabled", true);
        options.setHeadless(true);
//        WebDriver driver = new ChromeDriver(options);
        ChromeDriver driver = new ChromeDriver(options);

        // 打开网页
        driver.get("https://www.baidu.com");
        //获取高度和宽度一定要在设置URL之后，不然会导致获取不到页面真实的宽高；
        Long width = (Long)driver.executeScript("return document.documentElement.scrollWidth");
        Long height =(Long) driver.executeScript("return document.documentElement.scrollHeight");
        System.out.println("高度："+height);

        driver.manage().window().setSize(new Dimension(width.intValue(), height.intValue()));
        File screenshot = driver.getScreenshotAs(OutputType.FILE);

        // 截取网页屏幕截图
//        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        // 保存图片到文件
        ImageIO.write((BufferedImage)ImageIO.read(screenshot), "PNG", new File("../documents/screenshot.png"));

        // 关闭浏览器
        driver.quit();
    }


    public static void guge(String url){
        //这里设置下载的驱动路径，Windows对应chromedriver.exe Linux对应chromedriver，具体路径看你把驱动放在哪
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\86186\\Desktop\\phantomjs-2.1.1-windows\\bin\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        //ssl证书支持
        options.setCapability("acceptSslCerts", true);
        //截屏支持
        options.setCapability("takesScreenshot", true);
        //css搜索支持
        options.setCapability("cssSelectorsEnabled", true);
        options.setHeadless(true);
        ChromeDriver driver = new ChromeDriver(options);
        try {
            //设置需要访问的地址
            driver.get(url);
            //获取高度和宽度一定要在设置URL之后，不然会导致获取不到页面真实的宽高；
            Long width = (Long)driver.executeScript("return document.documentElement.scrollWidth");
            Long height =(Long) driver.executeScript("return document.documentElement.scrollHeight");
            System.out.println("高度："+height);
            //设置窗口宽高，设置后才能截全
            driver.manage().window().setSize(new Dimension(width.intValue(), height.intValue()));
            //设置截图文件保存的路径
            String screenshotPath = "C:\\Users\\86186\\Desktop\\phantomjs-2.1.1-windows\\bin\\img\\imgGG.png";
            File srcFile = driver.getScreenshotAs(OutputType.FILE);

            //FileUtils.copyFile(srcFile, new File(screenshotPath));


        }catch (Exception e){
            throw new RuntimeException("截图失败",e);
        }finally {
            driver.quit();
        }
    }


}
