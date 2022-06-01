package pepe.pisau.bos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import pepe.pisau.bos.adapter.LaporanAdapter;
import pepe.pisau.bos.data.Data;
import pepe.pisau.bos.model.Laporan;

public class ListLaporanActivity extends AppCompatActivity {

    LaporanAdapter adapterlaporan;
    DatabaseReference dblaporan;
    RecyclerView rvLaporan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_laporan);

        String nama = getIntent().getStringExtra("nama");
        TextView textView = findViewById(R.id.tv_nama);
        textView.setText("BOS " + nama);
        textView.setAllCaps(true);
        FirebaseDatabase database = FirebaseDatabase.getInstance(Data.DATABASE_URL);

        dblaporan = database.getReference("pegawai");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        rvLaporan = findViewById(R.id.rv_laporan);
        rvLaporan.setLayoutManager(linearLayoutManager);
        rvLaporan.setHasFixedSize(true);

        Query qu = dblaporan;
        qu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Laporan> list = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    list.add(postSnapshot.getValue(Laporan.class));
                    adapterlaporan = new LaporanAdapter(ListLaporanActivity.this, list);
                    rvLaporan.setAdapter(adapterlaporan);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ImageButton refresh = findViewById(R.id.refresh_laporan);
        refresh.setOnClickListener(v -> {
            finish();
            startActivity(getIntent());
        });
    }
}