package cadastro.de.animais;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalGUI extends JFrame {
    private List<Animal> animais = new ArrayList<>();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNome, txtIdade;
    private JComboBox<String> comboBoxTipo;
    private JButton btnAdicionar, btnListarSons, btnSalvar, btnLimpar, btnCarregar;
    private Color primaryColor = new Color(65, 105, 225); // RoyalBlue
    private Color secondaryColor = new Color(240, 248, 255); // AliceBlue
    private Color accentColor = new Color(255, 140, 0); // DarkOrange

    public AnimalGUI() {
        setTitle("Sistema de Cadastro de Animais");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        applyStyles();
        loadAnimaisFromFile();
        updateTable();
    }

    private void initComponents() {
        // Configurar layout principal
        setLayout(new BorderLayout(10, 10));
        
        // Painel de cabeçalho
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Painel de formulário
        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.WEST);
        
        // Painel de tabela
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(primaryColor);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        
        JLabel title = new JLabel("Sistema de Cadastro de Animais");
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
        panel.setPreferredSize(new Dimension(300, 0));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Título do formulário
        JLabel formTitle = new JLabel("Cadastrar Novo Animal");
        formTitle.setFont(new Font("Arial", Font.BOLD, 16));
        formTitle.setForeground(primaryColor);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(formTitle, gbc);
        
        // Campo Nome
        gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(15);
        panel.add(txtNome, gbc);
        
        // Campo Idade
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Idade:"), gbc);
        gbc.gridx = 1;
        txtIdade = new JTextField(15);
        panel.add(txtIdade, gbc);
        
        // Campo Tipo
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        comboBoxTipo = new JComboBox<>(new String[]{"Cachorro", "Gato"});
        panel.add(comboBoxTipo, gbc);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(primaryColor), 
                                           "Animais Cadastrados", 
                                           0, 0, 
                                           new Font("Arial", Font.BOLD, 14), 
                                           primaryColor),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        tableModel = new DefaultTableModel(new Object[]{"Tipo", "Nome", "Idade", "Som"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna a tabela não editável
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
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(secondaryColor);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnAdicionar = new JButton("Adicionar Animal");
        btnListarSons = new JButton("Listar Sons");
        btnSalvar = new JButton("Salvar");
        btnLimpar = new JButton("Limpar");
        btnCarregar = new JButton("Carregar");
        
        panel.add(btnAdicionar);
        panel.add(btnListarSons);
        panel.add(btnSalvar);
        panel.add(btnLimpar);
        panel.add(btnCarregar);
        
        // Adicionar listeners
        btnAdicionar.addActionListener(e -> adicionarAnimal());
        btnListarSons.addActionListener(e -> listarSonsRecursivamente());
        btnSalvar.addActionListener(e -> salvarAnimais());
        btnLimpar.addActionListener(e -> limparCampos());
        btnCarregar.addActionListener(e -> {
            loadAnimaisFromFile();
            updateTable();
        });
        
        return panel;
    }
    
    private void applyStyles() {
        // Estilizar botões
        for (JButton button : new JButton[]{btnAdicionar, btnListarSons, btnSalvar, btnLimpar, btnCarregar}) {
            if (button != null) {
                button.setFont(new Font("Arial", Font.BOLD, 12));
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(primaryColor, 1),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
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
    }
    
    private void adicionarAnimal() {
        try {
            String nome = txtNome.getText().trim();
            int idade = Integer.parseInt(txtIdade.getText().trim());
            String tipo = (String) comboBoxTipo.getSelectedItem();

            if (nome.isEmpty()) {
                showError("Nome não pode estar vazio!");
                return;
            }
            
            if (idade <= 0) {
                showError("Idade deve ser um número positivo!");
                return;
            }

            Animal animal;
            if (tipo.equals("Cachorro")) {
                animal = new Cachorro(nome, idade);
            } else {
                animal = new Gato(nome, idade);
            }

            animais.add(animal);
            updateTable();
            limparCampos();
            showSuccess("Animal adicionado com sucesso!");

        } catch (NumberFormatException ex) {
            showError("Idade deve ser um número válido!");
        }
    }

    private void listarSonsRecursivamente() {
        if (animais.isEmpty()) {
            showInfo("Nenhum animal cadastrado!");
            return;
        }

        JDialog dialog = new JDialog(this, "Sons dos Animais", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setMargin(new Insets(10, 10, 10, 10));
        
        StringBuilder sb = new StringBuilder("Sons dos Animais:\n\n");
        listarSonsRecursivo(0, sb);
        textArea.setText(sb.toString());
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        dialog.add(scrollPane, BorderLayout.CENTER);
        
        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.setVisible(true);
    }

    private void listarSonsRecursivo(int index, StringBuilder sb) {
        if (index >= animais.size()) {
            return;
        }

        Animal animal = animais.get(index);
        sb.append("• ").append(animal.getNome())
          .append(" (").append(animal.getClass().getSimpleName()).append("): ")
          .append(animal.fazerSom()).append("\n");

        // Chamada recursiva
        listarSonsRecursivo(index + 1, sb);
    }

    private void salvarAnimais() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("animais.dat"))) {
            oos.writeObject(animais);
            showSuccess("Animais salvos com sucesso!");
        } catch (IOException ex) {
            showError("Erro ao salvar animais: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadAnimaisFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("animais.dat"))) {
            animais = (List<Animal>) ois.readObject();
            showInfo(animais.size() + " animais carregados do arquivo.");
        } catch (FileNotFoundException ex) {
            // Arquivo não existe ainda, é normal
        } catch (IOException | ClassNotFoundException ex) {
            showError("Erro ao carregar animais: " + ex.getMessage());
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Animal animal : animais) {
            tableModel.addRow(new Object[]{
                animal.getClass().getSimpleName(),
                animal.getNome(),
                animal.getIdade(),
                animal.fazerSom()
            });
        }
        
        // Atualizar estatísticas
        updateStats();
    }
    
    private void updateStats() {
        int cachorros = 0, gatos = 0;
        for (Animal animal : animais) {
            if (animal instanceof Cachorro) cachorros++;
            else if (animal instanceof Gato) gatos++;
        }
        
        String stats = String.format("Total: %d animais | Cachorros: %d | Gatos: %d", 
                                    animais.size(), cachorros, gatos);
        
        // Adicionar uma barra de status se desejar
    }

    private void limparCampos() {
        txtNome.setText("");
        txtIdade.setText("");
        txtNome.requestFocus();
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
    
    private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            // Retornar um ícone padrão se o personalizado não for encontrado
            return new ImageIcon();
        }
    }

    public static void main(String[] args) {
        try {
            // Usar o look and feel do sistema para uma aparência mais nativa
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            AnimalGUI gui = new AnimalGUI();
            gui.setVisible(true);
        });
    }
}