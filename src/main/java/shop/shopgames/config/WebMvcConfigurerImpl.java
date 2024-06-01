package shop.shopgames.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfigurerImpl implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("logowanie");
        registry.addViewController("/url_error403").setViewName("error403");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mapowanie dla statycznych zasobów zewnętrznych
        registry.addResourceHandler("/external/**")
                .addResourceLocations("file:C:\\Users\\JaQob\\Desktop\\file"); // Ścieżka do zewnętrznych zasobów

//        // Mapowanie dla statycznych zasobów wewnątrz projektu
//        registry.addResourceHandler("/internal/**")
//                .addResourceLocations("classpath:/static/internal-resources/"); // Ścieżka do zasobów wewnątrz projektu
    }

}
