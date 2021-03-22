package br.com.dipaulamobilesolutions.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.dipaulamobilesolutions.agenda.R;
import br.com.dipaulamobilesolutions.agenda.database.AgendaDatabase;
import br.com.dipaulamobilesolutions.agenda.database.dao.AlunoDAO;
import br.com.dipaulamobilesolutions.agenda.model.activity.Aluno;

import static br.com.dipaulamobilesolutions.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;


public class FormularioAlunoActivity extends AppCompatActivity {


    public static final String TITULO_APPBAR_NOVO_ALUNO = "Novo aluno";
    private static final String TITULO_APPBAR_EDITA_ALUNO = "Edita aluno";
    private EditText etNome;
    private EditText etSobrenome;
    private EditText etTelefone;
    private EditText etEmail;
    private Aluno aluno;
    private AlunoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        inicializarCampos();
        carregaAluno();

        AgendaDatabase database = AgendaDatabase.getInstance(this);
        this.dao = database.getRoomAlunoDAO();
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

    private void preencheCampos() {
        etNome.setText(aluno.getNome());
        etSobrenome.setText(aluno.getSobrenome());
        etTelefone.setText(aluno.getTelefone());
        etEmail.setText(aluno.getEmail());
    }


    private void finalizaFormulario() {
        preencheAluno();
        if (aluno.temIdValido()) {
            dao.edita(aluno);
        } else {
            dao.salva(aluno);
        }
        finish();
    }

    private void inicializarCampos() {
        etNome = findViewById(R.id.etnome);
        etSobrenome = findViewById(R.id.etSobrenome);
        etTelefone = findViewById(R.id.etTelefone);
        etEmail = findViewById(R.id.etEmail);
    }

    private void preencheAluno() {
        String nome = etNome.getText().toString();
        String sobrenome = etSobrenome.getText().toString();
        String telefone = etTelefone.getText().toString();
        String email = etEmail.getText().toString();


        aluno.setNome(nome);
        aluno.setSobrenome(sobrenome);
        aluno.setTelefone(telefone);
        aluno.setEmail(email);
    }
}