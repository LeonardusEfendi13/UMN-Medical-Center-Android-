package umn.ac.id.uasbangaumedic;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ViewJobAdapter extends RecyclerView.Adapter<JobViewHolder>{
    public Context context;
    private LayoutInflater mInflater;

    ViewJobAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mDaftarRequest = mInflater.inflate(R.layout.view_job_design, parent, false);
        return new JobViewHolder(mDaftarRequest, this);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {

        String acara = ViewJob.job.get(position).getAcara();
        if(ViewJob.job.get(position).getStatus() == 0) {
            holder.judulAcara.setText(acara);
            holder.judulAcara.setTextColor(0xFF636363);
        } else if (ViewJob.job.get(position).getStatus() == 1) {
            holder.judulAcara.setText(acara);
            holder.judulAcara.setTextColor(0xFFFF0000);
        } else {
            holder.judulAcara.setText(acara);
            holder.judulAcara.setTextColor(0xFF11FF00);
        }

    }

    @Override
    public int getItemCount() {
        return ViewJob.job.size();
    }
}

class JobViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView judulAcara;


    final ViewJobAdapter mAdapter;

    public JobViewHolder(@NonNull View itemView, ViewJobAdapter adapter) {
        super(itemView);
        judulAcara = itemView.findViewById(R.id.jobList);

        this.mAdapter = adapter;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int mPosition = getLayoutPosition();
        Intent detailJob = new Intent(mAdapter.context, DetailJob.class);
        detailJob.putExtra("Index", mPosition);
        mAdapter.context.startActivity(detailJob);
    }
}
