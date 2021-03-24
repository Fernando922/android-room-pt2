package br.com.dipaulamobilesolutions.agenda.ui.asynctask;

import android.os.AsyncTask;
import android.widget.TextView;

import br.com.dipaulamobilesolutions.agenda.database.dao.TelefoneDAO;
import br.com.dipaulamobilesolutions.agenda.model.activity.Telefone;

public class BuscaPrimeiroTelefoneDoAlunoTask extends AsyncTask<Void, Void, Telefone> {

    private final TelefoneDAO dao;
    private final int alunoId;
    private final PrimeiroTelefoneEncontradoListener listener;

    public BuscaPrimeiroTelefoneDoAlunoTask(TelefoneDAO dao, int alunoId, PrimeiroTelefoneEncontradoListener listener) {
        this.dao = dao;
        this.alunoId = alunoId;
        this.listener = listener;
    }


    @Override
    protected Telefone doInBackground(Void... voids) {
        Telefone primeiroTelefone = dao.buscaPrimeiroTelefoneDoAluno(alunoId);
        return primeiroTelefone;
    }

    @Override
    protected void onPostExecute(Telefone telefoneEncontrado) {
        super.onPostExecute(telefoneEncontrado);
        listener.quandoEncontrado(telefoneEncontrado);
    }

    public interface PrimeiroTelefoneEncontradoListener {
        void quandoEncontrado(Telefone telefoneEncontrado);
    }
}
