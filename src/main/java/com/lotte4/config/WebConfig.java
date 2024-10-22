package com.lotte4.config;

import com.lotte4.interceptor.AppInfoInterceptor;
import com.lotte4.service.admin.config.BannerService;
import com.lotte4.service.admin.config.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private AppInfo appInfo;

    @Autowired
    private InfoService infoService;

    @Autowired
    private BannerService bannerService;

    // 업로드된 이미지를 html에서 가져오기 위해서
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AppInfoInterceptor(appInfo, infoService, bannerService));

    }
}
