package cn.hachin.hfuthelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.hachin.hfuthelper.Entity.Book;
import cn.hachin.hfuthelper.Entity.TheBook;
import cn.hachin.hfuthelper.Utils.LibraryNetUtils;
import cn.hachin.hfuthelper.Utils.LibraryTools;
import cn.hachin.hfuthelper.Utils.Tools;

public class TheBookActivity extends AppCompatActivity {
    private List<TheBook> theBooks=null;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_book);

        /*
            Intent intent = getIntent();
        final String bookName = intent.getStringExtra("bookName");

        new Thread(new Runnable() {
            @Override
            public void run() {
                books = LibraryTools.getBooksFromText(LibraryNetUtils.getBooksText(bookName));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (books != null) {
                            ListView lv_books = (ListView) findViewById(R.id.lv_books);
                            lv_books.setAdapter(new MyAdapter());
                            lv_books.setOnItemClickListener(new MyItemClickListener());
                        } else {
                            Toast.makeText(BooksActivity.this, "未找到图书", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        }).start();
         */
        Intent intent = getIntent();
        final String partUrl = intent.getStringExtra("partUrl");
        pd = ProgressDialog.show(TheBookActivity.this, "", "加载中");

        new Thread(new Runnable() {
            @Override
            public void run() {
                theBooks = LibraryTools.getTheBookFromText(LibraryNetUtils.getTheBookInfo(partUrl));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();// 关闭ProgressDialog
                        if(theBooks!=null){
                            ListView lv_theBook = (ListView) findViewById(R.id.lv_theBook);
                            lv_theBook.setAdapter(new MyAdapter());
                            lv_theBook.setOnItemClickListener(new MyItemCickListener());

                        }else{
                            Toast.makeText(TheBookActivity.this, "网络不稳定", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        }).start();

    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return theBooks.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_thebook, null);
            } else {
                view = convertView;
            }

            int flag = 0;

            for (TheBook theBook:theBooks ){
                if (flag == position) {
                    ((TextView) view.findViewById(R.id.tv_theBook_bookId)).setText(theBook.getBookId());
                    ((TextView) view.findViewById(R.id.tv_theBook_barCode)).setText(theBook.getBarCode());
                    ((TextView) view.findViewById(R.id.tv_theBook_location)).setText(theBook.getLocation());
                    String state = theBook.getState();
                    TextView tv_state = (TextView) view.findViewById(R.id.tv_theBook_state);
                    tv_state.setText(state);
                    if(state.equals("可借")){
                        //69,139,0
                    tv_state.setTextColor(Color.rgb(69,139,0));
                        ((TextView) view.findViewById(R.id.tv_theBook_url)).setText(theBook.getUrl());
                    }else{
                         tv_state.setTextColor(Color.rgb(220, 20, 60));
                    }

                    break;
                } else {
                    flag++;
                }
            }
            return view;
        }
    }

    private class MyItemCickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final String url=((TextView)(view.findViewById(R.id.tv_theBook_url))).getText().toString().trim();
            if(url==null|| TextUtils.isEmpty(url)){
                return;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final String location=LibraryTools.getLocationFromText(LibraryNetUtils.getLocation(url));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TheBookActivity.this,location, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();



        }
    }
}
