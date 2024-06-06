package umn.ac.id.uasbangaumedic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MemberScheduleAdapter extends RecyclerView.Adapter<MemberScheduleHolder>{
    public Context context;
    private LayoutInflater mInflater;

    MemberScheduleAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MemberScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mDaftarRequest = mInflater.inflate(R.layout.design_jadwal_piket, parent, false);
        return new MemberScheduleHolder(mDaftarRequest, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberScheduleHolder holder, int position) {

        String nama = MemberSchedule.jadwalPiket.get(position).getNama();
        String jam = MemberSchedule.jadwalPiket.get(position).getJam();
        int delete = MemberSchedule.jadwalPiket.get(position).getDel();
        holder.nama.setText(nama);
        holder.jam.setText(jam);
        holder.delete.setImageResource(delete);
        int pos = position;

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog confirmation = new BottomSheetDialog(view.getContext());
                confirmation.setContentView(R.layout.confirm_delete_page);
                confirmation.show();
                Button yesBtn = confirmation.findViewById(R.id.yesBtn);
                Button noBtn = confirmation.findViewById(R.id.noBtn);

                yesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MemberSchedule.checkIntent = MemberSchedule.jadwalPiket.get(pos).getId();
                        Intent intent = new Intent(v.getContext(), MemberSchedule.class);
                        v.getContext().startActivity(intent);
                        confirmation.dismiss();
                    }
                });

                noBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        confirmation.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return MemberSchedule.jadwalPiket.size();
    }
}


class MemberScheduleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView nama;
    public final TextView jam;
    public final ImageView delete;


    final MemberScheduleAdapter mAdapter;

    public MemberScheduleHolder(@NonNull View itemView, MemberScheduleAdapter adapter) {
        super(itemView);
        nama = itemView.findViewById(R.id.judulAcara);
        jam = itemView.findViewById(R.id.statusRequest);
        delete = itemView.findViewById(R.id.deleteSchedule);

        this.mAdapter = adapter;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int mPosition = getLayoutPosition();
    }
}
