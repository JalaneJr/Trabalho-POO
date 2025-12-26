package view;

import controller.AlunoController;
import model.Aluno;
import model.Bolseiro;
import model.NaoBolseiro;
import model.TipoAluno;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Painel para gestão de alunos.
 * @author Funda Inc.
 * @version 1.0
 */
public class AlunoPanel extends JPanel {
    private AlunoController alunoController;
    private JTable tabelaAlunos;
    private DefaultTableModel tableModel;
    
    // Componentes do formulário
    private JComboBox<TipoAluno> cmbTipo;
    private JTextField txtNome, txtIdade, txtEmail, txtTelefone, txtMatricula, txtCurso, txtNota, txtMensalidade;
    private JButton btnAdicionar, btnRemover, btnAtualizarNota;
    
    // Cores
    private final Color COR_FUNDO = new Color(236, 240, 241);
    private final Color COR_BOTAO = new Color(52, 152, 219);
    private final Color COR_BOTAO_TEXTO = Color.WHITE;

    public AlunoPanel(AlunoController alunoController) {
        this.alunoController = alunoController;
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
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastro de Aluno"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Componentes do formulário
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Tipo:"), gbc);
        
        gbc.gridx = 1;
        cmbTipo = new JComboBox<>(TipoAluno.values());
        formPanel.add(cmbTipo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Nome:"), gbc);
        
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        formPanel.add(txtNome, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Idade:"), gbc);
        
        gbc.gridx = 1;
        txtIdade = new JTextField(5);
        formPanel.add(txtIdade, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        formPanel.add(txtEmail, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Telefone:"), gbc);
        
        gbc.gridx = 1;
        txtTelefone = new JTextField(15);
        formPanel.add(txtTelefone, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Matrícula:"), gbc);
        
        gbc.gridx = 1;
        txtMatricula = new JTextField(15);
        formPanel.add(txtMatricula, gbc);
        
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Curso:"), gbc);
        
        gbc.gridx = 1;
        txtCurso = new JTextField(20);
        formPanel.add(txtCurso, gbc);
        
        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(new JLabel("Nota Final:"), gbc);
        
        gbc.gridx = 1;
        txtNota = new JTextField(5);
        formPanel.add(txtNota, gbc);
        
        gbc.gridx = 0; gbc.gridy = 8;
        formPanel.add(new JLabel("Mensalidade:"), gbc);
        
        gbc.gridx = 1;
        txtMensalidade = new JTextField(10);
        formPanel.add(txtMensalidade, gbc);
        
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
        btnAdicionar = criarBotao("Adicionar Aluno", COR_BOTAO);
        btnAdicionar.addActionListener(e -> adicionarAluno());
        formPanel.add(btnAdicionar, gbc);
        
        // Painel de ações
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.setBackground(COR_FUNDO);
        
        btnRemover = criarBotao("Remover Selecionado", new Color(231, 76, 60));
        btnRemover.addActionListener(e -> removerAluno());
        actionPanel.add(btnRemover);
        
        btnAtualizarNota = criarBotao("Atualizar Nota", new Color(241, 196, 15));
        btnAtualizarNota.addActionListener(e -> atualizarNota());
        actionPanel.add(btnAtualizarNota);
        
        // Tabela de alunos
        String[] colunas = {"Tipo", "Nome", "Matrícula", "Curso", "Nota", "Mensalidade", "Pagamento", "Situação"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna a tabela não editável
            }
        };
        
        tabelaAlunos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaAlunos);
        
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
     * Adiciona um novo aluno
     */
    private void adicionarAluno() {
        try {
            TipoAluno tipo = (TipoAluno) cmbTipo.getSelectedItem();
            String nome = txtNome.getText();
            int idade = Integer.parseInt(txtIdade.getText());
            String email = txtEmail.getText();
            String telefone = txtTelefone.getText();
            String matricula = txtMatricula.getText();
            String curso = txtCurso.getText();
            double nota = Double.parseDouble(txtNota.getText());
            double mensalidade = Double.parseDouble(txtMensalidade.getText());
            
            Aluno aluno = alunoController.criarAluno(tipo, nome, idade, email, telefone, 
                                                   matricula, curso, nota, mensalidade);
            
            if (aluno != null && alunoController.adicionarAluno(aluno)) {
                JOptionPane.showMessageDialog(this, "Aluno adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparFormulario();
                atualizarLista();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Remove o aluno selecionado
     */
    private void removerAluno() {
        int selectedRow = tabelaAlunos.getSelectedRow();
        if (selectedRow != -1) {
            String matricula = (String) tableModel.getValueAt(selectedRow, 2);
            if (alunoController.removerAluno(matricula)) {
                JOptionPane.showMessageDialog(this, "Aluno removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                atualizarLista();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um aluno para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Atualiza a nota do aluno selecionado
     */
    private void atualizarNota() {
        int selectedRow = tabelaAlunos.getSelectedRow();
        if (selectedRow != -1) {
            String matricula = (String) tableModel.getValueAt(selectedRow, 2);
            String novaNotaStr = JOptionPane.showInputDialog(this, "Digite a nova nota:", "Atualizar Nota", JOptionPane.QUESTION_MESSAGE);
            
            if (novaNotaStr != null && !novaNotaStr.trim().isEmpty()) {
                try {
                    double novaNota = Double.parseDouble(novaNotaStr);
                    if (alunoController.atualizarNota(matricula, novaNota)) {
                        JOptionPane.showMessageDialog(this, "Nota atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        atualizarLista();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Nota inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um aluno para atualizar a nota.", "Aviso", JOptionPane.WARNING_MESSAGE);
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
        txtMatricula.setText("");
        txtCurso.setText("");
        txtNota.setText("");
        txtMensalidade.setText("");
    }
    
    /**
     * Atualiza a lista de alunos na tabela
     */
    public void atualizarLista() {
        tableModel.setRowCount(0); // Limpa a tabela
        
        List<Aluno> alunos = alunoController.getAlunos();
        for (Aluno aluno : alunos) {
            String tipo = aluno instanceof Bolseiro ? "Bolseiro" : "Não-Bolseiro";
            String situacao = aluno.isDispensado() ? "Dispensado" : "Não Dispensado";
            
            tableModel.addRow(new Object[]{
                tipo,
                aluno.getNome(),
                aluno.getMatricula(),
                aluno.getCurso(),
                aluno.getNotaFinal(),
                aluno.getMensalidade(),
                aluno.calcularPagamento(),
                situacao
            });
        }
    }
}