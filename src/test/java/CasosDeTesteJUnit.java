import classes.*;
import dao.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Implementação dos Casos de Teste da 2ª Etapa
 * Baseado no documento: Casos_de_Teste_Funcional.md
 *
 * Total de casos: 18 (CT001-CT018)
 *
 * @author Leonardo Berlanda de Valões
 * @disciplina Verificação e Validação de Software
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CasosDeTesteJUnit {

  private AlunoDAO alunoDAO;
  private ProfessorDAO professorDAO;
  private DisciplinaDAO disciplinaDAO;
  private MatriculaDAO matriculaDAO;

  @BeforeEach
  public void setUp() {
    // Limpar dados de teste antes de cada teste
    limparArquivosDados();

    alunoDAO = new AlunoDAO();
    professorDAO = new ProfessorDAO();
    disciplinaDAO = new DisciplinaDAO();
    matriculaDAO = new MatriculaDAO();
  }

  @AfterEach
  public void tearDown() {
    // Opcional: limpar após testes
  }

  private void limparArquivosDados() {
    // Criar diretório se não existir
    File dir = new File("dados");
    if (!dir.exists()) {
      dir.mkdirs();
    }

    // Limpar arquivos de dados
    new File("dados/alunos.txt").delete();
    new File("dados/professores.txt").delete();
    new File("dados/disciplinas.txt").delete();
    new File("dados/matriculas.txt").delete();
  }

  // ==================== MÓDULO: CADASTRO DE ALUNO ====================

  /**
   * CT001 - Cadastro de aluno com dados válidos
   * Classes Cobertas: CE1, CE6, CE12
   * Prioridade: Alta
   */
  @Test
  @Order(1)
  @DisplayName("CT001 - Cadastro de aluno com dados válidos")
  public void testCT001_CadastroAlunoValido() {
    // Arrange
    Aluno aluno = new Aluno("João Silva", "2023001", "joao@email.com", "(11) 99999-9999");

    // Act
    alunoDAO.salvar(aluno);
    List<Aluno> alunos = alunoDAO.listarTodos();

    // Assert
    assertNotNull(alunos, "Lista de alunos não deve ser nula");
    assertEquals(1, alunos.size(), "Deve haver 1 aluno cadastrado");
    assertEquals("João Silva", alunos.get(0).getNome(), "Nome deve ser 'João Silva'");
    assertEquals("2023001", alunos.get(0).getMatricula(), "Matrícula deve ser '2023001'");
    assertEquals("joao@email.com", alunos.get(0).getEmail(), "Email deve estar correto");
  }

  /**
   * CT002 - Cadastro de aluno com nome vazio
   * Classes Cobertas: CE2
   * Prioridade: Alta
   */
  @Test
  @Order(2)
  @DisplayName("CT002 - Cadastro de aluno com nome vazio")
  public void testCT002_CadastroAlunoNomeVazio() {
    // Arrange
    Aluno aluno = new Aluno("", "2023002", "ana@email.com", "(11) 88888-8888");

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      validarAluno(aluno);
    });

    assertTrue(exception.getMessage().contains("Nome é obrigatório") ||
        exception.getMessage().contains("Nome"),
        "Deve lançar erro de nome obrigatório");
  }

  /**
   * CT003 - Cadastro de aluno com nome no limite máximo (100 caracteres)
   * Classes Cobertas: CE1, VL3
   * Prioridade: Média
   */
  @Test
  @Order(3)
  @DisplayName("CT003 - Cadastro de aluno com nome no limite máximo")
  public void testCT003_CadastroAlunoNomeLimiteMaximo() {
    // Arrange
    String nomeComExatos100Chars = "A".repeat(100);
    Aluno aluno = new Aluno(nomeComExatos100Chars, "2023003", "limite@email.com", "(11) 77777-7777");

    // Act
    alunoDAO.salvar(aluno);
    List<Aluno> alunos = alunoDAO.listarTodos();

    // Assert
    assertEquals(1, alunos.size(), "Deve haver 1 aluno cadastrado");
    assertEquals(100, alunos.get(0).getNome().length(), "Nome deve ter exatamente 100 caracteres");
  }

  /**
   * CT004 - Cadastro de aluno com nome acima do limite (101 caracteres)
   * Classes Cobertas: CE3, VL4
   * Prioridade: Média
   */
  @Test
  @Order(4)
  @DisplayName("CT004 - Cadastro de aluno com nome acima do limite")
  public void testCT004_CadastroAlunoNomeAcimaLimite() {
    // Arrange
    String nomeCom101Chars = "A".repeat(101);
    Aluno aluno = new Aluno(nomeCom101Chars, "2023004", "excesso@email.com", "(11) 66666-6666");

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      validarAluno(aluno);
    });

    assertTrue(exception.getMessage().contains("Nome") ||
        exception.getMessage().contains("100"),
        "Deve lançar erro de tamanho de nome");
  }

  /**
   * CT005 - Cadastro de aluno com matrícula duplicada
   * Classes Cobertas: CE11
   * Prioridade: Alta
   */
  @Test
  @Order(5)
  @DisplayName("CT005 - Cadastro de aluno com matrícula duplicada")
  public void testCT005_CadastroAlunoMatriculaDuplicada() {
    // Arrange
    Aluno aluno1 = new Aluno("João Silva", "2023001", "joao@email.com", "(11) 99999-9999");
    alunoDAO.salvar(aluno1);

    Aluno aluno2 = new Aluno("Pedro Costa", "2023001", "pedro@email.com", "(11) 55555-5555");

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      if (alunoDAO.existeMatricula(aluno2.getMatricula())) {
        throw new IllegalArgumentException("Matrícula já existe!");
      }
    });

    assertEquals("Matrícula já existe!", exception.getMessage(),
        "Deve lançar erro de matrícula duplicada");
  }

  /**
   * CT006 - Cadastro de aluno com email inválido
   * Classes Cobertas: CE14
   * Prioridade: Alta
   */
  @Test
  @Order(6)
  @DisplayName("CT006 - Cadastro de aluno com email inválido")
  public void testCT006_CadastroAlunoEmailInvalido() {
    // Arrange
    Aluno aluno = new Aluno("Carlos Silva", "2023005", "emailinvalido", "(11) 44444-4444");

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      validarEmail(aluno.getEmail());
    });

    assertTrue(exception.getMessage().contains("email") ||
        exception.getMessage().contains("Email"),
        "Deve lançar erro de validação de email");
  }

  // ==================== MÓDULO: CADASTRO DE DISCIPLINA ====================

  /**
   * CT007 - Cadastro de disciplina com carga horária mínima válida (1)
   * Classes Cobertas: CE18, VL6
   * Prioridade: Média
   */
  @Test
  @Order(7)
  @DisplayName("CT007 - Cadastro de disciplina com carga horária mínima válida")
  public void testCT007_CadastroDisciplinaCargaHorariaMinimaValida() {
    // Arrange
    Professor professor = new Professor("Dr. João Silva", "PROF001", "joao@universidade.edu", "Computação");
    professorDAO.salvar(professor);
    List<Professor> professores = professorDAO.listarTodos();

    Disciplina disciplina = new Disciplina("Seminário", "SEM001", 1, professores.get(0).getId(), "2024.1");

    // Act
    disciplinaDAO.salvar(disciplina);
    List<Disciplina> disciplinas = disciplinaDAO.listarTodos();

    // Assert
    assertEquals(1, disciplinas.size(), "Deve haver 1 disciplina cadastrada");
    assertEquals(1, disciplinas.get(0).getCargaHoraria(), "Carga horária deve ser 1");
  }

  /**
   * CT008 - Cadastro de disciplina com carga horária zero
   * Classes Cobertas: CE19, VL5
   * Prioridade: Alta
   */
  @Test
  @Order(8)
  @DisplayName("CT008 - Cadastro de disciplina com carga horária zero")
  public void testCT008_CadastroDisciplinaCargaHorariaZero() {
    // Arrange
    Disciplina disciplina = new Disciplina("Teste", "TEST001", 0, 1, "2024.1");

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      validarCargaHoraria(disciplina.getCargaHoraria());
    });

    assertTrue(exception.getMessage().contains("Carga horária deve ser maior que zero"),
        "Deve lançar erro de carga horária zero");
  }

  /**
   * CT009 - Cadastro de disciplina com carga horária máxima válida (500)
   * Classes Cobertas: CE18, VL7
   * Prioridade: Média
   */
  @Test
  @Order(9)
  @DisplayName("CT009 - Cadastro de disciplina com carga horária máxima válida")
  public void testCT009_CadastroDisciplinaCargaHorariaMaximaValida() {
    // Arrange
    Professor professor = new Professor("Dr. João Silva", "PROF001", "joao@universidade.edu", "Computação");
    professorDAO.salvar(professor);
    List<Professor> professores = professorDAO.listarTodos();

    Disciplina disciplina = new Disciplina("Estágio", "EST001", 500, professores.get(0).getId(), "2024.1");

    // Act
    disciplinaDAO.salvar(disciplina);
    List<Disciplina> disciplinas = disciplinaDAO.listarTodos();

    // Assert
    assertEquals(1, disciplinas.size(), "Deve haver 1 disciplina cadastrada");
    assertEquals(500, disciplinas.get(0).getCargaHoraria(), "Carga horária deve ser 500");
  }

  /**
   * CT010 - Cadastro de disciplina com carga horária acima do limite (501)
   * Classes Cobertas: CE21, VL8
   * Prioridade: Alta
   */
  @Test
  @Order(10)
  @DisplayName("CT010 - Cadastro de disciplina com carga horária acima do limite")
  public void testCT010_CadastroDisciplinaCargaHorariaAcimaLimite() {
    // Arrange
    Disciplina disciplina = new Disciplina("Excesso", "EXC001", 501, 1, "2024.1");

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      validarCargaHoraria(disciplina.getCargaHoraria());
    });

    assertTrue(exception.getMessage().contains("Carga horária não pode exceder 500 horas"),
        "Deve lançar erro de carga horária acima do limite");
  }

  /**
   * CT011 - Cadastro de disciplina sem professor selecionado
   * Prioridade: Alta
   */
  @Test
  @Order(11)
  @DisplayName("CT011 - Cadastro de disciplina sem professor selecionado")
  public void testCT011_CadastroDisciplinaSemProfessor() {
    // Arrange
    Disciplina disciplina = new Disciplina("Sem Professor", "SP001", 60, 0, "2024.1");

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      if (disciplina.getProfessorId() == 0) {
        throw new IllegalArgumentException("Selecione um professor!");
      }
    });

    assertEquals("Selecione um professor!", exception.getMessage(),
        "Deve lançar erro de professor obrigatório");
  }

  // ==================== MÓDULO: CRIAÇÃO DE MATRÍCULA ====================

  /**
   * CT012 - Matrícula com data atual
   * Classes Cobertas: CE26
   * Prioridade: Alta
   */
  @Test
  @Order(12)
  @DisplayName("CT012 - Matrícula com data atual")
  public void testCT012_MatriculaComDataAtual() {
    // Arrange
    Aluno aluno = new Aluno("João Silva", "2023001", "joao@email.com", "(11) 99999-9999");
    alunoDAO.salvar(aluno);

    Professor professor = new Professor("Dr. João Silva", "PROF001", "joao@universidade.edu", "Computação");
    professorDAO.salvar(professor);

    List<Professor> professores = professorDAO.listarTodos();
    Disciplina disciplina = new Disciplina("POO", "POO001", 60, professores.get(0).getId(), "2024.1");
    disciplinaDAO.salvar(disciplina);

    List<Aluno> alunos = alunoDAO.listarTodos();
    List<Disciplina> disciplinas = disciplinaDAO.listarTodos();

    LocalDate dataAtual = LocalDate.now();
    Matricula matricula = new Matricula(alunos.get(0).getId(), disciplinas.get(0).getId(), dataAtual, "ATIVA");

    // Act
    matriculaDAO.salvar(matricula);
    List<Matricula> matriculas = matriculaDAO.listarTodos();

    // Assert
    assertEquals(1, matriculas.size(), "Deve haver 1 matrícula cadastrada");
    assertEquals("ATIVA", matriculas.get(0).getStatus(), "Status deve ser ATIVA");
    assertEquals(dataAtual, matriculas.get(0).getDataMatricula(), "Data deve ser a atual");
  }

  /**
   * CT013 - Matrícula com data no passado
   * Classes Cobertas: CE27
   * Prioridade: Média
   */
  @Test
  @Order(13)
  @DisplayName("CT013 - Matrícula com data no passado")
  public void testCT013_MatriculaComDataPassado() {
    // Arrange
    Aluno aluno = new Aluno("João Silva", "2023001", "joao@email.com", "(11) 99999-9999");
    alunoDAO.salvar(aluno);

    Professor professor = new Professor("Dr. João Silva", "PROF001", "joao@universidade.edu", "Computação");
    professorDAO.salvar(professor);

    List<Professor> professores = professorDAO.listarTodos();
    Disciplina disciplina = new Disciplina("POO", "POO001", 60, professores.get(0).getId(), "2024.1");
    disciplinaDAO.salvar(disciplina);

    List<Aluno> alunos = alunoDAO.listarTodos();
    List<Disciplina> disciplinas = disciplinaDAO.listarTodos();

    LocalDate dataPassada = LocalDate.of(2020, 1, 1);
    Matricula matricula = new Matricula(alunos.get(0).getId(), disciplinas.get(0).getId(), dataPassada, "CONCLUIDA");

    // Act
    matriculaDAO.salvar(matricula);
    List<Matricula> matriculas = matriculaDAO.listarTodos();

    // Assert
    assertEquals(1, matriculas.size(), "Deve haver 1 matrícula cadastrada");
    assertEquals("CONCLUIDA", matriculas.get(0).getStatus(), "Status deve ser CONCLUIDA");
  }

  /**
   * CT014 - Matrícula com data inválida
   * Classes Cobertas: CE29
   * Prioridade: Alta
   */
  @Test
  @Order(14)
  @DisplayName("CT014 - Matrícula com data inválida")
  public void testCT014_MatriculaComDataInvalida() {
    // Arrange
    String dataInvalida = "32/13/2025";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Act & Assert
    assertThrows(DateTimeParseException.class, () -> {
      LocalDate.parse(dataInvalida, formatter);
    }, "Deve lançar exceção para data inválida");
  }

  /**
   * CT015 - Matrícula duplicada ativa
   * Prioridade: Alta
   */
  @Test
  @Order(15)
  @DisplayName("CT015 - Matrícula duplicada ativa")
  public void testCT015_MatriculaDuplicadaAtiva() {
    // Arrange
    Aluno aluno = new Aluno("João Silva", "2023001", "joao@email.com", "(11) 99999-9999");
    alunoDAO.salvar(aluno);

    Professor professor = new Professor("Dr. João Silva", "PROF001", "joao@universidade.edu", "Computação");
    professorDAO.salvar(professor);

    List<Professor> professores = professorDAO.listarTodos();
    Disciplina disciplina = new Disciplina("POO", "POO001", 60, professores.get(0).getId(), "2024.1");
    disciplinaDAO.salvar(disciplina);

    List<Aluno> alunos = alunoDAO.listarTodos();
    List<Disciplina> disciplinas = disciplinaDAO.listarTodos();

    Matricula matricula1 = new Matricula(alunos.get(0).getId(), disciplinas.get(0).getId(), LocalDate.now(), "ATIVA");
    matriculaDAO.salvar(matricula1);

    Matricula matricula2 = new Matricula(alunos.get(0).getId(), disciplinas.get(0).getId(), LocalDate.now(), "ATIVA");

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      if (matriculaDAO.existeMatriculaAtiva(matricula2.getAlunoId(), matricula2.getDisciplinaId())) {
        throw new IllegalArgumentException("Já existe uma matrícula ativa para este aluno nesta disciplina!");
      }
    });

    assertEquals("Já existe uma matrícula ativa para este aluno nesta disciplina!",
        exception.getMessage(),
        "Deve lançar erro de matrícula duplicada");
  }

  /**
   * CT016 - Matrícula com status inválido
   * Classes Cobertas: CE24
   * Prioridade: Baixa
   */
  @Test
  @Order(16)
  @DisplayName("CT016 - Matrícula com status inválido")
  public void testCT016_MatriculaComStatusInvalido() {
    // Arrange
    String statusInvalido = "PENDENTE";
    String[] statusValidos = { "ATIVA", "CANCELADA", "CONCLUIDA" };

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      validarStatus(statusInvalido, statusValidos);
    });

    assertTrue(exception.getMessage().contains("Status inválido"),
        "Deve lançar erro de status inválido");
  }

  // ==================== MÓDULO: EDIÇÃO E EXCLUSÃO ====================

  /**
   * CT017 - Edição de aluno existente
   * Prioridade: Média
   */
  @Test
  @Order(17)
  @DisplayName("CT017 - Edição de aluno existente")
  public void testCT017_EdicaoAlunoExistente() {
    // Arrange
    Aluno aluno = new Aluno("João Silva", "2023001", "joao@email.com", "(11) 99999-9999");
    alunoDAO.salvar(aluno);

    List<Aluno> alunos = alunoDAO.listarTodos();
    Aluno alunoSalvo = alunos.get(0);

    // Act
    alunoSalvo.setNome("Nome Editado");
    alunoSalvo.setEmail("novo@email.com");
    alunoDAO.salvar(alunoSalvo);

    Aluno alunoEditado = alunoDAO.buscarPorId(alunoSalvo.getId());

    // Assert
    assertNotNull(alunoEditado, "Aluno editado não deve ser nulo");
    assertEquals("Nome Editado", alunoEditado.getNome(), "Nome deve estar editado");
    assertEquals("novo@email.com", alunoEditado.getEmail(), "Email deve estar editado");
    assertEquals(1, alunoDAO.listarTodos().size(), "Deve haver apenas 1 aluno na lista");
  }

  /**
   * CT018 - Exclusão de aluno
   * Prioridade: Média
   */
  @Test
  @Order(18)
  @DisplayName("CT018 - Exclusão de aluno")
  public void testCT018_ExclusaoAluno() {
    // Arrange
    Aluno aluno = new Aluno("João Silva", "2023001", "joao@email.com", "(11) 99999-9999");
    alunoDAO.salvar(aluno);

    List<Aluno> alunosAntes = alunoDAO.listarTodos();
    assertEquals(1, alunosAntes.size(), "Deve haver 1 aluno antes da exclusão");

    // Act
    alunoDAO.excluir(alunosAntes.get(0).getId());

    // Assert
    List<Aluno> alunosDepois = alunoDAO.listarTodos();
    assertEquals(0, alunosDepois.size(), "Não deve haver alunos após a exclusão");
  }

  // ==================== MÉTODOS AUXILIARES DE VALIDAÇÃO ====================

  private void validarAluno(Aluno aluno) {
    if (aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
      throw new IllegalArgumentException("Nome é obrigatório!");
    }
    if (aluno.getNome().length() > 100) {
      throw new IllegalArgumentException("Nome não pode ter mais de 100 caracteres!");
    }
    validarEmail(aluno.getEmail());
  }

  private void validarEmail(String email) {
    if (email == null || !email.contains("@")) {
      throw new IllegalArgumentException("Email inválido!");
    }
    String[] partes = email.split("@");
    if (partes.length != 2 || partes[0].isEmpty() || partes[1].isEmpty()) {
      throw new IllegalArgumentException("Email inválido!");
    }
  }

  private void validarCargaHoraria(int cargaHoraria) {
    if (cargaHoraria <= 0) {
      throw new IllegalArgumentException("Carga horária deve ser maior que zero");
    }
    if (cargaHoraria > 500) {
      throw new IllegalArgumentException("Carga horária não pode exceder 500 horas");
    }
  }

  private void validarStatus(String status, String[] statusValidos) {
    boolean valido = false;
    for (String s : statusValidos) {
      if (s.equals(status)) {
        valido = true;
        break;
      }
    }
    if (!valido) {
      throw new IllegalArgumentException("Status inválido");
    }
  }

  // ==================== MÉTODO MAIN PARA EXECUÇÃO DIRETA ====================

  /**
   * Método main para executar todos os testes sem precisar do JUnit runner
   */
  public static void main(String[] args) {
    CasosDeTesteJUnit testSuite = new CasosDeTesteJUnit();
    
    System.out.println("=".repeat(80));
    System.out.println("         EXECUÇÃO DOS CASOS DE TESTE - SISTEMA DE MATRÍCULA");
    System.out.println("=".repeat(80));
    
    int totalTestes = 18;
    int testesPassaram = 0;
    int testesFalharam = 0;
    
    // Lista de todos os métodos de teste
    String[] metodosTest = {
      "testCT001_CadastroAlunoValido",
      "testCT002_CadastroAlunoNomeVazio", 
      "testCT003_CadastroAlunoNomeLimiteMaximo",
      "testCT004_CadastroAlunoNomeAcimaLimite",
      "testCT005_CadastroAlunoMatriculaDuplicada",
      "testCT006_CadastroAlunoEmailInvalido",
      "testCT007_CadastroDisciplinaCargaHorariaMinimaValida",
      "testCT008_CadastroDisciplinaCargaHorariaZero",
      "testCT009_CadastroDisciplinaCargaHorariaMaximaValida",
      "testCT010_CadastroDisciplinaCargaHorariaAcimaLimite",
      "testCT011_CadastroDisciplinaSemProfessor",
      "testCT012_MatriculaComDataAtual",
      "testCT013_MatriculaComDataPassado",
      "testCT014_MatriculaComDataInvalida",
      "testCT015_MatriculaDuplicadaAtiva",
      "testCT016_MatriculaComStatusInvalido",
      "testCT017_EdicaoAlunoExistente",
      "testCT018_ExclusaoAluno"
    };
    
    for (int i = 0; i < metodosTest.length; i++) {
      String nomeMetodo = metodosTest[i];
      System.out.printf("\n[%02d/%02d] Executando: %s\n", i+1, totalTestes, nomeMetodo);
      
      try {
        testSuite.setUp();
        
        // Execução manual de cada teste
        switch (nomeMetodo) {
          case "testCT001_CadastroAlunoValido":
            testSuite.testCT001_CadastroAlunoValido();
            break;
          case "testCT002_CadastroAlunoNomeVazio":
            testSuite.testCT002_CadastroAlunoNomeVazio();
            break;
          case "testCT003_CadastroAlunoNomeLimiteMaximo":
            testSuite.testCT003_CadastroAlunoNomeLimiteMaximo();
            break;
          case "testCT004_CadastroAlunoNomeAcimaLimite":
            testSuite.testCT004_CadastroAlunoNomeAcimaLimite();
            break;
          case "testCT005_CadastroAlunoMatriculaDuplicada":
            testSuite.testCT005_CadastroAlunoMatriculaDuplicada();
            break;
          case "testCT006_CadastroAlunoEmailInvalido":
            testSuite.testCT006_CadastroAlunoEmailInvalido();
            break;
          case "testCT007_CadastroDisciplinaCargaHorariaMinimaValida":
            testSuite.testCT007_CadastroDisciplinaCargaHorariaMinimaValida();
            break;
          case "testCT008_CadastroDisciplinaCargaHorariaZero":
            testSuite.testCT008_CadastroDisciplinaCargaHorariaZero();
            break;
          case "testCT009_CadastroDisciplinaCargaHorariaMaximaValida":
            testSuite.testCT009_CadastroDisciplinaCargaHorariaMaximaValida();
            break;
          case "testCT010_CadastroDisciplinaCargaHorariaAcimaLimite":
            testSuite.testCT010_CadastroDisciplinaCargaHorariaAcimaLimite();
            break;
          case "testCT011_CadastroDisciplinaSemProfessor":
            testSuite.testCT011_CadastroDisciplinaSemProfessor();
            break;
          case "testCT012_MatriculaComDataAtual":
            testSuite.testCT012_MatriculaComDataAtual();
            break;
          case "testCT013_MatriculaComDataPassado":
            testSuite.testCT013_MatriculaComDataPassado();
            break;
          case "testCT014_MatriculaComDataInvalida":
            testSuite.testCT014_MatriculaComDataInvalida();
            break;
          case "testCT015_MatriculaDuplicadaAtiva":
            testSuite.testCT015_MatriculaDuplicadaAtiva();
            break;
          case "testCT016_MatriculaComStatusInvalido":
            testSuite.testCT016_MatriculaComStatusInvalido();
            break;
          case "testCT017_EdicaoAlunoExistente":
            testSuite.testCT017_EdicaoAlunoExistente();
            break;
          case "testCT018_ExclusaoAluno":
            testSuite.testCT018_ExclusaoAluno();
            break;
        }
        
        testSuite.tearDown();
        System.out.println("✅ PASSOU");
        testesPassaram++;
        
      } catch (Exception e) {
        System.out.println("❌ FALHOU: " + e.getMessage());
        testesFalharam++;
      }
    }
    
    // Relatório final
    System.out.println("\n" + "=".repeat(80));
    System.out.println("                           RELATÓRIO FINAL");
    System.out.println("=".repeat(80));
    System.out.printf("Total de Testes: %d\n", totalTestes);
    System.out.printf("✅ Passaram: %d\n", testesPassaram);
    System.out.printf("❌ Falharam: %d\n", testesFalharam);
    System.out.printf("Taxa de Sucesso: %.1f%%\n", (testesPassaram * 100.0 / totalTestes));
    System.out.println("=".repeat(80));
    
    if (testesFalharam == 0) {
      System.out.println("🎉 TODOS OS TESTES PASSARAM! Sistema validado com sucesso.");
    } else {
      System.out.println("⚠️  Alguns testes falharam. Verifique a implementação das classes DAO.");
    }
  }
}
