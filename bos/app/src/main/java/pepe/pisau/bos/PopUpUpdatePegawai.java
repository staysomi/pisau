package pepe.pisau.bos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import pepe.pisau.bos.data.Data;

public class PopUpUpdatePegawai {

    String strid, strhp, stralamat, strpassword;

    public void showPopupWindow(final View view, String paramid) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_update_pegawai, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        TextView tvid = popupView.findViewById(R.id.update_id);
        EditText ethp = popupView.findViewById(R.id.update_hp);
        EditText etalamat = popupView.findViewById(R.id.update_alamat);
        EditText etpassword = popupView.findViewById(R.id.update_password);

        FirebaseDatabase database = FirebaseDatabase.getInstance(Data.DATABASE_URL);
        DatabaseReference mbase = database.getReference("pegawai").child(paramid);
        mbase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvid.setText(snapshot.child("id").getValue(String.class));
                ethp.setText(snapshot.child("hp").getValue(String.class));
                etalamat.setText(snapshot.child("alamat").getValue(String.class));
                etpassword.setText(snapshot.child("password").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "Fail");
            }
        });
        Button btn_update = popupView.findViewById(R.id.btn_pp_update_pegawai);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strid = tvid.getText().toString();
                strhp = ethp.getText().toString();
                stralamat = etalamat.getText().toString();
                strpassword = etpassword.getText().toString();
                if (strhp.isEmpty() || strpassword.isEmpty() || stralamat.isEmpty()) {
                    Toast.makeText(view.getContext(), "Isi Data", Toast.LENGTH_SHORT).show();
                } else {
                    updateDataFirebase(strid, strhp, stralamat, strpassword);
                    Toast.makeText(view.getContext(), "Sukses Ubah", Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            popupWindow.dismiss();
                        }
                    }, 3000);
                }
            }
        });

        Button btn_delete = popupView.findViewById(R.id.btn_pp_delete_pegawai);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strid = tvid.getText().toString();
                AlertDialog.Builder b1 = new AlertDialog.Builder(view.getContext());
                b1.setMessage("Yakin Ingin Menghapus ?");
                b1.setCancelable(true);

                b1.setPositiveButton("HAPUS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteData(strid);
                        Toast.makeText(view.getContext(), "Sukses Hapus", Toast.LENGTH_SHORT).show();
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

        Button buttonClose = popupView.findViewById(R.id.btn_ppu_close);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void deleteData(String id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(Data.DATABASE_URL);
        DatabaseReference mbase = database.getReference("pegawai").child(id);
        mbase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mbase.removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void updateDataFirebase(String id, String hp, String alamat, String pass) {
        Log.e("TAG", "updateDataFirebase: " + id+hp+alamat+pass );
        FirebaseDatabase dbup = FirebaseDatabase.getInstance(Data.DATABASE_URL);
        DatabaseReference dbupdate = dbup.getReference("pegawai").child(id);
        dbupdate.child("alamat").setValue(alamat);
        dbupdate.child("hp").setValue(hp);
        dbupdate.child("password").setValue(pass);
    }
}