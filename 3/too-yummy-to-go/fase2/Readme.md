Too Yummi To Go - Meta 2
========================


**Deadline:** 15 de Abril de 2019, 23:55, Hora de Lisboa

**Equipa:** O trabalho deve ser feito em grupos de 3. Excepcionalmente serão aceites grupos de 2.

**Dúvidas:** Dúvidas sobre o trabalho deverão ser feitas [nos issues do repositório da cadeira de DCO](https://git.alunos.di.fc.ul.pt/dco0001/dco_1920/issues).

**Instruções de como começar:** No repositório local da meta 1 deverá executar os seguintes comandos para obter a última versão:

```
git remote add enunciado https://git.alunos.di.fc.ul.pt/dco0001/too-yummy-to-go.git
git pull enunciado master
```

A última linha poderá fazer sempre que sair uma nova versão do enunciado, como na fase 3.

**Divisão de trabalho:** Existem 3 casos de uso a trabalhar. Cada um dos membros do grupo deverá fazer um, sendo que devem colaborar de forma a estarem todos em harmonia. O diagrama de classes deverá ser consistente com todos os casos de uso.

**Para entregar:** Deverá preencher o ficheiro autores.txt. Deverá entregar tudo num PDF chamado ```desenho.pdf```. Não esquecer fazer um commit no git:

```
git add autores.txt
git add desenho.pdf
git commit -m "Adicionados autores e documentação"
```

Depois, basta criar uma nova tag git e colocá-la no servidor:

```
git tag meta2
git push origin meta2
```

Não cumprir estas instruções levará a que o projecto nem seja corrigido, resultando numa nota de 0.

Fraude
------

Como futuro profissional, espera-se de si uma atitude irrepreensível,
em termos éticos e deontológicos. Tenha pois o maior cuidado em
respeitar e fazer respeitar a lei da criminalidade informática.

A nível académico, alunos detetados em situação de fraude ou plágio
(plagiadores e plagiados) em alguma prova ficam reprovados à
disciplina e serão alvo de processo disciplinar, o que levará a um
registo dessa incidência no processo de aluno, podendo conduzir à
suspensão letiva ou abandono da Universidade.

Objectivo
---------

Nesta primeira meta identificámos os casos de uso relevantes para a primeira iteração: UC5, UC6 e UC7. Vamos agora implementar o desenho dos mesmos.

Deverá fazer um Diagrama de Interação para cada operação do caso de uso, respeitando os contractos que escreveu na fase anterior. Se necessário, corrija os contractos.

A cada momento que seja necessário tomar uma decisão sobre que objecto tem a responsabilidade de fazer X, indique que padrão GRASP usou para tomar a decisão.

O ficheiro a entregar deverá ter:

* Um Diagrama de Interacção (ID) por cada operação dos casos de uso a desenvolver.
* O Diagrama de Classes desenvolvido em paralelo com os IDs.

Não é necessário fazer o Startup nem os Casos de Uso que não são para desenvolver nesta fase.



