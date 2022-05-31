package pepe.pisau.pegawai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import pepe.pisau.pegawai.data.Data;
import pepe.pisau.pegawai.model.Laporan;

public class AddLaporanActivity extends AppCompatActivity {

    Laporan laporan;
    EditText et_total;
    TextView tv_perongkos;
    Spinner spn_produk, spn_pekerjaan;
    DatabaseReference dbongkos, dbproduk, dbpekerjaan;
    String nama, nama_produk = "", nama_pekerjaan = "", idpekerjaan, imageuid;
    int ongkos = 0;
    private Button btnSelect, btnAdd;
    private ImageView imageViewBukti;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;
    StorageReference storageReference;
    ImageButton buttonClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_laporan);

        nama = getIntent().getStringExtra("paramnama");

        et_total = findViewById(R.id.et_total);
        spn_produk = findViewById(R.id.spinner_produk);
        spn_pekerjaan = findViewById(R.id.spinner_pekerjaan);
        tv_perongkos = findViewById(R.id.tv_perongkos);
        btnSelect = findViewById(R.id.btnChoose);
        btnAdd = findViewById(R.id.btn_pp_add_laporan);
        imageViewBukti = findViewById(R.id.imgView);
        buttonClose = findViewById(R.id.btn_pp_al_close);
        storageReference = FirebaseStorage.getInstance().getReference();

        FirebaseDatabase database = FirebaseDatabase.getInstance(Data.DATABASE_URL);
        dbproduk = database.getReference("produk/");
        Query queryproduk = dbproduk;
        queryproduk.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> listproduk = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String produk = postSnapshot.child("produk").getValue(String.class);
                    listproduk.add(produk);
                }
                ArrayAdapter<String> adapterproduk = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner, listproduk);
                spn_produk.setAdapter(adapterproduk);
                nama_produk = spn_produk.getSelectedItem().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbpekerjaan = database.getReference("jenis-pekerjaan/");
        Query querypekerjaan = dbpekerjaan;
        querypekerjaan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> listpekerjaan = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    listpekerjaan.add(postSnapshot.child("pekerjaan").getValue(String.class));
                }
                ArrayAdapter<String> adapterpekerjaan = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner, listpekerjaan);
                spn_pekerjaan.setAdapter(adapterpekerjaan);
                spn_pekerjaan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(view.getContext(), "Selected", Toast.LENGTH_SHORT).show();
                        nama_pekerjaan = spn_pekerjaan.getSelectedItem().toString();
                        switch (nama_pekerjaan) {
                            case "Motong + Meksemi":
                                idpekerjaan = "1";
                                break;
                            case "Nyelenet Mesin":
                                idpekerjaan = "2";
                                break;
                            case "Ngelus":
                                idpekerjaan = "3";
                                break;
                            case "Nyekrap":
                                idpekerjaan = "4";
                                break;
                            case "Nyepuhi":
                                idpekerjaan = "5";
                                break;
                            case "Ngenjingne":
                                idpekerjaan = "6";
                                break;
                            case "Moles":
                                idpekerjaan = "7";
                                break;
                            case "Ngebur":
                                idpekerjaan = "8";
                                break;
                        }

                        Query queryongkos = database.getReference("jenis-pekerjaan/" + idpekerjaan);
                        queryongkos.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ongkos = snapshot.child("ongkos").getValue(Integer.class);
                                tv_perongkos.setText("Ongkos /item : " + ongkos);

                                btnSelect.setOnClickListener(v -> {
                                    Intent intent = new Intent();
                                    intent.setType("image/*");
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    startActivityForResult(
                                            Intent.createChooser(
                                                    intent,
                                                    "Select Image from here..."),
                                            PICK_IMAGE_REQUEST);
                                });
                                btnAdd.setOnClickListener(v -> {
                                    if (et_total.getText().toString().equals("")) {
                                        Toast.makeText(view.getContext(), "Isi Data", Toast.LENGTH_SHORT).show();
                                    } else {
                                        int total = Integer.parseInt(et_total.getText().toString());
                                        String idlaporan = UUID.randomUUID().toString();
                                        if (filePath != null) {
                                            imageuid = UUID.randomUUID().toString();
                                            StorageReference ref = storageReference
                                                    .child("images/").child(nama).child(imageuid);
                                            ref.putFile(filePath).addOnSuccessListener(
                                                            taskSnapshot -> {
                                                                ref.getDownloadUrl().addOnSuccessListener(o -> {
                                                                    addDatatoFirebase(o.toString(), idlaporan, nama, ongkos * total, nama_pekerjaan, nama_produk, "pending", total);
                                                                    Toast.makeText(AddLaporanActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                                                                });
                                                            })

                                                    .addOnFailureListener(e -> Toast.makeText(AddLaporanActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show());
                                            Toast.makeText(view.getContext(), "Sukses Tambah Laporan", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(view.getContext(), "Harus Upload Bukti", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        buttonClose.setOnClickListener(v -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,
                resultCode,
                data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images
                        .Media.getBitmap(getContentResolver(), filePath);
                imageViewBukti.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addDatatoFirebase(String bukti, String id, String nama, int ongkostotal, String pekerjaan, String produk, String status, int total) {
        laporan = new Laporan();
        laporan.setBukti(bukti);
        laporan.setId(id);
        laporan.setNama(nama);
        laporan.setOngkostotal(ongkostotal);
        laporan.setPekerjaan(pekerjaan);
        laporan.setProduk(produk);
        laporan.setStatus(status);
        laporan.setTotal(total);
        DatabaseReference dbl = FirebaseDatabase.getInstance(Data.DATABASE_URL).getReference("laporan").child(nama).child(id);

        dbl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dbl.setValue(laporan);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}