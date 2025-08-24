public class ContaPoupanca implements Conta {
    private String numeroConta;
    private String titular;
    private double saldo;
    private static final double TAXA_PERCENTUAL = 0.02; // 2%

    public ContaPoupanca(String numeroConta, String titular, double saldoInicial) {
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    @Override
    public double calcularTaxa() {
        return saldo * TAXA_PERCENTUAL;
    }

    @Override
    public String getNumeroConta() { return numeroConta; }

    @Override
    public String getTitular() { return titular; }

    @Override
    public double getSaldo() { return saldo; }

    @Override
    public String getTipoConta() { return "Poupança"; }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
        }
    }

    public boolean sacar(double valor) {
        double taxa = calcularTaxa();
        if (valor > 0 && saldo >= valor + taxa) {
            saldo -= (valor + taxa);
            return true;
        }
        return false;
    }
}