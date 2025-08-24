import java.io.Serializable;

public interface Pagavel extends Serializable {
    double calcularPagamento();
    String getDescricao();
}