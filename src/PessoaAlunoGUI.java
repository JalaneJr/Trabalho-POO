import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaAlunoGUI extends JFrame {
    private List<Pessoa> pessoas = new ArrayList<>();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNome, txtIdade, txtMatricula;
    private JComboBox<String> comboBoxTipo;
    private JRadioButton rbPessoa, rbAluno;
    
    private Color primaryColor = new Color(0, 178, 255);    
    private Color secondaryColor = new Color(190, 230, 231); 
    private Color accentColor = new Color(46, 204, 113);     

    public PessoaAlunoGUI() {
        setTitle("Sistema de Cadastro de Pessoas e Alunos");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        applyStyles();
        loadPessoasFromFile();
        updateTable();
    }

    private void initComponents() {
        // Configurar layout principal
        setLayout(new BorderLayout(10, 10));
        
        // Painel de cabeçalho
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Painel principal (formulário + tabela)
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Painel de formulário
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel);
        
        // Painel de tabela
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(primaryColor);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        
        JLabel title = new JLabel("Sistema de Cadastro de Pessoas e Alunos");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        
        panel.add(title);
        return panel;
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(secondaryColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(primaryColor, 2, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Título do formulário
        JLabel formTitle = new JLabel("Cadastrar Nova Pessoa/Aluno");
        formTitle.setFont(new Font("Arial", Font.BOLD, 16));
        formTitle.setForeground(primaryColor);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(formTitle, gbc);
        
        // Seleção de tipo (Pessoa ou Aluno)
        gbc.gridy = 1; gbc.gridwidth = 2;
        JPanel tipoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tipoPanel.setBackground(secondaryColor);
        
        ButtonGroup tipoGroup = new ButtonGroup();
        rbPessoa = new JRadioButton("Pessoa", true);
        rbAluno = new JRadioButton("Aluno", false);
        
        tipoGroup.add(rbPessoa);
        tipoGroup.add(rbAluno);
        
        // Adicionar listener para mostrar/ocultar campo matrícula
        ActionListener tipoListener = e -> toggleMatriculaField();
        rbPessoa.addActionListener(tipoListener);
        rbAluno.addActionListener(tipoListener);
        
        tipoPanel.add(rbPessoa);
        tipoPanel.add(rbAluno);
        panel.add(tipoPanel, gbc);
        
        // Campo Nome
        gbc.gridy = 2; gbc.gridwidth = 1;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(15);
        panel.add(txtNome, gbc);
        
        // Campo Idade
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Idade:"), gbc);
        gbc.gridx = 1;
        txtIdade = new JTextField(15);
        panel.add(txtIdade, gbc);
        
        // Campo Matrícula (apenas para aluno)
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblMatricula = new JLabel("Matrícula:");
        panel.add(lblMatricula, gbc);
        gbc.gridx = 1;
        txtMatricula = new JTextField(15);
        panel.add(txtMatricula, gbc);
        
        return panel;
    }
    
    private void toggleMatriculaField() {
        boolean isAluno = rbAluno.isSelected();
        
        // Encontrar os componentes pelo tipo
        for (Component comp : ((JPanel) ((JPanel) getContentPane().getComponent(1)).getComponent(0)).getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if ("Matrícula:".equals(label.getText())) {
                    label.setVisible(isAluno);
                }
            }
        }
        
        for (Component comp : ((JPanel) ((JPanel) getContentPane().getComponent(1)).getComponent(0)).getComponents()) {
            if (comp instanceof JTextField) {
                JTextField field = (JTextField) comp;
                if (field == txtMatricula) {
                    field.setVisible(isAluno);
                }
            }
        }
        
        // Atualizar a interface
        revalidate();
        repaint();
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(primaryColor), 
                                           "Pessoas e Alunos Cadastrados", 
                                           0, 0, 
                                           new Font("Arial", Font.BOLD, 14), 
                                           primaryColor),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        tableModel = new DefaultTableModel(new Object[]{"Tipo", "Nome", "Idade", "Matrícula", "toString()"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(secondaryColor);
        table.getTableHeader().setForeground(Color.BLACK);
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(secondaryColor);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnCarregar = new JButton("Carregar");
        JButton btnSobre = new JButton("Sobre");
        
        panel.add(btnAdicionar);
        panel.add(btnSalvar);
        panel.add(btnLimpar);
        panel.add(btnCarregar);
        panel.add(btnSobre);
        
        // Adicionar listeners
        btnAdicionar.addActionListener(e -> adicionarPessoa());
        btnSalvar.addActionListener(e -> salvarPessoas());
        btnLimpar.addActionListener(e -> limparCampos());
        btnCarregar.addActionListener(e -> {
            loadPessoasFromFile();
            updateTable();
        });
        btnSobre.addActionListener(e -> mostrarSobre());
        
        // Aplicar estilos aos botões
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.setFont(new Font("Arial", Font.BOLD, 12));
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(primaryColor, 1),
                    BorderFactory.createEmptyBorder(8, 15, 8, 15)
                ));
                
                if (button == btnAdicionar) {
                    button.setBackground(secondaryColor);
                    button.setForeground(Color.WHITE);
                } else {
                    button.setBackground(Color.WHITE);
                    button.setForeground(primaryColor);
                }
                
                // Efeito hover nos botões
                button.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent evt) {
                        button.setBackground(primaryColor);
                        button.setForeground(Color.BLACK);
                    }
                    
                    public void mouseExited(MouseEvent evt) {
                        if (button == btnAdicionar) {
                            button.setBackground(accentColor);
                        } else {
                            button.setBackground(Color.BLACK);
                            button.setForeground(primaryColor);
                        }
                    }
                });
            }
        }
        
        return panel;
    }
    
    private void applyStyles() {
        getContentPane().setBackground(secondaryColor);
    }
    
    private void adicionarPessoa() {
        try {
            boolean isAluno = rbAluno.isSelected();
            String nome = txtNome.getText().trim();
            int idade = Integer.parseInt(txtIdade.getText().trim());

            if (nome.isEmpty()) {
                showError("Nome não pode estar vazio!");
                return;
            }
            
            if (idade <= 0) {
                showError("Idade deve ser um número positivo!");
                return;
            }

            Pessoa pessoa;
            if (isAluno) {
                String matricula = txtMatricula.getText().trim();
                if (matricula.isEmpty()) {
                    showError("Matrícula não pode estar vazia!");
                    return;
                }
                pessoa = new Aluno(nome, idade, matricula);
            } else {
                pessoa = new Pessoa(nome, idade);
            }

            pessoas.add(pessoa);
            updateTable();
            limparCampos();
            showSuccess((isAluno ? "Aluno" : "Pessoa") + " adicionado(a) com sucesso!");

        } catch (NumberFormatException ex) {
            showError("Idade deve ser um número válido!");
        }
    }

    private void salvarPessoas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("pessoas.dat"))) {
            oos.writeObject(pessoas);
            showSuccess("Dados salvos com sucesso!");
        } catch (IOException ex) {
            showError("Erro ao salvar dados: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadPessoasFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("pessoas.dat"))) {
            pessoas = (List<Pessoa>) ois.readObject();
            showInfo(pessoas.size() + " registros carregados do arquivo.");
        } catch (FileNotFoundException ex) {
            // Arquivo não existe ainda, é normal
        } catch (IOException | ClassNotFoundException ex) {
            showError("Erro ao carregar dados: " + ex.getMessage());
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Pessoa pessoa : pessoas) {
            String tipo = pessoa instanceof Aluno ? "Aluno" : "Pessoa";
            String matricula = pessoa instanceof Aluno ? ((Aluno) pessoa).getMatricula() : "N/A";
            
            tableModel.addRow(new Object[]{
                tipo,
                pessoa.getNome(),
                pessoa.getIdade(),
                matricula,
                pessoa.toString()  // Demonstração do método toString()
            });
        }
        
        updateStats();
    }
    
    private void updateStats() {
        int pessoasCount = 0, alunosCount = 0;
        for (Pessoa pessoa : pessoas) {
            if (pessoa instanceof Aluno) alunosCount++;
            else pessoasCount++;
        }
        
        String stats = String.format("Total: %d registros | Pessoas: %d | Alunos: %d", 
                                    pessoas.size(), pessoasCount, alunosCount);
        
        // Atualizar barra de status se desejar
    }

    private void limparCampos() {
        txtNome.setText("");
        txtIdade.setText("");
        txtMatricula.setText("");
        txtNome.requestFocus();
    }
    
    private void mostrarSobre() {
        JOptionPane.showMessageDialog(this, 
            "Sistema de Cadastro de Pessoas e Alunos\n\n" +
            "Exercício 3 - Pessoas e Alunos (toString + JTable)\n" +
            "• Cadastre pessoas e alunos\n" +
            "• Visualize os dados em tabela com polimorfismo\n" +
            "• Demonstração do método toString()\n" +
            "• Salve e carregue os dados de arquivo", 
            "Sobre o Sistema", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
    
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            PessoaAlunoGUI gui = new PessoaAlunoGUI();
            gui.setVisible(true);
        });
    }
}