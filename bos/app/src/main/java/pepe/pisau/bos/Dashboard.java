package pepe.pisau.bos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Dashboard extends AppCompatActivity {

    TextView nama;
    Button list_peg, list_lap;

    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        String strnama = getIntent().getStringExtra("nama");
        nama = findViewById(R.id.tv_nama);
        nama.setText(strnama);
        nama.setAllCaps(true);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();

        if (mFirebaseUser != null) {
            currentUserID = mFirebaseUser.getUid();
        } else {
            logout();
        }

        list_peg = findViewById(R.id.list_pegawai);
        list_peg.setOnClickListener(v -> {
            Intent inte = new Intent(Dashboard.this, ListPegawaiActivity.class);
            inte.putExtra("nama", strnama);
            startActivity(inte);
        });

        list_lap = findViewById(R.id.list_laporan);
        list_lap.setOnClickListener(v -> {
            Intent inte = new Intent(Dashboard.this, ListLaporanActivity.class);
            inte.putExtra("nama", strnama);
            startActivity(inte);
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Dashboard.this);
        builder1.setMessage("Yakin ingin keluar?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ya",
                (dialog, id) -> {
                    logout();
                });

        builder1.setNegativeButton(
                "Tidak",
                (dialog, id) -> dialog.cancel());
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(Dashboard.this, MainActivity.class));
    }
}