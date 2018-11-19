package br.com.dma.mutant.infra;

import br.com.dma.mutant.domain.Mutant;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * @author diegomarinho
 */
public interface MutantRepository extends ReactiveMongoRepository<Mutant, String>, MutantCustomRepository {
    
    Mono<Mutant> findById(Integer id);
}
