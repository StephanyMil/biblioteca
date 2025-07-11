package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.File;
import javafx.stage.FileChooser;

import Model.Database;
import Model.Livro;
import Model.LivroRepository;
import view.AppView;
import view.LivroView;

public class AppController implements Initializable {
    @FXML
    private TableView<LivroView> tabela;
    @FXML
    private TableColumn<LivroView, Integer> idCol;
    @FXML
    private TableColumn<LivroView, String> tituloCol;
    @FXML
    private TableColumn<LivroView, String> autorCol;
    @FXML
    private TableColumn<LivroView, Integer> anoPublicacaoCol;
    @FXML
    private TableColumn<LivroView, Double> precoCol;
    @FXML
    private TableColumn<LivroView, String> dataCadastroCol;

    @FXML
    private TextField idField;
    @FXML
    private TextField tituloField;
    @FXML
    private TextField autorField;
    @FXML
    private TextField anoPublicacaoField;
    @FXML
    private TextField precoField;
    @FXML
    private TextField dataCadastroField;

    @FXML
    private Button adicionarButton;
    @FXML
    private Button atualizarButton;
    @FXML
    private Button deletarButton;
    @FXML
    private Button cancelarButton;
    @FXML
    private Button salvarButton;

    AppView appView;

    private static Database database = new Database("livraria.sqlite");
    private static LivroRepository livroRepo = new LivroRepository(database);

    public AppController() {
        this.appView = new AppView();
    }

    public static void main(String[] args) throws Exception {
        AppController appController = new AppController();
        appController.appView.run(args);
    }

    private void desabilitarBotoes(boolean adicionar, boolean atualizar, boolean deletar, boolean cancelar, boolean salvar) {
        adicionarButton.setDisable(adicionar);
        atualizarButton.setDisable(atualizar);
        deletarButton.setDisable(deletar);
        cancelarButton.setDisable(cancelar);
        salvarButton.setDisable(salvar);
    }

    private void desabilitarCampos(boolean desabilitado) {
        tituloField.setDisable(desabilitado);
        autorField.setDisable(desabilitado);
        anoPublicacaoField.setDisable(desabilitado);
        precoField.setDisable(desabilitado);
        dataCadastroField.setDisable(desabilitado);
    }

    private void limparCampos() {
        idField.setText("");
        tituloField.setText("");
        autorField.setText("");
        anoPublicacaoField.setText("");
        precoField.setText("");
        dataCadastroField.setText("");
    }

    @FXML
    public void onCancelarButtonAction() {
        desabilitarCampos(true);
        desabilitarBotoes(false, true, true, true, true);
        limparCampos();
        tabela.getSelectionModel().select(-1);
    }

    @FXML
    public void onSalvarButtonAction() {
        try {
            Livro livro = new Livro();
            livro.setTitulo(tituloField.getText());
            livro.setAutor(autorField.getText());
            livro.setAnoPublicacao(Integer.parseInt(anoPublicacaoField.getText()));
            
            // --- CORREÇÃO APLICADA AQUI TAMBÉM ---
            // Garante que o formato do preço seja sempre com ponto.
            String precoTexto = precoField.getText().replace(",", ".");
            livro.setPreco(Double.parseDouble(precoTexto));
            // ------------------------------------

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dataCadastro = null;
            try {
                dataCadastro = dateFormat.parse(dataCadastroField.getText());
            } catch (ParseException e) {
                new Alert(AlertType.ERROR, "Formato de data inválido. Use DD/MM/AAAA.").show();
                return;
            }
            livro.setDataCadastro(dataCadastro);

            Livro livroSalvo = livroRepo.create(livro);
            LivroView livroView = modelToView(livroSalvo);
            tabela.getItems().add(livroView);
            tabela.getSelectionModel().select(livroView);
            desabilitarCampos(true);
            desabilitarBotoes(false, true, true, true, true);
        } catch (NumberFormatException e) {
            new Alert(AlertType.ERROR, "Erro de formato numérico: Verifique se 'Ano de Publicação' é um número inteiro e 'Preço' é um número válido.").show();
        } catch (Exception e) {
            new Alert(AlertType.ERROR, "Erro ao salvar livro: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    public void onAdicionarButtonAction() {
        tabela.getSelectionModel().select(-1);
        desabilitarCampos(false);
        desabilitarBotoes(true, true, true, false, false);
        limparCampos();
    }

    @FXML
    public void onAtualizarButtonAction() {
        try {
            LivroView selectedLivroView = tabela.getSelectionModel().getSelectedItem();
            if (selectedLivroView == null) {
                new Alert(AlertType.WARNING, "Selecione um livro para atualizar.").show();
                return;
            }

            Livro livro = livroRepo.loadFromId(selectedLivroView.getId());
            if (livro == null) {
                new Alert(AlertType.ERROR, "Livro não encontrado no banco de dados.").show();
                return;
            }

            livro.setTitulo(tituloField.getText());
            livro.setAutor(autorField.getText());
            livro.setAnoPublicacao(Integer.parseInt(anoPublicacaoField.getText()));

            // --- CORREÇÃO PRINCIPAL APLICADA AQUI ---
            // Substitui a vírgula por um ponto para garantir compatibilidade com parseDouble.
            String precoTexto = precoField.getText().replace(",", ".");
            livro.setPreco(Double.parseDouble(precoTexto));
            // -----------------------------------------

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dataCadastro = null;
            try {
                dataCadastro = dateFormat.parse(dataCadastroField.getText());
            } catch (ParseException e) {
                new Alert(AlertType.ERROR, "Formato de data inválido. Use DD/MM/AAAA.").show();
                return;
            }
            livro.setDataCadastro(dataCadastro);

            livroRepo.update(livro);
            tabela.getItems().set(tabela.getSelectionModel().getSelectedIndex(), modelToView(livro));
            desabilitarCampos(true);
            desabilitarBotoes(false, true, true, true, true);
        } catch (NumberFormatException e) {
            new Alert(AlertType.ERROR, "Erro de formato numérico: Verifique se 'Ano de Publicação' é um número inteiro e 'Preço' é um número válido.").show();
            e.printStackTrace();
        } catch (Exception e) {
            new Alert(AlertType.ERROR, "Erro ao atualizar livro: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    public void onDeletarButtonAction() {
        LivroView selectedLivroView = tabela.getSelectionModel().getSelectedItem();
        if (selectedLivroView == null) {
            new Alert(AlertType.WARNING, "Selecione um livro para deletar.").show();
            return;
        }

        try {
            Livro livro = livroRepo.loadFromId(selectedLivroView.getId());
            if (livro != null) {
                livroRepo.delete(livro);
                tabela.getItems().remove(selectedLivroView);
                limparCampos();
                desabilitarCampos(true);
                desabilitarBotoes(false, true, true, true, true);
            } else {
                new Alert(AlertType.ERROR, "Livro não encontrado no banco de dados para deleção.").show();
            }
        } catch (Exception e) {
            new Alert(AlertType.ERROR, "Erro ao deletar livro: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLivroSelected(LivroView newSelection) {
        if (newSelection != null) {
            idField.setText(Integer.toString(newSelection.getId()));
            tituloField.setText(newSelection.getTitulo());
            autorField.setText(newSelection.getAutor());
            anoPublicacaoField.setText(Integer.toString(newSelection.getAnoPublicacao()));
            // Usa String.format para mostrar a vírgula na interface, o que é amigável para o usuário
            precoField.setText(String.format("%.2f", newSelection.getPreco()));
            dataCadastroField.setText(newSelection.getDataCadastro());
            desabilitarCampos(false); 
            desabilitarBotoes(false, false, false, true, true); 
        } else {
            limparCampos();
            desabilitarCampos(true);
            desabilitarBotoes(false, true, true, true, true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        tituloCol.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        autorCol.setCellValueFactory(new PropertyValueFactory<>("autor"));
        anoPublicacaoCol.setCellValueFactory(new PropertyValueFactory<>("anoPublicacao"));
        precoCol.setCellValueFactory(new PropertyValueFactory<>("preco"));
        dataCadastroCol.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));

        tabela.setItems(loadAllLivros());
        tabela.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldSelection, newSelection) -> {
                    handleLivroSelected(newSelection);
                });

        desabilitarCampos(true);
        desabilitarBotoes(false, true, true, true, true);
    }

    private LivroView modelToView(Livro livro) {
        return new LivroView(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getAnoPublicacao(),
                livro.getPreco(),
                livro.printDataCadastro() 
        );
    }

    private ObservableList<LivroView> loadAllLivros() {
        ObservableList<LivroView> lista = FXCollections.observableArrayList();
        List<Livro> listaFromDatabase = livroRepo.loadAll();
        for (Livro livro : listaFromDatabase) {
            lista.add(modelToView(livro));
        }
        return lista;
    }

    // --- MÉTODOS onAction DO MENU ---

    @FXML
    private void onNovoDBAction(ActionEvent event) {
        new Alert(AlertType.INFORMATION, "Funcionalidade 'Novo Banco de Dados' ainda não implementada.").show();
    }

    @FXML
    private void onAbrirDBAction(ActionEvent event) {
        new Alert(AlertType.INFORMATION, "Funcionalidade 'Abrir Banco de Dados' ainda não implementada.").show();
    }

    @FXML
    private void onExportarJSONAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar para JSON");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos JSON", "*.json"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            if (livroRepo.dumpFile("JSON", file)) {
                new Alert(AlertType.INFORMATION, "Dados exportados com sucesso para JSON!").show();
            } else {
                new Alert(AlertType.ERROR, "Erro ao exportar dados para JSON.").show();
            }
        }
    }

    @FXML
    private void onExportarXMLAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar para XML");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos XML", "*.xml"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            if (livroRepo.dumpFile("XML", file)) {
                new Alert(AlertType.INFORMATION, "Dados exportados com sucesso para XML!").show();
            } else {
                new Alert(AlertType.ERROR, "Erro ao exportar dados para XML.").show();
            }
        }
    }

    @FXML
    private void onImportarJSONAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importar de JSON");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos JSON", "*.json"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            int importedCount = livroRepo.importFile("JSON", file);
            if (importedCount > 0) {
                new Alert(AlertType.INFORMATION, importedCount + " livros importados com sucesso de JSON!").show();
                tabela.setItems(loadAllLivros());
            } else {
                new Alert(AlertType.ERROR, "Nenhum livro importado ou erro na importação de JSON.").show();
            }
        }
    }

    @FXML
    private void onImportarXMLAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importar de XML");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos XML", "*.xml"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            int importedCount = livroRepo.importFile("XML", file);
            if (importedCount > 0) {
                new Alert(AlertType.INFORMATION, importedCount + " livros importados com sucesso de XML!").show();
                tabela.setItems(loadAllLivros());
            } else {
                new Alert(AlertType.ERROR, "Nenhum livro importado ou erro na importação de XML.").show();
            }
        }
    }

    @FXML
    private void onSairAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void onAtualizarTabelaAction(ActionEvent event) {
        tabela.setItems(loadAllLivros());
        new Alert(AlertType.INFORMATION, "Tabela de livros atualizada!").show();
    }

    @FXML
    public void onLimparCamposAction(ActionEvent event) {
        limparCampos();
        desabilitarCampos(true);
        desabilitarBotoes(false, true, true, true, true);
        tabela.getSelectionModel().select(-1);
    }

    @FXML
    private void onSobreAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sobre o Sistema");
        alert.setHeaderText("Sistema de Gerenciamento de Livros - CRUD");
        alert.setContentText("Desenvolvido para demonstrar operações CRUD com JavaFX e ORMLite.\nAutor: [Seu Nome]");
        alert.showAndWait();
    }
}