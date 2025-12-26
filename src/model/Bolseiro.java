package model;

/**
 * Classe que representa um aluno bolseiro.
 * @author Funda Inc.
 * @version 1.0
 */
public class Bolseiro extends Aluno {
    
    public Bolseiro(String nome, int idade, String email, String telefone, 
                    String matricula, String curso, double notaFinal, double mensalidade) {
        super(nome, idade, email, telefone, matricula, curso, notaFinal, mensalidade);
    }

    @Override
    public double calcularPagamento() {
        if (notaFinal >= 16) {
            return 0; // Dispensa total
        } else {
            return mensalidade / 2; // Paga metade
        }
    }

    @Override
    public boolean isDispensado() {
        return notaFinal >= 16;
    }
    
    @Override
    public String toString() {
        return "BOLSEIRO - " + super.toString();
    }
}