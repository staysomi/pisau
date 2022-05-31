package pepe.pisau.pegawai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import pepe.pisau.pegawai.adapter.DetailLaporanAdapter;
import pepe.pisau.pegawai.data.Data;
import pepe.pisau.pegawai.model.DetailLaporan;

public class Dashboard extends AppCompatActivity {

    DetailLaporanAdapter adapterdlaporan;
    DatabaseReference dbdlaporan;
    RecyclerView rvdLaporan;
    TextView ongkostotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


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
                    adapterdlaporan =  new DetailLaporanAdapter(Dashboard.this, list);
                    rvdLaporan.setAdapter(adapterdlaporan);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ongkostotal = findViewById(R.id.total_ongkos);
        Query qw = dbdlaporan.orderByChild("status").equalTo("accepted");
        qw.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Integer> total = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    total.add(postSnapshot.child("ongkostotal").getValue(Integer.class));
                }
                int sum = 0;
                for(int i = 0; i < total.size(); i++){
                    sum += total.get(i);
                }
                ongkostotal.setText("Total Ongkos : " + sum);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ImageButton add = findViewById(R.id.add_laporan);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, AddLaporanActivity.class);
                i.putExtra("paramnama", nama);
                startActivity(i);
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