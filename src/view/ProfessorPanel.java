package view;

import controller.ProfessorController;
import model.Professor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Painel para gestão de professores.
 * @author Funda Inc.
 * @version 1.0
 */
public class ProfessorPanel extends JPanel {
    private ProfessorController professorController;
    private JTable tabelaProfessores;
    private DefaultTableModel tableModel;
    
    // Componentes do formulário
    private JTextField txtNome, txtIdade, txtEmail, txtTelefone, txtDisciplina, txtSalario;
    private JButton btnAdicionar, btnRemover;
    
    // Cores
    private final Color COR_FUNDO = new Color(236, 240, 241);
    private final Color COR_BOTAO = new Color(52, 152, 219);
    private final Color COR_BOTAO_TEXTO = Color.WHITE;

    public ProfessorPanel(ProfessorController professorController) {
        this.professorController = professorController;
        initComponents();
        atualizarLista();
    }
    
    /**
     * Inicializa os componentes da interface
     */
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(COR_FUNDO);
        
        // Painel de formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(COR_FUNDO);
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastro de Professor"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Componentes do formulário
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nome:"), gbc);
        
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        formPanel.add(txtNome, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Idade:"), gbc);
        
        gbc.gridx = 1;
        txtIdade = new JTextField(5);
        formPanel.add(txtIdade, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        formPanel.add(txtEmail, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Telefone:"), gbc);
        
        gbc.gridx = 1;
        txtTelefone = new JTextField(15);
        formPanel.add(txtTelefone, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Disciplina:"), gbc);
        
        gbc.gridx = 1;
        txtDisciplina = new JTextField(20);
        formPanel.add(txtDisciplina, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Salário:"), gbc);
        
        gbc.gridx = 1;
        txtSalario = new JTextField(10);
        formPanel.add(txtSalario, gbc);
        
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        btnAdicionar = criarBotao("Adicionar Professor", COR_BOTAO);
        btnAdicionar.addActionListener(e -> adicionarProfessor());
        formPanel.add(btnAdicionar, gbc);
        
        // Painel de ações
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.setBackground(COR_FUNDO);
        
        btnRemover = criarBotao("Remover Selecionado", new Color(231, 76, 60));
        btnRemover.addActionListener(e -> removerProfessor());
        actionPanel.add(btnRemover);
        
        // Tabela de professores
        String[] colunas = {"Nome", "Email", "Telefone", "Disciplina", "Salário"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna a tabela não editável
            }
        };
        
        tabelaProfessores = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaProfessores);
        
        // Adicionar componentes ao painel principal
        add(formPanel, BorderLayout.NORTH);
        add(actionPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }
    
    /**
     * Cria um botão estilizado
     * @param texto Texto do botão
     * @param cor Cor de fundo do botão
     * @return Botão estilizado
     */
    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setBackground(cor);
        botao.setForeground(COR_BOTAO_TEXTO);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setOpaque(true);
        return botao;
    }
    
    /**
     * Adiciona um novo professor
     */
    private void adicionarProfessor() {
        try {
            String nome = txtNome.getText();
            int idade = Integer.parseInt(txtIdade.getText());
            String email = txtEmail.getText();
            String telefone = txtTelefone.getText();
            String disciplina = txtDisciplina.getText();
            double salario = Double.parseDouble(txtSalario.getText());
            
            Professor professor = professorController.criarProfessor(nome, idade, email, telefone, disciplina, salario);
            
            if (professor != null && professorController.adicionarProfessor(professor)) {
                JOptionPane.showMessageDialog(this, "Professor adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparFormulario();
                atualizarLista();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Remove o professor selecionado
     */
    private void removerProfessor() {
        int selectedRow = tabelaProfessores.getSelectedRow();
        if (selectedRow != -1) {
            String email = (String) tableModel.getValueAt(selectedRow, 1);
            if (professorController.removerProfessor(email)) {
                JOptionPane.showMessageDialog(this, "Professor removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                atualizarLista();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um professor para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Limpa o formulário de cadastro
     */
    private void limparFormulario() {
        txtNome.setText("");
        txtIdade.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtDisciplina.setText("");
        txtSalario.setText("");
    }
    
    /**
     * Atualiza a lista de professores na tabela
     */
    public void atualizarLista() {
        tableModel.setRowCount(0); // Limpa a tabela
        
        List<Professor> professores = professorController.getProfessores();
        for (Professor professor : professores) {
            tableModel.addRow(new Object[]{
                professor.getNome(),
                professor.getEmail(),
                professor.getTelefone(),
                professor.getDisciplina(),
                professor.getSalario()
            });
        }
    }
}