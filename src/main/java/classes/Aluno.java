package classes;

public class Aluno {
    private int id;
    private String nome;
    private String matricula;
    private String email;
    private String telefone;

    public Aluno() {
    }

    public Aluno(int id, String nome, String matricula, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.email = email;
        this.telefone = telefone;
    }

    public Aluno(String nome, String matricula, String email, String telefone) {
        this.nome = nome;
        this.matricula = matricula;
        this.email = email;
        this.telefone = telefone;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return id + ";" + nome + ";" + matricula + ";" + email + ";" + telefone;
    }

    public static Aluno fromString(String linha) {
        String[] dados = linha.split(";");
        return new Aluno(
            Integer.parseInt(dados[0]),
            dados[1],
            dados[2],
            dados[3],
            dados[4]
        );
    }

    public String toDisplayString() {
        return nome + " - " + matricula;
    }
}
