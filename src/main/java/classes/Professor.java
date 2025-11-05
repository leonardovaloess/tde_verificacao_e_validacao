package classes;

public class Professor {
    private int id;
    private String nome;
    private String identificador;
    private String email;
    private String departamento;

    public Professor() {
    }

    public Professor(int id, String nome, String identificador, String email, String departamento) {
        this.id = id;
        this.nome = nome;
        this.identificador = identificador;
        this.email = email;
        this.departamento = departamento;
    }

    public Professor(String nome, String identificador, String email, String departamento) {
        this.nome = nome;
        this.identificador = identificador;
        this.email = email;
        this.departamento = departamento;
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

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return id + ";" + nome + ";" + identificador + ";" + email + ";" + departamento;
    }

    public static Professor fromString(String linha) {
        String[] dados = linha.split(";");
        return new Professor(
            Integer.parseInt(dados[0]),
            dados[1],
            dados[2],
            dados[3],
            dados[4]
        );
    }

    public String toDisplayString() {
        return nome + " - " + identificador;
    }
}
