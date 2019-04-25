package com.aiitec.greedaostudydemo;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.aiitec.greedaostudydemo.adapter.NotesAdapter;
import com.aiitec.greedaostudydemo.base.App;
import com.aiitec.greedaostudydemo.enu.NoteType;
import com.aiitec.greedaostudydemo.model.DaoSession;
import com.aiitec.greedaostudydemo.model.Note;
import com.aiitec.greedaostudydemo.model.NoteDao;

import org.greenrobot.greendao.query.Query;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: ailibin
 * @Time: 2019/04/18
 * @Description: 测试activity
 * @Email: ailibin@qq.com
 */
public class NoteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private EditText editText;
    private View addNoteButton;

    private NoteDao noteDao;
    private Query<Note> notesQuery;
    private NotesAdapter notesAdapter;

    private ListPopupWindow listPopupWindow;
    private String[] operations = {"增加", "删除", "更新"};

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_note);

        initListPopWindow();

        setUpViews();

//        App.getInstance().newCachedThreadPool().execute(new Runnable() {
//            //子线程执行
//            @Override
//            public void run() {
//                //主线程执行
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });
//            }
//        });

        // get the note DAO
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        noteDao = daoSession.getNoteDao();

        // query all notes, sorted a-z by their text
        notesQuery = noteDao.queryBuilder().orderAsc(NoteDao.Properties.Text).build();
        updateNotes();
    }

    private void initListPopWindow() {

        listPopupWindow = new ListPopupWindow(
                this);
        listPopupWindow.setAdapter(
                new ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        operations));
        listPopupWindow.setWidth(400);
        listPopupWindow.setHeight(400);
        listPopupWindow.setModal(true);
        listPopupWindow.setOnItemClickListener(
                NoteActivity.this);


    }

    private void showPopList(View anchorView) {
        listPopupWindow.setAnchorView(anchorView);
        listPopupWindow.show();
    }

    private void updateNotes() {
        List<Note> notes = notesQuery.list();
        notesAdapter.setNotes(notes);
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    protected void setUpViews() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewNotes);
        //noinspection ConstantConditions
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notesAdapter = new NotesAdapter(noteClickListener);
        recyclerView.setAdapter(notesAdapter);

        addNoteButton = findViewById(R.id.buttonAdd);
        //noinspection ConstantConditions
        addNoteButton.setEnabled(false);

        editText = findViewById(R.id.editTextNote);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addNote();
                    return true;
                }
                return false;
            }
        });

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enable = s.length() != 0;
                addNoteButton.setEnabled(enable);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });
    }


    private void addNote() {

        String noteText = editText.getText().toString();
        editText.setText("");

        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());

        Note note = new Note();
        note.setText(noteText);
        note.setComment(comment);
        note.setDate(new Date());
        note.setType(NoteType.TEXT);
        noteDao.insert(note);
        Log.d("DaoExample", "Inserted new note, ID: " + note.getId());
        updateNotes();

    }

    NotesAdapter.NoteClickListener noteClickListener = new NotesAdapter.NoteClickListener() {
        @Override
        public void onNoteClick(int position) {

            Note note = notesAdapter.getNote(position);
            Long noteId = note.getId();
            noteDao.deleteByKey(noteId);
            Log.d("DaoExample", "Deleted note, ID: " + noteId);
            updateNotes();
        }
    };

    /**
     * listPopwindow item click
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                //增加
                break;
            case 1:
                //删除
                break;
            case 2:
                //更新
                break;
            default:
                break;
        }

    }
}
