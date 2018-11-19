package br.com.dma.mutant.application.reactor;

import br.com.dma.mutant.domain.Mutant;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

/**
 * @author diegomarinho
 */
@Component
public class MutantComponent {
    
    private MutantServiceImpl service;
    
    public MutantComponent(MutantServiceImpl service) {
        this.service = service;
    }
    
    public Mono<ServerResponse> getMutant(ServerRequest request) {
        return request.bodyToMono(Mutant.class)
            .flatMap(service::findOrCreateMutant)
            .map(Mutant::isMutant)
            .flatMap(isMutant -> {
                return isMutant ? ok().build() : status(FORBIDDEN).build();
            });
    }
}
