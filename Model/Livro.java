package Model;

/**
 * Escreva uma descrição da classe Livro aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
import java.util.Date;
import java.text.SimpleDateFormat;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;
import javax.xml.bind.annotation.*;

@DatabaseTable(tableName = "livro")
@XmlRootElement(name = "livro")
@XmlAccessorType(XmlAccessType.FIELD)
public class Livro {

    @DatabaseField(generatedId = true)
    @XmlElement
    private int id;

    @DatabaseField(canBeNull = false)
    @XmlElement
    private String titulo;

    @DatabaseField
    @XmlElement
    private String autor;

    @DatabaseField
    @XmlElement
    public int anoPublicacao;

    @DatabaseField
    @XmlElement
    public double preco;

    @DatabaseField(dataType = DataType.DATE)
    @XmlElement
    public Date dataCadastro;

    // Construtor vazio é necessário para ORMLite e JAXB
    public Livro() {
    }

    public Livro(String titulo, String autor, int anoPublicacao, double preco, Date dataCadastro) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.preco = preco;
        this.dataCadastro = dataCadastro;
    }

    public String printDataCadastro() {
        if (dataCadastro == null) return "Data não informada";
        SimpleDateFormat dateFor = new SimpleDateFormat("dd/MM/yyyy");
        return dateFor.format(dataCadastro);
    }

    @Override
    public String toString() {
        return String.format("Livro[ID: %d, Título: %s, Autor: %s, Ano: %d, Preço: %.2f, Data: %s]",
                            id, titulo, autor, anoPublicacao, preco, printDataCadastro());
    }

    // --- Getters e Setters ---

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitulo(){
        return this.titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public String getAutor(){
        return this.autor;
    }

    public void setAutor(String autor){
        this.autor = autor;
    }

    public int getAnoPublicacao(){
        return this.anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao){
        this.anoPublicacao = anoPublicacao;
    }

    public double getPreco(){
        return this.preco;
    }

    public void setPreco(double preco){
        this.preco = preco;
    }

    public Date getDataCadastro(){
        return this.dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro){
        this.dataCadastro = dataCadastro;
    }
}