package com.gretro.petclinic;

import com.fasterxml.classmate.TypeResolver;
import com.gretro.petclinic.web.errors.ApiError;
import com.gretro.petclinic.web.errors.ApiValidationError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api(TypeResolver typeResolver, @Value("${petclinic.debug}") boolean isDebugMode) {
        var docket = new Docket(DocumentationType.SWAGGER_2)
            .produces(Set.of("application/json"))
            .select()
                .apis(RequestHandlerSelectors.basePackage("com.gretro.petclinic"))
                .paths(PathSelectors.any())
                .build()
            .useDefaultResponseMessages(false)
            .additionalModels(
                typeResolver.resolve(ApiError.class),
                typeResolver.resolve(ApiValidationError.class));

        setupGlobalResponseMessages(docket, isDebugMode);

        return docket;
    }

    private void setupGlobalResponseMessages(Docket docket, boolean isDebugMode) {
        var allVerbsMessages = new ArrayList<ResponseMessage>();
        allVerbsMessages.add(buildApiErrorMessage(HttpStatus.NOT_FOUND, false));
        allVerbsMessages.add(buildApiErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, false));

        if (isDebugMode) {
            allVerbsMessages.add(buildApiErrorMessage(HttpStatus.UNAUTHORIZED, true));
            allVerbsMessages.add(buildApiErrorMessage(HttpStatus.FORBIDDEN, true));
        }

        var bodyCapableVerbsMessages = new ArrayList<>(allVerbsMessages);
        bodyCapableVerbsMessages.add(new ResponseMessageBuilder()
            .responseModel(new ModelRef("ApiValidationError"))
            .code(400)
            .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .build());

        var mapping = Map.ofEntries(
            Map.entry(RequestMethod.GET, allVerbsMessages),
            Map.entry(RequestMethod.POST, bodyCapableVerbsMessages),
            Map.entry(RequestMethod.PUT, bodyCapableVerbsMessages),
            Map.entry(RequestMethod.DELETE, allVerbsMessages),
            Map.entry(RequestMethod.PATCH, bodyCapableVerbsMessages)
        );

        mapping.entrySet().stream()
            .forEach(entry -> {
                docket.globalResponseMessage(entry.getKey(), entry.getValue());
            });
    }

    private ResponseMessage buildApiErrorMessage(HttpStatus httpStatus, boolean debugOnly) {
        var message = debugOnly
                ? String.format("%s (debug only)", httpStatus.getReasonPhrase()) : httpStatus.getReasonPhrase();

        return new ResponseMessageBuilder()
            .responseModel(new ModelRef("ApiError"))
            .code(httpStatus.value())
            .message(message)
            .build();
    }
}
