package com.deval.gestiondestock.config;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static com.deval.gestiondestock.utils.Constants.APP_ROOT;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
/*    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .description("Gestion de stock API Documentation")
                        .title("Gestion de stock REST API")
                        .build()
                )
                .groupName("REST API V1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.deval.gestiondestock"))
                .paths(PathSelectors.ant(APP_ROOT + "/.**"))
                .build();
    }*/

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.deval.gestiondestock"))
               // .paths(PathSelectors.regex("/.*"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfoMetaData())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfoMetaData() {

        return new ApiInfoBuilder()
                .title("Gestion de stock REST API")
                .description("Gestion de stock API Documentation")
                .contact(new Contact("Devalère-Team", "https://www.dev-team.com/", "devalerek@gmail.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }

    private ApiKey apiKey(){
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList( new SecurityReference("JWT", authorizationScopes) );
    }
}
