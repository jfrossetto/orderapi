# Processamento de Pedidos
## Serviço OrderBatch

### Stack:
* Java21 + Spring webflux : java por ser a linguagem que domino mais, webflux por conta da otimização de recursos principalmente em sistemas com alta demanda de i/o

### Arquitetura:

* Arquitetura principal esta descrita no projeto OrderBatch

### Para Executar:

* descrito no projeto orderbatch (o orderapi sobe junto no docker-compose)
* para chamar as apis na pasta postman do projeto tem uma collection que pode ser importada no postman
* ou via curl:
  * upload , no upload é importate informar o parametro recordType como orderRecord, isso será usado no OrderBatch para identificar o processador correto do tipo de arquivo
  ```
  curl --location 'localhost:8080/v1/upload' 
  --form 'recordType="orderRecord"' 
  --form 'file=@"/home/jfr/work/java/mglu/data_1.txt"'
  ```
  * get by id (orderId)
  ```
  curl --location --request GET 'localhost:8080/v1/order/492' 
  --form 'recordType="orderRecord"' 
  --form 'file=@"/home/jfr/work/java/mglu/data_1.txt"'
  ```
  * get by filters (userId, productId, startOrderDate, endOrderDate)
  ```
  curl --location --request GET 'localhost:8080/v1/order/orders?productId=2&startOrderDate=2021-10-01&endOrderDate=2021-04-01&userId=1' \
  --form 'recordType="orderRecord"' \
  --form 'file=@"/home/jfr/work/java/mglu/data_1.txt"'  
  ```
 
### Cobertura dos testes:

* plugin do jacoco para gradle, configurado com 80% cobertura (apenas para as classes principais)

![alt text](https://github.com/jfrossetto/orderapi/blob/master/coverage.png?raw=true)


