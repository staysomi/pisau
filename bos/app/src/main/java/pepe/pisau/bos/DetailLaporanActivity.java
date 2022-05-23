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

import pepe.pisau.bos.adapter.DetailLaporanAdapter;
import pepe.pisau.bos.adapter.LaporanAdapter;
import pepe.pisau.bos.data.Data;
import pepe.pisau.bos.model.DetailLaporan;

public class DetailLaporanActivity extends AppCompatActivity {

    DetailLaporanAdapter adapterdlaporan;
    DatabaseReference dbdlaporan;
    RecyclerView rvdLaporan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan);


        String nama = getIntent().getStringExtra("nama");
        TextView textView = findViewById(R.id.tv_nama);
        textView.setText("LAPORAN " + nama);
        textView.setAllCaps(true);
        FirebaseDatabase database = FirebaseDatabase.getInstance(Data.DATABASE_URL);

        dbdlaporan = database.getReference("laporan/" + nama);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        rvdLaporan = findViewById(R.id.rv_detail_laporan);
        rvdLaporan.setLayoutManager(linearLayoutManager);
        rvdLaporan.setHasFixedSize(true);

        Query qu = dbdlaporan;
        qu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<DetailLaporan> list = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    list.add(postSnapshot.getValue(DetailLaporan.class));
                    adapterdlaporan =  new DetailLaporanAdapter(DetailLaporanActivity.this, list);
                    rvdLaporan.setAdapter(adapterdlaporan);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ImageButton refresh = findViewById(R.id.refresh_detail_laporan);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
    }
}