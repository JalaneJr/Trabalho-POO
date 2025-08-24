package cadastro.de.animais;

import java.io.Serializable;

public abstract class Animal implements Serializable {
    private String nome;
    private int idade;

    public Animal(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public abstract String fazerSom();

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " - Nome: " + nome + ", Idade: " + idade;
    }
}