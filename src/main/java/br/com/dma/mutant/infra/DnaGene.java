package br.com.dma.mutant.infra;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import static java.lang.String.join;
import static java.util.regex.Pattern.compile;
import static reactor.core.scheduler.Schedulers.elastic;

/**
 * @author diegomarinho
 */
public class DnaGene {
    
    private static final Pattern PATTERN = compile("(\\w)\\1{3}");
    
    public static Boolean hasDnaSequencingWithString(String dnaSequence) {
        return PATTERN.matcher(dnaSequence).find();
    }

    public DnaGene() {
        super();
    }

    public static Flux<String> getDnaSequencingByColumns(List<String> dna) {
        return Flux.range(0, dna.size())
            .parallel()
            .runOn(elastic())
            .flatMap(x -> getDirectionalColumn(x, dna))
            .sequential();
    }
    
    public static Flux<String> getDnaSequencingByDiagonals(List<String> dna) {
        var loopRange = (dna.size()*2)-1;
        
        var rightDiagonal = Flux.range(0-dna.size(), loopRange)
            .parallel()
            .runOn(elastic())
            .flatMap(x -> getDirectionalDiagonal(x, dna, DnaGene::getAndIncrement))
            .sequential();
        
        var leftDiagonal = Flux.range(0, loopRange)
            .parallel()
            .runOn(elastic())
            .flatMap(x -> getDirectionalDiagonal(x, dna, DnaGene::getAndDecrement))
            .sequential();
        
        return rightDiagonal.mergeWith(leftDiagonal);
    }
    
    private static Mono<String> getDirectionalColumn(Integer columnIndex, List<String> dna) {
        return Flux.fromIterable(dna)
            .map(dnaSequence -> dnaSequence.substring(columnIndex, columnIndex+1))
            .collectList()
            .map(cells -> join("", cells));
    }
    
    private static Mono<String> getDirectionalDiagonal(Integer columnIndex, List<String> dna, BiFunction<AtomicInteger, String, Mono<String>> getCellAndMoveIndex) {
        var index = new AtomicInteger(columnIndex);
        
        return Flux.fromIterable(dna)
            .flatMap(dnaSequence -> getCellAndMoveIndex.apply(index, dnaSequence))
            .collectList()
            .map(cells -> join("", cells))
            .filter(dnaSequence -> dnaSequence.length() >= 4);
    }
    
    private static Mono<String> getAndDecrement(AtomicInteger index, String dnaSequence) {
        var dna = getIndex(index, dnaSequence);
        index.getAndDecrement();
        return dna;
    }
    
    private static Mono<String> getAndIncrement(AtomicInteger index, String dnaSequence) {
        var dna = getIndex(index, dnaSequence);
        index.getAndIncrement();
        return dna;
    }
    
    private static Mono<String> getIndex(AtomicInteger index, String dnaSequence) {
        if (index.get() >= 0 && index.get() < dnaSequence.length()) {
            var value = dnaSequence.substring(index.get(), index.get() + 1);
            return Mono.just(value);
        } else {
            return Mono.empty();
        }
    }
}
