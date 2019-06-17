package com.remember.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pixplicity.easyprefs.library.Prefs;
import com.remember.app.R;
import com.remember.app.data.models.ResponseEpitaphs;
import com.remember.app.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpitaphsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context context;
    private List<ResponseEpitaphs> responseEpitaphs = new ArrayList<>();

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new EpitaphsAdapter.EpitaphsAdapterViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_epitaphs, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return responseEpitaphs.size();
    }

    public void setItems(List<ResponseEpitaphs> responseEpitaphs) {
        this.responseEpitaphs.clear();
        this.responseEpitaphs.addAll(responseEpitaphs);
        notifyDataSetChanged();
    }

    public class EpitaphsAdapterViewHolder extends BaseViewHolder {

        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.name_user)
        TextView name;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.avatar)
        ImageView avatar;

        public EpitaphsAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        @Override
        public void onBind(int position) {
            name.setText(Prefs.getString("NAME_USER", ""));
            Glide.with(itemView)
                    .load(R.drawable.darth_vader)
                    .apply(RequestOptions.circleCropTransform())
                    .into(avatar);
            date.setText("Сегодня в 16:59");
            description.setText(responseEpitaphs.get(position).getBody());
        }
    }
}