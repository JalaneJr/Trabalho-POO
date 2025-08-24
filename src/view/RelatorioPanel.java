package view;

import controller.RelatorioController;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Painel para geração de relatórios.
 * @author Funda Inc.
 * @version 1.0
 */
public class RelatorioPanel extends JPanel {
    private RelatorioController relatorioController;
    private JTextArea areaRelatorio;
    private JButton btnRelatorioAlunos, btnRelatorioProfessores, btnRelatorioCompleto, btnExportar;
    
    // Cores
    private final Color COR_FUNDO = new Color(236, 240, 241);
    private final Color COR_BOTAO = new Color(52, 152, 219);
    private final Color COR_BOTAO_TEXTO = Color.WHITE;
    private final Color COR_TEXTO = new Color(52, 73, 94);

    public RelatorioPanel(RelatorioController relatorioController) {
        this.relatorioController = relatorioController;
        initComponents();
    }
    
    /**
     * Inicializa os componentes da interface
     */
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(COR_FUNDO);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(COR_FUNDO);
        
        btnRelatorioAlunos = criarBotao("Relatório de Alunos", COR_BOTAO);
        btnRelatorioAlunos.addActionListener(e -> gerarRelatorioAlunos());
        buttonPanel.add(btnRelatorioAlunos);
        
        btnRelatorioProfessores = criarBotao("Relatório de Professores", new Color(155, 89, 182));
        btnRelatorioProfessores.addActionListener(e -> gerarRelatorioProfessores());
        buttonPanel.add(btnRelatorioProfessores);
        
        btnRelatorioCompleto = criarBotao("Relatório Completo", new Color(46, 204, 113));
        btnRelatorioCompleto.addActionListener(e -> gerarRelatorioCompleto());
        buttonPanel.add(btnRelatorioCompleto);
        
        btnExportar = criarBotao("Exportar para TXT", new Color(241, 196, 15));
        btnExportar.addActionListener(e -> exportarRelatorio());
        buttonPanel.add(btnExportar);
        
        // Área de texto para relatório
        areaRelatorio = new JTextArea();
        areaRelatorio.setEditable(false);
        areaRelatorio.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaRelatorio.setForeground(COR_TEXTO);
        areaRelatorio.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(areaRelatorio);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Relatório"));
        
        // Adicionar componentes ao painel principal
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
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
     * Gera relatório de alunos
     */
    private void gerarRelatorioAlunos() {
        String relatorio = relatorioController.gerarRelatorioAlunos();
        areaRelatorio.setText(relatorio);
    }
    
    /**
     * Gera relatório de professores
     */
    private void gerarRelatorioProfessores() {
        String relatorio = relatorioController.gerarRelatorioProfessores();
        areaRelatorio.setText(relatorio);
    }
    
    /**
     * Gera relatório completo
     */
    private void gerarRelatorioCompleto() {
        String relatorio = relatorioController.gerarRelatorioCompleto();
        areaRelatorio.setText(relatorio);
    }
    
    /**
     * Exporta o relatório para arquivo de texto
     */
    private void exportarRelatorio() {
        String relatorio = areaRelatorio.getText();
        if (relatorio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Gere um relatório antes de exportar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String filename = "relatorios/relatorio_" + dateFormat.format(new Date()) + ".txt";
        
        if (relatorioController.exportarRelatorio(relatorio, filename)) {
            JOptionPane.showMessageDialog(this, "Relatório exportado com sucesso para: " + filename, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao exportar relatório.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}