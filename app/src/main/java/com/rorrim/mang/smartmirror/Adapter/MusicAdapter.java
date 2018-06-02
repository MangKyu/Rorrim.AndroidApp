package com.rorrim.mang.smartmirror.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rorrim.mang.smartmirror.BR;
import com.rorrim.mang.smartmirror.Listener.RecyclerViewClickListener;
import com.rorrim.mang.smartmirror.Model.Music;
import com.rorrim.mang.smartmirror.databinding.MusicListLowBinding;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder>  {
    private final RecyclerViewClickListener listener;
    private List<Music> musicList;
    private LayoutInflater inflater;
    private Activity activity;

    public MusicAdapter(Activity activity, List<Music> musicList, RecyclerViewClickListener listener) {
        this.activity = activity;
        this.musicList = musicList;
        this.listener = listener;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MusicListLowBinding binding = MusicListLowBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MusicAdapter.MyViewHolder holder,final int position) {
        //Music music = musicList.get(position);
        holder.bind(musicList.get(position), listener);

        /*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = view.getContext();
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_NEGATIVE: {
                                //Yes 버튼을 클릭했을때 처리
                                try {
                                    FileManager.getInstance().uploadMusic(context, musicList.get(position));
                                }
                                catch (Exception e) {
                                    Log.e("SimplePlayer", e.getMessage());
                                }
                                break;
                            }
                            case DialogInterface.BUTTON_POSITIVE:
                                //No 버튼을 클릭했을때 처리
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("정말?").setPositiveButton("아니?", dialogClickListener)
                        .setNegativeButton("그래!", dialogClickListener).show();

            }
        });
        */
    }

    public void setItem(List<Music> musicList) {
        if (musicList == null) {
            return;
        }
        this.musicList = musicList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private MusicListLowBinding binding;

        public MyViewHolder(MusicListLowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(final Music music, final RecyclerViewClickListener listener){
            binding.setVariable(BR.music, music);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.onItemClick(music);
                }
            });
        }
    }

}