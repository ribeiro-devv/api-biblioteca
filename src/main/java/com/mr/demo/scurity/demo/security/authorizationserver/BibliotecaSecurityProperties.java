package com.mr.demo.scurity.demo.security.authorizationserver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

@Component
@Getter
@Setter
@Validated
@ConfigurationProperties("biblioteca.auth")
public class BibliotecaSecurityProperties {

    @NotBlank
    private String providerUrl;

}
