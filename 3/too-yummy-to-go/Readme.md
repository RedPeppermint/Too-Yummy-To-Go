DCO - Too Yummy To Go
---------------------


O projecto de DCO vale 6 dos 20 valores da cadeira. Para aprovação a DCO é necessário obter pelo menos 2.8 em 6, sendo esses 2.8 arredondados às décimas.

O projecto está dividido em quatro metas, cada uma representando avanços do projecto ao longo de cada sprint. Como estamos a seguir uma metodologia ágil, pretende-se ter algo a funcionar o mais rapidamente possível embora que não cumpra todos os requisitos.

Sobre o projecto:

**Nome:** Too Yummy To Go

**Cliente:** Docentes de DCO do DI-FCUL

**Descrição:** Uma aplicação que permite vender pacotes de produtos perecíveis que seriam desperdiçados de outra forma.




Extra 1: API MonsterCard
========================

1) Exemplo do uso da API para verificar a validade de um cartão:

```java
Card c = new Card("1234123412341234", "123", "05", "2021");
MonsterCardAPI m = new MonsterCardAPI();
boolean ok = m.isValid(c);
```

2) Exemplo do uso da API para cativar 10 euros no cartão:

```java
m.block(c, 10)
```


3) Exemplo do uso da API para cativar 10 euros no cartão:

```java
m.charge(c, 10)
```

Extra 2: API PortugueseExpress
==============================

1) Exemplo do uso da API para verificar a validade de um cartão:
```java
PortugueseExpress api = new PortugueseExpress();
api.setNumber("1234123412341234")
api.setCcv(123)
api.setMonth(5)
api.setYear(2018)
boolean ok = api.validate()
```
 
2) Exemplo do uso da API para cativar 10 euros no cartão:
```java
api.block(10);
```

3) Exemplo do uso da API para cativar 10 euros no cartão:
```java
api.charge(10);
```