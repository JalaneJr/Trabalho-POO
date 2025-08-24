public class Freelancer implements Pagavel {
    private String nome;
    private double valorHora;
    private int horasTrabalhadas;

    public Freelancer(String nome, double valorHora, int horasTrabalhadas) {
        this.nome = nome;
        this.valorHora = valorHora;
        this.horasTrabalhadas = horasTrabalhadas;
    }

    @Override
    public double calcularPagamento() {
        return valorHora * horasTrabalhadas;
    }

    @Override
    public String getDescricao() {
        return "Freelancer: " + nome + " (R$ " + valorHora + "/hora)";
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public double getValorHora() { return valorHora; }
    public void setValorHora(double valorHora) { this.valorHora = valorHora; }
    
    public int getHorasTrabalhadas() { return horasTrabalhadas; }
    public void setHorasTrabalhadas(int horasTrabalhadas) { this.horasTrabalhadas = horasTrabalhadas; }
}