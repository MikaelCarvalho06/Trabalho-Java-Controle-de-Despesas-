package com.despesas.controller;

import java.time.LocalDate;
import java.util.List;

import com.despesas.dao.DespesaDAO;
import com.despesas.model.Despesa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TelaPrincipalController {

    @FXML private TextField txtDescricao;
    @FXML private TextField txtValor;
    @FXML private DatePicker dpData;
    @FXML private ComboBox<String> cbCategoria;

    @FXML private TableView<Despesa> tabelaDespesas;
    @FXML private TableColumn<Despesa, String> colDescricao;
    @FXML private TableColumn<Despesa, String> colCategoria;
    @FXML private TableColumn<Despesa, Double> colValor;
    @FXML private TableColumn<Despesa, String> colData;

    @FXML private Label totalLabel;
    @FXML private Button btnRemover;

    private final DespesaDAO dao = new DespesaDAO();
    private final ObservableList<Despesa> dados = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cbCategoria.setItems(FXCollections.observableArrayList("Alimentação", "Transporte", "Saúde", "Lazer", "Educação", "Outros"));

        colDescricao.setCellValueFactory(c -> c.getValue().descricaoProperty());
        colCategoria.setCellValueFactory(c -> c.getValue().categoriaProperty());
        colValor.setCellValueFactory(c -> c.getValue().valorProperty().asObject());
        colData.setCellValueFactory(c -> c.getValue().dataProperty());

        carregarDespesas();
        tabelaDespesas.setItems(dados);
    }

    private void carregarDespesas() {
        try {
            dados.clear();
            List<Despesa> lista = dao.listar();
            dados.addAll(lista);
            atualizarTotal();
        } catch (RuntimeException e) {
            mostrarAlerta("Erro ao carregar despesas: " + e.getMessage());
        }
    }

    @FXML
    public void adicionarDespesa(ActionEvent ev) {
        try {
            String descricao = txtDescricao.getText().trim();
            String categoria = cbCategoria.getValue();
            double valor = Double.parseDouble(txtValor.getText().replace(',', '.'));
            LocalDate data = dpData.getValue();
            if (descricao.isEmpty() || categoria == null || data == null) {
                mostrarAlerta("Preencha todos os campos.");
                return;
            }
            // enviar no formato ISO (YYYY-MM-DD)
            String dataFmt = data.toString();
            Despesa d = new Despesa(descricao, categoria, valor, dataFmt);
            dao.salvar(d);
            dados.add(0, d);
            atualizarTotal();

            limparCampos();
            mostrarInfo("Despesa adicionada.");
        } catch (NumberFormatException ex) {
            mostrarAlerta("Valor inválido.");
        } catch (RuntimeException ex) {
            mostrarAlerta(ex.getMessage());
        }
    }

    @FXML
    public void removerSelecionada(ActionEvent ev) {
        Despesa sel = tabelaDespesas.getSelectionModel().getSelectedItem();
        if (sel == null) { mostrarAlerta("Selecione uma despesa."); return; }
        try {
            dao.excluir(sel);
            dados.remove(sel);
            atualizarTotal();
            mostrarInfo("Despesa removida.");
        } catch (RuntimeException ex) {
            mostrarAlerta(ex.getMessage());
        }
    }

    private void atualizarTotal() {
        double total = dados.stream().mapToDouble(Despesa::getValor).sum();
        totalLabel.setText(String.format("Total: R$ %.2f", total));
    }

    private void limparCampos() {
        txtDescricao.clear();
        txtValor.clear();
        dpData.setValue(null);
        cbCategoria.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }

    private void mostrarInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
