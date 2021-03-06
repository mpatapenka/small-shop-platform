package by.underwear.shop.config;

import lombok.RequiredArgsConstructor;
import by.underwear.shop.UnderwearShopProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfig {
    private final UnderwearShopProperties appProperties;

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        UnderwearShopProperties.Contacts contacts = appProperties.getMetaInfo().getContacts();
        return new ApiInfoBuilder()
                .title(appProperties.getMetaInfo().getTitle())
                .description(appProperties.getMetaInfo().getDescription())
                .version(appProperties.getMetaInfo().getVersion())
                .contact(new Contact(contacts.getAppName(), contacts.getSiteUrl(), contacts.getEmail()))
                .build();
    }
}