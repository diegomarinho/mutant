# Mutant API

## Pré-requisitos:
  * ` Docker `
  * ` Docker-Compose `

## Rodar em ambiente local:
  ```
  $ ./gradlew clean build
  $ docker-compose up   
  ```

## Rodar em ambiente cloud (HEROKU):
  - URL`s:
  
    - ` https://mutantes.herokuapp.com/mutant `
    - ` https://mutantes.herokuapp.com/stats `

## Utilizando a Mutant API

* NO-MUTANT:
```
Request:
POST /mutant HTTP/1.1
Content-Type: application/json
Host: localhost:8080
Content-Length: 68
{"parameters": ["ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"]}

Response:
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
Content-Length: 0
{"result":403 Forbidden}
```

* MUTANT:
```
Request:
POST /mutant HTTP/1.1
Content-Type: application/json
Host: localhost:8080
Content-Length: 63
{"parameters": ["ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"]}

Response:
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
Content-Length: 0
{"result":200 OK}
```

* STATS:
```
Request:
GET /stats HTTP/1.1
Content-Type: application/json
Host: localhost:8080

Response:
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
Content-Length: 0
{"result":{ratio: 2,max_human_with_mutant_dna: 2,max_human_with_non_mutant_dna: 1}}
```
