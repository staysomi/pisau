package pepe.pisau.bos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import pepe.pisau.bos.PopupActionDetail;
import pepe.pisau.bos.PopupDetail;
import pepe.pisau.bos.R;
import pepe.pisau.bos.model.DetailLaporan;

public class DetailLaporanAdapter extends RecyclerView.Adapter<DetailLaporanAdapter.ViewHolder> {

    Context context;
    List<DetailLaporan> list;

    public DetailLaporanAdapter(Context context, List<DetailLaporan> TempList) {
        this.list = TempList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_detail_laporan, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DetailLaporan DetailLaporan = list.get(position);
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        holder.pekerjaan.setText(DetailLaporan.getPekerjaan());
        holder.produk.setText(DetailLaporan.getProduk());
        holder.total.setText(String.valueOf(DetailLaporan.getTotal()));
        holder.ongkos.setText(String.valueOf(DetailLaporan.getOngkostotal()));
        holder.status.setText(String.valueOf(DetailLaporan.getStatus()));
        holder.date.setText(dateformat.format(DetailLaporan.getDate()));
        holder.itemView.setOnClickListener(v -> {
            if (DetailLaporan.getStatus().equals("pending")){
                PopupActionDetail popUpClass = new PopupActionDetail();
                popUpClass.showPopupWindow(v, DetailLaporan.getId(), DetailLaporan.getNama());
            } else {
                PopupDetail popUpClass = new PopupDetail();
                popUpClass.showPopupWindow(v, DetailLaporan.getId(), DetailLaporan.getNama());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView pekerjaan, produk, total, ongkos, status, date;
        public ViewHolder(View itemView) {
            super(itemView);
            pekerjaan = (TextView) itemView.findViewById(R.id.tv_pekerjaan);
            produk = (TextView) itemView.findViewById(R.id.tv_produk);
            total = (TextView) itemView.findViewById(R.id.tv_total);
            ongkos = (TextView) itemView.findViewById(R.id.tv_ongkos);
            status = (TextView) itemView.findViewById(R.id.tv_status);
            date = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }
}