package util;

import model.Aluno;
import model.Professor;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitária para gerenciamento de arquivos (leitura e escrita de objetos).
 * @author Funda Inc.
 * @version 1.0
 */
public class FileManager {
    
    /**
     * Salva a lista de alunos em um arquivo binário
     * @param alunos Lista de alunos a ser salva
     * @param filename Nome do arquivo
     * @return true se salvou com sucesso, false caso contrário
     */
    public static boolean salvarAlunos(List<Aluno> alunos, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(alunos);
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao salvar alunos: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Carrega a lista de alunos de um arquivo binário
     * @param filename Nome do arquivo
     * @return Lista de alunos carregada
     */
    @SuppressWarnings("unchecked")
    public static List<Aluno> carregarAlunos(String filename) {
        List<Aluno> alunos = new ArrayList<>();
        File file = new File(filename);
        
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
                alunos = (List<Aluno>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar alunos: " + e.getMessage());
            }
        }
        
        return alunos;
    }
    
    /**
     * Salva a lista de professores em um arquivo binário
     * @param professores Lista de professores a ser salva
     * @param filename Nome do arquivo
     * @return true se salvou com sucesso, false caso contrário
     */
    public static boolean salvarProfessores(List<Professor> professores, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(professores);
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao salvar professores: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Carrega a lista de professores de um arquivo binário
     * @param filename Nome do arquivo
     * @return Lista de professores carregada
     */
    @SuppressWarnings("unchecked")
    public static List<Professor> carregarProfessores(String filename) {
        List<Professor> professores = new ArrayList<>();
        File file = new File(filename);
        
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
                professores = (List<Professor>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar professores: " + e.getMessage());
            }
        }
        
        return professores;
    }
    
    /**
     * Exporta texto para um arquivo .txt
     * @param texto Conteúdo a ser exportado
     * @param filename Nome do arquivo
     * @return true se exportou com sucesso, false caso contrário
     */
    public static boolean exportarTexto(String texto, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.write(texto);
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao exportar texto: " + e.getMessage());
            return false;
        }
    }
}
