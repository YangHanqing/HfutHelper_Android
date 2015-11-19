package cn.hachin.hfuthelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.hachin.hfuthelper.Entity.Book;
import cn.hachin.hfuthelper.Utils.LibraryNetUtils;
import cn.hachin.hfuthelper.Utils.LibraryTools;

public class BooksActivity extends AppCompatActivity {
    List<Book> books = null;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        Intent intent = getIntent();
        final String bookName = intent.getStringExtra("bookName");
        pd = ProgressDialog.show(BooksActivity.this, "", "加载中");
        new Thread(new Runnable() {
            @Override
            public void run() {
                books = LibraryTools.getBooksFromText(LibraryNetUtils.getBooksText(bookName));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();// 关闭ProgressDialog
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


    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return books.size();
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
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_book, null);
            } else {
                view = convertView;
            }
            int flag = 0;

            for (Book book : books) {
                if (flag == position) {
                    ((TextView) view.findViewById(R.id.tv_url)).setText(book.getUrl());
                    ((TextView) view.findViewById(R.id.tv_book_name)).setText(book.getName());
                    ((TextView) view.findViewById(R.id.tv_book_author)).setText(book.getAuthor());
                    ((TextView) view.findViewById(R.id.tv_book_number)).setText(book.getBookNumber());
                    ((TextView) view.findViewById(R.id.tv_book_publishing)).setText(book.getPublishing());
                    ((TextView) view.findViewById(R.id.tv_book_type)).setText(book.getBookType());
                    break;
                } else {
                    flag++;
                }
            }


            return view;
        }
    }


    private class MyItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String url = ((TextView) view.findViewById(R.id.tv_url)).getText().toString();

            //把URL发送给TheBook页
            Intent intent = new Intent(getApplicationContext(), TheBookActivity.class);
            intent.putExtra("partUrl", url);
            startActivity(intent);
        }
    }
}
