package pt.ipleiria.estg.dei.book.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LivroBDHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "dblivros";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "livros";
    private static final String TITLE = "titulo";
    private static final String SERIE = "serie";
    private static final String AUTOR = "autor";
    private static final String ANO = "ano";
    private static final String CAPA = "capa";
    private static final String ID = "id";

    private final SQLiteDatabase sql;

    public LivroBDHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.sql = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createLivroTable = "CREATE TABLE " + TABLE_NAME +
                "(" + ID + " INTEGER, " +
                TITLE + " TEXT NOT NULL, " +
                SERIE + " TEXT NOT NULL, " +
                AUTOR + " TEXT NOT NULL, " +
                ANO + " INTEGER NOT NULL, " +
                CAPA + " TEXT" +
                ");";

        sqLiteDatabase.execSQL(createLivroTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        this.onCreate(sqLiteDatabase);
    }

    public void adicionarLivroBD(Livro l) {
        ContentValues values = new ContentValues();

        values.put(TITLE, l.getTitulo());
        values.put(SERIE, l.getSerie());
        values.put(AUTOR, l.getAno());
        values.put(ANO, l.getAno());
        values.put(CAPA, l.getCapa());

        this.sql.insert(TABLE_NAME, null, values);
    }

    public boolean editarLivroBD(Livro l) {
        ContentValues values = new ContentValues();

        values.put(TITLE, l.getTitulo());
        values.put(SERIE, l.getSerie());
        values.put(AUTOR, l.getAno());
        values.put(ANO, l.getAno());
        values.put(CAPA, l.getCapa());

        return this.sql.update(TABLE_NAME, values, ID + " = ?", new String[]{""+l.getId()}) > 0;
    }

    public boolean removerLivroBd(int id) {
        return (this.sql.delete(TABLE_NAME, ID + " = ?", new String[] {"" + id}) == 1);
    }

    public ArrayList<Livro> getAllLivrosBD() {
        ArrayList<Livro> livros = new ArrayList<>();

        Cursor cursor = this.sql.query(TABLE_NAME, new String[]{ID, TITLE, SERIE, AUTOR, ANO, CAPA}, null, null, null, null, null);

        if(cursor.moveToFirst()) {
            do {
                Livro auxLivro = new Livro(cursor.getInt(0), cursor.getString(5), cursor.getInt(4), cursor.getString(1), cursor.getString(2), cursor.getString(3));

                livros.add(auxLivro);

            } while(cursor.moveToNext());
        }

        return livros;
    }

    public void apagarLivros() {
        this.sql.delete(TABLE_NAME, null, null);
    }
}
