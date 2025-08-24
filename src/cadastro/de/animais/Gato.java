package cadastro.de.animais;

public class Gato extends Animal {
    public Gato(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public String fazerSom() {
        return "Miau!";
    }
}