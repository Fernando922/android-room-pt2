package br.com.dipaulamobilesolutions.agenda.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.dipaulamobilesolutions.agenda.model.activity.Aluno;

@Dao
public interface AlunoDAO {

    @Insert  //ja implementa o metodo de salvar no banco, desde que o model passado seja uma entidade
    void salva(Aluno aluno);

    @Query("SELECT * FROM Aluno")  //neste caso a query deve ser manual
    List<Aluno> todos();

    @Delete   // este método já remove o aluno informado
    void remove(Aluno aluno);

    @Update
    void edita(Aluno aluno);
}
