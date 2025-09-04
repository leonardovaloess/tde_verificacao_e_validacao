# RELATÓRIO DE ANÁLISE DO SOFTWARE

## TDE - Verificação e Validação de Software

### 1ª Etapa: Seleção e Análise do Software Objeto de Teste

---

## 1. IDENTIFICAÇÃO DO PROJETO

**Nome do Projeto:** Sistema de Matrícula Acadêmica  
**Linguagem de Programação:** Java 11+  
**Frameworks/Tecnologias:** Java Swing, Maven  
**Repositório:** [GitHub - tde_verificacao_e_validacao](https://github.com/leonardovaloess/tde_verificacao_e_validacao)

---

## 2. DESCRIÇÃO DO SOFTWARE

### 2.1 Contexto do Projeto

O Sistema de Matrícula Acadêmica é uma aplicação desktop desenvolvida em Java que visa facilitar o gerenciamento de informações acadêmicas em instituições de ensino. O sistema foi desenvolvido como parte da disciplina de Programação Orientada a Objetos (POO) e posteriormente adaptado para atividades de verificação e validação de software.

### 2.2 Propósito

O software tem como objetivo principal fornecer uma interface intuitiva e funcional para:

- Gerenciar cadastros de alunos e professores
- Administrar disciplinas oferecidas pela instituição
- Controlar matrículas de alunos em disciplinas
- Manter persistência de dados através de arquivos de texto
- Validar integridade das informações inseridas

### 2.3 Principais Funcionalidades

#### 2.3.1 Gerenciamento de Alunos

- **Cadastro:** Permite inserir dados completos do aluno (nome, matrícula, email, telefone)
- **Edição:** Modificação de informações existentes
- **Exclusão:** Remoção segura de registros
- **Listagem:** Visualização tabular de todos os alunos cadastrados
- **Validações:** Garantia de unicidade da matrícula e obrigatoriedade de campos

#### 2.3.2 Gerenciamento de Professores

- **Cadastro:** Inserção de dados do professor (nome, identificador, email, departamento)
- **Edição:** Atualização de informações
- **Exclusão:** Remoção controlada de registros
- **Listagem:** Exibição organizada dos professores
- **Validações:** Identificador único e campos obrigatórios

#### 2.3.3 Gerenciamento de Disciplinas

- **Cadastro:** Criação de disciplinas com associação a professores
- **Dados:** Nome, código, carga horária, semestre, professor responsável
- **Relacionamentos:** Vinculação obrigatória com professor cadastrado
- **Validações:** Código único e seleção de professor

#### 2.3.4 Gerenciamento de Matrículas

- **Criação:** Matrícula de alunos em disciplinas específicas
- **Controle de Status:** ATIVA, CANCELADA, CONCLUÍDA
- **Validações:** Prevenção de matrículas duplicadas ativas
- **Rastreabilidade:** Data de matrícula e histórico de alterações

### 2.4 Arquitetura do Sistema

O sistema segue os princípios da programação orientada a objetos e implementa o padrão DAO (Data Access Object) para separação de responsabilidades:

- **Camada de Modelo (classes/):** Entidades de domínio do negócio
- **Camada de Acesso a Dados (dao/):** Responsável pela persistência
- **Camada de Apresentação (gui/):** Interface gráfica com usuário
- **Persistência:** Arquivos de texto em formato CSV

---

## 3. MÉTRICAS DE CÓDIGO

### 3.1 Métricas Gerais

| Métrica                             | Valor |
| ----------------------------------- | ----- |
| **Total de Linhas de Código (LOC)** | 2.177 |
| **Número de Classes**               | 14    |
| **Número de Arquivos Java**         | 14    |
| **Número de Pacotes**               | 4     |

### 3.2 Distribuição por Pacote

| Pacote      | Arquivos | LOC   | Descrição          |
| ----------- | -------- | ----- | ------------------ |
| **classes** | 4        | 365   | Modelos de domínio |
| **dao**     | 4        | 420   | Acesso a dados     |
| **gui**     | 5        | 1.381 | Interface gráfica  |
| **raiz**    | 1        | 11    | Classe principal   |

### 3.3 Detalhamento por Arquivo

| Arquivo              | LOC | Funcionalidade Principal    |
| -------------------- | --- | --------------------------- |
| MatriculaFrame.java  | 369 | Interface de matrículas     |
| DisciplinaFrame.java | 337 | Interface de disciplinas    |
| AlunoFrame.java      | 269 | Interface de alunos         |
| ProfessorFrame.java  | 267 | Interface de professores    |
| MainFrame.java       | 139 | Interface principal         |
| MatriculaDAO.java    | 114 | Persistência de matrículas  |
| DisciplinaDAO.java   | 106 | Persistência de disciplinas |
| AlunoDAO.java        | 100 | Persistência de alunos      |
| ProfessorDAO.java    | 100 | Persistência de professores |
| Disciplina.java      | 100 | Modelo de disciplina        |
| Matricula.java       | 89  | Modelo de matrícula         |
| Aluno.java           | 88  | Modelo de aluno             |
| Professor.java       | 88  | Modelo de professor         |
| App.java             | 11  | Classe principal            |

### 3.4 Análise de Complexidade

#### 3.4.1 Complexidade por Tipo de Classe

**Classes de Modelo (classes/):**

- Complexidade Baixa a Média
- Métodos simples de acesso e conversão
- Validações básicas

**Classes DAO (dao/):**

- Complexidade Média
- Operações CRUD completas
- Tratamento de exceções de I/O
- Validações de integridade

**Classes GUI (gui/):**

- Complexidade Alta
- Múltiplos métodos de interface
- Tratamento de eventos
- Validações de entrada
- Gerenciamento de estado

#### 3.4.2 Métodos com Maior Complexidade

1. **Métodos de Interface (GUI):**

   - `salvar*()` - Validação e persistência
   - `carregarTabela()` - Montagem de tabelas
   - `preencherFormulario()` - Sincronização de dados

2. **Métodos DAO:**
   - `listarTodos()` - Leitura e parsing de arquivos
   - `salvar()` - Lógica de inserção/atualização

### 3.5 Métricas de Qualidade

| Critério             | Avaliação   | Justificativa                                  |
| -------------------- | ----------- | ---------------------------------------------- |
| **Coesão**           | Alta        | Cada classe tem responsabilidade bem definida  |
| **Acoplamento**      | Baixo/Médio | Uso de interfaces e padrão DAO                 |
| **Reutilização**     | Média       | Código estruturado em camadas                  |
| **Manutenibilidade** | Boa         | Código organizado e documentado                |
| **Testabilidade**    | Boa         | Separação de responsabilidades facilita testes |

---

## 4. JUSTIFICATIVA DA COMPLEXIDADE ADEQUADA

### 4.1 Critérios Atendidos

O projeto atende aos requisitos de complexidade adequada pelos seguintes aspectos:

1. **Volume de Código:** Com 2.177 linhas de código, o projeto possui dimensão suficiente para atividades de teste significativas.

2. **Múltiplas Camadas:** A arquitetura em camadas (modelo, DAO, GUI) proporciona diferentes tipos de complexidade para teste.

3. **Diversidade de Funcionalidades:**

   - Operações CRUD completas
   - Validações de dados
   - Relacionamentos entre entidades
   - Persistência de dados
   - Interface gráfica interativa

4. **Complexidade Ciclomática Variada:**

   - Métodos simples (getters/setters)
   - Métodos de complexidade média (validações)
   - Métodos complexos (interface e persistência)

5. **Casos de Teste Diversos:**
   - Testes unitários para modelos
   - Testes de integração para DAOs
   - Testes de interface
   - Testes de validação
   - Testes de persistência

### 4.2 Oportunidades de Teste

O sistema oferece múltiplas oportunidades para aplicação de técnicas de teste:

- **Particionamento de Equivalência:** Validação de campos de entrada
- **Análise de Valor Limite:** Testes com dados extremos
- **Testes de Estado:** Diferentes status de matrícula
- **Testes de Integração:** Relacionamentos entre entidades
- **Testes de Persistência:** Operações de arquivo

---

## 5. FERRAMENTAS E METODOLOGIA

### 5.1 Ferramentas Utilizadas para Análise

- **Contador de LOC:** Comando `wc -l` do sistema Unix/Linux
- **Análise Manual:** Revisão de código para identificação de complexidade
- **IDE:** Visual Studio Code para navegação e análise

### 5.2 Metodologia de Análise

1. **Contagem Automatizada:** Scripts para contagem de linhas e arquivos
2. **Análise Qualitativa:** Revisão manual da estrutura e organização
3. **Categorização:** Classificação por tipo e complexidade
4. **Documentação:** Registro detalhado dos achados

---

## 6. CONCLUSÃO

O Sistema de Matrícula Acadêmica apresenta-se como um projeto adequado para atividades de verificação e validação de software, atendendo a todos os requisitos estabelecidos:

- **Nome Representativo:** "Sistema de Matrícula Acadêmica"
- **Linguagem Apropriada:** Java 11+
- **Propósito Bem Definido:** Gerenciamento acadêmico funcional
- **Complexidade Adequada:** 2.177 LOC distribuídas em 14 classes com diferentes níveis de complexidade

O projeto oferece oportunidades ricas para aplicação de técnicas de teste, desde testes unitários simples até testes de integração complexos, tornando-se uma base sólida para as atividades da disciplina de Verificação e Validação de Software.

---

**Data de Elaboração:** 03 de Setembro de 2025  
**Responsável:** Leonardo Berlanda de Valões  
**Disciplina:** Verificação e Validação de Software
