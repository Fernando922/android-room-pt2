package br.com.dipaulamobilesolutions.agenda.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.com.dipaulamobilesolutions.agenda.database.converter.ConversorCalendar;
import br.com.dipaulamobilesolutions.agenda.database.dao.AlunoDAO;
import br.com.dipaulamobilesolutions.agenda.model.activity.Aluno;

import static br.com.dipaulamobilesolutions.agenda.database.AgendaMigrations.TODAS_MIGRATIONS;

@Database(entities = {Aluno.class}, version = 5, exportSchema = false)
@TypeConverters({ConversorCalendar.class})
public abstract class AgendaDatabase extends RoomDatabase {

    private static final String NOME_BANCO_DE_DADOS = "agenda.db";


    public abstract AlunoDAO getRoomAlunoDAO();


    public static AgendaDatabase getInstance(Context context) {
        return Room.databaseBuilder(context, AgendaDatabase.class, NOME_BANCO_DE_DADOS)
                .allowMainThreadQueries()
                //.fallbackToDestructiveMigration()  //perde todos os dados, nao recomendado se estiver em prod
                .addMigrations(TODAS_MIGRATIONS)
                .build();
    }


}
