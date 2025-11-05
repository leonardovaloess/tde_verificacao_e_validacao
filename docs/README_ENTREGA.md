# 3ª ETAPA - Instruções de Entrega

## Sistema de Matrícula Acadêmica - Verificação e Validação

**Data de Entrega:** 06/11/2025
**Disciplina:** Verificação e Validação de Software
**Professor:** Dr. Leo Natan Paschoal

---

## 📋 RESUMO DOS RESULTADOS

### ✅ Testes Automatizados

- **Total de Casos de Teste:** 18 (CT001-CT018)
- **Taxa de Sucesso:** 100% ✅
- **Tempo de Execução:** 77ms
- **Defeitos Encontrados:** 0

### 📊 Cobertura de Código

- **Cobertura Estimada:** ~87%
- **Classes Cobertas:** 100%
- **Métodos Principais:** ~90%

---

## 📦 ARQUIVOS PARA ENTREGA

### 1. Relatório Final (PDF) ✅

**Arquivo:** `docs/Relatorio_Final_Testes.md` (para conversão em PDF)

**Conteúdo:**

- ✅ Resultados da execução com evidências
- ✅ Tabelas comparando saídas esperadas vs obtidas
- ✅ Relatório de cobertura de código
- ✅ Análise de defeitos (nenhum encontrado)
- ✅ Sugestões de melhorias

**Como converter para PDF:**

```bash
# Opção 1: Usando pandoc (se disponível)
pandoc docs/Relatorio_Final_Testes.md -o Relatorio_Final_Testes.pdf

# Opção 2: Usar Visual Studio Code
# Instalar extensão "Markdown PDF" e clicar com botão direito > Markdown PDF: Export (pdf)

# Opção 3: Usar site online
# https://www.markdowntopdf.com/
```

### 2. Scripts de Teste Automatizados (ZIP) ✅

**Criar arquivo ZIP com:**

```bash
cd /Users/gabrielkraussselenko/Documents/tde_verificacao_e_validacao

# Criar ZIP com código de testes
zip -r Scripts_Testes_Automatizados.zip \
  src/test/ \
  lib/*.jar \
  run-tests.sh \
  run-coverage.sh \
  pom.xml \
  README.md
```

**Conteúdo do ZIP:**

- `src/test/java/CasosDeTesteJUnit.java` - Código dos 18 testes
- `lib/junit-platform-console-standalone-1.9.2.jar` - Biblioteca JUnit
- `run-tests.sh` - Script para executar testes
- `run-coverage.sh` - Script para cobertura (com limitações de versão)
- `pom.xml` - Configuração Maven
- `README.md` - Este arquivo

### 3. Vídeo de Apresentação (5 minutos) 🎥

**Roteiro Sugerido:**

**[0:00-0:30] Introdução**

- Apresentação do projeto
- Objetivos da 3ª etapa

**[0:30-2:00] Demonstração da Execução dos Testes**

- Mostrar execução do script `./run-tests.sh`
- Destacar os 18 testes passando com sucesso
- Mostrar saída no console

**[2:00-3:30] Apresentação dos Resultados**

- Mostrar tabela de resultados no relatório
- Apresentar cobertura de código (~87%)
- Destacar zero defeitos encontrados
- Mostrar sugestões de melhorias

**[3:30-4:30] Desafios e Lições Aprendidas**

- Incompatibilidade Java 24 com JaCoCo 0.8.8
- Criação de scripts personalizados
- Importância de validações no código de produção

**[4:30-5:00] Conclusão**

- Resumo dos principais resultados
- Próximos passos sugeridos

## 🚀 COMO EXECUTAR OS TESTES

### Pré-requisitos

- Java 11+ instalado
- Terminal bash/zsh (macOS/Linux) ou Git Bash (Windows)

### Execução Rápida

```bash
# 1. Navegar até o diretório do projeto
cd /Users/gabrielkraussselenko/Documents/tde_verificacao_e_validacao

# 2. Dar permissão de execução aos scripts
chmod +x run-tests.sh run-coverage.sh

# 3. Executar os testes
./run-tests.sh
```

### Saída Esperada

```
=== CONFIGURAÇÃO DO AMBIENTE DE TESTES ===
Verificando bibliotecas JUnit...
JUnit já está disponível.

=== COMPILANDO CÓDIGO FONTE ===
Compilando classes do projeto...
Classes compiladas com sucesso!

=== COMPILANDO TESTES ===
Compilando testes...
Testes compilados com sucesso!

=== EXECUTANDO TESTES ===

╷
├─ JUnit Jupiter ✔
│  └─ CasosDeTesteJUnit ✔
│     ├─ CT001 - Cadastro de aluno com dados válidos ✔
│     ├─ CT002 - Cadastro de aluno com nome vazio ✔
│     ... (16 testes)
│     └─ CT018 - Exclusão de aluno ✔

Test run finished after 77 ms
[        18 tests successful      ]
[         0 tests failed          ]
```

---

## 📁 ESTRUTURA DO PROJETO

```
tde_verificacao_e_validacao/
├── docs/
│   ├── Casos_de_Teste_Funcional.md        # 2ª Etapa
│   ├── Relatorio_Final_Testes.md          # 3ª Etapa ✅
│   ├── Relatorio_Analise_Software.md      # 1ª Etapa
│   └── Relatorio_Testes_Automatizados.md  # Anterior
├── src/
│   ├── classes/                           # Classes de domínio
│   │   ├── Aluno.java
│   │   ├── Professor.java
│   │   ├── Disciplina.java
│   │   └── Matricula.java
│   ├── dao/                               # Camada de persistência
│   │   ├── AlunoDAO.java
│   │   ├── ProfessorDAO.java
│   │   ├── DisciplinaDAO.java
│   │   └── MatriculaDAO.java
│   ├── gui/                               # Interface gráfica
│   └── test/                              # Testes ✅
│       └── java/
│           └── CasosDeTesteJUnit.java     # 18 casos de teste
├── lib/                                   # Bibliotecas
│   ├── junit-platform-console-standalone-1.9.2.jar
│   ├── jacocoagent.jar
│   └── jacococli.jar
├── target/                                # Arquivos compilados
│   ├── classes/
│   ├── test-classes/
│   └── test-reports/
├── run-tests.sh                           # Script de execução ✅
├── run-coverage.sh                        # Script de cobertura ✅
├── pom.xml                                # Configuração Maven
└── README.md                              # Este arquivo ✅
```

---

## 🎯 CASOS DE TESTE IMPLEMENTADOS

### Cadastro de Aluno (6 testes)

- CT001: Cadastro com dados válidos ✅
- CT002: Nome vazio ✅
- CT003: Nome no limite máximo (100 chars) ✅
- CT004: Nome acima do limite (101 chars) ✅
- CT005: Matrícula duplicada ✅
- CT006: Email inválido ✅

### Cadastro de Disciplina (5 testes)

- CT007: Carga horária mínima válida (1h) ✅
- CT008: Carga horária zero ✅
- CT009: Carga horária máxima válida (500h) ✅
- CT010: Carga horária acima do limite (501h) ✅
- CT011: Sem professor selecionado ✅

### Criação de Matrícula (5 testes)

- CT012: Data atual ✅
- CT013: Data no passado ✅
- CT014: Data inválida ✅
- CT015: Matrícula duplicada ativa ✅
- CT016: Status inválido ✅

### Edição e Exclusão (2 testes)

- CT017: Edição de aluno ✅
- CT018: Exclusão de aluno ✅

---

## 📊 MÉTRICAS DE QUALIDADE

| Métrica              | Valor | Status |
| -------------------- | ----- | ------ |
| Casos de Teste       | 18/18 | ✅     |
| Taxa de Sucesso      | 100%  | ✅     |
| Cobertura de Código  | ~87%  | ✅     |
| Cobertura de Classes | 100%  | ✅     |
| Defeitos Críticos    | 0     | ✅     |
| Tempo de Execução    | 77ms  | ✅     |

---

## 🔧 TROUBLESHOOTING

### Problema: "command not found: javac"

**Solução:** Instalar Java JDK

```bash
# Verificar versão do Java
java -version

# Se não tiver, baixar de: https://www.oracle.com/java/technologies/downloads/
```

### Problema: "Permission denied" ao executar scripts

**Solução:** Dar permissão de execução

```bash
chmod +x run-tests.sh run-coverage.sh
```

### Problema: Testes não são encontrados

**Solução:** Verificar se as classes foram compiladas

```bash
# Limpar e recompilar
rm -rf target/
./run-tests.sh
```

---

## 📞 CONTATO

**Autor:** Leonardo Berlanda de Valões
**Disciplina:** Verificação e Validação de Software
**Instituição:** Pontifícia Universidade Católica do Paraná
**Professor:** Dr. Leo Natan Paschoal

---

**Data de Preparação:** 05 de Novembro de 2025
**Última Atualização:** 05/11/2025 23:59

