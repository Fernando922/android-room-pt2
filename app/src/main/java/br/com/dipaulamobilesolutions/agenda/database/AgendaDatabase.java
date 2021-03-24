package br.com.dipaulamobilesolutions.agenda.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.com.dipaulamobilesolutions.agenda.database.converter.ConversorCalendar;
import br.com.dipaulamobilesolutions.agenda.database.converter.ConversorTipoTelefone;
import br.com.dipaulamobilesolutions.agenda.database.dao.AlunoDAO;
import br.com.dipaulamobilesolutions.agenda.database.dao.TelefoneDAO;
import br.com.dipaulamobilesolutions.agenda.model.activity.Aluno;
import br.com.dipaulamobilesolutions.agenda.model.activity.Telefone;

import static br.com.dipaulamobilesolutions.agenda.database.AgendaMigrations.TODAS_MIGRATIONS;

@Database(entities = {Aluno.class, Telefone.class}, version = 6, exportSchema = false)
@TypeConverters({ConversorCalendar.class, ConversorTipoTelefone.class})
public abstract class AgendaDatabase extends RoomDatabase {

    private static final String NOME_BANCO_DE_DADOS = "agenda.db";

    public abstract AlunoDAO getRoomAlunoDAO();
    public abstract TelefoneDAO getTelefoneDAO();


    public static AgendaDatabase getInstance(Context context) {
        return Room.databaseBuilder(context, AgendaDatabase.class, NOME_BANCO_DE_DADOS)
                //.allowMainThreadQueries()  nao recomendado, deve executar em thread separada
                //.fallbackToDestructiveMigration()  //perde todos os dados, nao recomendado se estiver em prod
                .addMigrations(TODAS_MIGRATIONS)
                .build();
    }


}
