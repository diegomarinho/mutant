package br.com.dma.mutant.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author diegomarinho
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stats {
    @JsonProperty("max_human_with_mutant_dna")
    private Integer maxHumanWithMutantDna = 0;

    @JsonProperty("max_human_with_non_mutant_dna")
    private Integer maxHumanWithNonMutantDna = 0;

    private Double ratio = 0.0;
}
