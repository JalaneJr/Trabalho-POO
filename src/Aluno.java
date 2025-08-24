public class Aluno extends Pessoa {
    private String matricula;

    public Aluno(String nome, int idade, String matricula) {
        super(nome, idade);
        this.matricula = matricula;
    }

    // Getter e Setter
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    @Override
    public String toString() {
        return "Aluno: " + getNome() + " (" + getIdade() + " anos) - Matrícula: " + matricula;
    }
}