package ipo2.es.calculadorarcv.presentacion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.codesgood.views.JustifiedTextView;

import java.util.Objects;

import ipo2.es.calculadorarcv.R;

public class RecomendacionesActivity extends AppCompatActivity {

    private TextView twTitulo1;
    private TextView twTitulo2;
    private JustifiedTextView jtvInfo1;
    private JustifiedTextView jtvInfo2;
    private String txtTitulo1, txtTitulo2, txtInfo1, txtInfo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendaciones);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Bundle bundle=getIntent().getExtras();
        this.txtTitulo1=bundle.getString("titulo1");
        this.txtTitulo2=bundle.getString("titulo2");
        this.txtInfo1=bundle.getString("info1");
        this.txtInfo2=bundle.getString("info2");

        initViews();
        setText();
    }

    private void setText() {
        twTitulo1.setText(txtTitulo1);
        twTitulo2.setText(txtTitulo2);
        jtvInfo1.setText(txtInfo1);
        jtvInfo2.setText(txtInfo2);
    }

    private void initViews() {
        twTitulo1 = findViewById(R.id.txtTitulo1);
        twTitulo2 = findViewById(R.id.txtTitulo2);
        jtvInfo1 = findViewById(R.id.txtInfoTitulo1);
        jtvInfo2 = findViewById(R.id.txtInfoTitulo2);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }
}
