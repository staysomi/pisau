package pepe.pisau.bos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pepe.pisau.bos.R;
import pepe.pisau.bos.model.DetailLaporan;
import pepe.pisau.bos.model.Laporan;

public class DetailLaporanAdapter extends RecyclerView.Adapter<DetailLaporanAdapter.ViewHolder> {

    Context context;
    List<DetailLaporan> list;

    public DetailLaporanAdapter(Context context, List<DetailLaporan> TempList) {
        this.list = TempList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_laporan, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DetailLaporan DetailLaporan = list.get(position);
        holder.id.setText(DetailLaporan.getId());
        holder.pekerjaan.setText(DetailLaporan.getPekerjaan());
        holder.produk.setText(DetailLaporan.getProduk());
        holder.total.setText(String.valueOf(DetailLaporan.getTotal()));
        holder.ongkos.setText(String.valueOf(DetailLaporan.getOngkostotal()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView id;
        public TextView pekerjaan;
        public TextView produk;
        public TextView total;
        public TextView ongkos;
        public ViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.tv_idd);
            pekerjaan = (TextView) itemView.findViewById(R.id.tv_pekerjaan);
            produk = (TextView) itemView.findViewById(R.id.tv_produk);
            total = (TextView) itemView.findViewById(R.id.tv_total);
            ongkos = (TextView) itemView.findViewById(R.id.tv_ongkos);
        }
    }
}