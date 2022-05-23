package pepe.pisau.bos;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pepe.pisau.bos.data.Data;
import pepe.pisau.bos.model.Pegawai;

public class PopUpAddPegawai {

    Pegawai pegawai;

    public void showPopupWindow(final View view) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.add_pegawai_layout, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        EditText id = popupView.findViewById(R.id.add_id);
        EditText hp = popupView.findViewById(R.id.add_hp);
        EditText alamat = popupView.findViewById(R.id.add_alamat);
        EditText password = popupView.findViewById(R.id.add_password);

        Button button = popupView.findViewById(R.id.btn_pp_add_pegawai);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strid = id.getText().toString();
                String strhp = hp.getText().toString();
                String stralamat = alamat.getText().toString();
                String strpassword = password.getText().toString();
                if (strhp.isEmpty() || strid.isEmpty() || stralamat.isEmpty() || strpassword.isEmpty()) {
                    Toast.makeText(view.getContext(), "Isi Data", Toast.LENGTH_SHORT).show();
                } else {
                    addDatatoFirebase(strid, strhp, stralamat,  strpassword);
                    popupWindow.dismiss();
                }
            }
        });

        Button buttonClose = popupView.findViewById(R.id.btn_pp_close);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void addDatatoFirebase(String id, String hp, String alamat, String password) {
        pegawai = new Pegawai();
        pegawai.setId(id);
        pegawai.setHp(hp);
        pegawai.setAlamat(alamat);
        pegawai.setPassword(password);
        FirebaseDatabase database = FirebaseDatabase.getInstance(Data.DATABASE_URL);

        DatabaseReference mbase = database.getReference("pegawai").child(id);
        // we are use add value event listener method
        // which is called with database reference.
        mbase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mbase.setValue(pegawai);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}