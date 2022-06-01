package pepe.pisau.pegawai;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import pepe.pisau.pegawai.data.Data;
import pepe.pisau.pegawai.model.Laporan;

public class UpdateLaporanActivity extends AppCompatActivity {

    Laporan laporan;
    EditText et_total;
    TextView tv_perongkos;
    Spinner spn_produk, spn_pekerjaan;
    DatabaseReference dbongkos, dbproduk, dbpekerjaan;
    String idlaporan, nama, total, pekerjaan, produk, nama_produk = "", nama_pekerjaan = "", idpekerjaan, imageuid, imageurl;
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
        setContentView(R.layout.activity_update_laporan);

        nama = getIntent().getStringExtra("nama");
        imageurl = getIntent().getStringExtra("image");
        idlaporan = getIntent().getStringExtra("id");
        total = getIntent().getStringExtra("total");
        pekerjaan = getIntent().getStringExtra("pekerjaan");
        produk = getIntent().getStringExtra("produk");

        et_total = findViewById(R.id.et_total_u);
        et_total.setText(total);

        spn_produk = findViewById(R.id.spinner_produk_u);
        spn_pekerjaan = findViewById(R.id.spinner_pekerjaan_u);

        tv_perongkos = findViewById(R.id.tv_perongkos_u);
        btnSelect = findViewById(R.id.btnChoose_u);
        btnAdd = findViewById(R.id.btn_u_laporan);
        imageViewBukti = findViewById(R.id.imgView_u);
        Picasso.get().load(imageurl).into(imageViewBukti);

        buttonClose = findViewById(R.id.btn_ul_close);
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
                spn_produk.setSelection(getProduk(produk));
                spn_produk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        nama_produk = spn_produk.getSelectedItem().toString();
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
                                spn_pekerjaan.setSelection(getPekerjaan(pekerjaan));
                                spn_pekerjaan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        Toast.makeText(view.getContext(), "Selected", Toast.LENGTH_SHORT).show();
                                        nama_pekerjaan = spn_pekerjaan.getSelectedItem().toString();
                                        idpekerjaan = getIdPekerjaan(nama_pekerjaan);

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
                                                        if (filePath != null) {
                                                            imageuid = UUID.randomUUID().toString();
                                                            StorageReference ref = storageReference
                                                                    .child("images/").child(nama).child(imageuid);
                                                            ref.putFile(filePath).addOnSuccessListener(
                                                                            taskSnapshot -> {
                                                                                ref.getDownloadUrl().addOnSuccessListener(o -> {
                                                                                    updateDatatoFirebase(o.toString(), idlaporan, nama, ongkos * total, nama_pekerjaan, nama_produk, total);
                                                                                    Toast.makeText(UpdateLaporanActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                                                                                });
                                                                            })

                                                                    .addOnFailureListener(e -> Toast.makeText(UpdateLaporanActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show());
                                                            Toast.makeText(view.getContext(), "Sukses Update Laporan", Toast.LENGTH_SHORT).show();
                                                            finish();
                                                        } else {
                                                            updateDatatoFirebase("", idlaporan, nama, ongkos * total, nama_pekerjaan, nama_produk, total);
                                                            Toast.makeText(view.getContext(), "Sukses Update Laporan", Toast.LENGTH_SHORT).show();
                                                            finish();
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

    private void updateDatatoFirebase(String bukti, String id, String nama, int ongkostotal, String pekerjaan, String produk, int total) {
        Log.e("TAG", "pppp: " + produk);

        DatabaseReference dbl = FirebaseDatabase.getInstance(Data.DATABASE_URL).getReference("laporan").child(nama).child(id);
        dbl.child("pekerjaan").setValue(pekerjaan);
        dbl.child("produk").setValue(produk);
        dbl.child("status").setValue("pending");
        dbl.child("ongkostotal").setValue(ongkostotal);
        dbl.child("total").setValue(total);
        if (!bukti.equals("")) {
            dbl.child("bukti").setValue(bukti);
        }
    }

    private int getProduk(String produk) {
        int code;
        switch (produk) {
            case "Setrip Besar":
                code = 0;
                break;
            case "Setrip Kecil":
                code = 1;
                break;
            case "Setrip Bedok Panjang":
                code = 2;
                break;
            case "Setrip Bedok Pendek":
                code = 3;
                break;
            case "Ring Setrip Besar":
                code = 4;
                break;
            case "Ring Setrip Tanggung":
                code = 5;
                break;
            case "Ring Setrip Kecil":
                code = 6;
                break;
            case "Tanggung Plat Arify":
                code = 7;
                break;
            case "Tanggung Plat BM":
                code = 8;
                break;
            case "Plat Surabayaan":
                code = 9;
                break;
            case "Arit BM Besar":
                code = 10;
                break;
            case "Arit BM Kecil":
                code = 11;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + produk);
        }
        return code;
    }

    private int getPekerjaan(String pekerjaan) {
        int code;
        switch (pekerjaan) {
            case "Motong + Meksemi":
                code = 1;
                break;
            case "Nyelenet Mesin":
                code = 2;
                break;
            case "Ngelus":
                code = 3;
                break;
            case "Nyekrap":
                code = 4;
                break;
            case "Nyepuhi":
                code = 5;
                break;
            case "Ngenjingne":
                code = 6;
                break;
            case "Moles":
                code = 7;
                break;
            case "Ngebur":
                code = 8;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + pekerjaan);
        }
        return code - 1;
    }

    private String getIdPekerjaan(String nama_pekerjaan){
        String id;
        switch (nama_pekerjaan) {
            case "Motong + Meksemi":
                id = "1";
                break;
            case "Nyelenet Mesin":
                id = "2";
                break;
            case "Ngelus":
                id = "3";
                break;
            case "Nyekrap":
                id = "4";
                break;
            case "Nyepuhi":
                id = "5";
                break;
            case "Ngenjingne":
                id = "6";
                break;
            case "Moles":
                id = "7";
                break;
            case "Ngebur":
                id = "8";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + nama_pekerjaan);
        }
        return id;
    }
}