package view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty; // Para Date, se necessário

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Classe View para Autor - adaptada para JavaFX TableView
 * Utiliza Properties para binding com componentes JavaFX
 * * @author stephanymilhomem
 * @version 1.0
 */
public class AutorView {
    
    private SimpleIntegerProperty id;
    private SimpleStringProperty nome;
    private SimpleStringProperty nacionalidade;
    private SimpleStringProperty dataNascimento; // Usaremos String para exibição formatada
    
    /**
     * Construtor da classe AutorView
     */
    public AutorView(int id, String nome, String nacionalidade, String dataNascimento) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.nacionalidade = new SimpleStringProperty(nacionalidade);
        this.dataNascimento = new SimpleStringProperty(dataNascimento);
    }

    // Getters e Setters para Properties JavaFX
    
    /**GET Method Property id*/
    public int getId() {
        return this.id.get();
    }
    
    /**SET Method Property id*/
    public void setId(int id) {
        this.id.set(id);
    }
    
    /**GET Method Property nome*/
    public String getNome() {
        return this.nome.get();
    }
    
    /**SET Method Property nome*/
    public void setNome(String nome) {
        this.nome.set(nome);
    }
    
    /**GET Method Property nacionalidade*/
    public String getNacionalidade() {
        return this.nacionalidade.get();
    }
    
    /**SET Method Property nacionalidade*/
    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade.set(nacionalidade);
    }
    
    /**GET Method Property dataNascimento*/
    public String getDataNascimento() {
        return this.dataNascimento.get();
    }
    
    /**SET Method Property dataNascimento*/
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento.set(dataNascimento);
    }
}
