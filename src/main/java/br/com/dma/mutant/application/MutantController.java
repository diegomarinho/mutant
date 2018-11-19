package br.com.dma.mutant.application;

import br.com.dma.mutant.application.reactor.MutantComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author diegomarinho
 */
@Configuration
public class MutantController {
    
    @Bean
    public RouterFunction mutantExecute(MutantComponent mutantComponent) {
        return route(POST("/mutant").and(accept(APPLICATION_JSON)), mutantComponent::getMutant);
    }
}


