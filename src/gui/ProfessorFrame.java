package gui;

import classes.Professor;
import dao.ProfessorDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProfessorFrame extends JFrame {
    private ProfessorDAO professorDAO;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNome, txtIdentificador, txtEmail, txtDepartamento;
    private JButton btnSalvar, btnEditar, btnExcluir, btnLimpar;
    private int professorSelecionadoId = 0;

    public ProfessorFrame() {
        professorDAO = new ProfessorDAO();
        initializeComponents();
        carregarTabela();
    }

    private void initializeComponents() {
        setTitle("Gerenciar Professores");
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
        panel.setBorder(BorderFactory.createTitledBorder("Dados do Professor"));
        panel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        panel.add(txtNome, gbc);

        // Identificador
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Identificador:"), gbc);
        gbc.gridx = 1;
        txtIdentificador = new JTextField(20);
        panel.add(txtIdentificador, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        panel.add(txtEmail, gbc);

        // Departamento
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Departamento:"), gbc);
        gbc.gridx = 1;
        txtDepartamento = new JTextField(20);
        panel.add(txtDepartamento, gbc);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Professores"));

        String[] colunas = {"ID", "Nome", "Identificador", "Email", "Departamento"};
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

        btnSalvar.addActionListener(e -> salvarProfessor());
        btnEditar.addActionListener(e -> editarProfessor());
        btnExcluir.addActionListener(e -> excluirProfessor());
        btnLimpar.addActionListener(e -> limparFormulario());

        panel.add(btnSalvar);
        panel.add(btnEditar);
        panel.add(btnExcluir);
        panel.add(btnLimpar);

        return panel;
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        List<Professor> professores = professorDAO.listarTodos();

        for (Professor professor : professores) {
            Object[] row = {
                professor.getId(),
                professor.getNome(),
                professor.getIdentificador(),
                professor.getEmail(),
                professor.getDepartamento()
            };
            tableModel.addRow(row);
        }
    }

    private void preencherFormulario() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            professorSelecionadoId = (Integer) tableModel.getValueAt(selectedRow, 0);
            txtNome.setText((String) tableModel.getValueAt(selectedRow, 1));
            txtIdentificador.setText((String) tableModel.getValueAt(selectedRow, 2));
            txtEmail.setText((String) tableModel.getValueAt(selectedRow, 3));
            txtDepartamento.setText((String) tableModel.getValueAt(selectedRow, 4));
        }
    }

    private void salvarProfessor() {
        if (validarCampos()) {
            try {
                Professor professor = new Professor(
                    txtNome.getText().trim(),
                    txtIdentificador.getText().trim(),
                    txtEmail.getText().trim(),
                    txtDepartamento.getText().trim()
                );

                // Verificar se identificador já existe (apenas para novos professores)
                if (professorSelecionadoId == 0 && professorDAO.existeIdentificador(professor.getIdentificador())) {
                    JOptionPane.showMessageDialog(this, "Identificador já existe!");
                    return;
                }

                professorDAO.salvar(professor);
                carregarTabela();
                limparFormulario();
                JOptionPane.showMessageDialog(this, "Professor salvo com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar professor: " + e.getMessage());
            }
        }
    }

    private void editarProfessor() {
        if (professorSelecionadoId > 0 && validarCampos()) {
            try {
                Professor professor = new Professor(
                    professorSelecionadoId,
                    txtNome.getText().trim(),
                    txtIdentificador.getText().trim(),
                    txtEmail.getText().trim(),
                    txtDepartamento.getText().trim()
                );

                professorDAO.salvar(professor);
                carregarTabela();
                limparFormulario();
                JOptionPane.showMessageDialog(this, "Professor editado com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao editar professor: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um professor para editar!");
        }
    }

    private void excluirProfessor() {
        if (professorSelecionadoId > 0) {
            int resposta = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir este professor?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
            );

            if (resposta == JOptionPane.YES_OPTION) {
                try {
                    professorDAO.excluir(professorSelecionadoId);
                    carregarTabela();
                    limparFormulario();
                    JOptionPane.showMessageDialog(this, "Professor excluído com sucesso!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir professor: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um professor para excluir!");
        }
    }

    private void limparFormulario() {
        txtNome.setText("");
        txtIdentificador.setText("");
        txtEmail.setText("");
        txtDepartamento.setText("");
        professorSelecionadoId = 0;
        table.clearSelection();
    }

    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório!");
            txtNome.requestFocus();
            return false;
        }
        if (txtIdentificador.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Identificador é obrigatório!");
            txtIdentificador.requestFocus();
            return false;
        }
        if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email é obrigatório!");
            txtEmail.requestFocus();
            return false;
        }
        if (txtDepartamento.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Departamento é obrigatório!");
            txtDepartamento.requestFocus();
            return false;
        }
        return true;
    }
}
