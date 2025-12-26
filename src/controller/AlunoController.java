package controller;

import model.*;
import util.FileManager;
import util.Validacao;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Controlador para gerenciamento de alunos.
 * @author Funda Inc.
 * @version 1.0
 */
public class AlunoController {
    private List<Aluno> alunos;
    private static final String ARQUIVO_ALUNOS = "data/alunos.dat";

    public AlunoController() {
        carregarAlunos();
    }
    
    /**
     * Carrega alunos do arquivo
     */
    private void carregarAlunos() {
        alunos = FileManager.carregarAlunos(ARQUIVO_ALUNOS);
        if (alunos == null) {
            alunos = new ArrayList<>();
        }
    }
    
    /**
     * Salva alunos no arquivo
     */
    private void salvarAlunos() {
        FileManager.salvarAlunos(alunos, ARQUIVO_ALUNOS);
    }
    
    /**
     * Adiciona um novo aluno
     * @param aluno Aluno a ser adicionado
     * @return true se adicionado com sucesso, false caso contrário
     */
    public boolean adicionarAluno(Aluno aluno) {
        if (aluno != null) {
            alunos.add(aluno);
            salvarAlunos();
            return true;
        }
        return false;
    }
    
    /**
     * Remove um aluno pela matrícula
     * @param matricula Matrícula do aluno a ser removido
     * @return true se removido com sucesso, false caso contrário
     */
    public boolean removerAluno(String matricula) {
        for (int i = 0; i < alunos.size(); i++) {
            if (alunos.get(i).getMatricula().equals(matricula)) {
                alunos.remove(i);
                salvarAlunos();
                return true;
            }
        }
        return false;
    }
    
    /**
     * Busca um aluno pela matrícula
     * @param matricula Matrícula do aluno a ser buscado
     * @return Aluno encontrado ou null se não encontrado
     */
    public Aluno buscarAluno(String matricula) {
        for (Aluno aluno : alunos) {
            if (aluno.getMatricula().equals(matricula)) {
                return aluno;
            }
        }
        return null;
    }
    
    /**
     * Retorna todos os alunos
     * @return Lista de alunos
     */
    public List<Aluno> getAlunos() {
        return alunos;
    }
    
    /**
     * Atualiza a nota de um aluno
     * @param matricula Matrícula do aluno
     * @param nota Nova nota
     * @return true se atualizado com sucesso, false caso contrário
     */
    public boolean atualizarNota(String matricula, double nota) {
        if (!Validacao.validarNota(nota)) {
            Validacao.mostrarErro("Nota inválida! Deve estar entre 0 e 20.");
            return false;
        }
        
        Aluno aluno = buscarAluno(matricula);
        if (aluno != null) {
            aluno.setNotaFinal(nota);
            salvarAlunos();
            return true;
        }
        return false;
    }
    
    /**
     * Cria um aluno com base nos parâmetros fornecidos
     * @param tipo Tipo de aluno (Bolseiro ou Não-Bolseiro)
     * @param nome Nome do aluno
     * @param idade Idade do aluno
     * @param email Email do aluno
     * @param telefone Telefone do aluno
     * @param matricula Matrícula do aluno
     * @param curso Curso do aluno
     * @param notaFinal Nota final do aluno
     * @param mensalidade Valor da mensalidade
     * @return Aluno criado ou null em caso de erro
     */
    public Aluno criarAluno(TipoAluno tipo, String nome, int idade, String email, String telefone,
                           String matricula, String curso, double notaFinal, double mensalidade) {
        
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
        
        if (!Validacao.validarTexto(matricula)) {
            Validacao.mostrarErro("Matrícula inválida!");
            return null;
        }
        
        if (!Validacao.validarTexto(curso)) {
            Validacao.mostrarErro("Curso inválido!");
            return null;
        }
        
        if (!Validacao.validarNota(notaFinal)) {
            Validacao.mostrarErro("Nota inválida! Deve estar entre 0 e 20.");
            return null;
        }
        
        if (!Validacao.validarValorPositivo(mensalidade)) {
            Validacao.mostrarErro("Mensalidade inválida!");
            return null;
        }
        
        // Verificar se matrícula já existe
        if (buscarAluno(matricula) != null) {
            Validacao.mostrarErro("Já existe um aluno com esta matrícula!");
            return null;
        }
        
        // Criar aluno conforme o tipo
        switch (tipo) {
            case BOLSEIRO:
                return new Bolseiro(nome, idade, email, telefone, matricula, curso, notaFinal, mensalidade);
            case NAO_BOLSEIRO:
                return new NaoBolseiro(nome, idade, email, telefone, matricula, curso, notaFinal, mensalidade);
            default:
                return null;
        }
    }
}