package model;

/**
 * Classe que representa um professor no sistema acadêmico.
 */
public class Professor extends Pessoa {
    private String disciplina;
    private double salario;

    public Professor(String nome, int idade, String email, String telefone, 
                     String disciplina, double salario) {
        super(nome, idade, email, telefone);
        this.disciplina = disciplina;
        this.salario = salario;
    }

    // Getters e Setters
    public String getDisciplina() { return disciplina; }
    public void setDisciplina(String disciplina) { this.disciplina = disciplina; }
    
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
    
    @Override
    public String toString() {
        return "PROFESSOR - " + super.toString() + ", Disciplina: " + disciplina + ", Salário: " + salario;
    }
}