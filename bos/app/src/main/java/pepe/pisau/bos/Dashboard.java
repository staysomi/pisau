package pepe.pisau.bos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    TextView nama;
    Button list_peg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        String strnama = getIntent().getStringExtra("nama");
        nama = findViewById(R.id.tv_nama);
        nama.setText(strnama);

        list_peg = findViewById(R.id.list_pegawai);
        list_peg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(Dashboard.this, ListPegawaiActivity.class);
                inte.putExtra("nama", strnama);
                startActivity(inte);
            }
        });
    }
}