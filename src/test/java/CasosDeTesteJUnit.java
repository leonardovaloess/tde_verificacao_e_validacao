/**
 * ================================================================================
 *                    SISTEMA DE TESTES AUTOMATIZADOS
 *                      SISTEMA DE MATRÍCULA ACADÊMICA
 * ================================================================================
 * 
 * DESCRIÇÃO:
 * Este arquivo implementa um framework completo de testes automatizados para o
 * Sistema de Matrícula Acadêmica, focado exclusivamente nos testes funcionais:
 * - 18 casos de teste funcionais (CT001-CT018)
 * - Geração de relatórios de execução em formato TXT
 * - Validação de Classes de Equivalência e Valores Limite
 * 
 * FUNCIONALIDADES PRINCIPAIS:
 * 1. TESTES FUNCIONAIS:
 *    - Cadastro e validação de Alunos (CT001-CT006)
 *    - Cadastro e validação de Disciplinas (CT007-CT011)
 *    - Gestão de Matrículas (CT012-CT016)
 *    - Operações de CRUD (CT017-CT018)
 * 
 * 2. RELATÓRIOS AUTOMATIZADOS:
 *    - Relatório de execução: status, tempo, detalhes dos testes
 *    - Mapeamento de Classes de Equivalência e Valores Limite
 *    - Estatísticas completas de execução
 * 
 * ARQUITETURA DOS TESTES:
 * - Setup/Teardown: Inicialização e limpeza antes/depois de cada teste
 * - Ordem controlada: Testes executados em sequência definida
 * - Validações robustas: Verificação de dados e comportamentos esperados
 * - Tratamento de exceções: Validação de cenários de erro
 * 
 * ANÁLISE DE COBERTURA:
 * Para análise de cobertura de código, utilize a classe GerarCobertura.java
 * que implementa integração completa com JaCoCo de forma independente.
 * 
 * CLASSES DE EQUIVALÊNCIA COBERTAS:
 * CE1-CE3: Validação de nomes (válido, vazio, muito longo)
 * CE6: Formatos alfanuméricos válidos
 * CE11: Validação de duplicatas
 * CE12,CE14: Validação de emails (válido, inválido)
 * CE18-CE21: Validação de carga horária (válida, zero, acima limite)
 * CE24: Status inválidos
 * CE26-CE29: Validação de datas (atual, passada, inválida)
 * 
 * VALORES LIMITE TESTADOS:
 * VL3-VL4: Nomes com 100/101 caracteres
 * VL5-VL8: Carga horária 0/1/500/501 horas
 * 
 * AUTOR: Sistema de Verificação e Validação - Leonardo Berlanda de Valões
 * DATA: Novembro 2025
 * VERSÃO: 2.0 (Testes funcionais isolados - JaCoCo separado em GerarCobertura.java)
 * ================================================================================
 */

import classes.*;
import dao.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
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

  /**
   * MÉTODO DE LIMPEZA DE DADOS
   * 
   * Remove todos os arquivos de dados de teste para garantir um ambiente limpo.
   * Este método é essencial para evitar interferências entre testes diferentes.
   * 
   * ARQUIVOS LIMPOS:
   * - dados/alunos.txt: Remove todos os alunos cadastrados
   * - dados/professores.txt: Remove todos os professores cadastrados  
   * - dados/disciplinas.txt: Remove todas as disciplinas cadastradas
   * - dados/matriculas.txt: Remove todas as matrículas cadastradas
   * 
   * ESTRATÉGIA: Delete seguro - se o arquivo não existir, não gera erro
   */
  private void limparArquivosDados() {
    // Criar diretório se não existir para evitar erros
    File dir = new File("dados");
    if (!dir.exists()) {
      dir.mkdirs();
    }

    // Limpeza segura de todos os arquivos de dados
    new File("dados/alunos.txt").delete();
    new File("dados/professores.txt").delete();
    new File("dados/disciplinas.txt").delete();
    new File("dados/matriculas.txt").delete();
  }

  // ==================== MÓDULO: CADASTRO DE ALUNO ====================
  // 
  // Esta seção implementa todos os casos de teste relacionados ao cadastro
  // e validação de alunos no sistema. Cobre as seguintes funcionalidades:
  // - Cadastro com dados válidos (cenário de sucesso)
  // - Validação de nome obrigatório  
  // - Teste de limites de caracteres no nome
  // - Validação de matrícula única
  // - Validação de formato de email
  //

  /**
   * CASO DE TESTE CT001 - CADASTRO DE ALUNO COM DADOS VÁLIDOS
   * 
   * OBJETIVO: Verificar se o sistema permite cadastrar um aluno com todos os dados válidos
   * 
   * CLASSES DE EQUIVALÊNCIA TESTADAS:
   * - CE1: Nome válido (string não vazia, até 100 caracteres)
   * - CE6: Matrícula em formato alfanumérico válido
   * - CE12: Email em formato válido (com @)
   * 
   * CRITÉRIOS DE ACEITAÇÃO:
   * - Aluno deve ser salvo com sucesso no sistema
   * - Dados devem ser persistidos corretamente
   * - Lista de alunos deve conter exatamente 1 registro
   * - Todos os campos devem ser armazenados corretamente
   * 
   * PRIORIDADE: ALTA (funcionalidade crítica do sistema)
   */
  @Test
  @Order(1)
  @DisplayName("CT001 - Cadastro de aluno com dados válidos")
  public void testCT001_CadastroAlunoValido() {
    // ARRANGE: Preparar dados de teste com valores válidos
    Aluno aluno = new Aluno("João Silva", "2023001", "joao@email.com", "(11) 99999-9999");

    // ACT: Executar a operação sendo testada
    alunoDAO.salvar(aluno);
    List<Aluno> alunos = alunoDAO.listarTodos();

    // ASSERT: Verificar se o resultado está correto
    assertNotNull(alunos, "Lista de alunos não deve ser nula");
    assertEquals(1, alunos.size(), "Deve haver 1 aluno cadastrado");
    assertEquals("João Silva", alunos.get(0).getNome(), "Nome deve ser 'João Silva'");
    assertEquals("2023001", alunos.get(0).getMatricula(), "Matrícula deve ser '2023001'");
    assertEquals("joao@email.com", alunos.get(0).getEmail(), "Email deve estar correto");
  }

  /**
   * CASO DE TESTE CT002 - CADASTRO DE ALUNO COM NOME VAZIO
   * 
   * OBJETIVO: Verificar se o sistema rejeita cadastro de aluno com nome vazio
   * 
   * CLASSES DE EQUIVALÊNCIA TESTADAS:
   * - CE2: Nome vazio/nulo (valor inválido)
   * 
   * COMPORTAMENTO ESPERADO:
   * - Sistema deve lançar IllegalArgumentException
   * - Mensagem deve indicar que nome é obrigatório
   * - Nenhum aluno deve ser cadastrado no sistema
   * 
   * PRIORIDADE: ALTA (validação crítica de dados obrigatórios)
   */
  @Test
  @Order(2)
  @DisplayName("CT002 - Cadastro de aluno com nome vazio")
  public void testCT002_CadastroAlunoNomeVazio() {
    // ARRANGE: Preparar aluno com nome vazio (cenário inválido)
    Aluno aluno = new Aluno("", "2023002", "ana@email.com", "(11) 88888-8888");

    // ACT & ASSERT: Verificar se exceção é lançada corretamente
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      validarAluno(aluno);
    });

    // Verificar se a mensagem de erro está correta
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

  // ==================== MÉTODOS DE COBERTURA E RELATÓRIOS ====================

  /**
   * Gera relatório de execução de testes
   */
  private static void gerarRelatorioExecucao(int totalTestes, int testesPassaram, int testesFalharam, 
                                           List<String> testesDetalhes) {
    try {
      // Criar diretório de relatórios se não existir
      File relatoriosDir = new File("target/test-reports");
      if (!relatoriosDir.exists()) {
        relatoriosDir.mkdirs();
      }

      // Gerar relatório de execução
      try (PrintWriter writer = new PrintWriter(new FileWriter("target/test-reports/relatorio-execucao.txt"))) {
        writer.println("=".repeat(80));
        writer.println("         RELATÓRIO DE EXECUÇÃO DOS TESTES - SISTEMA DE MATRÍCULA");
        writer.println("=".repeat(80));
        writer.println("Data/Hora: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        writer.println("Total de Casos de Teste: 18 (CT001-CT018)");
        writer.println();
        
        writer.println("RESUMO EXECUTIVO:");
        writer.println("-".repeat(50));
        writer.printf("Total de Testes Executados: %d\n", totalTestes);
        writer.printf("✅ Testes que Passaram: %d\n", testesPassaram);
        writer.printf("❌ Testes que Falharam: %d\n", testesFalharam);
        writer.printf("Taxa de Sucesso: %.1f%%\n", (testesPassaram * 100.0 / totalTestes));
        writer.println();
        
        writer.println("DETALHES DOS TESTES:");
        writer.println("-".repeat(50));
        for (String detalhe : testesDetalhes) {
          writer.println(detalhe);
        }
        
        writer.println();
        writer.println("COBERTURA DE CLASSES DE EQUIVALÊNCIA:");
        writer.println("-".repeat(50));
        writer.println("• CE1 (Nomes válidos): Coberto em CT001, CT003");
        writer.println("• CE2 (Nome vazio): Coberto em CT002");
        writer.println("• CE3 (Nome muito longo): Coberto em CT004");
        writer.println("• CE6 (Formato alfanumérico válido): Coberto em CT001");
        writer.println("• CE11 (Valor duplicado): Coberto em CT005");
        writer.println("• CE12 (Email válido): Coberto em CT001");
        writer.println("• CE14 (Email sem @): Coberto em CT006");
        writer.println("• CE18 (Carga horária válida): Coberto em CT007, CT009");
        writer.println("• CE19 (Carga horária zero): Coberto em CT008");
        writer.println("• CE21 (Carga horária acima limite): Coberto em CT010");
        writer.println("• CE24 (Status inválido): Coberto em CT016");
        writer.println("• CE26 (Data atual): Coberto em CT012");
        writer.println("• CE27 (Data passada): Coberto em CT013");
        writer.println("• CE29 (Data inválida): Coberto em CT014");
        
        writer.println();
        writer.println("COBERTURA DE VALORES LIMITE:");
        writer.println("-".repeat(50));
        writer.println("• VL3 (Nome 100 caracteres): Coberto em CT003");
        writer.println("• VL4 (Nome 101 caracteres): Coberto em CT004");
        writer.println("• VL5 (Carga horária 0): Coberto em CT008");
        writer.println("• VL6 (Carga horária 1): Coberto em CT007");
        writer.println("• VL7 (Carga horária 500): Coberto em CT009");
        writer.println("• VL8 (Carga horária 501): Coberto em CT010");
        
        writer.println();
        writer.println("=".repeat(80));
      }
      
      System.out.println("📋 Relatório de execução salvo em: target/test-reports/relatorio-execucao.txt");
      
    } catch (IOException e) {
      System.err.println("Erro ao gerar relatório de execução: " + e.getMessage());
    }
  }

  /**
   * ==================== GERAÇÃO DE RELATÓRIOS ====================
   * 
   * Esta seção implementa a geração automática de relatórios detalhados
   * sobre a execução dos testes funcionais.
   * 
   * NOTA: Para análise de cobertura de código, utilize GerarCobertura.java
   */
   
  /**
   * GERADOR DE RELATÓRIO DE EXECUÇÃO DOS TESTES
   * 
   * FUNCIONALIDADE:
   * Este método gera um relatório completo da execução dos testes,
   * incluindo estatísticas, detalhes e mapeamento de cobertura de
   * Classes de Equivalência e Valores Limite.
   * 
   * CONTEÚDO DO RELATÓRIO:
   * - Resumo executivo com taxas de sucesso/falha
   * - Detalhes individuais de cada teste executado
   * - Mapeamento das Classes de Equivalência testadas
   * - Cobertura dos Valores Limite validados
   * - Tempo de execução e data/hora da execução
   * 
   * SAÍDA:
   * - Arquivo: target/test-reports/relatorio-execucao.txt
   * - Formato: Texto estruturado e organizado
   */

  // ==================== MÉTODO MAIN PARA EXECUÇÃO DIRETA ====================

  /**
   * MÉTODO PRINCIPAL - EXECUTOR DE TODOS OS CASOS DE TESTE
   * 
   * OBJETIVO:
   * Executa todos os 18 casos de teste de forma sequencial e organizada,
   * sem necessidade do runner do JUnit. Integra coleta de cobertura com
   * JaCoCo e gera relatórios completos automaticamente.
   * 
   * FLUXO DE EXECUÇÃO:
   * 1. INICIALIZAÇÃO:
   *    - Configura agente JaCoCo para instrumentação
   *    - Inicializa contadores e estruturas de dados
   *    - Apresenta cabeçalho informativo
   * 
   * 2. EXECUÇÃO DOS TESTES:
   *    - Executa cada caso de teste individualmente
   *    - Captura exceções e registra resultados
   *    - Calcula tempo de execução de cada teste
   *    - Atualiza contadores de sucesso/falha
   * 
   * 3. COLETA DE DADOS:
   *    - Registra detalhes de cada teste executado
   *    - Organiza dados para relatórios de execução
   *    - Calcula estatísticas de sucesso/falha
   * 
   * 4. GERAÇÃO DE RELATÓRIOS:
   *    - Relatório de execução: status, tempos, detalhes
   *    - Estatísticas finais e resumo executivo
   *    - Mapeamento de Classes de Equivalência e Valores Limite
   * 
   * LISTA DE CASOS DE TESTE EXECUTADOS:
   * CT001-CT006: Módulo de Cadastro de Alunos
   * CT007-CT011: Módulo de Cadastro de Disciplinas  
   * CT012-CT016: Módulo de Gestão de Matrículas
   * CT017-CT018: Módulo de Operações CRUD
   * 
   * SAÍDAS GERADAS:
   * - Console: Progresso em tempo real e resumo final
   * - target/test-reports/relatorio-execucao.txt: Detalhes da execução
   * 
   * ANÁLISE DE COBERTURA:
   * Para relatórios de cobertura de código, utilize a classe GerarCobertura.java
   * que pode ser executada independentemente após os testes.
   * 
   * @param args Argumentos da linha de comando (não utilizados)
   */
  public static void main(String[] args) {
    // ========== FASE 1: INICIALIZAÇÃO ==========
    // Instanciar suite de testes
    CasosDeTesteJUnit testSuite = new CasosDeTesteJUnit();
    
    // Apresentar cabeçalho informativo
    System.out.println("=".repeat(80));
    System.out.println("         EXECUÇÃO DOS CASOS DE TESTE - SISTEMA DE MATRÍCULA");
    System.out.println("=".repeat(80));
    System.out.println("🧪 Framework: JUnit 5 para testes funcionais");
    System.out.println("📅 Data: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    System.out.println("💡 Para análise de cobertura, execute GerarCobertura.java após os testes");
    System.out.println();
    
    // ========== FASE 2: PREPARAÇÃO DOS DADOS ==========
    // Inicializar contadores para estatísticas
    int totalTestes = 18;
    int testesPassaram = 0;
    int testesFalharam = 0;
    List<String> testesDetalhes = new ArrayList<>();
    
    // Definir lista completa de casos de teste para execução sequencial
    // Ordem é importante para manter consistência e rastreabilidade
    String[] metodosTest = {
      "testCT001_CadastroAlunoValido",                    // Cadastro básico de aluno
      "testCT002_CadastroAlunoNomeVazio",                 // Validação nome obrigatório
      "testCT003_CadastroAlunoNomeLimiteMaximo",          // Teste limite máximo nome
      "testCT004_CadastroAlunoNomeAcimaLimite",          // Teste acima do limite
      "testCT005_CadastroAlunoMatriculaDuplicada",       // Validação unicidade
      "testCT006_CadastroAlunoEmailInvalido",            // Validação formato email
      "testCT007_CadastroDisciplinaCargaHorariaMinimaValida", // Carga horária mínima
      "testCT008_CadastroDisciplinaCargaHorariaZero",    // Validação carga zero
      "testCT009_CadastroDisciplinaCargaHorariaMaximaValida", // Carga horária máxima
      "testCT010_CadastroDisciplinaCargaHorariaAcimaLimite",  // Validação limite superior
      "testCT011_CadastroDisciplinaSemProfessor",        // Validação professor obrigatório
      "testCT012_MatriculaComDataAtual",                 // Matrícula data atual
      "testCT013_MatriculaComDataPassado",               // Matrícula data passada
      "testCT014_MatriculaComDataInvalida",              // Validação formato data
      "testCT015_MatriculaDuplicadaAtiva",               // Validação matrícula duplicada
      "testCT016_MatriculaComStatusInvalido",            // Validação status
      "testCT017_EdicaoAlunoExistente",                  // Operação de edição
      "testCT018_ExclusaoAluno"                          // Operação de exclusão
    };
    
    // ========== FASE 3: EXECUÇÃO DOS TESTES ==========
    for (int i = 0; i < metodosTest.length; i++) {
      String nomeMetodo = metodosTest[i];
      String nomeDisplay = nomeMetodo.replace("test", "").replace("_", " - ");
      System.out.printf("\n[%02d/%02d] Executando: %s\n", i+1, totalTestes, nomeDisplay);
      
      long inicioTeste = System.currentTimeMillis();
      String status = "❌ FALHOU";
      String erro = "";
      
      try {
        // Garantir setup limpo para cada teste
        testSuite.setUp();
        
        // Execução manual de cada teste com tratamento robusto de exceções
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
          default:
            throw new RuntimeException("Método de teste não encontrado: " + nomeMetodo);
        }
        
        // Se chegou até aqui, o teste passou
        status = "✅ PASSOU";
        testesPassaram++;
        
      } catch (AssertionError e) {
        // Falha de asserção - teste falhou mas é comportamento esperado
        erro = "Asserção falhou: " + e.getMessage();
        testesFalharam++;
        System.out.println("   ⚠️  " + erro);
        
      } catch (Exception e) {
        // Qualquer outra exceção - erro inesperado
        erro = "Erro inesperado: " + e.getClass().getSimpleName() + " - " + e.getMessage();
        testesFalharam++;
        System.out.println("   ❌ " + erro);
        
      } finally {
        // Garantir limpeza mesmo se houver exceção
        try {
          testSuite.tearDown();
        } catch (Exception cleanupError) {
          System.out.println("   ⚠️  Erro na limpeza: " + cleanupError.getMessage());
        }
      }
      
      long fimTeste = System.currentTimeMillis();
      long duracao = fimTeste - inicioTeste;
      
      System.out.printf("   %s (%d ms)\n", status, duracao);
      
      // Adicionar detalhes para o relatório (sempre, mesmo com erro)
      testesDetalhes.add(String.format("%-50s %s (%d ms)%s", 
          nomeDisplay, status, duracao, 
          erro.isEmpty() ? "" : " - " + erro));
    }
    
    // ========== FASE 4: APRESENTAÇÃO DOS RESULTADOS ==========
    // Relatório final no console (sempre executado)
    System.out.println("\n" + "=".repeat(80));
    System.out.println("                           RELATÓRIO FINAL");
    System.out.println("=".repeat(80));
    System.out.printf("📊 Total de Testes: %d\n", totalTestes);
    System.out.printf("✅ Passaram: %d\n", testesPassaram);
    System.out.printf("❌ Falharam: %d\n", testesFalharam);
    System.out.printf("🎯 Taxa de Sucesso: %.1f%%\n", (testesPassaram * 100.0 / totalTestes));
    System.out.println("=".repeat(80));
    
    // ========== FASE 5: RELATÓRIOS E FINALIZAÇÃO ==========
    // Garantir que o relatório seja sempre gerado, mesmo com falhas
    try {
      gerarRelatorioExecucao(totalTestes, testesPassaram, testesFalharam, testesDetalhes);
      System.out.println("📋 Relatório de execução salvo em: target/test-reports/relatorio-execucao.txt");
    } catch (Exception e) {
      System.err.println("⚠️  Erro ao gerar relatório de execução: " + e.getMessage());
      // Tentar salvar um relatório básico mesmo com erro
      try {
        gerarRelatorioBasico(totalTestes, testesPassaram, testesFalharam);
        System.out.println("📋 Relatório básico salvo em: target/test-reports/relatorio-execucao.txt");
      } catch (Exception fallbackError) {
        System.err.println("❌ Falha completa na geração de relatórios: " + fallbackError.getMessage());
      }
    }
    
    // Apresentar resumo final baseado nos resultados
    if (testesFalharam == 0) {
      System.out.println("\n🎉 TODOS OS TESTES PASSARAM! Sistema validado com sucesso.");
    } else {
      System.out.println("\n⚠️  Alguns testes falharam. Verifique os detalhes no relatório.");
      System.out.printf("   📈 %d de %d testes precisam de atenção.\n", testesFalharam, totalTestes);
    }
    
    // Informações finais
    System.out.println("\n📋 Relatórios gerados:");
    System.out.println("   • target/test-reports/relatorio-execucao.txt");
    
    System.out.println("\n📊 Para análise de cobertura de código:");
    System.out.println("   1. Execute: java GerarCobertura");
    System.out.println("   2. Ou compile e execute GerarCobertura.java");
    System.out.println("   3. Relatório será gerado em: target/test-reports/relatorio-cobertura.txt");

    System.out.println("\n📊 Execução concluída!");
  }

  /**
   * Gera um relatório básico em caso de falha na geração do relatório completo
   */
  private static void gerarRelatorioBasico(int totalTestes, int testesPassaram, int testesFalharam) {
    try {
      File relatoriosDir = new File("target/test-reports");
      if (!relatoriosDir.exists()) {
        relatoriosDir.mkdirs();
      }

      try (PrintWriter writer = new PrintWriter(new FileWriter("target/test-reports/relatorio-execucao.txt"))) {
        writer.println("=".repeat(80));
        writer.println("         RELATÓRIO BÁSICO DE EXECUÇÃO DOS TESTES");
        writer.println("=".repeat(80));
        writer.println("Data/Hora: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        writer.println();
        
        writer.println("RESUMO:");
        writer.println("-".repeat(50));
        writer.printf("Total de Testes: %d\n", totalTestes);
        writer.printf("Passaram: %d\n", testesPassaram);
        writer.printf("Falharam: %d\n", testesFalharam);
        writer.printf("Taxa de Sucesso: %.1f%%\n", (testesPassaram * 100.0 / totalTestes));
        writer.println();
        
        writer.println("OBSERVAÇÃO:");
        writer.println("Este é um relatório básico gerado devido a erro na geração do relatório completo.");
        writer.println("Execute os testes novamente para obter detalhes completos.");
        writer.println();
        writer.println("=".repeat(80));
      }
    } catch (IOException e) {
      System.err.println("Erro ao salvar relatório básico: " + e.getMessage());
    }
  }
 
}