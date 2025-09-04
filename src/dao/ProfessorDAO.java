package dao;

import classes.Professor;
import java.io.*;
import java.util.*;

public class ProfessorDAO {
    private static final String ARQUIVO = "dados/professores.txt";
    private static int proximoId = 1;

    public ProfessorDAO() {
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
        List<Professor> professores = listarTodos();
        if (!professores.isEmpty()) {
            proximoId = professores.stream().mapToInt(Professor::getId).max().orElse(0) + 1;
        }
    }

    public void salvar(Professor professor) {
        if (professor.getId() == 0) {
            professor.setId(proximoId++);
        }
        
        List<Professor> professores = listarTodos();
        boolean encontrado = false;
        
        for (int i = 0; i < professores.size(); i++) {
            if (professores.get(i).getId() == professor.getId()) {
                professores.set(i, professor);
                encontrado = true;
                break;
            }
        }
        
        if (!encontrado) {
            professores.add(professor);
        }
        
        salvarTodos(professores);
    }

    public void excluir(int id) {
        List<Professor> professores = listarTodos();
        professores.removeIf(professor -> professor.getId() == id);
        salvarTodos(professores);
    }

    public Professor buscarPorId(int id) {
        return listarTodos().stream()
                .filter(professor -> professor.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Professor> listarTodos() {
        List<Professor> professores = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    professores.add(Professor.fromString(linha));
                }
            }
        } catch (FileNotFoundException e) {
            // Arquivo ainda não existe, retorna lista vazia
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return professores;
    }

    private void salvarTodos(List<Professor> professores) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Professor professor : professores) {
                writer.write(professor.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean existeIdentificador(String identificador) {
        return listarTodos().stream()
                .anyMatch(professor -> professor.getIdentificador().equals(identificador));
    }
}
