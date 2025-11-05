package dao;

import classes.Aluno;
import java.io.*;
import java.util.*;

public class AlunoDAO {
    private static final String ARQUIVO = "dados/alunos.txt";
    private static int proximoId = 1;

    public AlunoDAO() {
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
        List<Aluno> alunos = listarTodos();
        if (!alunos.isEmpty()) {
            proximoId = alunos.stream().mapToInt(Aluno::getId).max().orElse(0) + 1;
        }
    }

    public void salvar(Aluno aluno) {
        if (aluno.getId() == 0) {
            aluno.setId(proximoId++);
        }
        
        List<Aluno> alunos = listarTodos();
        boolean encontrado = false;
        
        for (int i = 0; i < alunos.size(); i++) {
            if (alunos.get(i).getId() == aluno.getId()) {
                alunos.set(i, aluno);
                encontrado = true;
                break;
            }
        }
        
        if (!encontrado) {
            alunos.add(aluno);
        }
        
        salvarTodos(alunos);
    }

    public void excluir(int id) {
        List<Aluno> alunos = listarTodos();
        alunos.removeIf(aluno -> aluno.getId() == id);
        salvarTodos(alunos);
    }

    public Aluno buscarPorId(int id) {
        return listarTodos().stream()
                .filter(aluno -> aluno.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Aluno> listarTodos() {
        List<Aluno> alunos = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    alunos.add(Aluno.fromString(linha));
                }
            }
        } catch (FileNotFoundException e) {
            // Arquivo ainda não existe, retorna lista vazia
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return alunos;
    }

    private void salvarTodos(List<Aluno> alunos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Aluno aluno : alunos) {
                writer.write(aluno.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean existeMatricula(String matricula) {
        return listarTodos().stream()
                .anyMatch(aluno -> aluno.getMatricula().equals(matricula));
    }
}
