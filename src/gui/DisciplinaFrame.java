package gui;

import classes.Disciplina;
import classes.Professor;
import dao.DisciplinaDAO;
import dao.ProfessorDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DisciplinaFrame extends JFrame {
    private DisciplinaDAO disciplinaDAO;
    private ProfessorDAO professorDAO;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNome, txtCodigo, txtCargaHoraria, txtSemestre;
    private JComboBox<Professor> cbProfessor;
    private JButton btnSalvar, btnEditar, btnExcluir, btnLimpar;
    private int disciplinaSelecionadaId = 0;

    public DisciplinaFrame() {
        disciplinaDAO = new DisciplinaDAO();
        professorDAO = new ProfessorDAO();
        initializeComponents();
        carregarComboBoxProfessores();
        carregarTabela();
    }

    private void initializeComponents() {
        setTitle("Gerenciar Disciplinas");
        setSize(900, 600);
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
        panel.setBorder(BorderFactory.createTitledBorder("Dados da Disciplina"));
        panel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        panel.add(txtNome, gbc);

        // Código
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1;
        txtCodigo = new JTextField(20);
        panel.add(txtCodigo, gbc);

        // Carga Horária
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Carga Horária:"), gbc);
        gbc.gridx = 1;
        txtCargaHoraria = new JTextField(20);
        panel.add(txtCargaHoraria, gbc);

        // Semestre
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Semestre:"), gbc);
        gbc.gridx = 1;
        txtSemestre = new JTextField(20);
        panel.add(txtSemestre, gbc);

        // Professor
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Professor:"), gbc);
        gbc.gridx = 1;
        cbProfessor = new JComboBox<>();
        cbProfessor.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Professor) {
                    setText(((Professor) value).toDisplayString());
                }
                return this;
            }
        });
        panel.add(cbProfessor, gbc);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Disciplinas"));

        String[] colunas = {"ID", "Nome", "Código", "Carga Horária", "Semestre", "Professor"};
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

        btnSalvar.addActionListener(e -> salvarDisciplina());
        btnEditar.addActionListener(e -> editarDisciplina());
        btnExcluir.addActionListener(e -> excluirDisciplina());
        btnLimpar.addActionListener(e -> limparFormulario());

        panel.add(btnSalvar);
        panel.add(btnEditar);
        panel.add(btnExcluir);
        panel.add(btnLimpar);

        return panel;
    }

    private void carregarComboBoxProfessores() {
        cbProfessor.removeAllItems();
        List<Professor> professores = professorDAO.listarTodos();
        for (Professor professor : professores) {
            cbProfessor.addItem(professor);
        }
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        List<Disciplina> disciplinas = disciplinaDAO.listarTodos();

        for (Disciplina disciplina : disciplinas) {
            Professor professor = professorDAO.buscarPorId(disciplina.getProfessorId());
            String nomeProfessor = professor != null ? professor.getNome() : "Professor não encontrado";
            
            Object[] row = {
                disciplina.getId(),
                disciplina.getNomeDisciplina(),
                disciplina.getCodigo(),
                disciplina.getCargaHoraria(),
                disciplina.getSemestre(),
                nomeProfessor
            };
            tableModel.addRow(row);
        }
    }

    private void preencherFormulario() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            disciplinaSelecionadaId = (Integer) tableModel.getValueAt(selectedRow, 0);
            txtNome.setText((String) tableModel.getValueAt(selectedRow, 1));
            txtCodigo.setText((String) tableModel.getValueAt(selectedRow, 2));
            txtCargaHoraria.setText(tableModel.getValueAt(selectedRow, 3).toString());
            txtSemestre.setText((String) tableModel.getValueAt(selectedRow, 4));
            
            // Selecionar o professor no combo box
            Disciplina disciplina = disciplinaDAO.buscarPorId(disciplinaSelecionadaId);
            if (disciplina != null) {
                for (int i = 0; i < cbProfessor.getItemCount(); i++) {
                    Professor professor = cbProfessor.getItemAt(i);
                    if (professor.getId() == disciplina.getProfessorId()) {
                        cbProfessor.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
    }

    private void salvarDisciplina() {
        if (validarCampos()) {
            try {
                Professor professorSelecionado = (Professor) cbProfessor.getSelectedItem();
                
                Disciplina disciplina = new Disciplina(
                    txtNome.getText().trim(),
                    txtCodigo.getText().trim(),
                    Integer.parseInt(txtCargaHoraria.getText().trim()),
                    professorSelecionado.getId(),
                    txtSemestre.getText().trim()
                );

                // Verificar se código já existe (apenas para novas disciplinas)
                if (disciplinaSelecionadaId == 0 && disciplinaDAO.existeCodigo(disciplina.getCodigo())) {
                    JOptionPane.showMessageDialog(this, "Código da disciplina já existe!");
                    return;
                }

                disciplinaDAO.salvar(disciplina);
                carregarTabela();
                limparFormulario();
                JOptionPane.showMessageDialog(this, "Disciplina salva com sucesso!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Carga horária deve ser um número válido!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar disciplina: " + e.getMessage());
            }
        }
    }

    private void editarDisciplina() {
        if (disciplinaSelecionadaId > 0 && validarCampos()) {
            try {
                Professor professorSelecionado = (Professor) cbProfessor.getSelectedItem();
                
                Disciplina disciplina = new Disciplina(
                    disciplinaSelecionadaId,
                    txtNome.getText().trim(),
                    txtCodigo.getText().trim(),
                    Integer.parseInt(txtCargaHoraria.getText().trim()),
                    professorSelecionado.getId(),
                    txtSemestre.getText().trim()
                );

                disciplinaDAO.salvar(disciplina);
                carregarTabela();
                limparFormulario();
                JOptionPane.showMessageDialog(this, "Disciplina editada com sucesso!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Carga horária deve ser um número válido!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao editar disciplina: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma disciplina para editar!");
        }
    }

    private void excluirDisciplina() {
        if (disciplinaSelecionadaId > 0) {
            int resposta = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir esta disciplina?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
            );

            if (resposta == JOptionPane.YES_OPTION) {
                try {
                    disciplinaDAO.excluir(disciplinaSelecionadaId);
                    carregarTabela();
                    limparFormulario();
                    JOptionPane.showMessageDialog(this, "Disciplina excluída com sucesso!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir disciplina: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma disciplina para excluir!");
        }
    }

    private void limparFormulario() {
        txtNome.setText("");
        txtCodigo.setText("");
        txtCargaHoraria.setText("");
        txtSemestre.setText("");
        cbProfessor.setSelectedIndex(-1);
        disciplinaSelecionadaId = 0;
        table.clearSelection();
    }

    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório!");
            txtNome.requestFocus();
            return false;
        }
        if (txtCodigo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Código é obrigatório!");
            txtCodigo.requestFocus();
            return false;
        }
        if (txtCargaHoraria.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Carga horária é obrigatória!");
            txtCargaHoraria.requestFocus();
            return false;
        }
        try {
            Integer.parseInt(txtCargaHoraria.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Carga horária deve ser um número válido!");
            txtCargaHoraria.requestFocus();
            return false;
        }
        if (txtSemestre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semestre é obrigatório!");
            txtSemestre.requestFocus();
            return false;
        }
        if (cbProfessor.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um professor!");
            cbProfessor.requestFocus();
            return false;
        }
        return true;
    }
}
