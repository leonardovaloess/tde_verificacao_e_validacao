package classes;

public class Disciplina {
    private int id;
    private String nomeDisciplina;
    private String codigo;
    private int cargaHoraria;
    private int professorId;
    private String semestre;

    public Disciplina() {
    }

    public Disciplina(int id, String nomeDisciplina, String codigo, int cargaHoraria, int professorId, String semestre) {
        this.id = id;
        this.nomeDisciplina = nomeDisciplina;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
        this.professorId = professorId;
        this.semestre = semestre;
    }

    public Disciplina(String nomeDisciplina, String codigo, int cargaHoraria, int professorId, String semestre) {
        this.nomeDisciplina = nomeDisciplina;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
        this.professorId = professorId;
        this.semestre = semestre;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    @Override
    public String toString() {
        return id + ";" + nomeDisciplina + ";" + codigo + ";" + cargaHoraria + ";" + professorId + ";" + semestre;
    }

    public static Disciplina fromString(String linha) {
        String[] dados = linha.split(";");
        return new Disciplina(
            Integer.parseInt(dados[0]),
            dados[1],
            dados[2],
            Integer.parseInt(dados[3]),
            Integer.parseInt(dados[4]),
            dados[5]
        );
    }

    public String toDisplayString() {
        return nomeDisciplina + " - " + codigo;
    }
}
