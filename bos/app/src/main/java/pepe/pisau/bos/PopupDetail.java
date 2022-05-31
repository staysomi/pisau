package pepe.pisau.bos;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pepe.pisau.bos.data.Data;

public class PopupDetail {

    String strid, strhp, stralamat, strpassword;
    TextView tvid, tvpek, tvpro, tvtot, tvong;
    Button acc, rej, dc;
    ImageView buk;

    public void showPopupWindow(final View view, String paramid, String paramnama) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_detail, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        tvid = popupView.findViewById(R.id.tv_idadd);
        tvpek = popupView.findViewById(R.id.tv_pkradd);
        tvpro = popupView.findViewById(R.id.tv_prdadd);
        tvtot = popupView.findViewById(R.id.tv_ttld);
        tvong = popupView.findViewById(R.id.tv_onkd);

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "Fail");
            }
        });

        dc = popupView.findViewById(R.id.btn_d_close);
        dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}