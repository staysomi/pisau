package pepe.pisau.bos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import pepe.pisau.bos.data.Data;
import pepe.pisau.bos.data.Zoom;

public class PopupActionDetail {

    String strid, strhp, stralamat, strpassword;
    TextView tvid, tvpek, tvpro, tvtot, tvong;
    Button acc, rej, adc;
    ImageView buk;

    public void showPopupWindow(final View view, String paramid, String paramnama) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_action_detail, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        tvid = popupView.findViewById(R.id.tv_idad);
        tvpek = popupView.findViewById(R.id.tv_pkrad);
        tvpro = popupView.findViewById(R.id.tv_prdad);
        tvtot = popupView.findViewById(R.id.tv_ttl);
        tvong = popupView.findViewById(R.id.tv_onk);
        buk = popupView.findViewById(R.id.bukti);

        FirebaseDatabase database = FirebaseDatabase.getInstance(Data.DATABASE_URL);
        DatabaseReference mbase = database.getReference("laporan").child(paramnama).child(paramid);
        mbase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvid.setText(snapshot.child("nama").getValue(String.class));
                tvpek.setText(snapshot.child("pekerjaan").getValue(String.class));
                tvpro.setText(snapshot.child("produk").getValue(String.class));
                tvtot.setText(String.valueOf(snapshot.child("total").getValue(Integer.class)));
                tvong.setText(String.valueOf(snapshot.child("ongkostotal").getValue(Integer.class)));
                Picasso.get().load(snapshot.child("bukti").getValue(String.class)).into(buk);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "Fail");
            }
        });
        acc = popupView.findViewById(R.id.accept);
        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acce(paramid, paramnama);
                Toast.makeText(view.getContext(), "Sukses Terima", Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow.dismiss();
                    }
                }, 3000);
            }
        });

        rej = popupView.findViewById(R.id.reject);
        rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strid = tvid.getText().toString();
                AlertDialog.Builder b1 = new AlertDialog.Builder(view.getContext());
                b1.setMessage("Yakin Ingin Menolak ?");
                b1.setCancelable(true);

                b1.setPositiveButton("Tolak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        reje(paramid, paramnama);
                        Toast.makeText(view.getContext(), "Sukses Tolak", Toast.LENGTH_SHORT).show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                popupWindow.dismiss();
                            }
                        }, 3000);
                    }
                });
                b1.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert11 = b1.create();
                alert11.show();
            }
        });

        adc = popupView.findViewById(R.id.btn_ad_close);
        adc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void acce(String id, String nama) {
        Log.e("TAG", "acc: " + id + nama);
        FirebaseDatabase dbup = FirebaseDatabase.getInstance(Data.DATABASE_URL);
        DatabaseReference dbupdate = dbup.getReference("laporan").child(nama).child(id);
        dbupdate.child("status").setValue("accepted");
    }

    private void reje(String id, String nama) {
        Log.e("TAG", "reje: " + id + nama);
        FirebaseDatabase dbup = FirebaseDatabase.getInstance(Data.DATABASE_URL);
        DatabaseReference dbupdate = dbup.getReference("laporan").child(nama).child(id);
        dbupdate.child("status").setValue("rejected");
    }
}