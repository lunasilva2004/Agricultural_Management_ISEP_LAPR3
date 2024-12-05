# USLP10 - Simular um Controlador do Sistema de Rega

## 1. Requirements Engineering


### 1.1. User Story Description

Como Product Owner, pretendo que seja melhorada a funcionalidade que consiste em simular um controlador do sistema de rega previamente desenvolvida na USLP02. 
A melhoria consiste modelar aspetos relacionados com a fertirrega.
Para tal deve ser consumido um ficheiro de texto com um conjunto de instruções e gerado um plano de rega para 30 dias. O ficheiro de texto deverá ter a seguinte informação e formato:
<Horas de rega>
Nesta linha são definidos as horas que se inicia um ciclo de rega, por exemplo: 8:30 e 17:00, significa que existem dois ciclos de rega diários que se iniciam respetivamente às 8:30 e 17:00.
<Sector, Duração, Regularidade,[Mix, Recorrência]>
Existe uma linha para cada sector a regar. Sector (uma ou mais parcelas) identifica a zona a ser regada (controlado por uma electroválvula); Duração, o tempo em minutos que o sector deve receber rega; e a Regularidade, a fórmula de recorrência que define os dias que o sector deve ser regado T, todos; I, ímpares, P, pares, 3, a cada 3 dias. Opcionalmente a linha pode contar informação sobre a fertirrega a realizar, definida por um mix (composição de factores de produção, e a fórmula de recorrência da sua aplicação, por exemplo 7, significa que será aplicada nos ordinais 1, 8, 15, . . . (primeiro, oitavo e décimo quinto) do plano de fertirrega.
Exemplo:
8:30, 17:00
A,14,T,mix1,5
B,8,T,
C,25,P,mix2,7
D,25,I
E,7,T,mix1,3
F,10,3
Note-se que por questões de capacidade, os sectores são regados de forma sequencial. A necessidade de regar em dias pares ou ímpares resulta da necessidade de balancear as necessidades de rega de todos sectores em função da capacidade máxima do sistema.
O controlador de rega, contem um plano de rega para 30 dias a partir da data de criação e em qualquer momento (data/hora) sabe responder se está a regar ou não, e em caso afirmativo qual o sector que está a regar e quantos minutos faltam para terminar.


### 1.2. Customer Specifications and Clarifications 

**From the client clarifications:**

> **Question:** Boa tarde,
Já foi levantada uma questão semelhante no entanto não totalmente igual.
Suponha a seguinte linha de instrução "A,10,I,Mix1,2", esta instrução afirma que a fertirrega será aplicada no segundo dia do plano, quarto dia do plano etc mas também diz inicialmente que apenas se aplica para dias ímpares. O que deverá acontecer caso os dias que respeitam a fórmula de recorrência não sejam dias Ímpares ? Por exemplo se o plano de rega for criado exatamente dia 1, os ordinais para a fórmula de recorrência "2" vão ser sempre pares o que tornaria a instrução incoerente caso esta apresente inicialmente a indicação de apenas acontecer nos dias ímpares.
>  
> **Answer:** Boa tarde, a questão é interessante e detecção completa de conflitos entre as especificações poderia ser complexa; no contexto do presente é suficiente apresentarem conflito que surjam num planeamento gerado.

> **Question:** Boa tarde, de acordo com o txt que forneceu temos: "A,14,T,mix1,5". Isto significa que a rega ocorre todos os dias e que a fertirrega ocorre de acordo com o ordinal, neste exemplo dia 5, 10, 15, etc após o início do plano? Ou neste setor apenas é aplicada a fertirrega? O tempo serve apenas para a rega ou também contabilizamos para a fertirrega?
>
> **Answer:** Boa tarde, excelente questão;
visto que a fertirrega não pode ser realizada sem a respectiva rega, deve ser gerado um erro para que o operador possa corrigir o ficheiro de entrada.

### 1.3. Acceptance Criteria


* **AC1:** A validação das regras de negócio que devem ser respeitadas aquando do registo e atualização de dados.

* **AC2:** A Base de Dados será o repositório principal de informação do sistema e deverá refletir a necessária integridade de dados. A informação deverá ser persistida num SGBD remoto.
* **AC3:** A estrutura de classes deve ser concebida por forma a permitir a sua fácil manutenção e adição de novas funcionalidades, de acordo com as melhores práticas de OO.


### 1.4. Found out Dependencies


* Tem dependência na "USLP03 - Simular um controlador do sistema de rega".


### 1.5 Input and Output Data


**Input Data:**

* Typed data:
	* data de início do programa

**Output Data:**

* Ficheiro CSV com o programa de irrigação gerado
* (In)Sucesso da operação

### 1.6. System Sequence Diagram (SSD)

**Other alternatives might exist.**

![System Sequence Diagram - Alternative One](/Users/josemendes/Documents/IdeaProjects/sem3pi2023_24_g085/docs/LAPR3/SoftwareEngineeringDocumentation/US_LP10_11-Documentation/01-RequirementsEngineering/svg/US_LP10-system-sequence-diagram.svg)
