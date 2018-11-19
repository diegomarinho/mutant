package br.com.dma.mutant.domain


import reactor.test.StepVerifier
import spock.lang.Specification
import spock.lang.Unroll

import static br.com.dma.mutant.infra.MutantStrand.verifyMutant
import static br.com.dma.mutant.infra.MutantStrand.verifyMutantWithHorizontal
import static br.com.dma.mutant.infra.MutantStrand.verifyMutantWithLeftDiagonal
import static br.com.dma.mutant.infra.MutantStrand.verifyMutantWithRightDiagonal
import static br.com.dma.mutant.infra.MutantStrand.verifyNonMutant
import static java.lang.Boolean.FALSE
import static java.lang.Boolean.TRUE

class MutantTest extends Specification {

    @Unroll
    def "given #human should identify mutation and return #isMutant" () {
        expect:
            StepVerifier.create(human.identifyMutation())
                .assertNext { it.isMutant() == isMutant }
                .verifyComplete()
        where:
            human                           | isMutant
            verifyMutant()                  | TRUE
            verifyMutantWithHorizontal()    | TRUE
            verifyMutantWithLeftDiagonal()  | TRUE
            verifyMutantWithRightDiagonal() | TRUE
            verifyNonMutant()               | FALSE
    }
}
