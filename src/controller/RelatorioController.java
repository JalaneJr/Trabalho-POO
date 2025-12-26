package controller;

import model.Aluno;
import model.Professor;
import util.FileManager;
import java.util.List;

/**
 * Controlador para geração de relatórios.
 * @author Funda Inc.
 * @version 1.0
 */
public class RelatorioController {
    private AlunoController alunoController;
    private ProfessorController professorController;

    public RelatorioController(AlunoController alunoController, ProfessorController professorController) {
        this.alunoController = alunoController;
        this.professorController = professorController;
    }
    
    /**
     * Gera relatório de todos os alunos usando recursividade
     * @return Texto do relatório
     */
    public String gerarRelatorioAlunos() {
        List<Aluno> alunos = alunoController.getAlunos();
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("========== RELATÓRIO DE ALUNOS ==========\n\n");
        
        if (alunos.isEmpty()) {
            relatorio.append("Nenhum aluno cadastrado.\n");
        } else {
            // Usando recursividade para percorrer a lista
            percorrerAlunosRecursivamente(alunos, 0, relatorio);
        }
        
        return relatorio.toString();
    }
    
    /**
     * Método recursivo para percorrer a lista de alunos
     * @param alunos Lista de alunos
     * @param index Índice atual
     * @param relatorio StringBuilder para construir o relatório
     */
    private void percorrerAlunosRecursivamente(List<Aluno> alunos, int index, StringBuilder relatorio) {
        if (index >= alunos.size()) {
            return; // Caso base: percorreu todos os elementos
        }
        
        Aluno aluno = alunos.get(index);
        relatorio.append("Aluno ").append(index + 1).append(":\n");
        relatorio.append(aluno.toString()).append("\n");
        relatorio.append("Pagamento: ").append(aluno.calcularPagamento()).append("\n");
        relatorio.append("Situação: ").append(aluno.isDispensado() ? "DISPENSADO" : "NÃO DISPENSADO").append("\n");
        relatorio.append("----------------------------------------\n");
        
        // Chamada recursiva para o próximo elemento
        percorrerAlunosRecursivamente(alunos, index + 1, relatorio);
    }
    
    /**
     * Gera relatório de todos os professores usando recursividade
     * @return Texto do relatório
     */
    public String gerarRelatorioProfessores() {
        List<Professor> professores = professorController.getProfessores();
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("========== RELATÓRIO DE PROFESSORES ==========\n\n");
        
        if (professores.isEmpty()) {
            relatorio.append("Nenhum professor cadastrado.\n");
        } else {
            // Usando recursividade para percorrer a lista
            percorrerProfessoresRecursivamente(professores, 0, relatorio);
        }
        
        return relatorio.toString();
    }
    
    /**
     * Método recursivo para percorrer a lista de professores
     * @param professores Lista de professores
     * @param index Índice atual
     * @param relatorio StringBuilder para construir o relatório
     */
    private void percorrerProfessoresRecursivamente(List<Professor> professores, int index, StringBuilder relatorio) {
        if (index >= professores.size()) {
            return; // Caso base: percorreu todos os elementos
        }
        
        Professor professor = professores.get(index);
        relatorio.append("Professor ").append(index + 1).append(":\n");
        relatorio.append(professor.toString()).append("\n");
        relatorio.append("----------------------------------------\n");
        
        // Chamada recursiva para o próximo elemento
        percorrerProfessoresRecursivamente(professores, index + 1, relatorio);
    }
    
    /**
     * Gera relatório completo (alunos + professores)
     * @return Texto do relatório completo
     */
    public String gerarRelatorioCompleto() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append(gerarRelatorioAlunos()).append("\n\n");
        relatorio.append(gerarRelatorioProfessores());
        return relatorio.toString();
    }
    
    /**
     * Exporta relatório para arquivo de texto
     * @param relatorio Texto do relatório
     * @param filename Nome do arquivo
     * @return true se exportado com sucesso, false caso contrário
     */
    public boolean exportarRelatorio(String relatorio, String filename) {
        return FileManager.exportarTexto(relatorio, filename);
    }
}