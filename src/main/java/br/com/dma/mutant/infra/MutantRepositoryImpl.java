package br.com.dma.mutant.infra;

import br.com.dma.mutant.domain.Stats;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.ConditionalOperators.when;
import static org.springframework.data.mongodb.core.query.Criteria.where;


/**
 * @author Diego Marinho
 */
@Repository
public class MutantRepositoryImpl implements MutantCustomRepository {
    
    private final ReactiveMongoTemplate mongo;

    public MutantRepositoryImpl(ReactiveMongoTemplate mongo) {
        this.mongo = mongo;
    }
    
    public Mono<Stats> getStats() {
        
        var condMutant = when(where("mutant").is(true)).then(1).otherwise(0);
        var condNonMutant = when(where("mutant").is(false)).then(1).otherwise(0);
        
        Aggregation aggregation = newAggregation(
            project()
                .and(condMutant).as("maxHumanWithMutantDna")
                .and(condNonMutant).as("maxHumanWithNonMutantDna")
                .and("1").as("aux"),
            group("$aux")
                .sum("maxHumanWithNonMutantDna").as("maxHumanWithNonMutantDna")
                .sum("maxHumanWithMutantDna").as("maxHumanWithMutantDna"),
            project("maxHumanWithMutantDna", "maxHumanWithNonMutantDna")
                .and("maxHumanWithMutantDna")
                .divide(when(where("maxHumanWithNonMutantDna").is(0)).then(1)
                    .otherwiseValueOf("maxHumanWithNonMutantDna"))
                .as("ratio")
                
        );
    
        return mongo
            .aggregate(aggregation, "mutants", Stats.class)
            .defaultIfEmpty(new Stats())
            .collectList()
            .map(x -> x.get(0));
    }
}
