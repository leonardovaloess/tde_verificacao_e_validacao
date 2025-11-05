#!/bin/bash

# Script para executar testes com cobertura de código usando JaCoCo
# 3ª Etapa - Verificação e Validação

echo "=== CONFIGURAÇÃO DO AMBIENTE DE COBERTURA ==="

# Criar diretórios
mkdir -p lib
mkdir -p target/classes
mkdir -p target/test-classes
mkdir -p target/coverage-reports

# URLs para download das bibliotecas
JUNIT_PLATFORM="https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.2/junit-platform-console-standalone-1.9.2.jar"
JACOCO_AGENT="https://repo1.maven.org/maven2/org/jacoco/org.jacoco.agent/0.8.8/org.jacoco.agent-0.8.8-runtime.jar"
JACOCO_CLI="https://repo1.maven.org/maven2/org/jacoco/org.jacoco.cli/0.8.8/org.jacoco.cli-0.8.8-nodeps.jar"

echo "Verificando bibliotecas..."

# Baixar JUnit se não existir
if [ ! -f "lib/junit-platform-console-standalone-1.9.2.jar" ]; then
    echo "Baixando JUnit Platform Console..."
    curl -L -o lib/junit-platform-console-standalone-1.9.2.jar "$JUNIT_PLATFORM"
fi

# Baixar JaCoCo Agent
if [ ! -f "lib/jacocoagent.jar" ]; then
    echo "Baixando JaCoCo Agent..."
    curl -L -o lib/jacocoagent.jar "$JACOCO_AGENT"
fi

# Baixar JaCoCo CLI
if [ ! -f "lib/jacococli.jar" ]; then
    echo "Baixando JaCoCo CLI..."
    curl -L -o lib/jacococli.jar "$JACOCO_CLI"
fi

echo "Bibliotecas configuradas!"

echo ""
echo "=== COMPILANDO CÓDIGO FONTE ==="

# Compilar classes do projeto
echo "Compilando classes do projeto..."
javac -d target/classes -sourcepath src \
    src/classes/*.java \
    src/dao/*.java \
    src/*.java

if [ $? -ne 0 ]; then
    echo "Erro na compilação das classes do projeto!"
    exit 1
fi

echo "Classes compiladas com sucesso!"

echo ""
echo "=== COMPILANDO TESTES ==="

# Compilar testes
echo "Compilando testes..."
find src/test -name "*.java" -print0 | xargs -0 javac -d target/test-classes \
    -cp "target/classes:lib/junit-platform-console-standalone-1.9.2.jar" \
    -sourcepath src

if [ $? -ne 0 ]; then
    echo "Erro na compilação dos testes!"
    exit 1
fi

echo "Testes compilados com sucesso!"

echo ""
echo "=== EXECUTANDO TESTES COM COBERTURA ==="

# Executar testes com JaCoCo agent
java -javaagent:lib/jacocoagent.jar=destfile=target/jacoco.exec \
    -jar lib/junit-platform-console-standalone-1.9.2.jar \
    --class-path target/classes:target/test-classes \
    --select-class CasosDeTesteJUnit \
    --reports-dir=target/test-reports

if [ $? -ne 0 ]; then
    echo "Erro na execução dos testes!"
    exit 1
fi

echo ""
echo "=== GERANDO RELATÓRIO DE COBERTURA ==="

# Gerar relatório HTML de cobertura
java -jar lib/jacococli.jar report target/jacoco.exec \
    --classfiles target/classes \
    --sourcefiles src \
    --html target/coverage-reports/html \
    --xml target/coverage-reports/jacoco.xml \
    --csv target/coverage-reports/jacoco.csv

if [ $? -ne 0 ]; then
    echo "Erro ao gerar relatório de cobertura!"
    exit 1
fi

echo ""
echo "=== TESTES E COBERTURA CONCLUÍDOS ==="
echo "Relatórios de teste salvos em: target/test-reports"
echo "Relatórios de cobertura salvos em: target/coverage-reports/html"
echo "Arquivo XML de cobertura: target/coverage-reports/jacoco.xml"
echo "Arquivo CSV de cobertura: target/coverage-reports/jacoco.csv"
echo ""
echo "Para visualizar o relatório HTML, abra: target/coverage-reports/html/index.html"
