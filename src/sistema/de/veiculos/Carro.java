package sistema.de.veiculos;

public class Carro extends Veiculo {
    private String tipoCombustivel;

    public Carro(String modelo, String marca, int ano, String tipoCombustivel) {
        super(modelo, marca, ano);
        this.tipoCombustivel = tipoCombustivel;
    }

    @Override
    public String mover() {
        return "O carro " + getMarca() + " " + getModelo() + " está se movendo com motor a " + tipoCombustivel;
    }

    public String getTipoCombustivel() { return tipoCombustivel; }
    public void setTipoCombustivel(String tipoCombustivel) { this.tipoCombustivel = tipoCombustivel; }
}