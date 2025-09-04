package gui;

import classes.Aluno;
import classes.Disciplina;
import classes.Matricula;
import classes.Professor;
import dao.AlunoDAO;
import dao.DisciplinaDAO;
import dao.MatriculaDAO;
import dao.ProfessorDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MatriculaFrame extends JFrame {
    private MatriculaDAO matriculaDAO;
    private AlunoDAO alunoDAO;
    private DisciplinaDAO disciplinaDAO;
    private ProfessorDAO professorDAO;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<Aluno> cbAluno;
    private JComboBox<Disciplina> cbDisciplina;
    private JComboBox<String> cbStatus;
    private JTextField txtDataMatricula;
    private JButton btnSalvar, btnEditar, btnExcluir, btnLimpar;
    private int matriculaSelecionadaId = 0;

    public MatriculaFrame() {
        matriculaDAO = new MatriculaDAO();
        alunoDAO = new AlunoDAO();
        disciplinaDAO = new DisciplinaDAO();
        professorDAO = new ProfessorDAO();
        initializeComponents();
        carregarComboBoxes();
        carregarTabela();
    }

    private void initializeComponents() {
        setTitle("Gerenciar Matrículas");
        setSize(1000, 600);
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
        panel.setBorder(BorderFactory.createTitledBorder("Dados da Matrícula"));
        panel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Aluno
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Aluno:"), gbc);
        gbc.gridx = 1;
        cbAluno = new JComboBox<>();
        cbAluno.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Aluno) {
                    setText(((Aluno) value).toDisplayString());
                }
                return this;
            }
        });
        panel.add(cbAluno, gbc);

        // Disciplina
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Disciplina:"), gbc);
        gbc.gridx = 1;
        cbDisciplina = new JComboBox<>();
        cbDisciplina.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Disciplina) {
                    setText(((Disciplina) value).toDisplayString());
                }
                return this;
            }
        });
        panel.add(cbDisciplina, gbc);

        // Data da Matrícula
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Data Matrícula (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1;
        txtDataMatricula = new JTextField(20);
        // Preencher com data atual
        txtDataMatricula.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        panel.add(txtDataMatricula, gbc);

        // Status
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        cbStatus = new JComboBox<>(new String[]{"ATIVA", "CANCELADA", "CONCLUIDA"});
        panel.add(cbStatus, gbc);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Matrículas"));

        String[] colunas = {"ID", "Aluno", "Disciplina", "Professor", "Data Matrícula", "Status"};
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

        btnSalvar.addActionListener(e -> salvarMatricula());
        btnEditar.addActionListener(e -> editarMatricula());
        btnExcluir.addActionListener(e -> excluirMatricula());
        btnLimpar.addActionListener(e -> limparFormulario());

        panel.add(btnSalvar);
        panel.add(btnEditar);
        panel.add(btnExcluir);
        panel.add(btnLimpar);

        return panel;
    }

    private void carregarComboBoxes() {
        // Carregar alunos
        cbAluno.removeAllItems();
        List<Aluno> alunos = alunoDAO.listarTodos();
        for (Aluno aluno : alunos) {
            cbAluno.addItem(aluno);
        }

        // Carregar disciplinas
        cbDisciplina.removeAllItems();
        List<Disciplina> disciplinas = disciplinaDAO.listarTodos();
        for (Disciplina disciplina : disciplinas) {
            cbDisciplina.addItem(disciplina);
        }
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        List<Matricula> matriculas = matriculaDAO.listarTodos();

        for (Matricula matricula : matriculas) {
            Aluno aluno = alunoDAO.buscarPorId(matricula.getAlunoId());
            Disciplina disciplina = disciplinaDAO.buscarPorId(matricula.getDisciplinaId());
            Professor professor = professorDAO.buscarPorId(disciplina != null ? disciplina.getProfessorId() : 0);

            String nomeAluno = aluno != null ? aluno.getNome() : "Aluno não encontrado";
            String nomeDisciplina = disciplina != null ? disciplina.getNomeDisciplina() : "Disciplina não encontrada";
            String nomeProfessor = professor != null ? professor.getNome() : "Professor não encontrado";

            Object[] row = {
                matricula.getId(),
                nomeAluno,
                nomeDisciplina,
                nomeProfessor,
                matricula.getDataMatricula().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                matricula.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    private void preencherFormulario() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            matriculaSelecionadaId = (Integer) tableModel.getValueAt(selectedRow, 0);
            
            Matricula matricula = matriculaDAO.buscarPorId(matriculaSelecionadaId);
            if (matricula != null) {
                // Selecionar aluno
                for (int i = 0; i < cbAluno.getItemCount(); i++) {
                    Aluno aluno = cbAluno.getItemAt(i);
                    if (aluno.getId() == matricula.getAlunoId()) {
                        cbAluno.setSelectedIndex(i);
                        break;
                    }
                }

                // Selecionar disciplina
                for (int i = 0; i < cbDisciplina.getItemCount(); i++) {
                    Disciplina disciplina = cbDisciplina.getItemAt(i);
                    if (disciplina.getId() == matricula.getDisciplinaId()) {
                        cbDisciplina.setSelectedIndex(i);
                        break;
                    }
                }

                txtDataMatricula.setText(matricula.getDataMatricula().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                cbStatus.setSelectedItem(matricula.getStatus());
            }
        }
    }

    private void salvarMatricula() {
        if (validarCampos()) {
            try {
                Aluno alunoSelecionado = (Aluno) cbAluno.getSelectedItem();
                Disciplina disciplinaSelecionada = (Disciplina) cbDisciplina.getSelectedItem();
                String status = (String) cbStatus.getSelectedItem();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataMatricula = LocalDate.parse(txtDataMatricula.getText().trim(), formatter);

                // Verificar se já existe matrícula ativa para este aluno e disciplina
                if (matriculaSelecionadaId == 0 && matriculaDAO.existeMatricula(alunoSelecionado.getId(), disciplinaSelecionada.getId())) {
                    JOptionPane.showMessageDialog(this, "Já existe uma matrícula ativa para este aluno nesta disciplina!");
                    return;
                }

                Matricula matricula = new Matricula(
                    alunoSelecionado.getId(),
                    disciplinaSelecionada.getId(),
                    dataMatricula,
                    status
                );

                matriculaDAO.salvar(matricula);
                carregarTabela();
                limparFormulario();
                JOptionPane.showMessageDialog(this, "Matrícula salva com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar matrícula: " + e.getMessage());
            }
        }
    }

    private void editarMatricula() {
        if (matriculaSelecionadaId > 0 && validarCampos()) {
            try {
                Aluno alunoSelecionado = (Aluno) cbAluno.getSelectedItem();
                Disciplina disciplinaSelecionada = (Disciplina) cbDisciplina.getSelectedItem();
                String status = (String) cbStatus.getSelectedItem();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataMatricula = LocalDate.parse(txtDataMatricula.getText().trim(), formatter);

                Matricula matricula = new Matricula(
                    matriculaSelecionadaId,
                    alunoSelecionado.getId(),
                    disciplinaSelecionada.getId(),
                    dataMatricula,
                    status
                );

                matriculaDAO.salvar(matricula);
                carregarTabela();
                limparFormulario();
                JOptionPane.showMessageDialog(this, "Matrícula editada com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao editar matrícula: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma matrícula para editar!");
        }
    }

    private void excluirMatricula() {
        if (matriculaSelecionadaId > 0) {
            int resposta = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir esta matrícula?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
            );

            if (resposta == JOptionPane.YES_OPTION) {
                try {
                    matriculaDAO.excluir(matriculaSelecionadaId);
                    carregarTabela();
                    limparFormulario();
                    JOptionPane.showMessageDialog(this, "Matrícula excluída com sucesso!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir matrícula: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma matrícula para excluir!");
        }
    }

    private void limparFormulario() {
        cbAluno.setSelectedIndex(-1);
        cbDisciplina.setSelectedIndex(-1);
        cbStatus.setSelectedIndex(0);
        txtDataMatricula.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        matriculaSelecionadaId = 0;
        table.clearSelection();
    }

    private boolean validarCampos() {
        if (cbAluno.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno!");
            cbAluno.requestFocus();
            return false;
        }
        if (cbDisciplina.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma disciplina!");
            cbDisciplina.requestFocus();
            return false;
        }
        if (txtDataMatricula.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data da matrícula é obrigatória!");
            txtDataMatricula.requestFocus();
            return false;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(txtDataMatricula.getText().trim(), formatter);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data da matrícula deve estar no formato dd/MM/yyyy!");
            txtDataMatricula.requestFocus();
            return false;
        }
        return true;
    }
}
