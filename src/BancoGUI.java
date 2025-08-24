import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class BancoGUI extends JFrame {
    private List<Conta> contas = new ArrayList<>();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNumeroConta, txtTitular, txtSaldoInicial, txtValorOperacao;
    private JComboBox<String> comboBoxTipo;
    private JTextArea textAreaResultados;
    
    // Paleta de cores azul marinho e dourado
    private Color primaryColor = new Color(0, 51, 102);       // Azul marinho escuro
    private Color secondaryColor = new Color(240, 248, 255);  // Azul claro
    private Color accentColor = new Color(255, 215, 0);       // Dourado
    private Color highlightColor = new Color(25, 25, 112);    // MidnightBlue
    private Color successColor = new Color(34, 139, 34);      // ForestGreen
    private Color warningColor = new Color(205, 133, 63);     // Peru

    public BancoGUI() {
        setTitle("🏦 Sistema Bancário Recursivo");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("bank_icon.png").getImage()); // Ícone opcional

        initComponents();
        applyStyles();
        loadContasFromFile();
        updateTable();
    }

    private void initComponents() {
        // Configurar layout principal
        setLayout(new BorderLayout(15, 15));
        
        // Painel de cabeçalho
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Painel principal (formulário + tabela)
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Painel de formulário
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel);
        
        // Painel de tabela
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Painel de operações
        JPanel operacoesPanel = createOperacoesPanel();
        add(operacoesPanel, BorderLayout.SOUTH);
        
        // Painel de botões
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.EAST);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(primaryColor);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("🏦 Sistema Bancário Recursivo", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        
        JLabel subtitle = new JLabel("Gerencie suas contas correntes e poupanças", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(new Color(200, 200, 200));
        
        panel.add(title, BorderLayout.CENTER);
        panel.add(subtitle, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(secondaryColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(primaryColor, 3, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Título do formulário
        JLabel formTitle = new JLabel("📝 Nova Conta Bancária");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        formTitle.setForeground(primaryColor);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(formTitle, gbc);
        
        // Campo Tipo
        gbc.gridy = 1; gbc.gridwidth = 1;
        JLabel lblTipo = new JLabel("🏷️ Tipo de Conta:");
        lblTipo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(lblTipo, gbc);
        gbc.gridx = 1;
        comboBoxTipo = new JComboBox<>(new String[]{"Conta Corrente", "Conta Poupança"});
        comboBoxTipo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(comboBoxTipo, gbc);
        
        // Campo Número da Conta
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblNumero = new JLabel("🔢 Número da Conta:");
        lblNumero.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(lblNumero, gbc);
        gbc.gridx = 1;
        txtNumeroConta = new JTextField(15);
        txtNumeroConta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(txtNumeroConta, gbc);
        
        // Campo Titular
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblTitular = new JLabel("👤 Titular:");
        lblTitular.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(lblTitular, gbc);
        gbc.gridx = 1;
        txtTitular = new JTextField(15);
        txtTitular.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(txtTitular, gbc);
        
        // Campo Saldo Inicial
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblSaldo = new JLabel("💰 Saldo Inicial (R$):");
        lblSaldo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(lblSaldo, gbc);
        gbc.gridx = 1;
        txtSaldoInicial = new JTextField(15);
        txtSaldoInicial.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(txtSaldoInicial, gbc);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(accentColor, 2), 
                "📊 Contas Bancárias Cadastradas", 
                0, 0, 
                new Font("Segoe UI", Font.BOLD, 18), 
                accentColor
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        tableModel = new DefaultTableModel(new Object[]{"Tipo", "Número", "Titular", "Saldo (R$)", "Taxa (R$)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(highlightColor);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(new Color(220, 220, 220));
        
        // Renderers personalizados
        table.getColumnModel().getColumn(3).setCellRenderer((TableCellRenderer) new MonetaryCellRenderer());
        table.getColumnModel().getColumn(4).setCellRenderer((TableCellRenderer) new MonetaryCellRenderer());
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(accentColor, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createOperacoesPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(secondaryColor);
        
        // Painel de operações
        JPanel operacoes = new JPanel(new GridBagLayout());
        operacoes.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(warningColor, 2), 
            "⚡ Operações Bancárias", 
            0, 0, 
            new Font("Segoe UI", Font.BOLD, 16), 
            warningColor
        ));
        operacoes.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel lblValor = new JLabel("💵 Valor (R$):");
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        operacoes.add(lblValor, gbc);
        
        txtValorOperacao = new JTextField(10);
        txtValorOperacao.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 0;
        operacoes.add(txtValorOperacao, gbc);
        
        JButton btnDepositar = createOperationButton("📥 Depositar", successColor);
        JButton btnSacar = createOperationButton("📤 Sacar", new Color(178, 34, 34));
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        operacoes.add(btnDepositar, gbc);
        
        gbc.gridy = 2;
        operacoes.add(btnSacar, gbc);
        
        // Painel de resultados
        JPanel resultados = new JPanel(new BorderLayout());
        resultados.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(successColor, 2), 
            "📈 Resultados e Taxas", 
            0, 0, 
            new Font("Segoe UI", Font.BOLD, 16), 
            successColor
        ));
        resultados.setBackground(Color.WHITE);
        
        textAreaResultados = new JTextArea();
        textAreaResultados.setEditable(false);
        textAreaResultados.setFont(new Font("Consolas", Font.PLAIN, 12));
        textAreaResultados.setMargin(new Insets(10, 10, 10, 10));
        textAreaResultados.setBackground(new Color(245, 245, 245));
        
        JScrollPane scrollPane = new JScrollPane(textAreaResultados);
        resultados.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(operacoes);
        panel.add(resultados);
        
        // Listeners dos botões de operação
        btnDepositar.addActionListener(e -> realizarDeposito());
        btnSacar.addActionListener(e -> realizarSaque());
        
        return panel;
    }
    
    private JButton createOperationButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        
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
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 12));
        panel.setBackground(secondaryColor);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JButton btnAdicionar = createStyledButton("➕ Adicionar Conta", accentColor);
        JButton btnCalcularTaxas = createStyledButton("🧮 Calcular Taxas", primaryColor);
        JButton btnSalvar = createStyledButton("💾 Salvar Dados", successColor);
        JButton btnLimpar = createStyledButton("🗑️ Limpar", new Color(139, 0, 0));
        JButton btnCarregar = createStyledButton("📂 Carregar", new Color(70, 130, 180));
        JButton btnInfo = createStyledButton("ℹ️ Info Conta", highlightColor);
        JButton btnSobre = createStyledButton("❓ Sobre", new Color(105, 105, 105));
        
        panel.add(btnAdicionar);
        panel.add(btnCalcularTaxas);
        panel.add(btnSalvar);
        panel.add(btnLimpar);
        panel.add(btnCarregar);
        panel.add(btnInfo);
        panel.add(btnSobre);
        
        // Adicionar listeners
        btnAdicionar.addActionListener(e -> adicionarConta());
        btnCalcularTaxas.addActionListener(e -> calcularTaxasRecursivamente());
        btnSalvar.addActionListener(e -> salvarContas());
        btnLimpar.addActionListener(e -> limparCampos());
        btnCarregar.addActionListener(e -> {
            loadContasFromFile();
            updateTable();
        });
        btnInfo.addActionListener(e -> mostrarInfoConta());
        btnSobre.addActionListener(e -> mostrarSobre());
        
        return panel;
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(12, 8, 12, 8)
        ));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        
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
        
        // Aplicar estilo consistente aos labels
        for (Component comp : getContentPane().getComponents()) {
            if (comp instanceof JPanel) {
                for (Component subComp : ((JPanel) comp).getComponents()) {
                    if (subComp instanceof JLabel) {
                        JLabel label = (JLabel) subComp;
                        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    }
                }
            }
        }
    }
    
    private void adicionarConta() {
        try {
            String tipo = (String) comboBoxTipo.getSelectedItem();
            String numero = txtNumeroConta.getText().trim();
            String titular = txtTitular.getText().trim();
            double saldo = Double.parseDouble(txtSaldoInicial.getText().trim());

            if (numero.isEmpty() || titular.isEmpty()) {
                showError("Número da conta e titular são obrigatórios!");
                return;
            }
            
            if (saldo < 0) {
                showError("Saldo inicial não pode ser negativo!");
                return;
            }

            Conta conta;
            if (tipo.equals("Conta Corrente")) {
                conta = new ContaCorrente(numero, titular, saldo);
            } else {
                conta = new ContaPoupanca(numero, titular, saldo);
            }

            contas.add(conta);
            updateTable();
            limparCampos();
            showSuccess("Conta " + tipo + " adicionada com sucesso! ✅");

        } catch (NumberFormatException ex) {
            showError("Saldo deve ser um valor numérico válido!");
        }
    }
    
    private void realizarDeposito() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            showError("Selecione uma conta na tabela!");
            return;
        }
        
        try {
            double valor = Double.parseDouble(txtValorOperacao.getText().trim());
            if (valor <= 0) {
                showError("Valor do depósito deve ser positivo!");
                return;
            }
            
            Conta conta = contas.get(selectedRow);
            if (conta instanceof ContaCorrente) {
                ((ContaCorrente) conta).depositar(valor);
            } else {
                ((ContaPoupanca) conta).depositar(valor);
            }
            
            updateTable();
            showSuccess("Depósito de R$ " + String.format("%,.2f", valor) + " realizado com sucesso! 💰");
            txtValorOperacao.setText("");
            
        } catch (NumberFormatException ex) {
            showError("Valor deve ser um número válido!");
        }
    }
    
    private void realizarSaque() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            showError("Selecione uma conta na tabela!");
            return;
        }
        
        try {
            double valor = Double.parseDouble(txtValorOperacao.getText().trim());
            if (valor <= 0) {
                showError("Valor do saque deve ser positivo!");
                return;
            }
            
            Conta conta = contas.get(selectedRow);
            boolean sucesso;
            
            if (conta instanceof ContaCorrente) {
                sucesso = ((ContaCorrente) conta).sacar(valor);
            } else {
                sucesso = ((ContaPoupanca) conta).sacar(valor);
            }
            
            if (sucesso) {
                updateTable();
                showSuccess("Saque de R$ " + String.format("%,.2f", valor) + " realizado com sucesso! 💸");
            } else {
                showError("Saldo insuficiente para o saque! ❌");
            }
            
            txtValorOperacao.setText("");
            
        } catch (NumberFormatException ex) {
            showError("Valor deve ser um número válido!");
        }
    }
    
    private void calcularTaxasRecursivamente() {
        if (contas.isEmpty()) {
            showInfo("Nenhuma conta cadastrada!");
            return;
        }

        textAreaResultados.setText("📋 CÁLCULO RECURSIVO DE TAXAS\n");
        textAreaResultados.append("=".repeat(60) + "\n\n");
        
        double totalTaxas = calcularTaxasRecursivo(0);
        
        textAreaResultados.append("\n" + "=".repeat(60) + "\n");
        textAreaResultados.append("💵 TOTAL DE TAXAS: R$ " + String.format("%,.2f", totalTaxas) + "\n");
        textAreaResultados.append("=".repeat(60));
    }
    
    private double calcularTaxasRecursivo(int index) {
        if (index >= contas.size()) {
            return 0;
        }

        Conta conta = contas.get(index);
        double taxa = conta.calcularTaxa();
        
        textAreaResultados.append("• " + conta.getTitular() + " (" + conta.getTipoConta() + ")\n");
        textAreaResultados.append("  Taxa: R$ " + String.format("%,.2f", taxa) + "\n\n");

        return taxa + calcularTaxasRecursivo(index + 1);
    }
    
    private void mostrarInfoConta() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            showError("Selecione uma conta na tabela!");
            return;
        }
        
        Conta conta = contas.get(selectedRow);
        String info = String.format(
            "🏦 Informações da Conta\n\n" +
            "📋 Tipo: %s\n" +
            "🔢 Número: %s\n" +
            "👤 Titular: %s\n" +
            "💰 Saldo: R$ %,.2f\n" +
            "💵 Taxa: R$ %,.2f\n" +
            "📊 Saldo após taxa: R$ %,.2f",
            conta.getTipoConta(),
            conta.getNumeroConta(),
            conta.getTitular(),
            conta.getSaldo(),
            conta.calcularTaxa(),
            conta.getSaldo() - conta.calcularTaxa()
        );
        
        textAreaResultados.setText(info);
    }

    private void salvarContas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("contas.dat"))) {
            oos.writeObject(contas);
            showSuccess("Dados das contas salvos com sucesso! 💾");
        } catch (IOException ex) {
            showError("Erro ao salvar dados: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadContasFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("contas.dat"))) {
            contas = (List<Conta>) ois.readObject();
            showInfo(contas.size() + " contas carregadas do arquivo. 📂");
        } catch (FileNotFoundException ex) {
            // Arquivo não existe ainda
        } catch (IOException | ClassNotFoundException ex) {
            showError("Erro ao carregar dados: " + ex.getMessage());
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Conta conta : contas) {
            tableModel.addRow(new Object[]{
                conta.getTipoConta(),
                conta.getNumeroConta(),
                conta.getTitular(),
                conta.getSaldo(),
                conta.calcularTaxa()
            });
        }
    }

    private void limparCampos() {
        txtNumeroConta.setText("");
        txtTitular.setText("");
        txtSaldoInicial.setText("");
        txtValorOperacao.setText("");
        txtNumeroConta.requestFocus();
    }
    
    private void mostrarSobre() {
        JOptionPane.showMessageDialog(this, 
            "🏦 Sistema Bancário Recursivo\n\n" +
            "Exercício 6 - Banco Recursivo com Contas\n" +
            "• Interface Conta com calcularTaxa()\n" +
            "• ContaCorrente (taxa fixa) e ContaPoupanca (taxa sobre saldo)\n" +
            "• Cálculo recursivo de taxas totais\n" +
            "• Operações de depósito e saque\n\n" +
            "🎨 Design Premium com:\n" +
            "• Paleta azul marinho e dourado\n" +
            "• Ícones e visual moderno\n" +
            "• Interface intuitiva e profissional\n" +
            "• Recursos bancários completos", 
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
            BancoGUI gui = new BancoGUI();
            gui.setVisible(true);
        });
    }
}