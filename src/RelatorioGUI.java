import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatorioGUI extends JFrame {
    // Dados de exemplo para demonstração
    private List<Object> objetos = new ArrayList<>();
    private JComboBox<String> comboBoxTipo;
    private JTextArea textAreaRelatorio;
    private JTable tableDados;
    private DefaultTableModel tableModel;
    
    // Paleta de cores profissional - Tons de azul e cinza
    private Color primaryColor = new Color(47, 84, 150);     // Azul profissional
    private Color secondaryColor = new Color(240, 245, 249); // Azul muito claro
    private Color accentColor = new Color(30, 144, 255);     // DodgerBlue
    private Color highlightColor = new Color(70, 130, 180);  // SteelBlue
    private Color successColor = new Color(46, 139, 87);     // SeaGreen
    private Color headerColor = new Color(220, 230, 240);    // Azul header

    public RelatorioGUI() {
        setTitle("📊 Sistema de Relatórios Automáticos");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Carregar dados de exemplo
        carregarDadosExemplo();
        
        initComponents();
        applyStyles();
        gerarRelatorioGeral();
    }

    private void carregarDadosExemplo() {
        // Dados de exemplo para demonstração
        objetos.add(new Animal("Rex", 3) {
            public String fazerSom() { return "Au Au!"; }
        });
        objetos.add(new Animal("Mimi", 2) {
            public String fazerSom() { return "Miau!"; }
        });
        
        objetos.add(new Pessoa("João Silva", 30));
        objetos.add(new Pessoa("Maria Santos", 25));
        objetos.add(new Aluno("Carlos Oliveira", 20, "2023001"));
        
        objetos.add(new Funcionario("Ana Costa", 3500.0));
        objetos.add(new Gerente("Pedro Alves", 6000.0, "TI"));
        
        objetos.add(new Carro("Civic", "Honda", 2022, "Gasolina"));
        objetos.add(new Bicicleta("Mountain", "Caloi", 2023, 21));
        
        objetos.add(new Funcionario("Mariana Lima", 2800.0));
        objetos.add(new Freelancer("Ricardo Souza", 50.0, 80));
    }

    private void initComponents() {
        // Configurar layout principal
        setLayout(new BorderLayout(15, 15));
        
        // Painel de cabeçalho
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Painel principal (controles + relatório)
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Painel de controles
        JPanel controlPanel = createControlPanel();
        mainPanel.add(controlPanel);
        
        // Painel de relatório
        JPanel reportPanel = createReportPanel();
        mainPanel.add(reportPanel);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Painel de tabela de dados
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(primaryColor);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        JLabel title = new JLabel("📊 Sistema de Relatórios Automáticos", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        
        JLabel subtitle = new JLabel("Gere relatórios personalizados por tipo de objeto", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(new Color(200, 220, 255));
        
        panel.add(title, BorderLayout.CENTER);
        panel.add(subtitle, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(secondaryColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(primaryColor, 3, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Painel de seleção
        JPanel selectionPanel = new JPanel(new GridBagLayout());
        selectionPanel.setBackground(secondaryColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel lblTipo = new JLabel("🎯 Selecione o Tipo de Objeto:");
        lblTipo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTipo.setForeground(primaryColor);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        selectionPanel.add(lblTipo, gbc);
        
        comboBoxTipo = new JComboBox<>(new String[]{
            "Todos os Tipos", "Animais", "Pessoas", "Alunos", 
            "Funcionários", "Gerentes", "Veículos", "Carros", 
            "Bicicletas", "Pagáveis"
        });
        comboBoxTipo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBoxTipo.setMaximumRowCount(10);
        gbc.gridy = 1;
        selectionPanel.add(comboBoxTipo, gbc);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 12));
        buttonPanel.setBackground(secondaryColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JButton btnGerar = createStyledButton("🔄 Gerar Relatório", Color.WHITE);
        JButton btnExportar = createStyledButton("💾 Exportar para TXT", Color.WHITE);
        JButton btnLimpar = createStyledButton("🗑️ Limpar Relatório", Color.WHITE);
        JButton btnEstatisticas = createStyledButton("📈 Estatísticas", Color.WHITE);
        JButton btnSobre = createStyledButton("ℹ️ Sobre", Color.WHITE);
        
        buttonPanel.add(btnGerar);
        buttonPanel.add(btnExportar);
        buttonPanel.add(btnLimpar);
        buttonPanel.add(btnEstatisticas);
        buttonPanel.add(btnSobre);
        
        // Adicionar listeners
        btnGerar.addActionListener(e -> gerarRelatorioPorTipo());
        btnExportar.addActionListener(e -> exportarRelatorio());
        btnLimpar.addActionListener(e -> textAreaRelatorio.setText(""));
        btnEstatisticas.addActionListener(e -> mostrarEstatisticas());
        btnSobre.addActionListener(e -> mostrarSobre());
        
        panel.add(selectionPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(accentColor, 2), 
                "📋 Relatório Gerado", 
                0, 0, 
                new Font("Segoe UI", Font.BOLD, 18), 
                accentColor
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setBackground(Color.black);
        
        textAreaRelatorio = new JTextArea();
        textAreaRelatorio.setEditable(false);
        textAreaRelatorio.setFont(new Font("Consolas", Font.PLAIN, 12));
        textAreaRelatorio.setMargin(new Insets(10, 10, 10, 10));
        textAreaRelatorio.setBackground(new Color(250, 250, 250));
        textAreaRelatorio.setForeground(new Color(50, 50, 50));
        textAreaRelatorio.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        JScrollPane scrollPane = new JScrollPane(textAreaRelatorio);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(highlightColor, 2), 
                "📊 Visualização dos Dados", 
                0, 0, 
                new Font("Segoe UI", Font.BOLD, 16), 
                highlightColor
            ),
            BorderFactory.createEmptyBorder(10, 15, 15, 15)
        ));
        panel.setPreferredSize(new Dimension(0, 200));
        
        tableModel = new DefaultTableModel(new Object[]{"Tipo", "Descrição", "Detalhes"}, 0);
        tableDados = new JTable(tableModel);
        tableDados.setRowHeight(25);
        tableDados.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tableDados.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tableDados.getTableHeader().setBackground(headerColor);
        tableDados.setGridColor(new Color(220, 220, 220));
        
        JScrollPane scrollPane = new JScrollPane(tableDados);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        button.setBackground(color);
        button.setForeground(Color.black);
        
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
    }
    
    private void gerarRelatorioPorTipo() {
        String tipoSelecionado = (String) comboBoxTipo.getSelectedItem();
        
        switch (tipoSelecionado) {
            case "Todos os Tipos":
                gerarRelatorioGeral();
                break;
            case "Animais":
                gerarRelatorioAnimais();
                break;
            case "Pessoas":
                gerarRelatorioPessoas();
                break;
            case "Alunos":
                gerarRelatorioAlunos();
                break;
            case "Funcionários":
                gerarRelatorioFuncionarios();
                break;
            case "Gerentes":
                gerarRelatorioGerentes();
                break;
            case "Veículos":
                gerarRelatorioVeiculos();
                break;
            case "Carros":
                gerarRelatorioCarros();
                break;
            case "Bicicletas":
                gerarRelatorioBicicletas();
                break;
            case "Pagáveis":
                gerarRelatorioPagaveis();
                break;
        }
        
        atualizarTabelaDados(tipoSelecionado);
    }
    
    private void gerarRelatorioGeral() {
        textAreaRelatorio.setText("📊 RELATÓRIO GERAL - TODOS OS OBJETOS\n");
        textAreaRelatorio.append("=".repeat(80) + "\n\n");
        
        // Agrupar por tipo usando recursividade
        Map<String, List<Object>> grupos = new HashMap<>();
        agruparPorTipoRecursivo(0, grupos);
        
        for (Map.Entry<String, List<Object>> entry : grupos.entrySet()) {
            textAreaRelatorio.append("📁 " + entry.getKey().toUpperCase() + " (" + entry.getValue().size() + " itens)\n");
            textAreaRelatorio.append("-".repeat(40) + "\n");
            
            for (Object obj : entry.getValue()) {
                textAreaRelatorio.append("• " + obj.toString() + "\n");
            }
            textAreaRelatorio.append("\n");
        }
        
        textAreaRelatorio.append("=".repeat(80) + "\n");
        textAreaRelatorio.append("📈 TOTAL: " + objetos.size() + " objetos encontrados\n");
    }
    
    private void agruparPorTipoRecursivo(int index, Map<String, List<Object>> grupos) {
        if (index >= objetos.size()) {
            return;
        }
        
        Object obj = objetos.get(index);
        String tipo = obj.getClass().getSimpleName();
        
        if (!grupos.containsKey(tipo)) {
            grupos.put(tipo, new ArrayList<>());
        }
        grupos.get(tipo).add(obj);
        
        // Chamada recursiva
        agruparPorTipoRecursivo(index + 1, grupos);
    }
    
    private void gerarRelatorioAnimais() {
        textAreaRelatorio.setText("🐾 RELATÓRIO DE ANIMAIS\n");
        textAreaRelatorio.append("=".repeat(80) + "\n\n");
        
        int count = 0;
        for (Object obj : objetos) {
            if (obj instanceof Animal) {
                Animal animal = (Animal) obj;
                textAreaRelatorio.append("• " + animal.toString() + " - Som: " + animal.fazerSom() + "\n");
                count++;
            }
        }
        
        textAreaRelatorio.append("\n" + "=".repeat(80) + "\n");
        textAreaRelatorio.append("📈 TOTAL: " + count + " animais encontrados\n");
    }
    
    private void gerarRelatorioPessoas() {
        textAreaRelatorio.setText("👥 RELATÓRIO DE PESSOAS\n");
        textAreaRelatorio.append("=".repeat(80) + "\n\n");
        
        int count = 0;
        for (Object obj : objetos) {
            if (obj instanceof Pessoa && !(obj instanceof Aluno)) {
                Pessoa pessoa = (Pessoa) obj;
                textAreaRelatorio.append("• " + pessoa.toString() + "\n");
                count++;
            }
        }
        
        textAreaRelatorio.append("\n" + "=".repeat(80) + "\n");
        textAreaRelatorio.append("📈 TOTAL: " + count + " pessoas encontradas\n");
    }
    
    private void gerarRelatorioAlunos() {
        textAreaRelatorio.setText("🎓 RELATÓRIO DE ALUNOS\n");
        textAreaRelatorio.append("=".repeat(80) + "\n\n");
        
        int count = 0;
        for (Object obj : objetos) {
            if (obj instanceof Aluno) {
                Aluno aluno = (Aluno) obj;
                textAreaRelatorio.append("• " + aluno.toString() + "\n");
                count++;
            }
        }
        
        textAreaRelatorio.append("\n" + "=".repeat(80) + "\n");
        textAreaRelatorio.append("📈 TOTAL: " + count + " alunos encontrados\n");
    }
    
    private void gerarRelatorioFuncionarios() {
        textAreaRelatorio.setText("💼 RELATÓRIO DE FUNCIONÁRIOS\n");
        textAreaRelatorio.append("=".repeat(80) + "\n\n");
        
        int count = 0;
        for (Object obj : objetos) {
            if (obj instanceof Funcionario && !(obj instanceof Gerente)) {
                Funcionario func = (Funcionario) obj;
                textAreaRelatorio.append("• " + func.toString() + "\n");
                count++;
            }
        }
        
        textAreaRelatorio.append("\n" + "=".repeat(80) + "\n");
        textAreaRelatorio.append("📈 TOTAL: " + count + " funcionários encontrados\n");
    }
    
    private void gerarRelatorioGerentes() {
        textAreaRelatorio.setText("👔 RELATÓRIO DE GERENTES\n");
        textAreaRelatorio.append("=".repeat(80) + "\n\n");
        
        int count = 0;
        for (Object obj : objetos) {
            if (obj instanceof Gerente) {
                Gerente gerente = (Gerente) obj;
                textAreaRelatorio.append("• " + gerente.toString() + "\n");
                count++;
            }
        }
        
        textAreaRelatorio.append("\n" + "=".repeat(80) + "\n");
        textAreaRelatorio.append("📈 TOTAL: " + count + " gerentes encontrados\n");
    }
    
    private void gerarRelatorioVeiculos() {
        textAreaRelatorio.setText("🚗 RELATÓRIO DE VEÍCULOS\n");
        textAreaRelatorio.append("=".repeat(80) + "\n\n");
        
        int count = 0;
        for (Object obj : objetos) {
            if (obj instanceof Veiculo) {
                Veiculo veiculo = (Veiculo) obj;
                textAreaRelatorio.append("• " + veiculo.toString() + " - Movimento: " + veiculo.mover() + "\n");
                count++;
            }
        }
        
        textAreaRelatorio.append("\n" + "=".repeat(80) + "\n");
        textAreaRelatorio.append("📈 TOTAL: " + count + " veículos encontrados\n");
    }
    
    private void gerarRelatorioCarros() {
        textAreaRelatorio.setText("🚙 RELATÓRIO DE CARROS\n");
        textAreaRelatorio.append("=".repeat(80) + "\n\n");
        
        int count = 0;
        for (Object obj : objetos) {
            if (obj instanceof Carro) {
                Carro carro = (Carro) obj;
                textAreaRelatorio.append("• " + carro.toString() + "\n");
                count++;
            }
        }
        
        textAreaRelatorio.append("\n" + "=".repeat(80) + "\n");
        textAreaRelatorio.append("📈 TOTAL: " + count + " carros encontrados\n");
    }
    
    private void gerarRelatorioBicicletas() {
        textAreaRelatorio.setText("🚴 RELATÓRIO DE BICICLETAS\n");
        textAreaRelatorio.append("=".repeat(80) + "\n\n");
        
        int count = 0;
        for (Object obj : objetos) {
            if (obj instanceof Bicicleta) {
                Bicicleta bike = (Bicicleta) obj;
                textAreaRelatorio.append("• " + bike.toString() + "\n");
                count++;
            }
        }
        
        textAreaRelatorio.append("\n" + "=".repeat(80) + "\n");
        textAreaRelatorio.append("📈 TOTAL: " + count + " bicicletas encontradas\n");
    }
    
    private void gerarRelatorioPagaveis() {
        textAreaRelatorio.setText("💰 RELATÓRIO DE PAGÁVEIS\n");
        textAreaRelatorio.append("=".repeat(80) + "\n\n");
        
        int count = 0;
        double total = 0;
        for (Object obj : objetos) {
            if (obj instanceof Pagavel) {
                Pagavel pagavel = (Pagavel) obj;
                double pagamento = pagavel.calcularPagamento();
                textAreaRelatorio.append("• " + pagavel.getDescricao() + " - Pagamento: R$ " + 
                                       String.format("%,.2f", pagamento) + "\n");
                total += pagamento;
                count++;
            }
        }
        
        textAreaRelatorio.append("\n" + "=".repeat(80) + "\n");
        textAreaRelatorio.append("📈 TOTAL: " + count + " itens pagáveis\n");
        textAreaRelatorio.append("💵 VALOR TOTAL: R$ " + String.format("%,.2f", total) + "\n");
    }
    
    private void atualizarTabelaDados(String tipo) {
        tableModel.setRowCount(0);
        
        for (Object obj : objetos) {
            boolean matches = false;
            String objType = obj.getClass().getSimpleName();
            
            switch (tipo) {
                case "Todos os Tipos": matches = true; break;
                case "Animais": matches = obj instanceof Animal; break;
                case "Pessoas": matches = obj instanceof Pessoa && !(obj instanceof Aluno); break;
                case "Alunos": matches = obj instanceof Aluno; break;
                case "Funcionários": matches = obj instanceof Funcionario && !(obj instanceof Gerente); break;
                case "Gerentes": matches = obj instanceof Gerente; break;
                case "Veículos": matches = obj instanceof Veiculo; break;
                case "Carros": matches = obj instanceof Carro; break;
                case "Bicicletas": matches = obj instanceof Bicicleta; break;
                case "Pagáveis": matches = obj instanceof Pagavel; break;
            }
            
            if (matches) {
                tableModel.addRow(new Object[]{
                    objType,
                    obj.toString(),
                    getDetalhesObjeto(obj)
                });
            }
        }
    }
    
    private String getDetalhesObjeto(Object obj) {
        if (obj instanceof Animal) {
            return "Som: " + ((Animal) obj).fazerSom();
        } else if (obj instanceof Veiculo) {
            return "Movimento: " + ((Veiculo) obj).mover();
        } else if (obj instanceof Pagavel) {
            return "Pagamento: R$ " + String.format("%,.2f", ((Pagavel) obj).calcularPagamento());
        } else if (obj instanceof Gerente) {
            return "Departamento: " + ((Gerente) obj).getDepartamento();
        } else if (obj instanceof Aluno) {
            return "Matrícula: " + ((Aluno) obj).getMatricula();
        }
        return "N/A";
    }
    
    private void exportarRelatorio() {
        if (textAreaRelatorio.getText().isEmpty()) {
            showError("Gere um relatório antes de exportar!");
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Relatório");
        fileChooser.setSelectedFile(new File("relatorio.txt"));
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (PrintWriter writer = new PrintWriter(fileChooser.getSelectedFile())) {
                writer.write(textAreaRelatorio.getText());
                showSuccess("Relatório exportado com sucesso! ✅");
            } catch (IOException ex) {
                showError("Erro ao exportar relatório: " + ex.getMessage());
            }
        }
    }
    
    private void mostrarEstatisticas() {
        Map<String, Integer> estatisticas = new HashMap<>();
        for (Object obj : objetos) {
            String tipo = obj.getClass().getSimpleName();
            estatisticas.put(tipo, estatisticas.getOrDefault(tipo, 0) + 1);
        }
        
        StringBuilder stats = new StringBuilder("📊 ESTATÍSTICAS GERAIS\n");
        stats.append("=".repeat(50)).append("\n\n");
        
        for (Map.Entry<String, Integer> entry : estatisticas.entrySet()) {
            stats.append("• ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        
        stats.append("\n").append("=".repeat(50)).append("\n");
        stats.append("📈 TOTAL GERAL: ").append(objetos.size()).append(" objetos\n");
        
        textAreaRelatorio.setText(stats.toString());
    }
    
    private void mostrarSobre() {
        JOptionPane.showMessageDialog(this, 
            "📊 Sistema de Relatórios Automáticos\n\n" +
            "Exercício 7 - Relatório Automático com Swing\n" +
            "• Gere relatórios por tipo de objeto\n" +
            "• Use recursividade para agrupar objetos\n" +
            "• Exporte relatórios para arquivo TXT\n" +
            "• Visualize dados em tabela\n\n" +
            " Tipos suportados:\n" +
            "• Animais, Pessoas, Alunos\n" +
            "• Funcionários, Gerentes\n" +
            "• Veículos, Carros, Bicicletas\n" +
            "• Itens Pagáveis\n\n" +
            " Recursos avançados:\n" +
            "• Interface moderna e intuitiva\n" +
            "• Estatísticas em tempo real\n" +
            "• Exportação de relatórios\n" +
            "• Visualização tabular dos dados", 
            "ℹ️ Sobre o Sistema", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, "❌ " + message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
    
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, "✅ " + message, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            RelatorioGUI gui = new RelatorioGUI();
            gui.setVisible(true);
        });
    }
}

// Classes de exemplo para compilação (devem estar em arquivos separados)
abstract class Animal {
    private String nome;
    private int idade;
    
    public Animal(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }
    
    public abstract String fazerSom();
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + nome + " (" + idade + " anos)";
    }
}

class Pessoa {
    private String nome;
    private int idade;
    
    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }
    
    @Override
    public String toString() {
        return nome + " (" + idade + " anos)";
    }
}

class Aluno extends Pessoa {
    private String matricula;
    
    public Aluno(String nome, int idade, String matricula) {
        super(nome, idade);
        this.matricula = matricula;
    }
    
    public String getMatricula() { return matricula; }
    
    @Override
    public String toString() {
        return super.toString() + " - Matrícula: " + matricula;
    }
}

class Funcionario {
    private String nome;
    private double salario;
    
    public Funcionario(String nome, double salario) {
        this.nome = nome;
        this.salario = salario;
    }
    
    @Override
    public String toString() {
        return nome + " - Salário: R$ " + salario;
    }
}

class Gerente extends Funcionario {
    private String departamento;
    
    public Gerente(String nome, double salario, String departamento) {
        super(nome, salario);
        this.departamento = departamento;
    }
    
    public String getDepartamento() { return departamento; }
    
    @Override
    public String toString() {
        return super.toString() + " - Departamento: " + departamento;
    }
}

abstract class Veiculo {
    private String modelo;
    private String marca;
    private int ano;
    
    public Veiculo(String modelo, String marca, int ano) {
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
    }
    
    public abstract String mover();
    
    @Override
    public String toString() {
        return marca + " " + modelo + " (" + ano + ")";
    }
}

class Carro extends Veiculo {
    private String combustivel;
    
    public Carro(String modelo, String marca, int ano, String combustivel) {
        super(modelo, marca, ano);
        this.combustivel = combustivel;
    }
    
    @Override
    public String mover() {
        return "Movendo com motor a " + combustivel;
    }
    
    @Override
    public String toString() {
        return super.toString() + " - " + combustivel;
    }
}

class Bicicleta extends Veiculo {
    private int marchas;
    
    public Bicicleta(String modelo, String marca, int ano, int marchas) {
        super(modelo, marca, ano);
        this.marchas = marchas;
    }
    
    @Override
    public String mover() {
        return "Pedalando com " + marchas + " marchas";
    }
    
    @Override
    public String toString() {
        return super.toString() + " - " + marchas + " marchas";
    }
}

interface Pagavel {
    double calcularPagamento();
    String getDescricao();
}

class Freelancer implements Pagavel {
    private String nome;
    private double valorHora;
    private int horas;
    
    public Freelancer(String nome, double valorHora, int horas) {
        this.nome = nome;
        this.valorHora = valorHora;
        this.horas = horas;
    }
    
    @Override
    public double calcularPagamento() {
        return valorHora * horas;
    }
    
    @Override
    public String getDescricao() {
        return "Freelancer: " + nome + " - R$ " + valorHora + "/hora";
    }
    
    @Override
    public String toString() {
        return getDescricao() + " - " + horas + " horas";
    }
}