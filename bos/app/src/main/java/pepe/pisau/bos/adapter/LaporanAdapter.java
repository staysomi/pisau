package pepe.pisau.bos.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import pepe.pisau.bos.DetailLaporanActivity;
import pepe.pisau.bos.R;
import pepe.pisau.bos.data.Data;
import pepe.pisau.bos.model.Laporan;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.ViewHolder> {

    Context context;
    List<Laporan> list;

    public LaporanAdapter(Context context, List<Laporan> TempList) {
        this.list = TempList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_laporan, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Laporan Laporan = list.get(position);
        holder.id.setText(Laporan.getId());
        FirebaseDatabase database = FirebaseDatabase.getInstance(Data.DATABASE_URL);
        DatabaseReference db = database.getReference("laporan").child(Laporan.getId());

        db.orderByChild("status").equalTo("pending").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.tp.setText(String.valueOf(snapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        db.orderByChild("status").equalTo("accepted").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.ta.setText(String.valueOf(snapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        db.orderByChild("status").equalTo("rejected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.tr.setText(String.valueOf(snapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(context, DetailLaporanActivity.class);
                inte.putExtra("nama", Laporan.getId());
                context.startActivity(inte);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView id;
        public TextView tp;
        public TextView tr;
        public TextView ta;
        public ViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.laporan_pegawai);
            tp = (TextView) itemView.findViewById(R.id.laporan_pending);
            tr = (TextView) itemView.findViewById(R.id.laporan_tolak);
            ta = (TextView) itemView.findViewById(R.id.laporan_terima);
        }
    }
}