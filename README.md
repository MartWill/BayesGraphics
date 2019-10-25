# BayesGraphics

Ferramenta para correlação de variáveis, utilizando técnicas de Machine Learning (ML) , mais especificamente Naive Bayes.

![Bayes](https://raw.githubusercontent.com/MartWill/BayesGraphics/version2/readme_imgs/StartWindow.PNG "StartWindow")
![Bayes](https://raw.githubusercontent.com/MartWill/BayesGraphics/version2/readme_imgs/BGWindow.PNG "BGWindow")



# Metodologia

### Teorema de Bayes
O algoritmo probabilístico supervisionado Naive Bayes, é baseado no teorema de Bayes, no qual, resumidamente, dado 2 eventos, evento A e B, calcula a probabilidade do `evento A` acontecer dados que o `evento B` ocorreu, utilizando a equação a seguir.


<p align="center">
  <img src="https://www.economiasimple.net/wp-content/uploads/2019/01/calcular-la-formula-del-teorema-de-bayes.jpg "Teorema de Bayes"">
</p>

### Redes Bayesiana
Desta forma, para correlacionar dois ou mais eventos cria-se um Rede Bayesiana ( RB ), no qual, os eventos chamaremos de `variáveis ou atributos` e de `estado` o seu valor.

No qual é escolhido um dos atributos para ser alvo do estudo (chamado de `atributo alvo`), e utiliza-se do Teorema de Bayes para o atributo alvo com cada um dos atributos restantes (chamados também de `atributos de entrada`), gerando uma representação desta forma:


<p align="center">
  <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSCBvuW1pExlUft7lghYxCgLwTZbOQ2s_QX4NF2tHjEUceLyI3M&s "Rede Bayesiana"">
</p>


No caso desta imagem, o `atributo alvo` é o A e os `atributos de entradas` são o os atributos  B e C. Desta forma forçando uma correlação entre o atributo alvo com o restantes e ignorando as correlações entre o atributos de entrada com eles mesmo.

### Inferência

Com a RB criada, é possível criar inferências, desta forma, criando um cenário, de acordo com os valores inferidos e como se comporta os restantes dos atributos na RB.

### Grau de Correlação

A proposta desta ferramenta está relacionada com este tópico, no qual, comparamos os dados da RB sem inferência ( somente distribuição estatísticas dos conjunto de dados estudado ) com a RB com determinada inferência(s), essa comparação tem como intuito analisar a variação dos valores para determinar quanto cada atributo corrobora para a inferência feita, através da subtração de cada valor (`RELEVÂNCIA ENTRE VALORES`), e somando essas diferenças encontradas (`RELEVÂNCIA GERAL`) . Por exemplo:

<p align="center">
  <imgsrc="hhttps://raw.githubusercontent.com/MartWill/BayesGraphics/version2/readme_imgs/outlookLine.png "Rede Bayesiana"">
</p>

# Primeiros Passos

Primeiro baixe o conjunto de dados `Weather.csv` aqui, será utilizado no nosso primeiro uso da ferramenta.

- Com o software aberto, click em `ESCOLHER`, para selecionar o conjunto de dados para o estudo, neste caso o arquivo Weather.csv baixado.
- Logo abaixo, preencha os campos com a inferência desejada e click em `GERAR ANÁLISE`

Após o tempo de carregamento, já estará pronto os resultados, simples!

# Arquivo `.csv`

Este software suporta somente conjunto de dados `.csv`, separados por vírgulas.
Além disso, alguns critérios:
- TODOS os valores presentes devem ser discretos.
- De preferência sem caracteres especiais.
- E de boa prática deixa os valores vazios como campos em branco, sem a presença de nenhum caractere.


Conjunto de Dados Ideal:


| OUTLOOK  | TEMPERATURE | HUMIDITY | WINDY      | PLAY |
|----------|-------------|----------|------------|------|
| sunny    | hot         | high     | FALSO      | no   |
| sunny    | hot         | high     | VERDADEIRO | no   |
| overcast | hot         | high     | FALSO      | yes  |
| rainy    | mild        | high     | FALSO      | yes  |
| rainy    | cool        | normal   | FALSO      | yes  |
| rainy    | cool        | normal   | VERDADEIRO | no   |
| overcast | cool        | normal   | VERDADEIRO | yes  |
| sunny    | mild        | high     | FALSO      | no   |
| sunny    | cool        | normal   | FALSO      | yes  |
| rainy    | mild        | normal   | FALSO      | yes  |
| sunny    | mild        | normal   | VERDADEIRO | yes  |
| overcast | mild        | high     | VERDADEIRO | yes  |
| overcast | hot         | normal   | FALSO      | yes  |
| rainy    | mild        | high     | VERDADEIRO | no   |

Principais erros : 

| OUTLOOK  | TEMPERATURE | HUMIDITY | WINDY      | PLAY |
|----------|-------------|----------|------------|------|
| rainy    |  **32ºC**        | normal   | VERDADEIRO | no   |
| overcast |  **30ºC**        | normal   | VERDADEIRO | yes  |
| sunny    |  **29ºC**        | high     |  **VAZIO**  | no   |
| sunny    |  **30ºC**        | normal   | FALSO      | yes  |
| overcast |  **31ºC**        | high     | VERDADEIRO | yes  |
| overcast |  **30ºC**         |  **NULL**   | FALSO      | yes  |
| rainy    |  **29ºC**        | high     | VERDADEIRO | no   |

# Como Baixar

EM BREVE
