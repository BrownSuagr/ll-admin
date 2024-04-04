//package com.love.low;
//
//import javafx.application.Application;
//import javafx.embed.swing.SwingFXUtils;
//import javafx.geometry.HPos;
//import javafx.geometry.VPos;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.image.WritableImage;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Priority;
//import javafx.scene.layout.Region;
//import javafx.scene.paint.Color;
////import javafx.scene.web.WebEngine;
////import javafx.scene.web.WebView;
//import javafx.stage.Stage;
//import javax.imageio.ImageIO;
//import java.awt.image.RenderedImage;
//import java.io.File;
//import java.io.IOException;
//
//// 需要继承 javafx.application.Application
//public class JavaFxDemo extends Application {
//    private Scene scene;
//    @Override
//    public void start(Stage stage) throws Exception {
//        // 创建画布
//        stage.setTitle("Web View");
//        // 设置场景
//        scene = new Scene(new Browser(), 750, 500, Color.web("#666970"));
//        stage.setScene(scene);
//        // 风格样式
//        scene.getStylesheets().add("webviewsample/BrowserToolbar.css");
//        stage.show();
//    }
//}
//
//
//// 需要继承 Region
//class Browser extends Region {
//    final WebView browser = new WebView();
//    // 浏览器引擎
//    final WebEngine webEngine = browser.getEngine();
//    public Browser() {
//        // 浏览器应用风格
//        getStyleClass().add("browser");
//        // 加载web页面
//        webEngine.load("http://www.oracle.com/products/index.html");
//        // 页面增加到引擎中
//        getChildren().add(browser);
//
//    }
//    private Node createSpacer() {
//        Region spacer = new Region();
//        HBox.setHgrow(spacer, Priority.ALWAYS);
//        return spacer;
//    }
//    @Override protected void layoutChildren() {
//        double w = getWidth();
//        double h = getHeight();
//        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
//    }
//    @Override protected double computePrefWidth(double height) {
//        return 750;
//    }
//    @Override protected double computePrefHeight(double width) {
//        return 500;
//    }
//}
//
//public class Application {
//    public static void main(String[] args) {
//        System.out.println("hello, world");
//        javaFxDemo.run("https://www.baidu.com");
//    }
//}
