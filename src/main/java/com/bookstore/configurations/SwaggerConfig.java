package com.bookstore.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bookstore.data.SampleDiscountsData;
import com.bookstore.data.SampleStoreData;
import com.bookstore.repository.BookStoreRepository;
import com.bookstore.repository.CheckoutRepository;
import com.bookstore.repository.DiscountRepository;
import com.bookstore.repository.OrderBookDetailRepository;
import com.bookstore.service.BookStoreService;
import com.bookstore.service.CheckoutService;
import com.bookstore.service.impl.AssertServiceImpl;
import com.bookstore.service.impl.BookStoreServiceImpl;
import com.bookstore.service.impl.CheckoutServiceImpl;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Component
@Slf4j
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer  {



//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.bookstore.controllers")).build();
//    }
    
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bookstore.controllers"))
                .build();
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    
    private ApiInfo getApiInfo() {

        return new ApiInfoBuilder()
                .title("Swagger API Doc")
                .description("More description about the API")
                .version("1.0.0")
                .build();
    }
        
        
    @Bean
    public SampleStoreData injectInitialStoreLoader(BookStoreRepository bookStoreRepository){
        return new SampleStoreData(bookStoreRepository);
    }

    @Bean
    public SampleDiscountsData injectDiscountLoader(final DiscountRepository discountRepository){
        return new SampleDiscountsData(discountRepository);
    }

    @Bean
    public BookStoreService injectBookStoreService(BookStoreRepository bookStoreRepository){
        return new BookStoreServiceImpl(bookStoreRepository);
    }

    @Bean
    public AssertServiceImpl injectAssertService(
            BookStoreRepository bookStoreRepository, CheckoutRepository checkoutRepository,
            DiscountRepository discountRepository, OrderBookDetailRepository orderBookDetailRepository){
        return new AssertServiceImpl(bookStoreRepository,checkoutRepository,discountRepository,orderBookDetailRepository);
    }

    @Bean
    public CheckoutService injectCheckoutService(
            CheckoutRepository checkoutRepository, DiscountRepository discountRepository,
            BookStoreService bookStoreService, OrderBookDetailRepository orderBookDetailRepository){
        return new CheckoutServiceImpl(checkoutRepository, discountRepository, bookStoreService,orderBookDetailRepository);
    }

}
