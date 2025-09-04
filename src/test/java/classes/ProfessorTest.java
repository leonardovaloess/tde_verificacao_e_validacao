package classes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da Classe Professor")
public class ProfessorTest {

    private Professor professor;
    
    @BeforeEach
    void setUp() {
        professor = new Professor();
    }

    @Test
    @DisplayName("Deve criar professor com construtor completo")
    void testConstrutorCompleto() {
        // Arrange & Act
        Professor professorCompleto = new Professor(1, "Dr. João Silva", "PROF001", "joao@universidade.edu", "Ciência da Computação");
        
        // Assert
        assertEquals(1, professorCompleto.getId());
        assertEquals("Dr. João Silva", professorCompleto.getNome());
        assertEquals("PROF001", professorCompleto.getIdentificador());
        assertEquals("joao@universidade.edu", professorCompleto.getEmail());
        assertEquals("Ciência da Computação", professorCompleto.getDepartamento());
    }

    @Test
    @DisplayName("Deve criar professor sem ID")
    void testConstrutorSemId() {
        // Arrange & Act
        Professor professorSemId = new Professor("Dra. Maria Santos", "PROF002", "maria@universidade.edu", "Matemática");
        
        // Assert
        assertEquals(0, professorSemId.getId()); // ID padrão
        assertEquals("Dra. Maria Santos", professorSemId.getNome());
        assertEquals("PROF002", professorSemId.getIdentificador());
        assertEquals("maria@universidade.edu", professorSemId.getEmail());
        assertEquals("Matemática", professorSemId.getDepartamento());
    }

    @Test
    @DisplayName("Deve configurar e recuperar todos os campos")
    void testSettersGetters() {
        // Arrange
        int id = 10;
        String nome = "Prof. Carlos Oliveira";
        String identificador = "PROF010";
        String email = "carlos@universidade.edu";
        String departamento = "Física";
        
        // Act
        professor.setId(id);
        professor.setNome(nome);
        professor.setIdentificador(identificador);
        professor.setEmail(email);
        professor.setDepartamento(departamento);
        
        // Assert
        assertEquals(id, professor.getId());
        assertEquals(nome, professor.getNome());
        assertEquals(identificador, professor.getIdentificador());
        assertEquals(email, professor.getEmail());
        assertEquals(departamento, professor.getDepartamento());
    }

    @ParameterizedTest
    @ValueSource(strings = {"PROF001", "P123", "DOC_001", "TEACHER-99", "ABC123XYZ"})
    @DisplayName("Deve aceitar diferentes formatos de identificador")
    void testIdentificadoresValidos(String identificador) {
        // Act
        professor.setIdentificador(identificador);
        
        // Assert
        assertEquals(identificador, professor.getIdentificador());
    }

    @Test
    @DisplayName("Deve converter professor para string no formato CSV")
    void testToString() {
        // Arrange
        Professor professorTeste = new Professor(5, "Prof. Ana Costa", "PROF005", "ana@universidade.edu", "Química");
        String expectedString = "5;Prof. Ana Costa;PROF005;ana@universidade.edu;Química";
        
        // Act
        String resultado = professorTeste.toString();
        
        // Assert
        assertEquals(expectedString, resultado);
    }

    @Test
    @DisplayName("Deve criar professor a partir de string CSV")
    void testFromString() {
        // Arrange
        String csvString = "8;Dr. Roberto Ferreira;PROF008;roberto@universidade.edu;Biologia";
        
        // Act
        Professor professorFromString = Professor.fromString(csvString);
        
        // Assert
        assertEquals(8, professorFromString.getId());
        assertEquals("Dr. Roberto Ferreira", professorFromString.getNome());
        assertEquals("PROF008", professorFromString.getIdentificador());
        assertEquals("roberto@universidade.edu", professorFromString.getEmail());
        assertEquals("Biologia", professorFromString.getDepartamento());
    }

    @Test
    @DisplayName("Deve retornar string de exibição formatada")
    void testToDisplayString() {
        // Arrange
        Professor professorDisplay = new Professor(3, "Profa. Lucia Santos", "PROF003", "lucia@universidade.edu", "História");
        String expectedDisplay = "Profa. Lucia Santos - PROF003";
        
        // Act
        String resultado = professorDisplay.toDisplayString();
        
        // Assert
        assertEquals(expectedDisplay, resultado);
    }

    @Test
    @DisplayName("Deve lidar com valores nulos")
    void testValoresNulos() {
        // Act & Assert - não deve lançar exceção
        assertDoesNotThrow(() -> {
            professor.setNome(null);
            professor.setIdentificador(null);
            professor.setEmail(null);
            professor.setDepartamento(null);
        });
        
        assertNull(professor.getNome());
        assertNull(professor.getIdentificador());
        assertNull(professor.getEmail());
        assertNull(professor.getDepartamento());
    }

    @Test
    @DisplayName("Deve testar ciclo completo de serialização/deserialização")
    void testCicloSerializacaoDeserializacao() {
        // Arrange
        Professor professorOriginal = new Professor(12, "Prof. Pedro Silva", "PROF012", "pedro@universidade.edu", "Engenharia");
        
        // Act
        String csvString = professorOriginal.toString();
        Professor professorReconstruido = Professor.fromString(csvString);
        
        // Assert
        assertEquals(professorOriginal.getId(), professorReconstruido.getId());
        assertEquals(professorOriginal.getNome(), professorReconstruido.getNome());
        assertEquals(professorOriginal.getIdentificador(), professorReconstruido.getIdentificador());
        assertEquals(professorOriginal.getEmail(), professorReconstruido.getEmail());
        assertEquals(professorOriginal.getDepartamento(), professorReconstruido.getDepartamento());
    }
}
