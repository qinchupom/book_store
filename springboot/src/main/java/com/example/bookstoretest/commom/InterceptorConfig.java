package com.example.bookstoretest.commom;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器注入
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor())
                .addPathPatterns("/**")// 拦截所有的路径
                .excludePathPatterns("/swagger**/**",
                        "/webjars/**",
                        "/v3/**",
                        "/doc.html");// 排除swagger
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/bookstoretest/**")
                // 设置允许跨域请求的域名
                // 当**Credentials为true时，**Origin不能为星号，需为具体的ip地址【如果接口不带cookie,ip无需设成具体ip】
                .allowedOrigins("http://127.0.0.1:3000", "http://localhost:3000")
                // 是否允许证书 不再默认开启
                .allowCredentials(true)
                // 设置允许的方法
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // 跨域允许时间
                .maxAge(5*60)
                // 允许头部设置
                .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept");
    }
}
