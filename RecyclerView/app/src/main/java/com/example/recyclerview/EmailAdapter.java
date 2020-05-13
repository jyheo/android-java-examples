package com.example.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recyclerview.databinding.ItemBinding;

import java.util.List;

class ViewHolder extends RecyclerView.ViewHolder {
    ItemBinding mBinding;

    ViewHolder(ItemBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }
}

public class EmailAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Email> mEmails;

    EmailAdapter(Context context, List<Email> emails) {
        mEmails = emails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemBinding binding = ItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Email email = mEmails.get(position);
        holder.mBinding.textSender.setText(email.mSender);
        holder.mBinding.textTitle.setText(email.mTitle);
    }

    @Override
    public int getItemCount() {
        return mEmails.size();
    }
}
