package com.mr.demo.scurity.demo.core.springdoc;

import com.mr.demo.scurity.demo.api.exceptionhandler.Problem;
import com.mr.demo.scurity.demo.api.exceptionhandler.ProblemType;
import com.mr.demo.scurity.demo.core.constante.Constates;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
@SecurityScheme(name = "security_auth",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(authorizationCode = @OAuthFlow(
                authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}",
                tokenUrl = "${springdoc.oAuthFlow.tokenUrl}",
                scopes = {
                        @OAuthScope(name = "READ", description = "read scope"),
                        @OAuthScope(name = "WRITE", description = "write scope")
                }
        )))
public class SpringDocConfig {

    private static final String badRequestResponse = "BadRequestResponse";
    private static final String notFoundResponse = "NotFoundResponse";
    private static final String notAcceptableResponse = "NotAcceptableResponse";
    private static final String internalServerErrorResponse = "InternalServerErrorResponse";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info
                        (new Info()
                                .title("Biblioteca API")
                                .version("v1")
                                .description("REST API de Biblioteca")
                                .contact(new Contact()
                                        .name("Matheus Ribeiro")
                                        .email("matheus.ribeiro@biblioteca.com")
                                        .url("https://biblioteca.com/contato")
                                )
                                .license(new License()
                                        .name("Apache 2.0")
                                        .url("https://www.apache.org/licenses/LICENSE-2.0")
                                )
                        )
                .externalDocs(new ExternalDocumentation()
                        .description("MRSOFT - Biblioteca")
                        .url("https://www.mrsoft.com.br/produtcs/biblioteca")
                ).tags(Arrays.asList(
                                new Tag().name("Livros").description("Gerencia os Livros")
                        )
                ).components(new Components()
                        .schemas(gerarSchemas())
                        .responses(gerarResponses())
                );
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            openApi.getPaths()
                    .values()
                    .forEach(pathItem -> pathItem.readOperationsMap()
                            .forEach((httpMethod, operation) -> {
                                ApiResponses responses = operation.getResponses();
                                switch (httpMethod) {
                                    case GET:
                                        responses.addApiResponse("404", new ApiResponse().$ref(notFoundResponse));
                                        responses.addApiResponse("406", new ApiResponse().$ref(notAcceptableResponse));
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;
                                    case POST:
                                        responses.addApiResponse("400", new ApiResponse().$ref(badRequestResponse));
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;
                                    case PUT:
                                        responses.addApiResponse("404", new ApiResponse().$ref(notFoundResponse));
                                        responses.addApiResponse("400", new ApiResponse().$ref(badRequestResponse));
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;
                                    case DELETE:
                                        responses.addApiResponse("404", new ApiResponse().$ref(notFoundResponse));
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;
                                    default:
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;
                                }
                            })
                    );
        };
    }

    private Map<String, Schema> gerarSchemas() {
        final Map<String, Schema> schemaMap = new HashMap<>();

        Map<String, Schema> problemSchema = ModelConverters.getInstance().read(Problem.class);
        Map<String, Schema> problemObjectSchema = ModelConverters.getInstance().read(Problem.Object.class);

        schemaMap.putAll(problemSchema);
        schemaMap.putAll(problemObjectSchema);

        return schemaMap;
    }

    private Map<String, ApiResponse> gerarResponses() {
        final Map<String, ApiResponse> apiResponseMap = new HashMap<>();

        Content content = new Content()
                .addMediaType(APPLICATION_JSON_VALUE,
                        new MediaType().schema(new Schema<Problem>().$ref("Problema")));

        Map<String, Object> exemploResponseInternalServerError = montaMapaExemploResponseInternalServerError();
        Map<String, Object> exemploResponseNotFound = montaMapaExemploResponseNotFound();

        Content internalServerErrorContent = new Content()
                .addMediaType(APPLICATION_JSON_VALUE,
                        new MediaType()
                                .schema(new Schema<Problem>().$ref("Problema"))
                                .example(exemploResponseInternalServerError)
                );

        Content notFoundContent = new Content()
                .addMediaType(APPLICATION_JSON_VALUE,
                        new MediaType()
                                .schema(new Schema<Problem>().$ref("Problema"))
                                .example(exemploResponseNotFound)
                );

        apiResponseMap.put(badRequestResponse, new ApiResponse()
                .description("Requisição inválida")
                .content(content));

        apiResponseMap.put(notFoundResponse, new ApiResponse()
                        .description("Recurso não encontrado")
                        .content(notFoundContent));

        apiResponseMap.put(notAcceptableResponse, new ApiResponse()
                .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                .content(content));

        apiResponseMap.put(internalServerErrorResponse, new ApiResponse()
                .description("Erro interno no servidor")
                .content(internalServerErrorContent));

        return apiResponseMap;
    }

    private Map<String, Object> montaMapaExemploResponseInternalServerError() {
        Map<String, Object> exemploResponse = new LinkedHashMap<>();
        exemploResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        exemploResponse.put("title", ProblemType.ERRO_DE_SISTEMA.getTitle());
        exemploResponse.put("type", ProblemType.ERRO_DE_SISTEMA.getUri());
        exemploResponse.put("userMessage", Constates.MSG_ERRO_GENERICA_USUARIO_FINAL);
        exemploResponse.put("detail", Constates.MSG_ERRO_GENERICA_USUARIO_FINAL);
        exemploResponse.put("timestamp", "2022-07-15T11:21:50.902245498Z");

        return exemploResponse;
    }

    private Map<String, Object> montaMapaExemploResponseNotFound() {
        Map<String, Object> exemploResponse = new LinkedHashMap<>();
        exemploResponse.put("status", HttpStatus.NOT_FOUND.value());
        exemploResponse.put("title", ProblemType.RECURSO_NAO_ENCONTRADO.getTitle());
        exemploResponse.put("type", ProblemType.RECURSO_NAO_ENCONTRADO.getUri());
        exemploResponse.put("userMessage", Constates.MSG_ERRO_NOT_FOUND);
        exemploResponse.put("detail", Constates.MSG_ERRO_NOT_FOUND);
        exemploResponse.put("timestamp", "2022-07-15T11:21:50.902245498Z");

        return exemploResponse;
    }
}
