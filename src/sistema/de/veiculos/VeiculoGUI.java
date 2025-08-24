package sistema.de.veiculos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoGUI extends JFrame {
    private List<Veiculo> veiculos = new ArrayList<>();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtModelo, txtMarca, txtAno, txtCombustivel, txtMarchas;
    private JComboBox<String> comboBoxTipo;
    private JTextArea textAreaMovimentos;
    
    private Color primaryColor = new Color(41, 128, 185);    // Azul
    private Color secondaryColor = new Color(236, 240, 241); // Cinza claro
    private Color accentColor = new Color(231, 76, 60);      // Vermelho

    public VeiculoGUI() {
        setTitle("Sistema de Cadastro de Veículos");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        applyStyles();
        loadVeiculosFromFile();
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
        
        // Painel de exibição de movimentos
        JPanel movementPanel = createMovementPanel();
        add(movementPanel, BorderLayout.SOUTH);
        
        // Painel de botões
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.EAST);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(primaryColor);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        
        JLabel title = new JLabel("Sistema de Cadastro de Veículos");
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
        JLabel formTitle = new JLabel("Cadastrar Novo Veículo");
        formTitle.setFont(new Font("Arial", Font.BOLD, 16));
        formTitle.setForeground(primaryColor);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(formTitle, gbc);
        
        // Campo Tipo
        gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        comboBoxTipo = new JComboBox<>(new String[]{"Carro", "Bicicleta"});
        comboBoxTipo.addActionListener(e -> toggleFields());
        panel.add(comboBoxTipo, gbc);
        
        // Campo Marca
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Marca:"), gbc);
        gbc.gridx = 1;
        txtMarca = new JTextField(15);
        panel.add(txtMarca, gbc);
        
        // Campo Modelo
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Modelo:"), gbc);
        gbc.gridx = 1;
        txtModelo = new JTextField(15);
        panel.add(txtModelo, gbc);
        
        // Campo Ano
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Ano:"), gbc);
        gbc.gridx = 1;
        txtAno = new JTextField(15);
        panel.add(txtAno, gbc);
        
        // Campo Combustível (apenas para carro)
        gbc.gridx = 0; gbc.gridy = 5;
        JLabel lblCombustivel = new JLabel("Combustível:");
        panel.add(lblCombustivel, gbc);
        gbc.gridx = 1;
        txtCombustivel = new JTextField(15);
        panel.add(txtCombustivel, gbc);
        
        // Campo Marchas (apenas para bicicleta)
        gbc.gridx = 0; gbc.gridy = 6;
        JLabel lblMarchas = new JLabel("Marchas:");
        panel.add(lblMarchas, gbc);
        gbc.gridx = 1;
        txtMarchas = new JTextField(15);
        panel.add(txtMarchas, gbc);
        
        // Inicialmente esconder campos específicos
        lblCombustivel.setVisible(false);
        txtCombustivel.setVisible(false);
        lblMarchas.setVisible(false);
        txtMarchas.setVisible(false);
        
        return panel;
    }
    
    private void toggleFields() {
        String tipo = (String) comboBoxTipo.getSelectedItem();
        boolean isCarro = "Carro".equals(tipo);
        
        // Encontrar os componentes pelo tipo
        for (Component comp : ((JPanel) ((JPanel) getContentPane().getComponent(1)).getComponent(0)).getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if ("Combustível:".equals(label.getText())) {
                    label.setVisible(isCarro);
                } else if ("Marchas:".equals(label.getText())) {
                    label.setVisible(!isCarro);
                }
            }
        }
        
        for (Component comp : ((JPanel) ((JPanel) getContentPane().getComponent(1)).getComponent(0)).getComponents()) {
            if (comp instanceof JTextField) {
                JTextField field = (JTextField) comp;
                if (field == txtCombustivel) {
                    field.setVisible(isCarro);
                } else if (field == txtMarchas) {
                    field.setVisible(!isCarro);
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
                                           "Veículos Cadastrados", 
                                           0, 0, 
                                           new Font("Arial", Font.BOLD, 14), 
                                           primaryColor),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        tableModel = new DefaultTableModel(new Object[]{"Tipo", "Marca", "Modelo", "Ano", "Detalhe"}, 0) {
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
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createMovementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(accentColor), 
                                           "Movimentos dos Veículos", 
                                           0, 0, 
                                           new Font("Arial", Font.BOLD, 14), 
                                           accentColor),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setPreferredSize(new Dimension(0, 150));
        
        textAreaMovimentos = new JTextArea();
        textAreaMovimentos.setEditable(false);
        textAreaMovimentos.setFont(new Font("Arial", Font.PLAIN, 12));
        textAreaMovimentos.setMargin(new Insets(5, 5, 5, 5));
        
        JScrollPane scrollPane = new JScrollPane(textAreaMovimentos);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 5, 10));
        panel.setBackground(secondaryColor);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnMostrarTodos = new JButton("Mostrar Todos");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnCarregar = new JButton("Carregar");
        JButton btnSobre = new JButton("Sobre");
        
        panel.add(btnAdicionar);
        panel.add(btnMostrarTodos);
        panel.add(btnSalvar);
        panel.add(btnLimpar);
        panel.add(btnCarregar);
        panel.add(btnSobre);
        
        // Adicionar listeners
        btnAdicionar.addActionListener(e -> adicionarVeiculo());
        btnMostrarTodos.addActionListener(e -> mostrarMovimentosRecursivamente());
        btnSalvar.addActionListener(e -> salvarVeiculos());
        btnLimpar.addActionListener(e -> limparCampos());
        btnCarregar.addActionListener(e -> {
            loadVeiculosFromFile();
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
                    button.setBackground(accentColor);
                    button.setForeground(Color.WHITE);
                } else {
                    button.setBackground(Color.WHITE);
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
                            button.setBackground(accentColor);
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
    
    private void adicionarVeiculo() {
        try {
            String tipo = (String) comboBoxTipo.getSelectedItem();
            String marca = txtMarca.getText().trim();
            String modelo = txtModelo.getText().trim();
            int ano = Integer.parseInt(txtAno.getText().trim());

            if (marca.isEmpty() || modelo.isEmpty()) {
                showError("Marca e modelo não podem estar vazios!");
                return;
            }
            
            if (ano <= 0) {
                showError("Ano deve ser um número positivo!");
                return;
            }

            Veiculo veiculo;
            if (tipo.equals("Carro")) {
                String combustivel = txtCombustivel.getText().trim();
                if (combustivel.isEmpty()) {
                    showError("Tipo de combustível não pode estar vazio!");
                    return;
                }
                veiculo = new Carro(modelo, marca, ano, combustivel);
            } else {
                int marchas = Integer.parseInt(txtMarchas.getText().trim());
                if (marchas <= 0) {
                    showError("Número de marchas deve ser positivo!");
                    return;
                }
                veiculo = new Bicicleta(modelo, marca, ano, marchas);
            }

            veiculos.add(veiculo);
            updateTable();
            limparCampos();
            showSuccess("Veículo adicionado com sucesso!");

        } catch (NumberFormatException ex) {
            showError("Ano e marchas devem ser números válidos!");
        }
    }

    private void mostrarMovimentosRecursivamente() {
        if (veiculos.isEmpty()) {
            showInfo("Nenhum veículo cadastrado!");
            return;
        }

        textAreaMovimentos.setText("Movimentos dos Veículos:\n\n");
        mostrarMovimentosRecursivo(0);
    }

    private void mostrarMovimentosRecursivo(int index) {
        if (index >= veiculos.size()) {
            return;
        }

        Veiculo veiculo = veiculos.get(index);
        textAreaMovimentos.append("• " + veiculo.mover() + "\n");

        // Chamada recursiva
        mostrarMovimentosRecursivo(index + 1);
    }

    private void salvarVeiculos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("veiculos.dat"))) {
            oos.writeObject(veiculos);
            showSuccess("Veículos salvos com sucesso!");
        } catch (IOException ex) {
            showError("Erro ao salvar veículos: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadVeiculosFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("veiculos.dat"))) {
            veiculos = (List<Veiculo>) ois.readObject();
            showInfo(veiculos.size() + " veículos carregados do arquivo.");
        } catch (FileNotFoundException ex) {
            // Arquivo não existe ainda, é normal
        } catch (IOException | ClassNotFoundException ex) {
            showError("Erro ao carregar veículos: " + ex.getMessage());
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Veiculo veiculo : veiculos) {
            String detalhe = "";
            if (veiculo instanceof Carro) {
                detalhe = "Combustível: " + ((Carro) veiculo).getTipoCombustivel();
            } else if (veiculo instanceof Bicicleta) {
                detalhe = "Marchas: " + ((Bicicleta) veiculo).getNumeroMarchas();
            }
            
            tableModel.addRow(new Object[]{
                veiculo.getClass().getSimpleName(),
                veiculo.getMarca(),
                veiculo.getModelo(),
                veiculo.getAno(),
                detalhe
            });
        }
        
        updateStats();
    }
    
    private void updateStats() {
        int carros = 0, bicicletas = 0;
        for (Veiculo veiculo : veiculos) {
            if (veiculo instanceof Carro) carros++;
            else if (veiculo instanceof Bicicleta) bicicletas++;
        }
        
        String stats = String.format("Total: %d veículos | Carros: %d | Bicicletas: %d", 
                                    veiculos.size(), carros, bicicletas);
        
        // Atualizar barra de status se desejar
    }

    private void limparCampos() {
        txtMarca.setText("");
        txtModelo.setText("");
        txtAno.setText("");
        txtCombustivel.setText("");
        txtMarchas.setText("");
        txtMarca.requestFocus();
    }
    
    private void mostrarSobre() {
        JOptionPane.showMessageDialog(this, 
            "Sistema de Cadastro de Veículos\n\n" +
            "Exercício 2 - Sistema de Veículos com GUI e Recursividade\n" +
            "• Cadastre carros e bicicletas\n" +
            "• Visualize os movimentos com recursividade\n" +
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
            VeiculoGUI gui = new VeiculoGUI();
            gui.setVisible(true);
        });
    }
}