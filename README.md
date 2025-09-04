# Sistema de Matrícula - Faculdade

Sistema desenvolvido em Java com interface gráfica Swing para gerenciamento acadêmico, incluindo alunos, professores, disciplinas e matrículas.

## Características

- **Interface Gráfica**: Desenvolvida com Java Swing
- **Persistência**: Dados salvos em arquivos de texto (CSV)
- **Arquitetura**: Padrão DAO (Data Access Object)
- **Funcionalidades**:
  - Gerenciamento de Alunos
  - Gerenciamento de Professores
  - Gerenciamento de Disciplinas
  - Gerenciamento de Matrículas
  - Validações de dados
  - Interface intuitiva

## Estrutura do Projeto

```
sistema_matricula/
├── src/
│   ├── classes/          # Modelos de dados
│   │   ├── Aluno.java
│   │   ├── Professor.java
│   │   ├── Disciplina.java
│   │   └── Matricula.java
│   ├── dao/              # Acesso a dados
│   │   ├── AlunoDAO.java
│   │   ├── ProfessorDAO.java
│   │   ├── DisciplinaDAO.java
│   │   └── MatriculaDAO.java
│   ├── gui/              # Interface gráfica
│   │   ├── MainFrame.java
│   │   ├── AlunoFrame.java
│   │   ├── ProfessorFrame.java
│   │   ├── DisciplinaFrame.java
│   │   └── MatriculaFrame.java
│   └── App.java          # Classe principal
├── dados/                # Arquivos de dados (criado automaticamente)
│   ├── alunos.txt
│   ├── professores.txt
│   ├── disciplinas.txt
│   └── matriculas.txt
└── pom.xml               # Configuração Maven
```

## Como Executar

### Opção 1: Compilação Manual

1. **Compilar o projeto**:

   ```bash
   mkdir -p out
   javac -d out src/classes/*.java src/dao/*.java src/gui/*.java src/App.java
   ```

2. **Executar o sistema**:
   ```bash
   java -cp out App
   ```

### Opção 2: Usando Maven (se disponível)

1. **Compilar**:

   ```bash
   mvn clean compile
   ```

2. **Executar**:
   ```bash
   mvn exec:java -Dexec.mainClass="App"
   ```

## Funcionalidades

### 1. Gerenciamento de Alunos

- **Cadastrar**: Nome, matrícula, email, telefone
- **Editar**: Modificar dados existentes
- **Excluir**: Remover aluno do sistema
- **Listar**: Visualizar todos os alunos cadastrados
- **Validações**: Matrícula única, campos obrigatórios

### 2. Gerenciamento de Professores

- **Cadastrar**: Nome, identificador, email, departamento
- **Editar**: Modificar dados existentes
- **Excluir**: Remover professor do sistema
- **Listar**: Visualizar todos os professores cadastrados
- **Validações**: Identificador único, campos obrigatórios

### 3. Gerenciamento de Disciplinas

- **Cadastrar**: Nome, código, carga horária, semestre, professor responsável
- **Editar**: Modificar dados existentes
- **Excluir**: Remover disciplina do sistema
- **Listar**: Visualizar todas as disciplinas cadastradas
- **Validações**: Código único, professor obrigatório

### 4. Gerenciamento de Matrículas

- **Cadastrar**: Aluno, disciplina, data da matrícula, status
- **Editar**: Modificar dados existentes
- **Excluir**: Remover matrícula do sistema
- **Listar**: Visualizar todas as matrículas
- **Status**: ATIVA, CANCELADA, CONCLUIDA
- **Validações**: Não permitir matrícula duplicada ativa

## Persistência de Dados

Os dados são salvos em arquivos de texto no formato CSV (valores separados por ponto e vírgula) na pasta `dados/`:

- **alunos.txt**: id;nome;matrícula;email;telefone
- **professores.txt**: id;nome;identificador;email;departamento
- **disciplinas.txt**: id;nome;código;cargaHorária;professorId;semestre
- **matriculas.txt**: id;alunoId;disciplinaId;dataMatrícula;status

## Interface do Usuario

### Tela Principal

- Menu principal com acesso a todas as funcionalidades
- Botões grandes e intuitivos
- Design limpo e profissional

### Telas de Gerenciamento

- Formulários para entrada de dados
- Tabelas para visualização dos registros
- Botões para Salvar, Editar, Excluir e Limpar
- Validações em tempo real

## Regras de Negócio

1. **Alunos**: Matrícula deve ser única
2. **Professores**: Identificador deve ser único
3. **Disciplinas**: Código deve ser único, deve ter um professor responsável
4. **Matrículas**:
   - Não pode haver matrícula ativa duplicada (mesmo aluno, mesma disciplina)
   - Data deve estar no formato dd/MM/yyyy
   - Status pode ser: ATIVA, CANCELADA, CONCLUIDA

## Tecnologias Utilizadas

- **Java 11+**
- **Swing** (Interface Gráfica)
- **Arquivos de Texto** (Persistência)
- **Maven** (Gerenciamento de Projeto)

## Desenvolvido para

Faculdade - Disciplina de Programação Orientada a Objetos (POO)

---

## Exemplos de Uso

1. **Primeiro uso**: Execute o programa e comece cadastrando professores
2. **Cadastrar disciplinas**: Associe disciplinas aos professores cadastrados
3. **Cadastrar alunos**: Registre os alunos no sistema
4. **Realizar matrículas**: Matricule alunos nas disciplinas disponíveis

O sistema criará automaticamente a pasta `dados/` e os arquivos necessários na primeira execução.
# tde_verificacao_e_validacao
