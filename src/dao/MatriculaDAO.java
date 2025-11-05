package dao;

import classes.Matricula;
import java.io.*;
import java.util.*;

public class MatriculaDAO {
  private static final String ARQUIVO = "dados/matriculas.txt";
  private static int proximoId = 1;

  public MatriculaDAO() {
    criarDiretorioSeNaoExistir();
    carregarProximoId();
  }

  private void criarDiretorioSeNaoExistir() {
    File dir = new File("dados");
    if (!dir.exists()) {
      dir.mkdirs();
    }
  }

  private void carregarProximoId() {
    List<Matricula> matriculas = listarTodos();
    if (!matriculas.isEmpty()) {
      proximoId = matriculas.stream().mapToInt(Matricula::getId).max().orElse(0) + 1;
    }
  }

  public void salvar(Matricula matricula) {
    if (matricula.getId() == 0) {
      matricula.setId(proximoId++);
    }

    List<Matricula> matriculas = listarTodos();
    boolean encontrado = false;

    for (int i = 0; i < matriculas.size(); i++) {
      if (matriculas.get(i).getId() == matricula.getId()) {
        matriculas.set(i, matricula);
        encontrado = true;
        break;
      }
    }

    if (!encontrado) {
      matriculas.add(matricula);
    }

    salvarTodos(matriculas);
  }

  public void excluir(int id) {
    List<Matricula> matriculas = listarTodos();
    matriculas.removeIf(matricula -> matricula.getId() == id);
    salvarTodos(matriculas);
  }

  public Matricula buscarPorId(int id) {
    return listarTodos().stream()
        .filter(matricula -> matricula.getId() == id)
        .findFirst()
        .orElse(null);
  }

  public List<Matricula> listarTodos() {
    List<Matricula> matriculas = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
      String linha;
      while ((linha = reader.readLine()) != null) {
        if (!linha.trim().isEmpty()) {
          matriculas.add(Matricula.fromString(linha));
        }
      }
    } catch (FileNotFoundException e) {
      // Arquivo ainda não existe, retorna lista vazia
    } catch (IOException e) {
      e.printStackTrace();
    }

    return matriculas;
  }

  private void salvarTodos(List<Matricula> matriculas) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
      for (Matricula matricula : matriculas) {
        writer.write(matricula.toString());
        writer.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<Matricula> buscarPorAluno(int alunoId) {
    return listarTodos().stream()
        .filter(matricula -> matricula.getAlunoId() == alunoId)
        .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
  }

  public List<Matricula> buscarPorDisciplina(int disciplinaId) {
    return listarTodos().stream()
        .filter(matricula -> matricula.getDisciplinaId() == disciplinaId)
        .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
  }

  public boolean existeMatricula(int alunoId, int disciplinaId) {
    return listarTodos().stream()
        .anyMatch(matricula -> matricula.getAlunoId() == alunoId &&
            matricula.getDisciplinaId() == disciplinaId &&
            "ATIVA".equals(matricula.getStatus()));
  }

  public boolean existeMatriculaAtiva(int alunoId, int disciplinaId) {
    return listarTodos().stream()
        .anyMatch(matricula -> matricula.getAlunoId() == alunoId &&
            matricula.getDisciplinaId() == disciplinaId &&
            "ATIVA".equals(matricula.getStatus()));
  }
}
