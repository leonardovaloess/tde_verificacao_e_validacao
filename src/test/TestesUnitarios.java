package test;

import classes.*;
import dao.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Testes unitários simplificados para o Sistema de Matrícula
 * Não usa JUnit para evitar dependências externas
 */
public class TestesUnitarios {
    
    private static int testesExecutados = 0;
    private static int testesPassaram = 0;
    private static int testesFalharam = 0;
    
    public static void main(String[] args) {
        System.out.println("=== EXECUÇÃO DE TESTES UNITÁRIOS ===\n");
        
        testarClasseAluno();
        testarClasseProfessor();
        testarClasseDisciplina();
        testarClasseMatricula();
        testarDAOs();
        
        System.out.println("\n=== RESUMO DOS TESTES ===");
        System.out.println("Total de testes: " + testesExecutados);
        System.out.println("Testes passaram: " + testesPassaram);
        System.out.println("Testes falharam: " + testesFalharam);
        System.out.println("Taxa de sucesso: " + (testesPassaram * 100.0 / testesExecutados) + "%");
    }
    
    private static void testarClasseAluno() {
        System.out.println("TESTANDO CLASSE ALUNO");
        System.out.println("=====================");
        
        // Teste 1: Construtor completo
        executarTeste("Construtor completo", () -> {
            Aluno aluno = new Aluno(1, "João Silva", "2023001", "joao@email.com", "(11) 99999-9999");
            return aluno.getId() == 1 && 
                   "João Silva".equals(aluno.getNome()) &&
                   "2023001".equals(aluno.getMatricula()) &&
                   "joao@email.com".equals(aluno.getEmail()) &&
                   "(11) 99999-9999".equals(aluno.getTelefone());
        });
        
        // Teste 2: Construtor sem ID
        executarTeste("Construtor sem ID", () -> {
            Aluno aluno = new Aluno("Maria Santos", "2023002", "maria@email.com", "(11) 88888-8888");
            return aluno.getId() == 0 && "Maria Santos".equals(aluno.getNome());
        });
        
        // Teste 3: Setters e Getters
        executarTeste("Setters e Getters", () -> {
            Aluno aluno = new Aluno();
            aluno.setId(5);
            aluno.setNome("Pedro Oliveira");
            aluno.setMatricula("TEST001");
            aluno.setEmail("pedro@teste.com");
            aluno.setTelefone("(11) 12345-6789");
            
            return aluno.getId() == 5 &&
                   "Pedro Oliveira".equals(aluno.getNome()) &&
                   "TEST001".equals(aluno.getMatricula()) &&
                   "pedro@teste.com".equals(aluno.getEmail()) &&
                   "(11) 12345-6789".equals(aluno.getTelefone());
        });
        
        // Teste 4: Serialização toString
        executarTeste("Serialização toString", () -> {
            Aluno aluno = new Aluno(10, "Ana Costa", "2023003", "ana@email.com", "(11) 77777-7777");
            String esperado = "10;Ana Costa;2023003;ana@email.com;(11) 77777-7777";
            return esperado.equals(aluno.toString());
        });
        
        // Teste 5: Deserialização fromString
        executarTeste("Deserialização fromString", () -> {
            String csvString = "15;Carlos Ferreira;2023004;carlos@email.com;(11) 66666-6666";
            Aluno aluno = Aluno.fromString(csvString);
            return aluno.getId() == 15 &&
                   "Carlos Ferreira".equals(aluno.getNome()) &&
                   "2023004".equals(aluno.getMatricula());
        });
        
        // Teste 6: toDisplayString
        executarTeste("Display String", () -> {
            Aluno aluno = new Aluno(20, "Lucia Santos", "2023005", "lucia@email.com", "(11) 55555-5555");
            String esperado = "Lucia Santos - 2023005";
            return esperado.equals(aluno.toDisplayString());
        });
        
        // Teste 7: Ciclo serialização/deserialização
        executarTeste("Ciclo serialização/deserialização", () -> {
            Aluno original = new Aluno(25, "Roberto Silva", "2023006", "roberto@email.com", "(11) 44444-4444");
            String csv = original.toString();
            Aluno reconstruido = Aluno.fromString(csv);
            
            return original.getId() == reconstruido.getId() &&
                   original.getNome().equals(reconstruido.getNome()) &&
                   original.getMatricula().equals(reconstruido.getMatricula()) &&
                   original.getEmail().equals(reconstruido.getEmail()) &&
                   original.getTelefone().equals(reconstruido.getTelefone());
        });
        
        System.out.println();
    }
    
    private static void testarClasseProfessor() {
        System.out.println("TESTANDO CLASSE PROFESSOR");
        System.out.println("=========================");
        
        // Teste 1: Construtor completo
        executarTeste("Construtor completo", () -> {
            Professor prof = new Professor(1, "Dr. João Silva", "PROF001", "joao@universidade.edu", "Computação");
            return prof.getId() == 1 && 
                   "Dr. João Silva".equals(prof.getNome()) &&
                   "PROF001".equals(prof.getIdentificador());
        });
        
        // Teste 2: Construtor sem ID
        executarTeste("Construtor sem ID", () -> {
            Professor prof = new Professor("Dra. Maria Santos", "PROF002", "maria@universidade.edu", "Matemática");
            return prof.getId() == 0 && "Dra. Maria Santos".equals(prof.getNome());
        });
        
        // Teste 3: Serialização
        executarTeste("Serialização", () -> {
            Professor prof = new Professor(5, "Prof. Ana Costa", "PROF005", "ana@universidade.edu", "Química");
            String esperado = "5;Prof. Ana Costa;PROF005;ana@universidade.edu;Química";
            return esperado.equals(prof.toString());
        });
        
        // Teste 4: Display String
        executarTeste("Display String", () -> {
            Professor prof = new Professor(3, "Profa. Lucia Santos", "PROF003", "lucia@universidade.edu", "História");
            String esperado = "Profa. Lucia Santos - PROF003";
            return esperado.equals(prof.toDisplayString());
        });
        
        System.out.println();
    }
    
    private static void testarClasseDisciplina() {
        System.out.println("TESTANDO CLASSE DISCIPLINA");
        System.out.println("==========================");
        
        // Teste 1: Construtor completo
        executarTeste("Construtor completo", () -> {
            Disciplina disc = new Disciplina(1, "POO", "POO001", 60, 1, "2024.1");
            return disc.getId() == 1 && 
                   "POO".equals(disc.getNomeDisciplina()) &&
                   "POO001".equals(disc.getCodigo()) &&
                   disc.getCargaHoraria() == 60;
        });
        
        // Teste 2: Serialização
        executarTeste("Serialização", () -> {
            Disciplina disc = new Disciplina(2, "Cálculo I", "CALC001", 80, 1, "2024.1");
            String esperado = "2;Cálculo I;CALC001;80;1;2024.1";
            return esperado.equals(disc.toString());
        });
        
        // Teste 3: Display String
        executarTeste("Display String", () -> {
            Disciplina disc = new Disciplina(3, "Física I", "FIS001", 60, 1, "2024.1");
            String esperado = "Física I - FIS001";
            return esperado.equals(disc.toDisplayString());
        });
        
        System.out.println();
    }
    
    private static void testarClasseMatricula() {
        System.out.println("TESTANDO CLASSE MATRÍCULA");
        System.out.println("=========================");
        
        // Teste 1: Construtor completo
        executarTeste("Construtor completo", () -> {
            LocalDate data = LocalDate.of(2024, 9, 3);
            Matricula mat = new Matricula(1, 1, 1, data, "ATIVA");
            return mat.getId() == 1 && 
                   mat.getAlunoId() == 1 &&
                   mat.getDisciplinaId() == 1 &&
                   "ATIVA".equals(mat.getStatus());
        });
        
        // Teste 2: Serialização
        executarTeste("Serialização", () -> {
            LocalDate data = LocalDate.of(2024, 9, 3);
            Matricula mat = new Matricula(2, 1, 1, data, "ATIVA");
            String resultado = mat.toString();
            return resultado.contains("2;1;1;03/09/2024;ATIVA");
        });
        
        // Teste 3: Deserialização
        executarTeste("Deserialização", () -> {
            String csv = "3;2;1;15/08/2024;CONCLUIDA";
            Matricula mat = Matricula.fromString(csv);
            return mat.getId() == 3 && 
                   mat.getAlunoId() == 2 &&
                   "CONCLUIDA".equals(mat.getStatus());
        });
        
        System.out.println();
    }
    
    private static void testarDAOs() {
        System.out.println("TESTANDO CAMADA DAO");
        System.out.println("===================");
        
        // Teste 1: AlunoDAO
        executarTeste("AlunoDAO - Salvar e Listar", () -> {
            try {
                AlunoDAO dao = new AlunoDAO();
                int totalAntes = dao.listarTodos().size();
                
                Aluno aluno = new Aluno("Teste DAO", "TESTDAO001", "teste@dao.com", "(11) 99999-0000");
                dao.salvar(aluno);
                
                int totalDepois = dao.listarTodos().size();
                return totalDepois > totalAntes;
            } catch (Exception e) {
                return false;
            }
        });
        
        // Teste 2: ProfessorDAO
        executarTeste("ProfessorDAO - Salvar e Verificar Existência", () -> {
            try {
                ProfessorDAO dao = new ProfessorDAO();
                Professor prof = new Professor("Prof. Teste", "TESTDAO001", "teste@prof.com", "Teste");
                dao.salvar(prof);
                
                return dao.existeIdentificador("TESTDAO001");
            } catch (Exception e) {
                return false;
            }
        });
        
        // Teste 3: DisciplinaDAO
        executarTeste("DisciplinaDAO - Salvar e Verificar Código", () -> {
            try {
                DisciplinaDAO dao = new DisciplinaDAO();
                Disciplina disc = new Disciplina("Disciplina Teste", "TESTDAO001", 40, 1, "2024.1");
                dao.salvar(disc);
                
                return dao.existeCodigo("TESTDAO001");
            } catch (Exception e) {
                return false;
            }
        });
        
        // Teste 4: MatriculaDAO
        executarTeste("MatriculaDAO - Criar Matrícula", () -> {
            try {
                MatriculaDAO dao = new MatriculaDAO();
                Matricula mat = new Matricula(1, 1, LocalDate.now(), "ATIVA");
                dao.salvar(mat);
                
                List<Matricula> matriculas = dao.listarTodos();
                return matriculas.size() > 0;
            } catch (Exception e) {
                return false;
            }
        });
        
        System.out.println();
    }
    
    private static void executarTeste(String nomeTeste, TestCase teste) {
        testesExecutados++;
        try {
            boolean resultado = teste.executar();
            if (resultado) {
                System.out.println("  ✓ " + nomeTeste);
                testesPassaram++;
            } else {
                System.out.println("  ✗ " + nomeTeste + " (FALHOU)");
                testesFalharam++;
            }
        } catch (Exception e) {
            System.out.println("  ✗ " + nomeTeste + " (ERRO: " + e.getMessage() + ")");
            testesFalharam++;
        }
    }
    
    @FunctionalInterface
    interface TestCase {
        boolean executar() throws Exception;
    }
}
