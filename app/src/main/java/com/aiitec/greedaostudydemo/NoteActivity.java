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
import android.view.ViewGroup;
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
import com.aiitec.greedaostudydemo.util.KeyboardUtils;
import com.aiitec.greedaostudydemo.util.StringUtils;

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

    private static final String TAG = "ailibin";

    private EditText editText;
    private View addNoteButton, queryNotebutton, tvEmpty;

    private NoteDao noteDao;
    private Query<Note> notesQuery;
    private NotesAdapter notesAdapter;
    private RecyclerView recyclerView;

    private ListPopupWindow listPopupWindow;
    private String[] operations = {"删除", "更新"};

    private Note clickNote;

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_note);

        //关闭软件盘
        KeyboardUtils.hideSoftInput(this);

        initListPopWindow();

        setUpViews();

        // get the note DAO
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        noteDao = daoSession.getNoteDao();

        // query all notes, sorted a-z by their Date 升序排列
//        notesQuery = noteDao.queryBuilder().orderAsc(NoteDao.Properties.Date).build();
        //query all notes, sorted a-z by their Date 降序排列
        notesQuery = noteDao.queryBuilder().orderDesc(NoteDao.Properties.Date).build();
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
        listPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
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
        recyclerView = findViewById(R.id.recyclerViewNotes);
        //noinspection ConstantConditions
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notesAdapter = new NotesAdapter(noteClickListener, noteLongClickListener);
        recyclerView.setAdapter(notesAdapter);

        addNoteButton = findViewById(R.id.buttonAdd);
        queryNotebutton = findViewById(R.id.buttonQuery);
        tvEmpty = findViewById(R.id.tvEmpty);
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

        //add button
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

        //query button
        queryNotebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryNote();
            }
        });

    }

    private void queryNote() {

        String noteText = editText.getText().toString();
        editText.setText("");

        if (StringUtils.isEmpty(noteText)) {
            updateNotes();
            return;
        }
        //根据text字段查找,查询一个集合,这里是模糊查询,如果要精确查询
        List<Note> queryNotes = noteDao.queryBuilder().where(NoteDao.Properties.Text.like(noteText + "%")).list();
        //精确查询
//        List<Note> queryNotes = noteDao.queryBuilder().where(NoteDao.Properties.Text.eq(noteText)).list();
        if (queryNotes == null || queryNotes.size() <= 0) {
            showEmpty();
        }
        notesAdapter.setNotes(queryNotes);

    }

    private void showEmpty() {
        recyclerView.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.VISIBLE);
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
        Log.d(TAG, "Inserted new note, ID: " + note.getId());
        updateNotes();

    }

    NotesAdapter.NoteClickListener noteClickListener = new NotesAdapter.NoteClickListener() {
        @Override
        public void onNoteClick(int position) {

//            Note note = notesAdapter.getNote(position);
//            Long noteId = note.getId();
//            noteDao.deleteByKey(noteId);
//            Log.d("DaoExample", "Deleted note, ID: " + noteId);
//            updateNotes();
        }
    };

    NotesAdapter.OnNoteLongClickListener noteLongClickListener = new NotesAdapter.OnNoteLongClickListener() {
        @Override
        public void onNoteLongClick(int position, View view) {
            clickNote = notesAdapter.getNote(position);
            showPopList(view);
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
                //删除,批量删除后面再做
                Long noteId = clickNote.getId();
                noteDao.deleteByKey(noteId);
//                noteDao.deleteByKeyInTx();
                updateNotes();
                break;
            case 1:
                //更新,批量修改后面再加上去
                clickNote.setText("123");
                noteDao.update(clickNote);
//                noteDao.updateInTx(clickNote);
                updateNotes();
                break;
            default:
                break;
        }

    }
}
