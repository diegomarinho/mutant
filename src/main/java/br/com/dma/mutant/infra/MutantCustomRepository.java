package br.com.dma.mutant.infra;

import br.com.dma.mutant.domain.Stats;
import reactor.core.publisher.Mono;

/**
 * @author diegomarinho
 */
public interface MutantCustomRepository {
    
    Mono<Stats> getStats();
}
