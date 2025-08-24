import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;

public class PagamentoGUI extends JFrame {
    private List<Pagavel> pagaveis = new ArrayList<>();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNome, txtSalario, txtValorHora, txtHorasTrabalhadas;
    private JComboBox<String> comboBoxTipo;
    private JTextArea textAreaResultados;
    
    // Paleta de cores sofisticada - Tons de verde e dourado
    private Color primaryColor = new Color(0, 100, 0);       // DarkGreen
    private Color secondaryColor = new Color(245, 245, 220); // Bege claro
    private Color accentColor = new Color(218, 165, 32);     // GoldenRod
    private Color highlightColor = new Color(85, 107, 47);   // DarkOliveGreen
    private Color textColor = new Color(50, 50, 50);         // Cinza escuro

    public PagamentoGUI() {
        setTitle("Sistema de Gestão de Pagamentos");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        applyStyles();
        loadPagaveisFromFile();
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
        
        // Painel de resultados
        JPanel resultadosPanel = createResultadosPanel();
        add(resultadosPanel, BorderLayout.SOUTH);
        
        // Painel de botões
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.EAST);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(primaryColor);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        JLabel title = new JLabel("💼 Sistema de Gestão de Pagamentos");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(Color.BLACK);
        
        panel.add(title);
        return panel;
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(secondaryColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(primaryColor, 2, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Título do formulário
        JLabel formTitle = new JLabel("📋 Cadastrar Pagável");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        formTitle.setForeground(primaryColor);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(formTitle, gbc);
        
        // Campo Tipo
        gbc.gridy = 1; gbc.gridwidth = 1;
        JLabel lblTipo = new JLabel("🏷️ Tipo:");
        lblTipo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(lblTipo, gbc);
        gbc.gridx = 1;
        comboBoxTipo = new JComboBox<>(new String[]{"Funcionário", "Freelancer"});
        comboBoxTipo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBoxTipo.addActionListener(e -> toggleFields());
        panel.add(comboBoxTipo, gbc);
        
        // Campo Nome
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblNome = new JLabel("👤 Nome:");
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(lblNome, gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(15);
        txtNome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(txtNome, gbc);
        
        // Campo Salário (apenas para funcionário)
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblSalario = new JLabel("💰 Salário Fixo (R$):");
        lblSalario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(lblSalario, gbc);
        gbc.gridx = 1;
        txtSalario = new JTextField(15);
        txtSalario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(txtSalario, gbc);
        
        // Campo Valor Hora (apenas para freelancer)
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblValorHora = new JLabel("⏰ Valor Hora (R$):");
        lblValorHora.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(lblValorHora, gbc);
        gbc.gridx = 1;
        txtValorHora = new JTextField(15);
        txtValorHora.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(txtValorHora, gbc);
        
        // Campo Horas Trabalhadas (apenas para freelancer)
        gbc.gridx = 0; gbc.gridy = 5;
        JLabel lblHoras = new JLabel("🕒 Horas Trabalhadas:");
        lblHoras.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(lblHoras, gbc);
        gbc.gridx = 1;
        txtHorasTrabalhadas = new JTextField(15);
        txtHorasTrabalhadas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(txtHorasTrabalhadas, gbc);
        
        // Inicialmente esconder campos específicos do freelancer
        lblValorHora.setVisible(false);
        txtValorHora.setVisible(false);
        lblHoras.setVisible(false);
        txtHorasTrabalhadas.setVisible(false);
        
        return panel;
    }
    
    private void toggleFields() {
        String tipo = (String) comboBoxTipo.getSelectedItem();
        boolean isFuncionario = "Funcionário".equals(tipo);
        
        // Encontrar e atualizar os componentes
        for (Component comp : ((JPanel) ((JPanel) getContentPane().getComponent(1)).getComponent(0)).getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if ("💰 Salário Fixo (R$):".equals(label.getText())) {
                    label.setVisible(isFuncionario);
                } else if ("⏰ Valor Hora (R$):".equals(label.getText()) || 
                          "🕒 Horas Trabalhadas:".equals(label.getText())) {
                    label.setVisible(!isFuncionario);
                }
            }
        }
        
        for (Component comp : ((JPanel) ((JPanel) getContentPane().getComponent(1)).getComponent(0)).getComponents()) {
            if (comp instanceof JTextField) {
                JTextField field = (JTextField) comp;
                if (field == txtSalario) {
                    field.setVisible(isFuncionario);
                } else if (field == txtValorHora || field == txtHorasTrabalhadas) {
                    field.setVisible(!isFuncionario);
                }
            }
        }
        
        revalidate();
        repaint();
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(accentColor, 2), 
                "📊 Pagáveis Cadastrados", 
                0, 0, 
                new Font("Segoe UI", Font.BOLD, 16), 
                accentColor
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        tableModel = new DefaultTableModel(new Object[]{"Tipo", "Descrição", "Pagamento (R$)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(highlightColor);
        table.getTableHeader().setForeground(Color.black);
        table.setGridColor(new Color(200, 200, 200));
        
        // Renderer personalizado para valores monetários
        table.getColumnModel().getColumn(2).setCellRenderer(new MonetaryCellRenderer());
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(accentColor, 1));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createResultadosPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(highlightColor, 2), 
                "📈 Resultados dos Pagamentos", 
                0, 0, 
                new Font("Segoe UI", Font.BOLD, 16), 
                highlightColor
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setPreferredSize(new Dimension(0, 150));
        panel.setBackground(new Color(250, 250, 245));
        
        textAreaResultados = new JTextArea();
        textAreaResultados.setEditable(false);
        textAreaResultados.setFont(new Font("Consolas", Font.PLAIN, 13));
        textAreaResultados.setMargin(new Insets(10, 10, 10, 10));
        textAreaResultados.setBackground(new Color(253, 253, 250));
        textAreaResultados.setForeground(textColor);
        textAreaResultados.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        JScrollPane scrollPane = new JScrollPane(textAreaResultados);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 8, 12));
        panel.setBackground(secondaryColor);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JButton btnAdicionar = createStyledButton("➕ Adicionar", highlightColor);
        JButton btnCalcular = createStyledButton("🧮 Calcular Todos", highlightColor);
        JButton btnSalvar = createStyledButton("💾 Salvar", highlightColor);
        JButton btnLimpar = createStyledButton("🗑️ Limpar", highlightColor);
        JButton btnCarregar = createStyledButton("📂 Carregar",highlightColor);
        JButton btnSobre = createStyledButton("ℹ️ Sobre", highlightColor);
        
        panel.add(btnAdicionar);
        panel.add(btnCalcular);
        panel.add(btnSalvar);
        panel.add(btnLimpar);
        panel.add(btnCarregar);
        panel.add(btnSobre);
        
        // Adicionar listeners
        btnAdicionar.addActionListener(e -> adicionarPagavel());
        btnCalcular.addActionListener(e -> calcularTodosPagamentos());
        btnSalvar.addActionListener(e -> salvarPagaveis());
        btnLimpar.addActionListener(e -> limparCampos());
        btnCarregar.addActionListener(e -> {
            loadPagaveisFromFile();
            updateTable();
        });
        btnSobre.addActionListener(e -> mostrarSobre());
        
        return panel;
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        
        // Efeito hover
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            
            public void mouseExited(MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private void applyStyles() {
        getContentPane().setBackground(secondaryColor);
        
        // Aplicar estilo a todos os labels
        for (Component comp : getContentPane().getComponents()) {
            if (comp instanceof JPanel) {
                for (Component subComp : ((JPanel) comp).getComponents()) {
                    if (subComp instanceof JLabel) {
                        JLabel label = (JLabel) subComp;
                        if (!label.getText().contains("💰") && !label.getText().contains("⏰") &&
                            !label.getText().contains("🕒") && !label.getText().contains("👤") &&
                            !label.getText().contains("🏷️")) {
                            label.setForeground(textColor);
                            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                        }
                    }
                }
            }
        }
    }
    
    private void adicionarPagavel() {
        try {
            String tipo = (String) comboBoxTipo.getSelectedItem();
            String nome = txtNome.getText().trim();

            if (nome.isEmpty()) {
                showError("Nome não pode estar vazio!");
                return;
            }

            Pagavel pagavel;
            if (tipo.equals("Funcionário")) {
                double salario = Double.parseDouble(txtSalario.getText().trim());
                if (salario <= 0) {
                    showError("Salário deve ser positivo!");
                    return;
                }
                pagavel = new Funcionario(nome, salario);
            } else {
                double valorHora = Double.parseDouble(txtValorHora.getText().trim());
                int horas = Integer.parseInt(txtHorasTrabalhadas.getText().trim());
                if (valorHora <= 0 || horas <= 0) {
                    showError("Valor hora e horas devem ser positivos!");
                    return;
                }
                pagavel = new Freelancer(nome, valorHora, horas);
            }

            pagaveis.add(pagavel);
            updateTable();
            limparCampos();
            showSuccess(tipo + " adicionado(a) com sucesso!");

        } catch (NumberFormatException ex) {
            showError("Valores numéricos devem ser válidos!");
        }
    }
    
    private void calcularTodosPagamentos() {
        if (pagaveis.isEmpty()) {
            showInfo("Nenhum pagável cadastrado!");
            return;
        }

        textAreaResultados.setText("💵 CÁLCULO DE PAGAMENTOS\n");
        textAreaResultados.append("=".repeat(50) + "\n\n");
        
        double total = 0;
        for (Pagavel pagavel : pagaveis) {
            double pagamento = pagavel.calcularPagamento();
            total += pagamento;
            
            textAreaResultados.append("• " + pagavel.getDescricao() + "\n");
            textAreaResultados.append("  Pagamento: R$ " + String.format("%,.2f", pagamento) + "\n\n");
        }
        
        textAreaResultados.append("=".repeat(50) + "\n");
        textAreaResultados.append("💰 TOTAL: R$ " + String.format("%,.2f", total) + "\n");
        textAreaResultados.append("=".repeat(50));
    }

    private void salvarPagaveis() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("pagaveis.dat"))) {
            oos.writeObject(pagaveis);
            showSuccess("Dados salvos com sucesso! 💾");
        } catch (IOException ex) {
            showError("Erro ao salvar dados: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadPagaveisFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("pagaveis.dat"))) {
            pagaveis = (List<Pagavel>) ois.readObject();
            showInfo(pagaveis.size() + " registros carregados do arquivo. 📂");
        } catch (FileNotFoundException ex) {
            // Arquivo não existe ainda
        } catch (IOException | ClassNotFoundException ex) {
            showError("Erro ao carregar dados: " + ex.getMessage());
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Pagavel pagavel : pagaveis) {
            String tipo = pagavel instanceof Funcionario ? "Funcionário" : "Freelancer";
            
            tableModel.addRow(new Object[]{
                tipo,
                pagavel.getDescricao(),
                pagavel.calcularPagamento()
            });
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtSalario.setText("");
        txtValorHora.setText("");
        txtHorasTrabalhadas.setText("");
        txtNome.requestFocus();
    }
    
    private void mostrarSobre() {
        JOptionPane.showMessageDialog(this, 
            "💼 Sistema de Gestão de Pagamentos\n\n" +
            "Exercício 5 - Sistema de Pagamentos com Swing\n" +
            "• Interface Pagavel com calcularPagamento()\n" +
            "• Funcionário (salário fixo) e Freelancer (pagamento por hora)\n" +
            "• Cálculo de pagamentos usando polimorfismo\n" +
            "• Interface moderna com paleta de cores verde/dourado\n\n" +
            "🎨 Design melhorado com:\n" +
            "• Ícones e emojis para melhor UX\n" +
            "• Tipografia consistente\n" +
            "• Cores harmoniosas\n" +
            "• Efeitos hover nos botões", 
            "ℹ️ Sobre o Sistema", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, "❌ " + message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
    
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, "✅ " + message, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, "💡 " + message, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Renderer para valores monetários
    class MonetaryCellRenderer extends DefaultTableCellRenderer {
        public MonetaryCellRenderer() {
            setHorizontalAlignment(JLabel.RIGHT);
            setFont(new Font("Segoe UI", Font.BOLD, 14));
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (value instanceof Double) {
                setText("R$ " + String.format("%,.2f", (Double) value));
                setForeground(primaryColor);
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
            PagamentoGUI gui = new PagamentoGUI();
            gui.setVisible(true);
        });
    }
}