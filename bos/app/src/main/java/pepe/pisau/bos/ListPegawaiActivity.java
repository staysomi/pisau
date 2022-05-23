package pepe.pisau.bos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import pepe.pisau.bos.adapter.PegawaiAdapter;
import pepe.pisau.bos.data.Pegawai;

public class ListPegawaiActivity extends AppCompatActivity {

    PegawaiAdapter adapter;
    DatabaseReference mbase;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pegawai);

//        startActivity(getIntent());
        String nama = getIntent().getStringExtra("nama");
        TextView textView = findViewById(R.id.tv_nama);
        textView.setText(nama);
        textView.setAllCaps(true);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://pisau-tulungagung-default-rtdb.asia-southeast1.firebasedatabase.app/");

        mbase = database.getReference("pegawai");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.rv_pegawai);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        Query qu = mbase;
        qu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Pegawai> list = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    list.add(postSnapshot.getValue(Pegawai.class));
                    adapter =  new PegawaiAdapter(ListPegawaiActivity.this, list);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ImageButton add = findViewById(R.id.add_pegawai);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpAddPegawai popUpClass = new PopUpAddPegawai();
                popUpClass.showPopupWindow(v);
            }
        });

        ImageButton refresh = findViewById(R.id.refresh_pegawai);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
    }
}