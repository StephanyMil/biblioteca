package view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Classe View para Livro - adaptada para JavaFX TableView
 * Utiliza Properties para binding com componentes JavaFX
 * 
 * @author stephanymilhomem
 * @version 1.0
 */
public class LivroView {
    
    private SimpleIntegerProperty id;
    private SimpleStringProperty titulo;
    private SimpleStringProperty autor;
    private SimpleIntegerProperty anoPublicacao;
    private SimpleDoubleProperty preco;
    private SimpleStringProperty dataCadastro;
    
    /**
     * Construtor da classe LivroView
     */
    public LivroView(int id, String titulo, String autor, 
                     int anoPublicacao, double preco, String dataCadastro) {
        this.id = new SimpleIntegerProperty(id);
        this.titulo = new SimpleStringProperty(titulo);
        this.autor = new SimpleStringProperty(autor);
        this.anoPublicacao = new SimpleIntegerProperty(anoPublicacao);
        this.preco = new SimpleDoubleProperty(preco);
        this.dataCadastro = new SimpleStringProperty(dataCadastro);
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
    
    /**GET Method Property titulo*/
    public String getTitulo() {
        return this.titulo.get();
    }
    
    /**SET Method Property titulo*/
    public void setTitulo(String titulo) {
        this.titulo.set(titulo);
    }
    
    /**GET Method Property autor*/
    public String getAutor() {
        return this.autor.get();
    }
    
    /**SET Method Property autor*/
    public void setAutor(String autor) {
        this.autor.set(autor);
    }
    
    /**GET Method Property anoPublicacao*/
    public int getAnoPublicacao() {
        return this.anoPublicacao.get();
    }
    
    /**SET Method Property anoPublicacao*/
    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao.set(anoPublicacao);
    }
    
    /**GET Method Property preco*/
    public double getPreco() {
        return this.preco.get();
    }
    
    /**SET Method Property preco*/
    public void setPreco(double preco) {
        this.preco.set(preco);
    }
    
    /**GET Method Property dataCadastro*/
    public String getDataCadastro() {
        return this.dataCadastro.get();
    }
    
    /**SET Method Property dataCadastro*/
    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro.set(dataCadastro);
    }
}
