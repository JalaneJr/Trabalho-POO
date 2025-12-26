import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;

public class FuncionarioGUI extends JFrame {
    private List<Funcionario> funcionarios = new ArrayList<>();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNome, txtSalario, txtDepartamento, txtPercentual;
    private JComboBox<String> comboBoxTipo;
    private JTextArea textAreaFolha;
    
    // Nova paleta de cores - Tons de roxo e lavanda
    private Color primaryColor = new Color(102, 51, 153);    // Roxo escuro
    private Color secondaryColor = new Color(230, 230, 250); // Lavanda claro
    private Color accentColor = new Color(147, 112, 219);    // Roxo médio
    private Color highlightColor = new Color(186, 85, 211);  // Orchid

    public FuncionarioGUI() {
        setTitle("Sistema de Gestão de Funcionários");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        applyStyles();
        loadFuncionariosFromFile();
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
        
        // Painel de folha de pagamento
        JPanel folhaPanel = createFolhaPanel();
        add(folhaPanel, BorderLayout.SOUTH);
        
        // Painel de botões
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.EAST);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(primaryColor);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        
        JLabel title = new JLabel("Sistema de Gestão de Funcionários");
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
        JLabel formTitle = new JLabel("Cadastrar Novo Funcionário");
        formTitle.setFont(new Font("Arial", Font.BOLD, 16));
        formTitle.setForeground(primaryColor);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(formTitle, gbc);
        
        // Campo Tipo
        gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        comboBoxTipo = new JComboBox<>(new String[]{"Funcionário", "Gerente"});
        comboBoxTipo.addActionListener(e -> toggleFields());
        panel.add(comboBoxTipo, gbc);
        
        // Campo Nome
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(15);
        panel.add(txtNome, gbc);
        
        // Campo Salário
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Salário (R$):"), gbc);
        gbc.gridx = 1;
        txtSalario = new JTextField(15);
        panel.add(txtSalario, gbc);
        
        // Campo Departamento (apenas para gerente)
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblDepartamento = new JLabel("Departamento:");
        panel.add(lblDepartamento, gbc);
        gbc.gridx = 1;
        txtDepartamento = new JTextField(15);
        panel.add(txtDepartamento, gbc);
        
        // Campo Percentual (para aumento de salário)
        gbc.gridx = 0; gbc.gridy = 5;
        JLabel lblPercentual = new JLabel("Aumento (%):");
        panel.add(lblPercentual, gbc);
        gbc.gridx = 1;
        txtPercentual = new JTextField(15);
        panel.add(txtPercentual, gbc);
        
        // Inicialmente esconder campos específicos
        lblDepartamento.setVisible(false);
        txtDepartamento.setVisible(false);
        
        return panel;
    }
    
    private void toggleFields() {
        String tipo = (String) comboBoxTipo.getSelectedItem();
        boolean isGerente = "Gerente".equals(tipo);
        
        // Encontrar os componentes pelo tipo
        for (Component comp : ((JPanel) ((JPanel) getContentPane().getComponent(1)).getComponent(0)).getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if ("Departamento:".equals(label.getText())) {
                    label.setVisible(isGerente);
                }
            }
        }
        
        for (Component comp : ((JPanel) ((JPanel) getContentPane().getComponent(1)).getComponent(0)).getComponents()) {
            if (comp instanceof JTextField) {
                JTextField field = (JTextField) comp;
                if (field == txtDepartamento) {
                    field.setVisible(isGerente);
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
                                           "Funcionários Cadastrados", 
                                           0, 0, 
                                           new Font("Arial", Font.BOLD, 14), 
                                           primaryColor),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        tableModel = new DefaultTableModel(new Object[]{"Tipo", "Nome", "Salário (R$)", "Departamento"}, 0) {
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
        table.getTableHeader().setBackground(primaryColor);
        table.getTableHeader().setForeground(Color.BLACK);
        
        // Renderer personalizado para valores monetários
        table.getColumnModel().getColumn(2).setCellRenderer(new MonetaryCellRenderer());
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createFolhaPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(accentColor), 
                                           "Cálculo de Folha de Pagamento", 
                                           0, 0, 
                                           new Font("Arial", Font.BOLD, 14), 
                                           accentColor),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setPreferredSize(new Dimension(0, 120));
        
        textAreaFolha = new JTextArea();
        textAreaFolha.setEditable(false);
        textAreaFolha.setFont(new Font("Arial", Font.PLAIN, 12));
        textAreaFolha.setMargin(new Insets(5, 5, 5, 5));
        textAreaFolha.setBackground(new Color(245, 245, 245));
        
        JScrollPane scrollPane = new JScrollPane(textAreaFolha);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 1, 5, 10));
        panel.setBackground(secondaryColor);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnCalcularFolha = new JButton("Calcular Folha");
        JButton btnAumentarSalario = new JButton("Aumentar Salário");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnCarregar = new JButton("Carregar");
        JButton btnSobre = new JButton("Sobre");
        
        panel.add(btnAdicionar);
        panel.add(btnCalcularFolha);
        panel.add(btnAumentarSalario);
        panel.add(btnSalvar);
        panel.add(btnLimpar);
        panel.add(btnCarregar);
        panel.add(btnSobre);
        
        // Adicionar listeners
        btnAdicionar.addActionListener(e -> adicionarFuncionario());
        btnCalcularFolha.addActionListener(e -> calcularFolhaTotal());
        btnAumentarSalario.addActionListener(e -> aumentarSalario());
        btnSalvar.addActionListener(e -> salvarFuncionarios());
        btnLimpar.addActionListener(e -> limparCampos());
        btnCarregar.addActionListener(e -> {
            loadFuncionariosFromFile();
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
                    BorderFactory.createEmptyBorder(8, 5, 8, 5)
                ));
                
                if (button == btnAdicionar) {
                    button.setBackground(primaryColor);
                    button.setForeground(Color.BLACK);
                } else if (button == btnCalcularFolha) {
                    button.setBackground(primaryColor);
                    button.setForeground(Color.BLACK);
                } else {
                    button.setBackground(Color.BLACK);
                    button.setForeground(primaryColor);
                }
                
                // Efeito hover nos botões
                button.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent evt) {
                        button.setBackground(primaryColor);
                        button.setForeground(Color.WHITE);
                    }
                    
                    public void mouseExited(MouseEvent evt) {
                        if (button == btnAdicionar) {
                            button.setBackground(highlightColor);
                        } else if (button == btnCalcularFolha) {
                            button.setBackground(primaryColor);
                        } else {
                            button.setBackground(Color.WHITE);
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
    
    private void adicionarFuncionario() {
        try {
            String tipo = (String) comboBoxTipo.getSelectedItem();
            String nome = txtNome.getText().trim();
            double salario = Double.parseDouble(txtSalario.getText().trim());

            if (nome.isEmpty()) {
                showError("Nome não pode estar vazio!");
                return;
            }
            
            if (salario <= 0) {
                showError("Salário deve ser um valor positivo!");
                return;
            }

            Funcionario funcionario;
            if (tipo.equals("Gerente")) {
                String departamento = txtDepartamento.getText().trim();
                if (departamento.isEmpty()) {
                    showError("Departamento não pode estar vazio!");
                    return;
                }
                funcionario = new Gerente(nome, salario, departamento);
            } else {
                funcionario = new Funcionario(nome, salario);
            }

            funcionarios.add(funcionario);
            updateTable();
            limparCampos();
            showSuccess(tipo + " adicionado(a) com sucesso!");

        } catch (NumberFormatException ex) {
            showError("Salário deve ser um número válido!");
        }
    }
    
    private void aumentarSalario() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            showError("Selecione um funcionário na tabela!");
            return;
        }
        
        try {
            double percentual = Double.parseDouble(txtPercentual.getText().trim());
            if (percentual <= 0) {
                showError("Percentual deve ser um valor positivo!");
                return;
            }
            
            Funcionario funcionario = funcionarios.get(selectedRow);
            if (funcionario instanceof Gerente) {
                ((Gerente) funcionario).aumentarSalario(percentual);
                showSuccess("Salário aumentado em " + percentual + "% para o gerente " + funcionario.getNome());
            } else {
                showError("Apenas gerentes podem receber aumento por esta interface!");
            }
            
            updateTable();
        } catch (NumberFormatException ex) {
            showError("Percentual deve ser um número válido!");
        }
    }
    
    private void calcularFolhaTotal() {
        if (funcionarios.isEmpty()) {
            showInfo("Nenhum funcionário cadastrado!");
            return;
        }

        textAreaFolha.setText("Calculando folha de pagamento...\n\n");
        double total = calcularFolhaRecursivo(0);
        
        textAreaFolha.append("\n==========================================\n");
        textAreaFolha.append("TOTAL DA FOLHA: R$ " + String.format("%.2f", total) + "\n");
        textAreaFolha.append("==========================================");
    }
    
    private double calcularFolhaRecursivo(int index) {
        if (index >= funcionarios.size()) {
            return 0;
        }

        Funcionario funcionario = funcionarios.get(index);
        double salario = funcionario.getSalario();
        
        textAreaFolha.append("• " + funcionario.getNome() + ": R$ " + 
                            String.format("%.2f", salario) + "\n");

        // Chamada recursiva
        return salario + calcularFolhaRecursivo(index + 1);
    }

    private void salvarFuncionarios() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("funcionarios.dat"))) {
            oos.writeObject(funcionarios);
            showSuccess("Dados salvos com sucesso!");
        } catch (IOException ex) {
            showError("Erro ao salvar dados: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFuncionariosFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("funcionarios.dat"))) {
            funcionarios = (List<Funcionario>) ois.readObject();
            showInfo(funcionarios.size() + " registros carregados do arquivo.");
        } catch (FileNotFoundException ex) {
            // Arquivo não existe ainda, é normal
        } catch (IOException | ClassNotFoundException ex) {
            showError("Erro ao carregar dados: " + ex.getMessage());
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Funcionario funcionario : funcionarios) {
            String tipo = funcionario instanceof Gerente ? "Gerente" : "Funcionário";
            String departamento = funcionario instanceof Gerente ? 
                                 ((Gerente) funcionario).getDepartamento() : "N/A";
            
            tableModel.addRow(new Object[]{
                tipo,
                funcionario.getNome(),
                funcionario.getSalario(),
                departamento
            });
        }
        
        updateStats();
    }
    
    private void updateStats() {
        int funcionariosCount = 0, gerentesCount = 0;
        double totalSalarios = 0;
        
        for (Funcionario funcionario : funcionarios) {
            if (funcionario instanceof Gerente) {
                gerentesCount++;
            } else {
                funcionariosCount++;
            }
            totalSalarios += funcionario.getSalario();
        }
        
        String stats = String.format("Total: %d | Funcionários: %d | Gerentes: %d | Folha: R$ %.2f", 
                                    funcionarios.size(), funcionariosCount, gerentesCount, totalSalarios);
        
        // Atualizar barra de status se desejar
    }

    private void limparCampos() {
        txtNome.setText("");
        txtSalario.setText("");
        txtDepartamento.setText("");
        txtPercentual.setText("");
        txtNome.requestFocus();
    }
    
    private void mostrarSobre() {
        JOptionPane.showMessageDialog(this, 
            "Sistema de Gestão de Funcionários\n\n" +
            "Exercício 4 - Funcionários e Gerentes (Salário + Recursividade)\n" +
            "• Cadastre funcionários e gerentes\n" +
            "• Aumente salários de gerentes\n" +
            "• Calcule folha total com recursividade\n" +
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
    
    // Classe interna para renderização de valores monetários
    class MonetaryCellRenderer extends DefaultTableCellRenderer {
        public MonetaryCellRenderer() {
            setHorizontalAlignment(JLabel.RIGHT);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (value instanceof Double) {
                setText("R$ " + String.format("%.2f", (Double) value));
            }
            
            return this;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            FuncionarioGUI gui = new FuncionarioGUI();
            gui.setVisible(true);
        });
    }
}