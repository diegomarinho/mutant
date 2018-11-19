package br.com.dma.mutant.application;

import br.com.dma.mutant.application.reactor.StatsComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class StatsController {
    
    @Bean
    public RouterFunction statsExecute(StatsComponent statsComponent) {
        return route(GET("/stats"), statsComponent::getStats);
    }
}
