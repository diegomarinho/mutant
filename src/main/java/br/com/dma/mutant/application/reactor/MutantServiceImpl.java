package br.com.dma.mutant.application.reactor;

import br.com.dma.mutant.domain.Mutant;
import br.com.dma.mutant.infra.MutantRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.lang.String.join;
import static reactor.core.publisher.Mono.defer;

/**
 * @author diegomarinho
 */
@Service
public class MutantServiceImpl {
    
    private MutantRepository repository;
    
    public MutantServiceImpl(MutantRepository repository) {
        this.repository = repository;
    }
    
    public Mono<Mutant> findOrCreateMutant(Mutant mutant) {
        return Mono.justOrEmpty(mutant)
            .flatMap(it -> findByDna(mutant.getDna()))
            .switchIfEmpty(defer(mutant::identifyMutation))
            .doOnNext(Mutant::fillId)
            .flatMap(repository::save);
    }
    
    private Mono<Mutant> findByDna(List<String> dna) {
        return repository.findById(join("", dna).hashCode());
    }
    
}
