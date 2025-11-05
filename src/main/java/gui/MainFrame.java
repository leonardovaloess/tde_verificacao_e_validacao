package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    
    public MainFrame() {
        initializeComponents();
    }
    
    private void initializeComponents() {
        setTitle("Sistema de Matrícula - Faculdade");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        // Criar menu
        createMenuBar();
        
        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Painel de boas-vindas
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setBackground(new Color(240, 248, 255));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel titleLabel = new JLabel("Sistema de Matrícula");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0;
        gbc.gridy = 0;
        welcomePanel.add(titleLabel, gbc);
        
        JLabel subtitleLabel = new JLabel("Gerenciamento Acadêmico");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(70, 130, 180));
        gbc.gridy = 1;
        welcomePanel.add(subtitleLabel, gbc);
        
        // Painel de botões principais
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JButton btnAlunos = createStyledButton("Gerenciar Alunos", "");
        JButton btnProfessores = createStyledButton("Gerenciar Professores", "");
        JButton btnDisciplinas = createStyledButton("Gerenciar Disciplinas", "");
        JButton btnMatriculas = createStyledButton("Gerenciar Matrículas", "");
        
        buttonPanel.add(btnAlunos);
        buttonPanel.add(btnProfessores);
        buttonPanel.add(btnDisciplinas);
        buttonPanel.add(btnMatriculas);
        
        // Adicionar listeners
        btnAlunos.addActionListener(e -> new AlunoFrame().setVisible(true));
        btnProfessores.addActionListener(e -> new ProfessorFrame().setVisible(true));
        btnDisciplinas.addActionListener(e -> new DisciplinaFrame().setVisible(true));
        btnMatriculas.addActionListener(e -> new MatriculaFrame().setVisible(true));
        
        mainPanel.add(welcomePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JButton createStyledButton(String text, String icon) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(200, 80));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        
        // Efeito hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
        
        return button;
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuArquivo = new JMenu("Arquivo");
        JMenuItem itemSair = new JMenuItem("Sair");
        itemSair.addActionListener(e -> System.exit(0));
        menuArquivo.add(itemSair);
        
        JMenu menuGerenciar = new JMenu("Gerenciar");
        JMenuItem itemAlunos = new JMenuItem("Alunos");
        JMenuItem itemProfessores = new JMenuItem("Professores");
        JMenuItem itemDisciplinas = new JMenuItem("Disciplinas");
        JMenuItem itemMatriculas = new JMenuItem("Matrículas");
        
        itemAlunos.addActionListener(e -> new AlunoFrame().setVisible(true));
        itemProfessores.addActionListener(e -> new ProfessorFrame().setVisible(true));
        itemDisciplinas.addActionListener(e -> new DisciplinaFrame().setVisible(true));
        itemMatriculas.addActionListener(e -> new MatriculaFrame().setVisible(true));
        
        menuGerenciar.add(itemAlunos);
        menuGerenciar.add(itemProfessores);
        menuGerenciar.add(itemDisciplinas);
        menuGerenciar.add(itemMatriculas);
        
        JMenu menuAjuda = new JMenu("Ajuda");
        JMenuItem itemSobre = new JMenuItem("Sobre");
        itemSobre.addActionListener(e -> mostrarSobre());
        menuAjuda.add(itemSobre);
        
        menuBar.add(menuArquivo);
        menuBar.add(menuGerenciar);
        menuBar.add(menuAjuda);
        
        setJMenuBar(menuBar);
    }
    
    private void mostrarSobre() {
        JOptionPane.showMessageDialog(this,
            "Sistema de Matrícula v1.0\n\n" +
            "Desenvolvido para gerenciamento acadêmico\n" +
            "Faculdade - POO\n\n" +
            "© 2025",
            "Sobre",
            JOptionPane.INFORMATION_MESSAGE);
    }
}
