package itn.issuemanager.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(
		basePackages={"itn.issuemanager.*"}
)
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(WebMvcConfig.class);

	
	@Bean
	public LoggingInterceptor loggingInterceptor(){
		return new LoggingInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		log.debug("interceptor add");
		registry.addInterceptor(loggingInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/js/**")
				.excludePathPatterns("/","/user/login","/user/new");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
		registry.addResourceHandler("/img/**").addResourceLocations("/img/");
	}
}
