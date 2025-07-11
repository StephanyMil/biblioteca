package Model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class AutorRepository {

    private static Dao<Autor, Integer> dao;

    public AutorRepository(Database db) {
        try {
            dao = DaoManager.createDao(db.getConnection(), Autor.class);
            TableUtils.createTableIfNotExists(db.getConnection(), Autor.class);
        } catch (SQLException e) {
            System.out.println("Erro ao criar DAO ou tabela para Autor: " + e.getMessage());
        }
    }

    public Autor create(Autor autor) {
        try {
            dao.create(autor);
        } catch (SQLException e) {
            System.out.println("Erro ao criar autor: " + e.getMessage());
        }
        return autor;
    }

    public void update(Autor autor) {
        try {
            dao.update(autor);
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar autor: " + e.getMessage());
        }
    }

    public void delete(Autor autor) {
        try {
            dao.delete(autor);
        } catch (SQLException e) {
            System.out.println("Erro ao deletar autor: " + e.getMessage());
        }
    }

    public List<Autor> loadAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            System.out.println("Erro ao carregar todos os autores: " + e.getMessage());
        }
        return Collections.emptyList(); // Retorna lista vazia em caso de erro
    }
}
