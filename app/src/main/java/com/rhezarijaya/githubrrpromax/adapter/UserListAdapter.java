package com.rhezarijaya.githubrrpromax.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rhezarijaya.githubrrpromax.R;
import com.rhezarijaya.githubrrpromax.databinding.ItemUserBinding;
import com.rhezarijaya.githubrrpromax.model.UserDetail;
import com.rhezarijaya.githubrrpromax.util.OnItemClick;

public class UserListAdapter extends ListAdapter<UserDetail, UserListAdapter.ViewHolder> {

    private final OnItemClick<UserDetail> onItemClick, onFavoriteClick;

    public UserListAdapter(OnItemClick<UserDetail> onItemClick, OnItemClick<UserDetail> onFavoriteClick) {
        super(new DiffUtil.ItemCallback<UserDetail>() {
            @Override
            public boolean areItemsTheSame(@NonNull UserDetail oldItem, @NonNull UserDetail newItem) {
                return oldItem.getLogin().equalsIgnoreCase(newItem.getLogin());
            }

            @Override
            public boolean areContentsTheSame(@NonNull UserDetail oldItem, @NonNull UserDetail newItem) {
                // pakai false karena jika pakai equals ada bug tidak terdeteksi perubahan isOnFavorite
                return false;
            }
        });

        this.onItemClick = onItemClick;
        this.onFavoriteClick = onFavoriteClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserDetail userDetail = getItem(position);

        holder.initItem(userDetail);

        Glide.with(holder.itemView.getContext())
                .load(userDetail.isOnFavorite() ?
                        R.drawable.ic_baseline_star_24 :
                        R.drawable.ic_baseline_star_outline_24)
                .into(holder.binding.itemUserIvFavorite);

        holder.itemView.setOnClickListener(v -> onItemClick.onItemClick(userDetail));
        holder.binding.itemUserIvFavorite.setOnClickListener(v -> onFavoriteClick.onItemClick(userDetail));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemUserBinding binding;

        public ViewHolder(ItemUserBinding itemUserBinding) {
            super(itemUserBinding.getRoot());
            this.binding = itemUserBinding;
        }

        void initItem(UserDetail userDetail){
            Context context = itemView.getContext();

            Glide.with(context)
                    .load(userDetail.getAvatarUrl())
                    .into(binding.itemUserCivAvatar);
            binding.itemUserTvUsername.setText(
                    String.format(context.getString(R.string.username_item_string), userDetail.getLogin()));
            binding.itemUserTvId.setText(
                    String.format(context.getString(R.string.id_item_string), userDetail.getId()));
        }
    }
}
