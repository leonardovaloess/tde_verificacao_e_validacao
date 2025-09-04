package classes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da Classe Aluno")
public class AlunoTest {

    private Aluno aluno;
    
    @BeforeEach
    void setUp() {
        aluno = new Aluno();
    }

    @Test
    @DisplayName("Deve criar aluno com construtor completo")
    void testConstrutorCompleto() {
        // Arrange & Act
        Aluno alunoCompleto = new Aluno(1, "João Silva", "2023001", "joao@email.com", "(11) 99999-9999");
        
        // Assert
        assertEquals(1, alunoCompleto.getId());
        assertEquals("João Silva", alunoCompleto.getNome());
        assertEquals("2023001", alunoCompleto.getMatricula());
        assertEquals("joao@email.com", alunoCompleto.getEmail());
        assertEquals("(11) 99999-9999", alunoCompleto.getTelefone());
    }

    @Test
    @DisplayName("Deve criar aluno sem ID")
    void testConstrutorSemId() {
        // Arrange & Act
        Aluno alunoSemId = new Aluno("Maria Santos", "2023002", "maria@email.com", "(11) 88888-8888");
        
        // Assert
        assertEquals(0, alunoSemId.getId()); // ID padrão
        assertEquals("Maria Santos", alunoSemId.getNome());
        assertEquals("2023002", alunoSemId.getMatricula());
        assertEquals("maria@email.com", alunoSemId.getEmail());
        assertEquals("(11) 88888-8888", alunoSemId.getTelefone());
    }

    @Test
    @DisplayName("Deve configurar e recuperar ID")
    void testSetterGetterId() {
        // Arrange & Act
        aluno.setId(5);
        
        // Assert
        assertEquals(5, aluno.getId());
    }

    @Test
    @DisplayName("Deve configurar e recuperar nome")
    void testSetterGetterNome() {
        // Arrange
        String nomeEsperado = "Pedro Oliveira";
        
        // Act
        aluno.setNome(nomeEsperado);
        
        // Assert
        assertEquals(nomeEsperado, aluno.getNome());
    }

    @ParameterizedTest
    @ValueSource(strings = {"2023001", "2022999", "2024001", "ABC123", "123456"})
    @DisplayName("Deve aceitar diferentes formatos de matrícula válidos")
    void testMatriculasValidas(String matricula) {
        // Act
        aluno.setMatricula(matricula);
        
        // Assert
        assertEquals(matricula, aluno.getMatricula());
    }

    @ParameterizedTest
    @ValueSource(strings = {"email@teste.com", "usuario@dominio.com.br", "test@example.org"})
    @DisplayName("Deve aceitar diferentes formatos de email")
    void testEmailsValidos(String email) {
        // Act
        aluno.setEmail(email);
        
        // Assert
        assertEquals(email, aluno.getEmail());
    }

    @Test
    @DisplayName("Deve configurar e recuperar telefone")
    void testSetterGetterTelefone() {
        // Arrange
        String telefoneEsperado = "(11) 98765-4321";
        
        // Act
        aluno.setTelefone(telefoneEsperado);
        
        // Assert
        assertEquals(telefoneEsperado, aluno.getTelefone());
    }

    @Test
    @DisplayName("Deve converter aluno para string no formato CSV")
    void testToString() {
        // Arrange
        Aluno alunoTeste = new Aluno(10, "Ana Costa", "2023003", "ana@email.com", "(11) 77777-7777");
        String expectedString = "10;Ana Costa;2023003;ana@email.com;(11) 77777-7777";
        
        // Act
        String resultado = alunoTeste.toString();
        
        // Assert
        assertEquals(expectedString, resultado);
    }

    @Test
    @DisplayName("Deve criar aluno a partir de string CSV")
    void testFromString() {
        // Arrange
        String csvString = "15;Carlos Ferreira;2023004;carlos@email.com;(11) 66666-6666";
        
        // Act
        Aluno alunoFromString = Aluno.fromString(csvString);
        
        // Assert
        assertEquals(15, alunoFromString.getId());
        assertEquals("Carlos Ferreira", alunoFromString.getNome());
        assertEquals("2023004", alunoFromString.getMatricula());
        assertEquals("carlos@email.com", alunoFromString.getEmail());
        assertEquals("(11) 66666-6666", alunoFromString.getTelefone());
    }

    @Test
    @DisplayName("Deve retornar string de exibição formatada")
    void testToDisplayString() {
        // Arrange
        Aluno alunoDisplay = new Aluno(20, "Lucia Santos", "2023005", "lucia@email.com", "(11) 55555-5555");
        String expectedDisplay = "Lucia Santos - 2023005";
        
        // Act
        String resultado = alunoDisplay.toDisplayString();
        
        // Assert
        assertEquals(expectedDisplay, resultado);
    }

    @Test
    @DisplayName("Deve lidar com valores nulos nos setters")
    void testSettersComValoresNulos() {
        // Act & Assert - não deve lançar exceção
        assertDoesNotThrow(() -> {
            aluno.setNome(null);
            aluno.setMatricula(null);
            aluno.setEmail(null);
            aluno.setTelefone(null);
        });
        
        assertNull(aluno.getNome());
        assertNull(aluno.getMatricula());
        assertNull(aluno.getEmail());
        assertNull(aluno.getTelefone());
    }

    @Test
    @DisplayName("Deve testar ciclo completo de serialização/deserialização")
    void testCicloSerializacaoDeserializacao() {
        // Arrange
        Aluno alunoOriginal = new Aluno(25, "Roberto Silva", "2023006", "roberto@email.com", "(11) 44444-4444");
        
        // Act
        String csvString = alunoOriginal.toString();
        Aluno alunoReconstruido = Aluno.fromString(csvString);
        
        // Assert
        assertEquals(alunoOriginal.getId(), alunoReconstruido.getId());
        assertEquals(alunoOriginal.getNome(), alunoReconstruido.getNome());
        assertEquals(alunoOriginal.getMatricula(), alunoReconstruido.getMatricula());
        assertEquals(alunoOriginal.getEmail(), alunoReconstruido.getEmail());
        assertEquals(alunoOriginal.getTelefone(), alunoReconstruido.getTelefone());
    }

    @Test
    @DisplayName("Deve testar equals entre objetos Aluno")
    void testIgualdadeEntreAlunos() {
        // Arrange
        Aluno aluno1 = new Aluno(1, "João", "2023001", "joao@email.com", "(11) 99999-9999");
        Aluno aluno2 = new Aluno(1, "João", "2023001", "joao@email.com", "(11) 99999-9999");
        Aluno aluno3 = new Aluno(2, "Maria", "2023002", "maria@email.com", "(11) 88888-8888");
        
        // Act & Assert
        assertEquals(aluno1.toString(), aluno2.toString()); // Comparação via string
        assertNotEquals(aluno1.toString(), aluno3.toString());
    }
}
