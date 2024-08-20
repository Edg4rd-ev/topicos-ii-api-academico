# Documentação
<https://documenter.getpostman.com/view/25360327/2sA3s7jUKg>

Habilitar a Documentação via Swagger

1) Acesse o site abaixo:
https://springdoc.org/

2) Copie a dependência abaixo e cole no pom.xml do seu projeto	
		

3) Execute o seu projeto e após iniciado, acesso a url abaixo: 
http://localhost:8080/api/academico/swagger-ui/index.html

----------------------------------------------------------------------------------------------------------------------
Customização

1) Criem uma nova Packager de nome documentation dentro de config
2) Crie uma classe de nome SwaggerConfig 
3) Copie o código abaixo e cole dentro da sua classe

package br.edu.ifs.apiacademico.config.documentation;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração para a documentação da API usando Swagger.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configuração do OpenAPI para a API Acadêmico.
     *
     * @return Uma instância de OpenAPI configurada para a API Acadêmico.
     */
    @Bean
    public OpenAPI sysNewSigaaOpenAPI(){
        return new OpenAPI()
                .info(new Info().title("Acadêmico API")
                        .description("Api do Projeto Corporativo do Acadêmico")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Acadêmico Wiki Documentation")
                        .url("https://acadamico.github.org/docs"));
    }
}
