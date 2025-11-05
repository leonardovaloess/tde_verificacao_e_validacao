package dao;

import classes.Disciplina;
import java.io.*;
import java.util.*;

public class DisciplinaDAO {
    private static final String ARQUIVO = "dados/disciplinas.txt";
    private static int proximoId = 1;

    public DisciplinaDAO() {
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
        List<Disciplina> disciplinas = listarTodos();
        if (!disciplinas.isEmpty()) {
            proximoId = disciplinas.stream().mapToInt(Disciplina::getId).max().orElse(0) + 1;
        }
    }

    public void salvar(Disciplina disciplina) {
        if (disciplina.getId() == 0) {
            disciplina.setId(proximoId++);
        }
        
        List<Disciplina> disciplinas = listarTodos();
        boolean encontrado = false;
        
        for (int i = 0; i < disciplinas.size(); i++) {
            if (disciplinas.get(i).getId() == disciplina.getId()) {
                disciplinas.set(i, disciplina);
                encontrado = true;
                break;
            }
        }
        
        if (!encontrado) {
            disciplinas.add(disciplina);
        }
        
        salvarTodos(disciplinas);
    }

    public void excluir(int id) {
        List<Disciplina> disciplinas = listarTodos();
        disciplinas.removeIf(disciplina -> disciplina.getId() == id);
        salvarTodos(disciplinas);
    }

    public Disciplina buscarPorId(int id) {
        return listarTodos().stream()
                .filter(disciplina -> disciplina.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Disciplina> listarTodos() {
        List<Disciplina> disciplinas = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    disciplinas.add(Disciplina.fromString(linha));
                }
            }
        } catch (FileNotFoundException e) {
            // Arquivo ainda não existe, retorna lista vazia
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return disciplinas;
    }

    private void salvarTodos(List<Disciplina> disciplinas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Disciplina disciplina : disciplinas) {
                writer.write(disciplina.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean existeCodigo(String codigo) {
        return listarTodos().stream()
                .anyMatch(disciplina -> disciplina.getCodigo().equals(codigo));
    }

    public List<Disciplina> buscarPorProfessor(int professorId) {
        return listarTodos().stream()
                .filter(disciplina -> disciplina.getProfessorId() == professorId)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}
