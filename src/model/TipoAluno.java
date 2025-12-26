package model;

/**
 * Enum que representa os tipos de alunos disponíveis no sistema.
 * @author Funda Inc.
 * @version 1.0
 */
public enum TipoAluno {
    BOLSEIRO("Bolseiro"),
    NAO_BOLSEIRO("Não-Bolseiro");
    
    private String descricao;
    
    private TipoAluno(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    @Override
    public String toString() {
        return descricao;
    }
}