package br.com.dma.mutant.application.reactor


import br.com.dma.mutant.domain.Mutant
import br.com.dma.mutant.infra.MutantRepository
import reactor.core.publisher.Mono
import spock.lang.Specification

class MutantServiceImplTest extends Specification{
    
    private MutantServiceImpl service
    private MutantRepository repositoryMock

    void setup() {
        repositoryMock = Mock(MutantRepository)
        service = new MutantServiceImpl(repositoryMock)
    }

    def 'should identify mutation if human does not exists' () {
        given:
            Mutant humanMock = Mock(Mutant)
        when:
            service.findOrCreateMutant(humanMock).block()
        then:
            1 * humanMock.getDna() >> ["ABCDEF"]
            1 * repositoryMock.findById(_ as Integer) >> Mono.empty()
            1 * humanMock.identifyMutation() >> Mono.just(humanMock)
            1 * humanMock.fillId() >> {}
            1 * repositoryMock.save(_ as Mutant) >> Mono.just(humanMock)
    }

    def 'should not identify mutation if human exists' () {
        given:
            Mutant humanMock = Mock(Mutant)
        when:
            service.findOrCreateMutant(humanMock).block()
        then:
            1 * humanMock.getDna() >> ["ABCDEF"]
            1 * repositoryMock.findById(*_) >> Mono.just(humanMock)
            0 * humanMock.identifyMutation() >> Mono.just(humanMock)
            1 * humanMock.fillId() >> {}
            1 * repositoryMock.save(_ as Mutant) >> Mono.just(humanMock)
    }

}
