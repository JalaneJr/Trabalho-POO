package view;

import javax.swing.*;
import java.awt.*;

/**
 * Painel de boas-vindas com imagem atrativa.
 * @author Funda Inc.
 * @version 1.0
 */
public class WelcomePanel extends JPanel {
    private final Color COR_FUNDO = new Color(236, 240, 241);
    private final Color COR_TEXTO = new Color(52, 73, 94);

    public WelcomePanel() {
        setLayout(new BorderLayout());
        setBackground(COR_FUNDO);
        
        // Painel de conteúdo
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(COR_FUNDO);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        // Título
        JLabel titulo = new JLabel("Sistema Acadêmico - Funda Inc.");
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(COR_TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Mensagem de boas-vindas
        JLabel mensagem = new JLabel("Bem-vindo ao Sistema de Gestão Acadêmica");
        mensagem.setFont(new Font("Arial", Font.PLAIN, 20));
        mensagem.setForeground(COR_TEXTO);
        mensagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Imagem (substitua pelo caminho real da sua imagem)
        ImageIcon icon = new ImageIcon("images/welcome.png");
        JLabel imagem = new JLabel(icon);
        imagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Informações do sistema
        JLabel info = new JLabel("<html><center>"
                + "Este sistema permite gerenciar alunos, professores e gerar relatórios acadêmicos.<br>"
                + "Use o menu acima para navegar entre as diferentes funcionalidades."
                + "</center></html>");
        info.setFont(new Font("Arial", Font.PLAIN, 16));
        info.setForeground(COR_TEXTO);
        info.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Adicionar componentes ao painel
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(titulo);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(mensagem);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        contentPanel.add(imagem);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        contentPanel.add(info);
        contentPanel.add(Box.createVerticalGlue());
        
        add(contentPanel, BorderLayout.CENTER);
    }
}