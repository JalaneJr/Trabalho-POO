package util;

import javax.swing.JOptionPane;

/**
 * Classe utilitária para validação de dados de entrada.
 * @author Funda Inc.
 * @version 1.0
 */
public class Validacao {
    
    public static boolean validarEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
    
    public static boolean validarTelefone(String telefone) {
        return telefone != null && telefone.matches("\\d{9}");
    }
    
    public static boolean validarNota(double nota) {
        return nota >= 0 && nota <= 20;
    }
    
    public static boolean validarValorPositivo(double valor) {
        return valor >= 0;
    }
    
    public static boolean validarIdade(int idade) {
        return idade > 0 && idade < 120;
    }
    
    public static boolean validarTexto(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
    
    public static void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Erro de Validação", JOptionPane.ERROR_MESSAGE);
    }
}