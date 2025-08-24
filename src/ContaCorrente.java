public class ContaCorrente implements Conta {
    private String numeroConta;
    private String titular;
    private double saldo;
    private static final double TAXA_FIXA = 15.00;

    public ContaCorrente(String numeroConta, String titular, double saldoInicial) {
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    @Override
    public double calcularTaxa() {
        return TAXA_FIXA;
    }

    @Override
    public String getNumeroConta() { return numeroConta; }

    @Override
    public String getTitular() { return titular; }

    @Override
    public double getSaldo() { return saldo; }

    @Override
    public String getTipoConta() { return "Corrente"; }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
        }
    }

    public boolean sacar(double valor) {
        if (valor > 0 && saldo >= valor + TAXA_FIXA) {
            saldo -= (valor + TAXA_FIXA);
            return true;
        }
        return false;
    }
}