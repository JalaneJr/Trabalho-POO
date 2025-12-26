package model;

/**
 * Classe que representa um aluno no sistema acadêmico.
 * @author Funda Inc.
 * @version 1.0
 */
public abstract class Aluno extends Pessoa {
    protected String matricula;
    protected String curso;
    protected double notaFinal;
    protected double mensalidade;

    public Aluno(String nome, int idade, String email, String telefone, 
                 String matricula, String curso, double notaFinal, double mensalidade) {
        super(nome, idade, email, telefone);
        this.matricula = matricula;
        this.curso = curso;
        this.notaFinal = notaFinal;
        this.mensalidade = mensalidade;
    }

    // Getters e Setters
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
    
    public double getNotaFinal() { return notaFinal; }
    public void setNotaFinal(double notaFinal) { this.notaFinal = notaFinal; }
    
    public double getMensalidade() { return mensalidade; }
    public void setMensalidade(double mensalidade) { this.mensalidade = mensalidade; }
    
    /**
     * Método abstrato para calcular o pagamento da mensalidade
     * @return valor a pagar
     */
    public abstract double calcularPagamento();
    
    /**
     * Método abstrato para verificar se o aluno está dispensado
     * @return true se estiver dispensado, false caso contrário
     */
    public abstract boolean isDispensado();
    
    @Override
    public String toString() {
        return super.toString() + ", Matrícula: " + matricula + ", Curso: " + curso + 
               ", Nota Final: " + notaFinal + ", Mensalidade: " + mensalidade;
    }
}