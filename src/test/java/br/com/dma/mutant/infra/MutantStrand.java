package br.com.dma.mutant.infra;

import br.com.dma.mutant.domain.Mutant;

import java.util.List;

public class MutantStrand {
    
    public static Mutant verifyMutant() {
        return Mutant
            .builder()
            .dna(List.of(
                "EBCDEF",
                "HGBCDC",
                "TRPBCC",
                "AHOLBC",
                "AHOLBC",
                "FGHIJK"))
            .build();
    }

    public static Mutant verifyNonMutant() {
        return Mutant
            .builder()
            .dna(List.of(
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGABGG",
                "CBCCTA",
                "TCACTG"))
            .build();
    }
    
    public static Mutant verifyMutantWithLeftDiagonal() {
        return Mutant
            .builder()
            .dna(List.of(
                "ALKJIH",
                "BLJEHG",
                "CJAZXV",
                "JQBAVU",
                "EPOVML",
                "FGVIJK"))
            .build();
    }
    
    public static Mutant verifyMutantWithRightDiagonal() {
        return Mutant
            .builder()
            .dna(List.of(
                "ALUJIH",
                "BLLUHG",
                "CJALUV",
                "JQBALU",
                "EPOVML",
                "FGVIJK"))
            .build();
    }
    
    public static Mutant verifyMutantWithHorizontal() {
        return Mutant
            .builder()
            .dna(List.of(
                "ATAAAA",
                "CAGTGC",
                "TTATGT",
                "AAGGGG",
                "CBCCTA",
                "TCACTG"))
            .build();
    }
}
