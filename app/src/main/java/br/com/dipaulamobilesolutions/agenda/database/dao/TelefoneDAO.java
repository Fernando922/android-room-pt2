package br.com.dipaulamobilesolutions.agenda.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.com.dipaulamobilesolutions.agenda.model.activity.Telefone;

@Dao
public interface TelefoneDAO {

    @Query("SELECT * FROM Telefone WHERE alunoId = :alunoId LIMIT 1")
    Telefone buscaPrimeiroTelefoneDoAluno(int alunoId);

    @Insert  //pode-se passar vários parametros!
    void salva(Telefone... telefones);  //varargs vários parametros do mesmo tipo!

    @Query("SELECT * FROM Telefone WHERE alunoId = :alunoId")
    List<Telefone> buscaTodosTelefonesDoAluno(int alunoId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)  //mas se ja existir com o mesmo ID ele vai atualizar
    void atualiza(Telefone... telefones);
}
