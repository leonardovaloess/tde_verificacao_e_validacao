package gui;

import classes.Aluno;
import dao.AlunoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AlunoFrame extends JFrame {
    private AlunoDAO alunoDAO;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNome, txtMatricula, txtEmail, txtTelefone;
    private JButton btnSalvar, btnEditar, btnExcluir, btnLimpar;
    private int alunoSelecionadoId = 0;

    public AlunoFrame() {
        alunoDAO = new AlunoDAO();
        initializeComponents();
        carregarTabela();
    }

    private void initializeComponents() {
        setTitle("Gerenciar Alunos");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Painel do formulário
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.NORTH);

        // Painel da tabela
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Painel dos botões
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Dados do Aluno"));
        panel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        panel.add(txtNome, gbc);

        // Matrícula
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Matrícula:"), gbc);
        gbc.gridx = 1;
        txtMatricula = new JTextField(20);
        panel.add(txtMatricula, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        panel.add(txtEmail, gbc);

        // Telefone
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        txtTelefone = new JTextField(20);
        panel.add(txtTelefone, gbc);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Alunos"));

        String[] colunas = {"ID", "Nome", "Matrícula", "Email", "Telefone"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherFormulario();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        btnSalvar = new JButton("Salvar");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Limpar");

        btnSalvar.addActionListener(e -> salvarAluno());
        btnEditar.addActionListener(e -> editarAluno());
        btnExcluir.addActionListener(e -> excluirAluno());
        btnLimpar.addActionListener(e -> limparFormulario());

        panel.add(btnSalvar);
        panel.add(btnEditar);
        panel.add(btnExcluir);
        panel.add(btnLimpar);

        return panel;
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        List<Aluno> alunos = alunoDAO.listarTodos();

        for (Aluno aluno : alunos) {
            Object[] row = {
                aluno.getId(),
                aluno.getNome(),
                aluno.getMatricula(),
                aluno.getEmail(),
                aluno.getTelefone()
            };
            tableModel.addRow(row);
        }
    }

    private void preencherFormulario() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            alunoSelecionadoId = (Integer) tableModel.getValueAt(selectedRow, 0);
            txtNome.setText((String) tableModel.getValueAt(selectedRow, 1));
            txtMatricula.setText((String) tableModel.getValueAt(selectedRow, 2));
            txtEmail.setText((String) tableModel.getValueAt(selectedRow, 3));
            txtTelefone.setText((String) tableModel.getValueAt(selectedRow, 4));
        }
    }

    private void salvarAluno() {
        if (validarCampos()) {
            try {
                Aluno aluno = new Aluno(
                    txtNome.getText().trim(),
                    txtMatricula.getText().trim(),
                    txtEmail.getText().trim(),
                    txtTelefone.getText().trim()
                );

                // Verificar se matrícula já existe (apenas para novos alunos)
                if (alunoSelecionadoId == 0 && alunoDAO.existeMatricula(aluno.getMatricula())) {
                    JOptionPane.showMessageDialog(this, "Matrícula já existe!");
                    return;
                }

                alunoDAO.salvar(aluno);
                carregarTabela();
                limparFormulario();
                JOptionPane.showMessageDialog(this, "Aluno salvo com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar aluno: " + e.getMessage());
            }
        }
    }

    private void editarAluno() {
        if (alunoSelecionadoId > 0 && validarCampos()) {
            try {
                Aluno aluno = new Aluno(
                    alunoSelecionadoId,
                    txtNome.getText().trim(),
                    txtMatricula.getText().trim(),
                    txtEmail.getText().trim(),
                    txtTelefone.getText().trim()
                );

                alunoDAO.salvar(aluno);
                carregarTabela();
                limparFormulario();
                JOptionPane.showMessageDialog(this, "Aluno editado com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao editar aluno: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um aluno para editar!");
        }
    }

    private void excluirAluno() {
        if (alunoSelecionadoId > 0) {
            int resposta = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir este aluno?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
            );

            if (resposta == JOptionPane.YES_OPTION) {
                try {
                    alunoDAO.excluir(alunoSelecionadoId);
                    carregarTabela();
                    limparFormulario();
                    JOptionPane.showMessageDialog(this, "Aluno excluído com sucesso!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir aluno: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um aluno para excluir!");
        }
    }

    private void limparFormulario() {
        txtNome.setText("");
        txtMatricula.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        alunoSelecionadoId = 0;
        table.clearSelection();
    }

    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório!");
            txtNome.requestFocus();
            return false;
        }
        if (txtMatricula.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Matrícula é obrigatória!");
            txtMatricula.requestFocus();
            return false;
        }
        if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email é obrigatório!");
            txtEmail.requestFocus();
            return false;
        }
        if (txtTelefone.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Telefone é obrigatório!");
            txtTelefone.requestFocus();
            return false;
        }
        return true;
    }
}
