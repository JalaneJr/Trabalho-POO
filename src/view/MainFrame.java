package view;

import controller.AlunoController;
import controller.ProfessorController;
import controller.RelatorioController;
import javax.swing.*;
import java.awt.*;

/**
 * Classe principal que representa a janela principal do sistema.
 * @author Funda Inc.
 * @version 1.0
 */
public class MainFrame extends JFrame {
    private AlunoController alunoController;
    private ProfessorController professorController;
    private RelatorioController relatorioController;
    
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private WelcomePanel welcomePanel;
    private AlunoPanel alunoPanel;
    private ProfessorPanel professorPanel;
    private RelatorioPanel relatorioPanel;
    
    // Cores da paleta
    private final Color COR_PRIMARIA = new Color(41, 128, 185);
    private final Color COR_SECUNDARIA = new Color(52, 152, 219);
    private final Color COR_TERCIARIA = new Color(236, 240, 241);
    private final Color COR_TEXTO = new Color(255, 255, 255);
    private final Color COR_DESTAQUE = new Color(46, 204, 113);

    public MainFrame() {
        // Inicializar controladores
        alunoController = new AlunoController();
        professorController = new ProfessorController();
        relatorioController = new RelatorioController(alunoController, professorController);
        
        // Configurar a janela principal
        setTitle("Sistema Acadêmico - Funda Inc.");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Criar painel principal com CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Criar os painéis das diferentes telas
        welcomePanel = new WelcomePanel();
        alunoPanel = new AlunoPanel(alunoController);
        professorPanel = new ProfessorPanel(professorController);
        relatorioPanel = new RelatorioPanel(relatorioController);
        
        // Adicionar painéis ao cardLayout
        mainPanel.add(welcomePanel, "Welcome");
        mainPanel.add(alunoPanel, "Alunos");
        mainPanel.add(professorPanel, "Professores");
        mainPanel.add(relatorioPanel, "Relatorios");
        
        // Criar barra de menu
        criarMenu();
        
        // Adicionar painel principal à janela
        add(mainPanel);
        
        // Mostrar painel de boas-vindas inicialmente
        cardLayout.show(mainPanel, "Welcome");
    }
    
    /**
     * Cria a barra de menu com as opções do sistema
     */
    private void criarMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(COR_PRIMARIA);
        menuBar.setForeground(COR_TEXTO);
        
        // Menu Sistema
        JMenu menuSistema = new JMenu("Sistema");
        menuSistema.setForeground(COR_TEXTO);
        
        JMenuItem itemWelcome = new JMenuItem("Tela Inicial");
        itemWelcome.addActionListener(e -> cardLayout.show(mainPanel, "Welcome"));
        menuSistema.add(itemWelcome);
        
        JMenuItem itemSair = new JMenuItem("Sair");
        itemSair.addActionListener(e -> System.exit(0));
        menuSistema.add(itemSair);
        
        // Menu Cadastros
        JMenu menuCadastros = new JMenu("Cadastros");
        menuCadastros.setForeground(COR_TEXTO);
        
        JMenuItem itemAlunos = new JMenuItem("Gestão de Alunos");
        itemAlunos.addActionListener(e -> {
            alunoPanel.atualizarLista();
            cardLayout.show(mainPanel, "Alunos");
        });
        menuCadastros.add(itemAlunos);
        
        JMenuItem itemProfessores = new JMenuItem("Gestão de Professores");
        itemProfessores.addActionListener(e -> {
            professorPanel.atualizarLista();
            cardLayout.show(mainPanel, "Professores");
        });
        menuCadastros.add(itemProfessores);
        
        // Menu Relatórios
        JMenu menuRelatorios = new JMenu("Relatórios");
        menuRelatorios.setForeground(COR_TEXTO);
        
        JMenuItem itemRelatorios = new JMenuItem("Relatórios");
        itemRelatorios.addActionListener(e -> cardLayout.show(mainPanel, "Relatorios"));
        menuRelatorios.add(itemRelatorios);
        
        // Adicionar menus à barra
        menuBar.add(menuSistema);
        menuBar.add(menuCadastros);
        menuBar.add(menuRelatorios);
        
        setJMenuBar(menuBar);
    }
}