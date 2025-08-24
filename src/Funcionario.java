public class Funcionario implements Pagavel {
    private String nome;
    private double salarioFixo;

    public Funcionario(String nome, double salarioFixo) {
        this.nome = nome;
        this.salarioFixo = salarioFixo;
    }

    @Override
    public double calcularPagamento() {
        return salarioFixo;
    }

    @Override
    public String getDescricao() {
        return "Funcionário: " + nome + " (Salário Fixo)";
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public double getSalarioFixo() { return salarioFixo; }
    public void setSalarioFixo(double salarioFixo) { this.salarioFixo = salarioFixo; }
}