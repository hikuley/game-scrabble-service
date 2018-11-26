## Game Scrabble Service with JavaEE

### Requirments;
1. Maven
2. Java 8

### How to start application;

you can run com.sahibinden.Main->  "main" metot.

### Test
Servislerin test edilmesi için postman linki: https://www.getpostman.com/collections/cb0da443a1cd368c31cc

### How to run test methods:
1. Postman üzerinden "createBoard" servisi çağrısı yapılır.
2. Postman üzerinden "play1" servisi çağrısı yapılır ve ilk hamle yapılır.
3. Postman üzerinden "drawBoard" servisi çağrısı yapılır ve console üzerinden boardın görsel halini görebilirsiniz.
4. Tekrardan hamle yapmak için  postman üzerinden "play2" servisi çağrılır.  

### Used Technologies:
1. Embeded Jetty server
2. Hibernate
3. RestEasy
4. H2 Database
