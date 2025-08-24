package model;

import java.io.Serializable;

/**
 * Classe base que representa uma pessoa no sistema acadêmico.
 * @author Funda Inc.
 * @version 1.0
 */
public abstract class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String nome;
    protected int idade;
    protected String email;
    protected String telefone;

    public Pessoa(String nome, int idade, String email, String telefone) {
        this.nome = nome;
        this.idade = idade;
        this.email = email;
        this.telefone = telefone;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    @Override
    public String toString() {
        return "Nome: " + nome + ", Idade: " + idade + ", Email: " + email + ", Telefone: " + telefone;
    }
}