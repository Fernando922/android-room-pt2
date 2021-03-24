package br.com.dipaulamobilesolutions.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.com.dipaulamobilesolutions.agenda.R;
import br.com.dipaulamobilesolutions.agenda.database.AgendaDatabase;
import br.com.dipaulamobilesolutions.agenda.database.dao.AlunoDAO;
import br.com.dipaulamobilesolutions.agenda.database.dao.TelefoneDAO;
import br.com.dipaulamobilesolutions.agenda.model.activity.Aluno;
import br.com.dipaulamobilesolutions.agenda.model.activity.Telefone;
import br.com.dipaulamobilesolutions.agenda.model.activity.TipoTelefone;
import br.com.dipaulamobilesolutions.agenda.ui.asynctask.BuscaTodosTelefonesDoAlunoTask;
import br.com.dipaulamobilesolutions.agenda.ui.asynctask.EditaAlunoTask;
import br.com.dipaulamobilesolutions.agenda.ui.asynctask.SalvaAlunoTask;

import static br.com.dipaulamobilesolutions.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;


public class FormularioAlunoActivity extends AppCompatActivity {


    public static final String TITULO_APPBAR_NOVO_ALUNO = "Novo aluno";
    private static final String TITULO_APPBAR_EDITA_ALUNO = "Edita aluno";
    private EditText etNome;
    private EditText etTelefoneFixo;
    private EditText etTelefoneCelular;
    private EditText etEmail;
    private Aluno aluno;
    private AlunoDAO alunoDAO;
    private TelefoneDAO telefoneDAO;
    private List<Telefone> telefonesDoAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        AgendaDatabase database = AgendaDatabase.getInstance(this);
        telefoneDAO = database.getTelefoneDAO();
        alunoDAO = database.getRoomAlunoDAO();

        inicializarCampos();
        carregaAluno();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_salvar) {
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, "oioi", Toast.LENGTH_SHORT).show();
        return super.onContextItemSelected(item);
    }

    private void inicializarCampos() {
        etNome = findViewById(R.id.etNome);
        etTelefoneFixo = findViewById(R.id.etTelefone_fixo);
        etTelefoneCelular = findViewById(R.id.etTelefone_celular);
        etEmail = findViewById(R.id.etEmail);
    }


    private void carregaAluno() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_ALUNO)) {  //se estiver editando, então chegaram novas referencias!
            setTitle(TITULO_APPBAR_EDITA_ALUNO);
            aluno = (Aluno) dados.getSerializableExtra("aluno");  //casting para aluno
            preencheCampos();
        } else {
            setTitle(TITULO_APPBAR_NOVO_ALUNO);
            aluno = new Aluno();
        }
    }

    private void preencheAluno() {
        String nome = etNome.getText().toString();
        String email = etEmail.getText().toString();
        aluno.setNome(nome);
        aluno.setEmail(email);
    }

    private void preencheCampos() {
        etNome.setText(aluno.getNome());
        etEmail.setText(aluno.getEmail());
        preencheCamposDeTelefone();
    }

    private void preencheCamposDeTelefone() {
        new BuscaTodosTelefonesDoAlunoTask(telefoneDAO, aluno, telefones -> {
            this.telefonesDoAluno = telefones;
            for (Telefone telefone : telefonesDoAluno) {
                if (telefone.getTipo() == TipoTelefone.FIXO) {
                    etTelefoneFixo.setText(telefone.getNumero());
                } else {
                    etTelefoneCelular.setText(telefone.getNumero());
                }
            }
        }).execute();
    }


    private void finalizaFormulario() {
        preencheAluno();
        Telefone telefoneFixo = criaTelefone(etTelefoneFixo, TipoTelefone.FIXO);
        Telefone telefoneCelular = criaTelefone(etTelefoneCelular, TipoTelefone.CELULAR);
        if (aluno.temIdValido()) {
            editaAluno(telefoneFixo, telefoneCelular);
        } else {
            salvaAluno(telefoneFixo, telefoneCelular);
        }
    }

    private Telefone criaTelefone(EditText etTelefoneFixo, TipoTelefone fixo) {
        String numeroFixo = etTelefoneFixo.getText().toString();
        return new Telefone(numeroFixo, fixo);
    }

    private void salvaAluno(Telefone telefoneFixo, Telefone telefoneCelular) {
        new SalvaAlunoTask(alunoDAO, aluno, telefoneFixo, telefoneCelular, telefoneDAO, this::finish).execute();
    }

    private void editaAluno(Telefone telefoneFixo, Telefone telefoneCelular) {
        new EditaAlunoTask(alunoDAO, aluno, telefoneFixo, telefoneCelular, telefoneDAO, telefonesDoAluno, this::finish).execute();

    }


}