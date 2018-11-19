package br.com.dma.mutant.infra

import spock.lang.Specification
import spock.lang.Unroll

import static java.lang.Boolean.FALSE
import static java.lang.Boolean.TRUE

class DnaGeneTest extends Specification {

    @Unroll
    def 'given dnaSequence #dnaSequence should return #hasFourConsecutiveEqualChars'() {
        expect:
        DnaGene.hasDnaSequencingWithString(dnaSequence) == hasFourConsecutiveEqualChars
        where:
            dnaSequence | hasFourConsecutiveEqualChars
            "AAAALP"    | TRUE
            "PLOKIJ"    | FALSE
    }

    @Unroll
    def 'given #dna when getSequencesInColumns should return #result'() {
        expect:
            DnaGene.getDnaSequencingByColumns(dna).collectList().block().sort() == result.sort()
        where:
            dna                                                         | result
            ["ABCDEF","ABCDEF","ABCDEF","ABCDEF","ABCDEF","ABCDEF"]     | ["AAAAAA", "BBBBBB", "CCCCCC", "DDDDDD", "EEEEEE", "FFFFFF"]
    }

    @Unroll
    def 'given #dna when getSequencesInDiagonals should return #result'() {
        expect:
            DnaGene.getDnaSequencingByDiagonals(dna).collectList().block().sort() == result.sort()
        where:
            dna                                                         | result
            ["ABCDEF","FABCDE","EFABCD","DEFABC","CDEFAB","ABCDEF"]     | ["AAAAAF", "BBBBB", "CCCC", "DBFC", "DBFD", "ECAEB", "ECAEC", "EEED", "FDBFDA", "FFFFE"]
    }
}
