import java.io.Serializable;

public interface Conta extends Serializable {
    double calcularTaxa();
    String getNumeroConta();
    String getTitular();
    double getSaldo();
    String getTipoConta();
}