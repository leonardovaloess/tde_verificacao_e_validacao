package classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Matricula {
    private int id;
    private int alunoId;
    private int disciplinaId;
    private LocalDate dataMatricula;
    private String status; // ATIVA, CANCELADA, CONCLUIDA

    public Matricula() {
    }

    public Matricula(int id, int alunoId, int disciplinaId, LocalDate dataMatricula, String status) {
        this.id = id;
        this.alunoId = alunoId;
        this.disciplinaId = disciplinaId;
        this.dataMatricula = dataMatricula;
        this.status = status;
    }

    public Matricula(int alunoId, int disciplinaId, LocalDate dataMatricula, String status) {
        this.alunoId = alunoId;
        this.disciplinaId = disciplinaId;
        this.dataMatricula = dataMatricula;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public int getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(int disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return id + ";" + alunoId + ";" + disciplinaId + ";" + dataMatricula.format(formatter) + ";" + status;
    }

    public static Matricula fromString(String linha) {
        String[] dados = linha.split(";");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return new Matricula(
            Integer.parseInt(dados[0]),
            Integer.parseInt(dados[1]),
            Integer.parseInt(dados[2]),
            LocalDate.parse(dados[3], formatter),
            dados[4]
        );
    }
}
