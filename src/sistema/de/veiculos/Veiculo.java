package sistema.de.veiculos;

import java.io.Serializable;

public abstract class Veiculo implements Serializable {
    private String modelo;
    private String marca;
    private int ano;

    public Veiculo(String modelo, String marca, int ano) {
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
    }

    public abstract String mover();

    // Getters e Setters
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    
    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " - " + marca + " " + modelo + " (" + ano + ")";
    }
}