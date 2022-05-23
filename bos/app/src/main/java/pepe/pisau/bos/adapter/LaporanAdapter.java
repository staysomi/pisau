package pepe.pisau.bos.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pepe.pisau.bos.Dashboard;
import pepe.pisau.bos.DetailLaporanActivity;
import pepe.pisau.bos.ListLaporanActivity;
import pepe.pisau.bos.PopUpUpdatePegawai;
import pepe.pisau.bos.R;
import pepe.pisau.bos.model.Laporan;
import pepe.pisau.bos.model.Pegawai;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.ViewHolder> {

    Context context;
    List<Laporan> list;

    public LaporanAdapter(Context context, List<Laporan> TempList) {
        this.list = TempList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.laporan, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Laporan Laporan = list.get(position);
        holder.id.setText(Laporan.getId());
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
        public ViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.laporan_pegawai);
        }
    }
}