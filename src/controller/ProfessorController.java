package controller;

import model.Professor;
import util.FileManager;
import util.Validacao;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para gerenciamento de professores.
 * @author Funda Inc.
 * @version 1.0
 */
public class ProfessorController {
    private List<Professor> professores;
    private static final String ARQUIVO_PROFESSORES = "data/professores.dat";

    public ProfessorController() {
        carregarProfessores();
    }
    
    /**
     * Carrega professores do arquivo
     */
    private void carregarProfessores() {
        professores = FileManager.carregarProfessores(ARQUIVO_PROFESSORES);
        if (professores == null) {
            professores = new ArrayList<>();
        }
    }
    
    /**
     * Salva professores no arquivo
     */
    private void salvarProfessores() {
        FileManager.salvarProfessores(professores, ARQUIVO_PROFESSORES);
    }
    
    /**
     * Adiciona um novo professor
     * @param professor Professor a ser adicionado
     * @return true se adicionado com sucesso, false caso contrário
     */
    public boolean adicionarProfessor(Professor professor) {
        if (professor != null) {
            professores.add(professor);
            salvarProfessores();
            return true;
        }
        return false;
    }
    
    /**
     * Remove um professor pelo email
     * @param email Email do professor a ser removido
     * @return true se removido com sucesso, false caso contrário
     */
    public boolean removerProfessor(String email) {
        for (int i = 0; i < professores.size(); i++) {
            if (professores.get(i).getEmail().equals(email)) {
                professores.remove(i);
                salvarProfessores();
                return true;
            }
        }
        return false;
    }
    
    /**
     * Busca um professor pelo email
     * @param email Email do professor a ser buscado
     * @return Professor encontrado ou null se não encontrado
     */
    public Professor buscarProfessor(String email) {
        for (Professor professor : professores) {
            if (professor.getEmail().equals(email)) {
                return professor;
            }
        }
        return null;
    }
    
    /**
     * Retorna todos os professores
     * @return Lista de professores
     */
    public List<Professor> getProfessores() {
        return professores;
    }
    
    /**
     * Cria um professor com base nos parâmetros fornecidos
     * @param nome Nome do professor
     * @param idade Idade do professor
     * @param email Email do professor
     * @param telefone Telefone do professor
     * @param disciplina Disciplina do professor
     * @param salario Salário do professor
     * @return Professor criado ou null em caso de erro
     */
    public Professor criarProfessor(String nome, int idade, String email, String telefone,
                                  String disciplina, double salario) {
        
        // Validações
        if (!Validacao.validarTexto(nome)) {
            Validacao.mostrarErro("Nome inválido!");
            return null;
        }
        
        if (!Validacao.validarIdade(idade)) {
            Validacao.mostrarErro("Idade inválida!");
            return null;
        }
        
        if (!Validacao.validarEmail(email)) {
            Validacao.mostrarErro("Email inválido!");
            return null;
        }
        
        if (!Validacao.validarTelefone(telefone)) {
            Validacao.mostrarErro("Telefone inválido! Deve ter 9 dígitos.");
            return null;
        }
        
        if (!Validacao.validarTexto(disciplina)) {
            Validacao.mostrarErro("Disciplina inválida!");
            return null;
        }
        
        if (!Validacao.validarValorPositivo(salario)) {
            Validacao.mostrarErro("Salário inválido!");
            return null;
        }
        
        // Verificar se email já existe
        if (buscarProfessor(email) != null) {
            Validacao.mostrarErro("Já existe um professor com este email!");
            return null;
        }
        
        return new Professor(nome, idade, email, telefone, disciplina, salario);
    }
}