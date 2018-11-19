package br.com.dma.mutant.infra.acceptanceTest

import br.com.dma.mutant.MutantApplication
import br.com.dma.mutant.domain.Stats
import br.com.dma.mutant.infra.MutantRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import spock.lang.Specification

import static br.com.dma.mutant.infra.MutantStrand.verifyMutant
import static br.com.dma.mutant.infra.MutantStrand.verifyMutantWithLeftDiagonal
import static br.com.dma.mutant.infra.MutantStrand.verifyMutantWithRightDiagonal
import static br.com.dma.mutant.infra.MutantStrand.verifyNonMutant
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static reactor.core.publisher.Flux.fromIterable

@SpringBootTest(classes = MutantApplication, webEnvironment = RANDOM_PORT)
@ContextConfiguration(loader = SpringBootContextLoader, classes = MutantApplication)
@ActiveProfiles("test")
class StatsStep extends Specification {

    @Autowired
    private MutantRepository repository

    void setup() {
        def create = fromIterable(
            [verifyMutant(),
             verifyNonMutant(),
             verifyMutantWithRightDiagonal(),
             verifyMutantWithLeftDiagonal()
            ]
        )
        .flatMap { it.identifyMutation() }
        .flatMap {
            it.fillId()
            repository.save(it)
        }
        .doOnNext {
            println "saved $it"
        }

        repository
            .deleteAll()
            .thenMany(create)
            .collectList()
            .block()
    }

    @Autowired
    WebTestClient webTestClient

    def 'should return stats given database'() {
        when:
            def exchange = webTestClient
                .get()
                .uri("/stats")
                .exchange()
        then:
            exchange
                .expectBody(Stats)
                .isEqualTo(new Stats(3, 1, 3.0))
    }


}
