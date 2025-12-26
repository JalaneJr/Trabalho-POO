package model;

/**
 * Classe que representa um aluno não-bolseiro.
 * @author Funda Inc.
 * @version 1.0
 */
public class NaoBolseiro extends Aluno {
    
    public NaoBolseiro(String nome, int idade, String email, String telefone, 
                       String matricula, String curso, double notaFinal, double mensalidade) {
        super(nome, idade, email, telefone, matricula, curso, notaFinal, mensalidade);
    }

    @Override
    public double calcularPagamento() {
        return mensalidade; // Sempre paga a mensalidade completa
    }

    @Override
    public boolean isDispensado() {
        return notaFinal >= 14;
    }
    
    @Override
    public String toString() {
        return "NÃO-BOLSEIRO - " + super.toString();
    }
}