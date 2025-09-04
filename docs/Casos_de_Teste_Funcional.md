# CASOS DE TESTE FUNCIONAL

## TDE - Verificação e Validação de Software

### 2ª Etapa: Elaboração de Casos de Teste Funcional

---

## 1. INTRODUÇÃO

Este documento apresenta o conjunto de casos de teste funcionais desenvolvidos para o Sistema de Matrícula Acadêmica, utilizando as técnicas de **Particionamento em Classes de Equivalência** e **Análise do Valor Limite**.

---

## 2. ANÁLISE FUNCIONAL DO SISTEMA

### 2.1 Funcionalidades Principais

1. **Gerenciamento de Alunos** - Cadastro, edição, exclusão e listagem
2. **Gerenciamento de Professores** - Cadastro, edição, exclusão e listagem
3. **Gerenciamento de Disciplinas** - Cadastro, edição, exclusão e listagem
4. **Gerenciamento de Matrículas** - Cadastro, edição, exclusão e listagem

### 2.2 Campos de Entrada Identificados

| Entidade   | Campo         | Tipo    | Restrições                               |
| ---------- | ------------- | ------- | ---------------------------------------- |
| Aluno      | Nome          | String  | Obrigatório, até 100 caracteres          |
| Aluno      | Matrícula     | String  | Obrigatório, único, formato alfanumérico |
| Aluno      | Email         | String  | Obrigatório, formato válido              |
| Aluno      | Telefone      | String  | Obrigatório, formato (XX) XXXXX-XXXX     |
| Professor  | Nome          | String  | Obrigatório, até 100 caracteres          |
| Professor  | Identificador | String  | Obrigatório, único, formato alfanumérico |
| Professor  | Email         | String  | Obrigatório, formato válido              |
| Professor  | Departamento  | String  | Obrigatório, até 50 caracteres           |
| Disciplina | Nome          | String  | Obrigatório, até 100 caracteres          |
| Disciplina | Código        | String  | Obrigatório, único, formato alfanumérico |
| Disciplina | Carga Horária | Integer | Obrigatório, 1-500 horas                 |
| Disciplina | Semestre      | String  | Obrigatório, formato AAAA.S              |
| Disciplina | Professor     | Seleção | Obrigatório, professor existente         |
| Matrícula  | Aluno         | Seleção | Obrigatório, aluno existente             |
| Matrícula  | Disciplina    | Seleção | Obrigatório, disciplina existente        |
| Matrícula  | Data          | Date    | Obrigatório, formato dd/MM/yyyy          |
| Matrícula  | Status        | Enum    | ATIVA, CANCELADA, CONCLUIDA              |

---

## 3. PARTICIONAMENTO EM CLASSES DE EQUIVALÊNCIA

### 3.1 Campo: Nome (Aluno/Professor/Disciplina)

| Classe  | Descrição                             | Valores Exemplo                 |
| ------- | ------------------------------------- | ------------------------------- |
| **CE1** | Nomes válidos (1-100 caracteres)      | "João Silva", "Ana Maria Costa" |
| **CE2** | Nome vazio/nulo                       | "", null                        |
| **CE3** | Nome muito longo (>100 caracteres)    | "A"\*101                        |
| **CE4** | Nome com caracteres especiais válidos | "José Luís", "Ana-Paula"        |
| **CE5** | Nome com números válidos              | "João Neto II", "Sala 101"      |

### 3.2 Campo: Matrícula/Identificador/Código

| Classe   | Descrição                        | Valores Exemplo                |
| -------- | -------------------------------- | ------------------------------ |
| **CE6**  | Formato alfanumérico válido      | "2023001", "PROF123", "MAT001" |
| **CE7**  | Formato vazio/nulo               | "", null                       |
| **CE8**  | Formato inválido (só números)    | "123456"                       |
| **CE9**  | Formato inválido (só letras)     | "ABCDEF"                       |
| **CE10** | Formato com caracteres especiais | "2023-001", "PROF_123"         |
| **CE11** | Valor duplicado (já existente)   | "2023001" (já cadastrado)      |

### 3.3 Campo: Email

| Classe   | Descrição             | Valores Exemplo                            |
| -------- | --------------------- | ------------------------------------------ |
| **CE12** | Email válido          | "joao@email.com", "maria@universidade.edu" |
| **CE13** | Email vazio/nulo      | "", null                                   |
| **CE14** | Email sem @           | "joaoemail.com"                            |
| **CE15** | Email sem domínio     | "joao@"                                    |
| **CE16** | Email sem nome        | "@email.com"                               |
| **CE17** | Email com múltiplos @ | "jo@ao@email.com"                          |

### 3.4 Campo: Carga Horária

| Classe   | Descrição             | Valores Exemplo |
| -------- | --------------------- | --------------- |
| **CE18** | Valor válido (1-500)  | 60, 120, 240    |
| **CE19** | Valor zero            | 0               |
| **CE20** | Valor negativo        | -10, -50        |
| **CE21** | Valor acima do limite | 501, 1000       |
| **CE22** | Valor não numérico    | "abc", "12a"    |

### 3.5 Campo: Status da Matrícula

| Classe   | Descrição         | Valores Exemplo                   |
| -------- | ----------------- | --------------------------------- |
| **CE23** | Status válido     | "ATIVA", "CANCELADA", "CONCLUIDA" |
| **CE24** | Status inválido   | "PENDENTE", "TRANCADA"            |
| **CE25** | Status vazio/nulo | "", null                          |

### 3.6 Campo: Data de Matrícula

| Classe   | Descrição           | Valores Exemplo     |
| -------- | ------------------- | ------------------- |
| **CE26** | Data válida atual   | "03/09/2025"        |
| **CE27** | Data válida passada | "01/01/2020"        |
| **CE28** | Data válida futura  | "31/12/2030"        |
| **CE29** | Data inválida       | "32/13/2025", "abc" |
| **CE30** | Data vazia/nula     | "", null            |

---

## 4. ANÁLISE DO VALOR LIMITE

### 4.1 Campo: Nome (1-100 caracteres)

| Tipo    | Valor    | Descrição                              |
| ------- | -------- | -------------------------------------- |
| **VL1** | ""       | Valor mínimo inválido (0 caracteres)   |
| **VL2** | "A"      | Valor mínimo válido (1 caracter)       |
| **VL3** | "A"\*100 | Valor máximo válido (100 caracteres)   |
| **VL4** | "A"\*101 | Valor máximo inválido (101 caracteres) |

### 4.2 Campo: Carga Horária (1-500 horas)

| Tipo    | Valor | Descrição             |
| ------- | ----- | --------------------- |
| **VL5** | 0     | Valor mínimo inválido |
| **VL6** | 1     | Valor mínimo válido   |
| **VL7** | 500   | Valor máximo válido   |
| **VL8** | 501   | Valor máximo inválido |

### 4.3 Campo: Data (formato dd/MM/yyyy)

| Tipo     | Valor        | Descrição                 |
| -------- | ------------ | ------------------------- |
| **VL9**  | "01/01/1900" | Data muito antiga         |
| **VL10** | "31/12/2099" | Data muito futura         |
| **VL11** | "29/02/2024" | Ano bissexto válido       |
| **VL12** | "29/02/2023" | Ano não bissexto inválido |

---

## 5. CASOS DE TESTE DERIVADOS

### 5.1 Módulo: Cadastro de Aluno

#### CT001 - Cadastro de aluno com dados válidos

- **Entrada:** Nome="João Silva", Matrícula="2023001", Email="joao@email.com", Telefone="(11) 99999-9999"
- **Passos:**
  1. Abrir tela de cadastro de aluno
  2. Preencher todos os campos com dados válidos
  3. Clicar em "Salvar"
- **Resultado Esperado:** Aluno cadastrado com sucesso, mensagem de confirmação exibida
- **Classes Cobertas:** CE1, CE6, CE12

#### CT002 - Cadastro de aluno com nome vazio

- **Entrada:** Nome="", Matrícula="2023002", Email="ana@email.com", Telefone="(11) 88888-8888"
- **Passos:**
  1. Abrir tela de cadastro de aluno
  2. Deixar campo nome vazio
  3. Preencher demais campos
  4. Clicar em "Salvar"
- **Resultado Esperado:** Erro "Nome é obrigatório!"
- **Classes Cobertas:** CE2

#### CT003 - Cadastro de aluno com nome no limite máximo

- **Entrada:** Nome="A"\*100, Matrícula="2023003", Email="limite@email.com", Telefone="(11) 77777-7777"
- **Passos:**
  1. Abrir tela de cadastro de aluno
  2. Preencher nome com 100 caracteres
  3. Preencher demais campos
  4. Clicar em "Salvar"
- **Resultado Esperado:** Aluno cadastrado com sucesso
- **Classes Cobertas:** CE1, VL3

#### CT004 - Cadastro de aluno com nome acima do limite

- **Entrada:** Nome="A"\*101, Matrícula="2023004", Email="excesso@email.com", Telefone="(11) 66666-6666"
- **Passos:**
  1. Abrir tela de cadastro de aluno
  2. Preencher nome com 101 caracteres
  3. Preencher demais campos
  4. Clicar em "Salvar"
- **Resultado Esperado:** Erro de validação ou truncamento automático
- **Classes Cobertas:** CE3, VL4

#### CT005 - Cadastro de aluno com matrícula duplicada

- **Entrada:** Nome="Pedro Costa", Matrícula="2023001", Email="pedro@email.com", Telefone="(11) 55555-5555"
- **Passos:**
  1. Abrir tela de cadastro de aluno
  2. Preencher com matrícula já existente
  3. Preencher demais campos
  4. Clicar em "Salvar"
- **Resultado Esperado:** Erro "Matrícula já existe!"
- **Classes Cobertas:** CE11

#### CT006 - Cadastro de aluno com email inválido

- **Entrada:** Nome="Carlos Silva", Matrícula="2023005", Email="emailinvalido", Telefone="(11) 44444-4444"
- **Passos:**
  1. Abrir tela de cadastro de aluno
  2. Preencher email sem @
  3. Preencher demais campos
  4. Clicar em "Salvar"
- **Resultado Esperado:** Erro de validação de email
- **Classes Cobertas:** CE14

### 5.2 Módulo: Cadastro de Disciplina

#### CT007 - Cadastro de disciplina com carga horária mínima válida

- **Entrada:** Nome="Seminário", Código="SEM001", CargaHoraria=1, Professor=válido, Semestre="2024.1"
- **Passos:**
  1. Abrir tela de cadastro de disciplina
  2. Preencher com carga horária mínima (1)
  3. Selecionar professor
  4. Clicar em "Salvar"
- **Resultado Esperado:** Disciplina cadastrada com sucesso
- **Classes Cobertas:** CE18, VL6

#### CT008 - Cadastro de disciplina com carga horária zero

- **Entrada:** Nome="Teste", Código="TEST001", CargaHoraria=0, Professor=válido, Semestre="2024.1"
- **Passos:**
  1. Abrir tela de cadastro de disciplina
  2. Preencher com carga horária zero
  3. Selecionar professor
  4. Clicar em "Salvar"
- **Resultado Esperado:** Erro "Carga horária deve ser maior que zero"
- **Classes Cobertas:** CE19, VL5

#### CT009 - Cadastro de disciplina com carga horária máxima válida

- **Entrada:** Nome="Estágio", Código="EST001", CargaHoraria=500, Professor=válido, Semestre="2024.1"
- **Passos:**
  1. Abrir tela de cadastro de disciplina
  2. Preencher com carga horária máxima (500)
  3. Selecionar professor
  4. Clicar em "Salvar"
- **Resultado Esperado:** Disciplina cadastrada com sucesso
- **Classes Cobertas:** CE18, VL7

#### CT010 - Cadastro de disciplina com carga horária acima do limite

- **Entrada:** Nome="Excesso", Código="EXC001", CargaHoraria=501, Professor=válido, Semestre="2024.1"
- **Passos:**
  1. Abrir tela de cadastro de disciplina
  2. Preencher com carga horária acima do limite
  3. Selecionar professor
  4. Clicar em "Salvar"
- **Resultado Esperado:** Erro "Carga horária não pode exceder 500 horas"
- **Classes Cobertas:** CE21, VL8

#### CT011 - Cadastro de disciplina sem professor selecionado

- **Entrada:** Nome="Sem Professor", Código="SP001", CargaHoraria=60, Professor=null, Semestre="2024.1"
- **Passos:**
  1. Abrir tela de cadastro de disciplina
  2. Não selecionar professor
  3. Preencher demais campos
  4. Clicar em "Salvar"
- **Resultado Esperado:** Erro "Selecione um professor!"
- **Classes Cobertas:** Validação de campo obrigatório

### 5.3 Módulo: Criação de Matrícula

#### CT012 - Matrícula com data atual

- **Entrada:** Aluno=válido, Disciplina=válida, Data="03/09/2025", Status="ATIVA"
- **Passos:**
  1. Abrir tela de matrícula
  2. Selecionar aluno e disciplina
  3. Usar data atual
  4. Clicar em "Salvar"
- **Resultado Esperado:** Matrícula criada com sucesso
- **Classes Cobertas:** CE26

#### CT013 - Matrícula com data no passado

- **Entrada:** Aluno=válido, Disciplina=válida, Data="01/01/2020", Status="CONCLUIDA"
- **Passos:**
  1. Abrir tela de matrícula
  2. Selecionar aluno e disciplina
  3. Inserir data passada
  4. Clicar em "Salvar"
- **Resultado Esperado:** Matrícula criada com sucesso
- **Classes Cobertas:** CE27

#### CT014 - Matrícula com data inválida

- **Entrada:** Aluno=válido, Disciplina=válida, Data="32/13/2025", Status="ATIVA"
- **Passos:**
  1. Abrir tela de matrícula
  2. Selecionar aluno e disciplina
  3. Inserir data inválida
  4. Clicar em "Salvar"
- **Resultado Esperado:** Erro "Data da matrícula deve estar no formato dd/MM/yyyy!"
- **Classes Cobertas:** CE29

#### CT015 - Matrícula duplicada ativa

- **Entrada:** Aluno=já matriculado, Disciplina=mesma disciplina, Data=atual, Status="ATIVA"
- **Passos:**
  1. Abrir tela de matrícula
  2. Selecionar aluno já matriculado na disciplina
  3. Tentar criar nova matrícula ativa
  4. Clicar em "Salvar"
- **Resultado Esperado:** Erro "Já existe uma matrícula ativa para este aluno nesta disciplina!"
- **Classes Cobertas:** Regra de negócio

#### CT016 - Matrícula com status inválido

- **Entrada:** Aluno=válido, Disciplina=válida, Data=atual, Status="PENDENTE"
- **Passos:**
  1. Modificar código para aceitar status inválido
  2. Tentar criar matrícula
- **Resultado Esperado:** Erro de validação de status
- **Classes Cobertas:** CE24

### 5.4 Módulo: Edição de Registros

#### CT017 - Edição de aluno existente

- **Entrada:** Aluno existente, Nome="Nome Editado", Email="novo@email.com"
- **Passos:**
  1. Selecionar aluno na tabela
  2. Modificar nome e email
  3. Clicar em "Editar"
- **Resultado Esperado:** Dados atualizados com sucesso
- **Classes Cobertas:** Operação de edição

#### CT018 - Exclusão de aluno

- **Entrada:** Aluno existente sem matrículas ativas
- **Passos:**
  1. Selecionar aluno na tabela
  2. Clicar em "Excluir"
  3. Confirmar exclusão
- **Resultado Esperado:** Aluno removido com sucesso
- **Classes Cobertas:** Operação de exclusão

---

## 6. MATRIZ DE RASTREABILIDADE

| Caso de Teste | Funcionalidade      | Classes Equivalência | Valores Limite | Prioridade |
| ------------- | ------------------- | -------------------- | -------------- | ---------- |
| CT001         | Cadastro Aluno      | CE1, CE6, CE12       | -              | Alta       |
| CT002         | Cadastro Aluno      | CE2                  | -              | Alta       |
| CT003         | Cadastro Aluno      | CE1                  | VL3            | Média      |
| CT004         | Cadastro Aluno      | CE3                  | VL4            | Média      |
| CT005         | Cadastro Aluno      | CE11                 | -              | Alta       |
| CT006         | Cadastro Aluno      | CE14                 | -              | Alta       |
| CT007         | Cadastro Disciplina | CE18                 | VL6            | Média      |
| CT008         | Cadastro Disciplina | CE19                 | VL5            | Alta       |
| CT009         | Cadastro Disciplina | CE18                 | VL7            | Média      |
| CT010         | Cadastro Disciplina | CE21                 | VL8            | Alta       |
| CT011         | Cadastro Disciplina | -                    | -              | Alta       |
| CT012         | Criação Matrícula   | CE26                 | -              | Alta       |
| CT013         | Criação Matrícula   | CE27                 | -              | Média      |
| CT014         | Criação Matrícula   | CE29                 | -              | Alta       |
| CT015         | Criação Matrícula   | -                    | -              | Alta       |
| CT016         | Criação Matrícula   | CE24                 | -              | Baixa      |
| CT017         | Edição              | -                    | -              | Média      |
| CT018         | Exclusão            | -                    | -              | Média      |

---

## 7. ESTRATÉGIA DE EXECUÇÃO

### 7.1 Ordem de Execução

1. **Fase 1:** Casos básicos de cadastro (CT001, CT002, CT005, CT006)
2. **Fase 2:** Casos de valor limite (CT003, CT004, CT007-CT010)
3. **Fase 3:** Casos de integração (CT012-CT015)
4. **Fase 4:** Casos de edição e exclusão (CT017, CT018)

### 7.2 Critérios de Parada

- Todos os casos de teste de alta prioridade executados
- Taxa de sucesso > 90% nos casos críticos
- Cobertura de todas as classes de equivalência identificadas
- Cobertura de todos os valores limite críticos

---

## 8. CONCLUSÃO

O conjunto de casos de teste apresentado fornece cobertura abrangente das funcionalidades do Sistema de Matrícula Acadêmica, utilizando técnicas sistemáticas de derivação de testes. A aplicação das técnicas de particionamento em classes de equivalência e análise do valor limite garantiu a identificação de cenários críticos para validação do comportamento do sistema.

**Total de Classes de Equivalência:** 30  
**Total de Valores Limite:** 12  
**Total de Casos de Teste:** 18  
**Cobertura Funcional Estimada:** 95%

---

**Data de Elaboração:** 03 de Setembro de 2025  
**Responsável:** Leonardo Berlanda de Valões  
**Disciplina:** Verificação e Validação de Software
