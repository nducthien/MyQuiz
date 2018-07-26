package com.tea.myquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tea.myquiz.QuizContract.*;
import com.tea.myquiz.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyQuiz.db";
    private static final int DATA_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;


        final String CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionTable.TABLE_NAME + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);

    }

    private void fillQuestionsTable() {
        Question q1 = new Question("How many the country in DNA", "Ten", "Eleven", "Nine", 2);
        addQuestion(q1);
        Question q2 = new Question("Which the country WC 2018", "France", "Rusia", "Quatar", 2);
        addQuestion(q2);
        Question q3 = new Question("The highest mountain in the world", "Everes", "Fanxibang", "Phusi", 1);
        addQuestion(q3);
        Question q4 = new Question("The champions WC 2010", "Spain", "Argentina", "Germany", 1);
        addQuestion(q4);
        Question q5 = new Question("Giải Grand Slam đầu tiên trong năm là giải nào", "Wimbledon", "Roland Garos", "Austrlia Open", 3);
        addQuestion(q5);
        Question q6 = new Question("Sông Bến Hải và sông Thạch Hãn nằm ở tỉnh nào", "Quảng Bình", "Quảng Ninh", "Quảng Trị", 3);
        addQuestion(q6);
        Question q7 = new Question("File nào liệt kê mà quyền ứng dụng yêu cầu", "AndroidManifest.xml", "string.xml", "R.java", 1);
        addQuestion(q7);
        Question q8 = new Question("File nào liệt kê mà quyền ứng dụng yêu cầu", "AndroidManifest.xml", "string.xml", "R.java", 1);
        addQuestion(q8);
        Question q9 = new Question("File nào liệt kê mà quyền ứng dụng yêu cầu", "AndroidManifest.xml", "string.xml", "R.java", 1);
        addQuestion(q9);
        Question q10 = new Question("File nào liệt kê mà quyền ứng dụng yêu cầu", "AndroidManifest.xml", "string.xml", "R.java", 1);
        addQuestion(q10);
        Question q11 = new Question("Layout nào cho phép sắp xếp View con theo dạng chiều ngang và chiều dọc", "TabLayout", "RelaytiveLayout", "LinearLayout", 3);
        addQuestion(q11);
        Question q12 = new Question("Trong GridView để hiển thị 4 cột, phải dùng khai báo nào", "android:Colums = 4", "android:numColums = 4", "android:Col = 4", 2);
        addQuestion(q12);
        Question q13 = new Question("Với RadioButton để thiết lập trạng thái Checked hay unchecked ta dùng lệnh nào sau đây", "setChecked()", "isChecked()", "clearCkecked", 1);
        addQuestion(q13);
        Question q14 = new Question("Với RadioButton để xóa bỏ trạng thái Checked trong Group ta dùng lệnh nào sau đây", "isChecked()", "clearCkecked", "setChecked", 2);
        addQuestion(q14);
        Question q15 = new Question("Phương thức nào không thuộc Animator.Animator.Listener", "onAnimatorRepeat()", "onAnimatorStart()", "onAnimatorPause()", 3);
        addQuestion(q15);
        Question q16 = new Question("Lớp nào trong Android SDK dùng để quản lí Camera", "Photo SDK", "Camera", "Camera SDK", 2);
        addQuestion(q16);
        Question q17 = new Question("Cuộc khởi nghĩa 2 Bà Trưng diễn ra năm bao nhiêu trước công nguyên", "40", "50", "30", 1);
        addQuestion(q17);
        Question q18 = new Question("Nhà Lý có bao nhiêu đời vua", "9", "10", "11", 1);
        addQuestion(q18);
        Question q19 = new Question("Nhà Trần trải qua bao nhiêu đời vua", "11", "12", "10", 2);
        addQuestion(q19);
        Question q20 = new Question("Quốc hiệu Việt Nam thời nhà Hồ có tên là gì", "Đại Việt", "Đại Cồ Việt", "Đại Ngu", 3);
        addQuestion(q20);
        Question q21 = new Question("Dưới thời vua Lý Nam Đế nước ta có quốc hiệu là gì", "Vạn Xuân", "Đại Cồ Việt", "Đại Việt", 1);
        addQuestion(q21);
        Question q22 = new Question("Quân dân nhà Trần mấy lần đánh thắng quân Nguyên Mông", "2", "3", "4", 2);
        addQuestion(q22);
        Question q23 = new Question("Thực dân Pháp mở đầu cuộc xâm lược Việt Nam vào năm nào", "1858", "1859", "1860", 1);
        addQuestion(q23);
        Question q24 = new Question("Hiệp định Paris kí kết năm nào", "1971", "1972", "1973", 3);
        addQuestion(q24);
        Question q25 = new Question("Hiệp định Geneve kí kết năm nào", "1945", "1954", "1975", 2);
        addQuestion(q25);


    }

    private void addQuestion(Question question) {

        ContentValues values = new ContentValues();
        values.put(QuestionTable.COLUMN_QUESTION, question.getQuestion());
        values.put(QuestionTable.COLUMN_OPTION1, question.getOption1());
        values.put(QuestionTable.COLUMN_OPTION2, question.getOption2());
        values.put(QuestionTable.COLUMN_OPTION3, question.getOption3());
        values.put(QuestionTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionTable.TABLE_NAME, null, values);

    }

    public ArrayList<Question> getAllQuestion() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
