package br.com.dma.mutant.domain;

import br.com.dma.mutant.infra.DnaGene;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.join;
import static reactor.core.publisher.Flux.fromIterable;
import static reactor.core.publisher.Mono.just;
import static reactor.core.scheduler.Schedulers.elastic;

/**
 * @author Diego Marinho
 */
@Data
@Document(collection = "mutants")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mutant {
    
    @Id
    private Integer id;
    private List<String> dna;
    private Boolean mutant;
    
    public Boolean isMutant() {
        return mutant;
    }
    
    public void fillId() {
        this.id = join("", this.dna).hashCode();
    }
    
    public Mono<Mutant> identifyMutation() {
        if (mutant == null) {
            return isMutant(dna)
                .map(isMutant -> {
                    mutant = isMutant;
                    return this;
                });
        } else return just(this);
    }
    
    private Mono<Boolean> isMutant(List<String> dna) {
        return getAllDnaSequences(dna)
            .parallel()
            .runOn(elastic())
            .filter(DnaGene::hasDnaSequencingWithString)
            .sequential()
            .collectList()
            .map(this::hasMinimumCollectionMatches);
    }
    
    private Flux<String> getAllDnaSequences(List<String> dna) {
        return fromIterable(dna)
            .mergeWith(DnaGene.getDnaSequencingByColumns(dna))
            .mergeWith(DnaGene.getDnaSequencingByDiagonals(dna));
    }
    
    private Boolean hasMinimumCollectionMatches(List<String> matches) {
        return matches.size() >= 2 ? TRUE : FALSE;
    }
    
}
