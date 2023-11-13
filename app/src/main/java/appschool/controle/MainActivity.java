package appschool.controle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import appschool.wsapi.wsApi;

public class MainActivity extends AppCompatActivity {
    EditText edtNome, edtIdade, edtEscola;
    TextView txtErro;
    Button btnConsultar;
    JSONObject jsondatareturn;
    ExecutorService executor;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtNome = findViewById(R.id.edtNome);
        edtIdade = findViewById(R.id.edtIdade);
        edtEscola = findViewById(R.id.edtEscola);
        txtErro = findViewById(R.id.txtErro);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConsultarAluno(view);
            }
        });
    }

    public void ConsultarAluno(View view) {
        String urlAddress = "http://192.168.200.2/alunos/3DSA/_profcarlos/banco",
                urlParameters = "/usuarios.php";
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                jsondatareturn = new wsApi().ReturnData(urlAddress, urlParameters);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            edtNome.setText(jsondatareturn.getString("nome"));
                            edtIdade.setText(jsondatareturn.getString("idade"));
                            edtEscola.setText(jsondatareturn.getString("escola"));
                        } catch (Exception e) {
                            txtErro.setText("Erro consulta" + e.toString());
                        }
                    }
                });
            }
        });
    }
}