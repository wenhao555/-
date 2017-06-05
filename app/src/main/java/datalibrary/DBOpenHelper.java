package datalibrary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/5/24.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBOpenHelper";
    private final Context mContext;
    private static final String DATABASE_NAME = "tangcco_database";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "tbl_contactinfo";


    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    //创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {

        //创建数据表
        String sql = "create table " + DATABASE_NAME + "(id integer primary key autoincrement,name varchar(20),phone varchar(20),relationship integer)";
        db.execSQL(sql);//执行

    }

    // 升级数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }


}

