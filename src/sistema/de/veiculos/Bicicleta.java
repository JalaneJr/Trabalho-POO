package sistema.de.veiculos;

public class Bicicleta extends Veiculo {
    private int numeroMarchas;

    public Bicicleta(String modelo, String marca, int ano, int numeroMarchas) {
        super(modelo, marca, ano);
        this.numeroMarchas = numeroMarchas;
    }

    @Override
    public String mover() {
        return "A bicicleta " + getMarca() + " " + getModelo() + " está se movendo com " + numeroMarchas + " marchas";
    }

    public int getNumeroMarchas() { return numeroMarchas; }
    public void setNumeroMarchas(int numeroMarchas) { this.numeroMarchas = numeroMarchas; }
}