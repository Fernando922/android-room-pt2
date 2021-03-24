package br.com.dipaulamobilesolutions.agenda.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.room.Room;

import br.com.dipaulamobilesolutions.agenda.database.AgendaDatabase;
import br.com.dipaulamobilesolutions.agenda.database.dao.AlunoDAO;
import br.com.dipaulamobilesolutions.agenda.model.activity.Aluno;
import br.com.dipaulamobilesolutions.agenda.ui.adapter.ListaAlunosAdapter;
import br.com.dipaulamobilesolutions.agenda.ui.asynctask.BuscaAlunosTask;
import br.com.dipaulamobilesolutions.agenda.ui.asynctask.RemoveAlunoTask;

public class ListaAlunosView {


    private Context context;
    private final AlunoDAO dao;
    private ListaAlunosAdapter adapter;

    public ListaAlunosView(Context context){
        this.context = context;
        AlunoDAO dao = Room.databaseBuilder(context, AgendaDatabase.class, "agenda.db")
                .allowMainThreadQueries()
                .build()
                .getRoomAlunoDAO();

        this.dao = AgendaDatabase.getInstance(context).getRoomAlunoDAO();
    }

    public void confirmaRemocao(MenuItem item) {
        new AlertDialog
                .Builder(context)
                .setTitle("Removendo aluno")
                .setMessage("Tem certeza que quer remover o aluno?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AdapterView.AdapterContextMenuInfo menuInfo =
                                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                        Aluno alunoEscolhido = adapter.getItem(menuInfo.position);
                        remove(alunoEscolhido);
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }



    public void configuraAdapter(ListView listaDeAlunos) {
        adapter = new ListaAlunosAdapter(context);
        listaDeAlunos.setAdapter(adapter);
    }

    private void remove(Aluno aluno) {
        new RemoveAlunoTask(dao, adapter, aluno).execute();

    }

    public void atualizaAlunos() {
        new BuscaAlunosTask(dao, adapter).execute();
    }

}
