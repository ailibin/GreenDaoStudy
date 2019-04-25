package com.aiitec.greedaostudydemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiitec.greedaostudydemo.R;
import com.aiitec.greedaostudydemo.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ailibin
 * @Time: 2019/04/18
 * @Description: 测试
 * @Email: ailibin@qq.com
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private NoteClickListener clickListener;
    private OnNoteLongClickListener longClickListener;
    private List<Note> dataset;

    public interface NoteClickListener {
        void onNoteClick(int position);
    }

    public interface OnNoteLongClickListener {

        void onNoteLongClick(int position, View view);

    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {

        public TextView text;
        public TextView comment;

        public NoteViewHolder(View itemView, final NoteClickListener clickListener, final OnNoteLongClickListener longClickListener) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.textViewNoteText);
            comment = (TextView) itemView.findViewById(R.id.textViewNoteComment);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onNoteClick(getAdapterPosition());
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longClickListener != null) {
                        longClickListener.onNoteLongClick(getAdapterPosition(), v);
                    }
                    return false;
                }
            });
        }
    }

    public NotesAdapter(NoteClickListener clickListener, OnNoteLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
        this.dataset = new ArrayList<Note>();
    }

    public NotesAdapter(OnNoteLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
        this.dataset = new ArrayList<Note>();
    }


    public void setNotes(@NonNull List<Note> notes) {
        dataset = notes;
        notifyDataSetChanged();
    }

    public Note getNote(int position) {
        return dataset.get(position);
    }

    @Override
    public NotesAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view, clickListener, longClickListener);
    }

    @Override
    public void onBindViewHolder(NotesAdapter.NoteViewHolder holder, int position) {
        Note note = dataset.get(position);
        holder.text.setText(note.getText());
        holder.comment.setText(note.getComment());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

}
