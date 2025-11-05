#!/bin/bash

echo "=========================================="
echo "  EXECUÇÃO DOS TESTES UNITÁRIOS"
echo "=========================================="
echo ""

# Compilar todas as classes
echo "🔨 Compilando classes..."
javac -cp "." src/main/java/classes/*.java src/main/java/dao/*.java src/test/java/*.java

if [ $? -eq 0 ]; then
    echo "✅ Compilação realizada com sucesso!"
    echo ""
    
    # Executar o TestRunner
    echo "🚀 Executando testes..."
    echo ""
    java -cp "src/main/java:src/test/java" TestRunner
    
    echo ""
    echo "=========================================="
    echo "  RELATÓRIOS GERADOS"
    echo "=========================================="
    echo "📄 Verificar: target/test-reports/"
    
    if [ -f "target/jacoco.exec" ]; then
        echo "📊 Cobertura JaCoCo: target/jacoco.exec"
    fi
    
else
    echo "❌ Erro na compilação!"
    exit 1
fi