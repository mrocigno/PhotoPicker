package lib.rocigno.photopicker.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import lib.rocigno.photopicker.Models.PhotosModel;
import lib.rocigno.photopicker.R;
import lib.rocigno.photopicker.Utils.GlideUtil;

public class PhotoRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<PhotosModel> itens;
    Activity activity;

    public PhotoRecyclerAdapter(ArrayList<PhotosModel> itens, Activity activity) {
        this.itens = itens;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View v = inflater.inflate(R.layout.adapter_grid_uploader, viewGroup, false);

        return new Holder(v, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Holder holder = (Holder) viewHolder;
        holder.setData(itens.get(i));
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        View v;
        Activity activity;

        public Holder(@NonNull View itemView, Activity activity) {
            super(itemView);
            this.v = itemView;
            this.activity = activity;
        }

        public void setData(PhotosModel item){
            GlideUtil.initGlide(activity, item.getImg(), ((ImageView) v.findViewById(R.id.grid_image)));
            ((TextView) v.findViewById(R.id.grid_text)).setText(item.getDescricao());
        }
    }

}
