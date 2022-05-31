package pepe.pisau.bos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pepe.pisau.bos.PopUpUpdatePegawai;
import pepe.pisau.bos.R;
import pepe.pisau.bos.model.Pegawai;

public class PegawaiAdapter extends RecyclerView.Adapter<PegawaiAdapter.ViewHolder> {

    Context context;
    List<Pegawai> list;

    public PegawaiAdapter(Context context, List<Pegawai> TempList) {
        this.list = TempList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pegawai, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pegawai Pegawai = list.get(position);
        holder.id.setText(Pegawai.getId());
        holder.alamat.setText(Pegawai.getAlamat());
        holder.hp.setText(Pegawai.getHp());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpUpdatePegawai popUpClass = new PopUpUpdatePegawai();
                popUpClass.showPopupWindow(v, Pegawai.getId());
            }
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView id;
        public TextView alamat;
        public TextView hp;
        public ViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.tv_id);
            alamat = (TextView) itemView.findViewById(R.id.tv_alamat);
            hp = (TextView) itemView.findViewById(R.id.tv_hp);
        }
    }
}