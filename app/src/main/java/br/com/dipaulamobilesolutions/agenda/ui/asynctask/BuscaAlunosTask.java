package br.com.dipaulamobilesolutions.agenda.ui.asynctask;

import android.os.AsyncTask;

import java.util.List;

import br.com.dipaulamobilesolutions.agenda.database.dao.AlunoDAO;
import br.com.dipaulamobilesolutions.agenda.model.activity.Aluno;
import br.com.dipaulamobilesolutions.agenda.ui.adapter.ListaAlunosAdapter;

//asynctask recebe parametros para doinbackground, progress, e tipo de retorno do onpostExecute
public class BuscaAlunosTask extends AsyncTask<Void,Void, List<Aluno>> {
    private final AlunoDAO dao;
    private final ListaAlunosAdapter adapter;

    public BuscaAlunosTask(AlunoDAO dao, ListaAlunosAdapter adapter) {

        this.dao = dao;
        this.adapter = adapter;
    }

    @Override
    protected List<Aluno> doInBackground(Void[] objects) {
        return dao.todos();
    }

    @Override
    protected void onPostExecute(List<Aluno> todosAlunos) {
        super.onPostExecute(todosAlunos);
        adapter.atualiza((List<Aluno>) todosAlunos);

    }
}
