package com.love.low;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class ScreenshotService {

    private static final Logger LOG = LoggerFactory.getLogger(ScreenshotService.class);

    private static WebDriver driver;
    @PostConstruct
    private void initDriver(){
        ChromeOptions options = new ChromeOptions();
        /*
         * 这里是设置要执行的命令
         * --headless: 不提供可视化页面（无头模式）
         * --disable-gpu: 禁用GPU加速
         * --window-size: 修改截图页面的尺寸 "--window-size=1920,1200"
         * --ignore-certificate-errors:
         */
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1920", "--ignore-certificate-errors");
        options.addArguments("--disable-dev-shm-usage", "--no-sandbox");
        driver = new ChromeDriver(options);
    }

    /**
     * 根据网络url获取网络截屏的字节数组
     * @param url 网络url
     * @return 字节数组
     */
    private static synchronized byte[] getFileByteArry(String url){

        ChromeOptions options = new ChromeOptions();
        /*
         * 这里是设置要执行的命令
         * --headless: 不提供可视化页面（无头模式）
         * --disable-gpu: 禁用GPU加速
         * --window-size: 修改截图页面的尺寸 "--window-size=1920,1200"
         * --ignore-certificate-errors:
         */
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1920", "--ignore-certificate-errors");
        options.addArguments("--disable-dev-shm-usage", "--no-sandbox");
        ChromeDriver d = new ChromeDriver(options);

        // 1.打印chromedriver驱动
        LOG.info("[页面抓取]- {}", d);
        long startTime = System.currentTimeMillis();
        // 2.加载web页面
        d.get(url);
        // 3.页面等待渲染时长，如果你的页面需要动态渲染数据的话一定要留出页面渲染的时间，单位默认是秒
        new WebDriverWait(d, 5);
        // 4.获取到截图的文件字节
        byte[] byteArry = ((TakesScreenshot) d).getScreenshotAs(OutputType.BYTES);
        LOG.info("[页面抓取]ChromeDriver处理结束用时{}s, Title:{}", DateUtil.format(DateUtil.date(), DatePattern.NORM_DATETIME_PATTERN), driver.getTitle());
        return byteArry;
    }

    public static void main(String[] args) throws MalformedURLException, IOException, URISyntaxException, AWTException {
        //此方法仅适用于JdK1.6及以上版本
        Desktop.getDesktop().browse(new URL("https://www.baidu.com/").toURI());
        Robot robot = new Robot();
        robot.delay(10000);
        java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Dimension d = new Dimension(screenSize.width, screenSize.height);
        int width = (int) d.getWidth();
        int height = (int) d.getHeight();
        //最大化浏览器
        robot.keyRelease(KeyEvent.VK_F11);
        robot.delay(2000);
        Image image = robot.createScreenCapture(new Rectangle(0, 0, width,
                height));
        BufferedImage bi = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        //保存图片
        ImageIO.write(bi, "jpg", new File("./documents/baidu.jpg"));
    }

    public static void mains(String[] args) {
        System.setProperty("webdriver.chrome.driver", "service/src/main/driver/mac_chromedriver");
        ChromeOptions options = new ChromeOptions();
        /*
         * 这里是设置要执行的命令
         * --headless: 不提供可视化页面（无头模式）
         * --disable-gpu: 禁用GPU加速
         * --window-size: 修改截图页面的尺寸 "--window-size=1920,1200"
         * --ignore-certificate-errors:
         */
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1920", "--ignore-certificate-errors");
        options.addArguments("--disable-dev-shm-usage", "--no-sandbox");
        ChromeDriver d = new ChromeDriver(options);

        // 1.打印chromedriver驱动
        LOG.info("[页面抓取]- {}", d);
        long startTime = System.currentTimeMillis();
        // 2.加载web页面
//        d.get("https://www.baidu.com");
        d.get("http://www.黑糖.site/home");

        Long width = (Long)d.executeScript("return document.documentElement.scrollWidth");
        Long height =(Long) d.executeScript("return document.documentElement.scrollHeight");
        System.out.println("高度："+height);

        d.manage().window().setSize(new Dimension(width.intValue(), height.intValue()));

//        File screenshot = d.getScreenshotAs(OutputType.FILE);

        // 3.页面等待渲染时长，如果你的页面需要动态渲染数据的话一定要留出页面渲染的时间，单位默认是秒
        new WebDriverWait(d, 5);
        // 4.获取到截图的文件字节
        byte[] byteArry = ((TakesScreenshot) d).getScreenshotAs(OutputType.BYTES);

        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(byteArry));

            // 确保image不为null，即字节数组确实是一个图片的有效表示
            if (image != null) {
                // 将BufferedImage保存为文件
                File outputFile = new File("./documents/screenshot-2.png");
                ImageIO.write(image, "png", outputFile);
            } else {
                System.out.println("字节数组不是有效的图片数据");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOG.info("[页面抓取]ChromeDriver处理结束用时{}s, Title:{}", DateUtil.format(DateUtil.date(), DatePattern.NORM_DATETIME_PATTERN), "");


    }

}
