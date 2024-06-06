package umn.ac.id.uasbangaumedic;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DaftarRequestAdapter extends RecyclerView.Adapter<RequestViewHolder> {
    public Context context;
    private LayoutInflater mInflater;

    DaftarRequestAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mDaftarRequest = mInflater.inflate(R.layout.daftar_request, parent, false);
        return new RequestViewHolder(mDaftarRequest, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        String acara = ViewRequest.request.get(position).getAcara();
        holder.judulAcara.setText(acara);
        if(ViewRequest.request.get(position).getStatus() == 0) {
            holder.statusRequest.setText("Pending");
            holder.statusRequest.setTextColor(0xFF636363);
        } else if (ViewRequest.request.get(position).getStatus() == 1) {
            holder.statusRequest.setText("Ditolak");
            holder.statusRequest.setTextColor(0xFFFF0000);
        } else {
            holder.statusRequest.setText("Diterima");
            holder.statusRequest.setTextColor(0xFF11FF00);
        }
    }

    @Override
    public int getItemCount() {
        return ViewRequest.request.size();
    }
}

class RequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView judulAcara, statusRequest;
    final DaftarRequestAdapter mAdapter;

    public RequestViewHolder(@NonNull View itemView, DaftarRequestAdapter adapter) {
        super(itemView);
        judulAcara = itemView.findViewById(R.id.judulAcara);
        statusRequest = itemView.findViewById(R.id.statusRequest);
        this.mAdapter = adapter;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int mPosition = getLayoutPosition();
        Intent detail = new Intent(mAdapter.context, DetailRequest.class);
        detail.putExtra("Index", mPosition);
        mAdapter.context.startActivity(detail);
    }
}
