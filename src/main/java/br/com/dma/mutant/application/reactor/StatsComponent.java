package br.com.dma.mutant.application.reactor;

import br.com.dma.mutant.domain.Stats;
import br.com.dma.mutant.infra.MutantRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author diegomarinho
 */
@Component
public class StatsComponent {
    
    private MutantRepository mutantRepository;
    
    public StatsComponent(MutantRepository mutantRepository) {
        this.mutantRepository = mutantRepository;
    }
    
    public Mono<ServerResponse> getStats(ServerRequest request) {
        return ok().body(mutantRepository.getStats(), Stats.class);
    }
}
