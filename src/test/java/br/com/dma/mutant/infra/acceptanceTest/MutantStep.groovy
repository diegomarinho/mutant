package br.com.dma.mutant.infra.acceptanceTest

import br.com.dma.mutant.MutantApplication
import br.com.dma.mutant.infra.MutantStrand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(classes = MutantApplication, webEnvironment = RANDOM_PORT)
@ContextConfiguration(loader = SpringBootContextLoader, classes = MutantApplication)
@ActiveProfiles("test")
class MutantStep extends Specification {

    @Autowired
    WebTestClient webTestClient

    def 'given mutant dna should return 200 OK'() {
        when:
            def exchange = webTestClient
                .post()
                .uri("/mutant")
                .body(BodyInserters.fromObject(MutantStrand.verifyMutant()))
                .exchange()
        then:
            exchange.expectStatus().isOk()
    }

    def 'given mutant dna should return 403 FORBIDDEN'() {
        when:
            def exchange = webTestClient
                .post()
                .uri("/mutant")
                .body(BodyInserters.fromObject(MutantStrand.verifyNonMutant()))
                .exchange()
        then:
            exchange.expectStatus().isForbidden()
    }
}
