#!/bin/bash

# Script para executar testes sem Maven
# 3ª Etapa - Verificação e Validação

echo "=== CONFIGURAÇÃO DO AMBIENTE DE TESTES ==="

# Criar diretórios
mkdir -p lib
mkdir -p target/classes
mkdir -p target/test-classes

# URLs para download das bibliotecas JUnit 5
JUNIT_PLATFORM_CONSOLE="https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.2/junit-platform-console-standalone-1.9.2.jar"

echo "Verificando bibliotecas JUnit..."

# Baixar JUnit se não existir
if [ ! -f "lib/junit-platform-console-standalone-1.9.2.jar" ]; then
    echo "Baixando JUnit Platform Console..."
    curl -L -o lib/junit-platform-console-standalone-1.9.2.jar "$JUNIT_PLATFORM_CONSOLE"
    echo "JUnit baixado com sucesso!"
else
    echo "JUnit já está disponível."
fi

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
echo "=== EXECUTANDO TESTES ==="

# Executar testes
java -jar lib/junit-platform-console-standalone-1.9.2.jar \
    --class-path target/classes:target/test-classes \
    --select-class CasosDeTesteJUnit \
    --reports-dir=target/test-reports

echo ""
echo "=== TESTES CONCLUÍDOS ==="
echo "Relatórios salvos em: target/test-reports"
