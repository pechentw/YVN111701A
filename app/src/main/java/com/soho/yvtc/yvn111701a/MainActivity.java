package com.soho.yvtc.yvn111701a;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.imageView);
    }
    public void click1(View v)
    {
        MyTask task = new MyTask();
        task.execute("http://www.drodd.com/images14/flower26.jpg");
    }

    class MyTask extends AsyncTask<String, Integer, Bitmap>
    {
        private Bitmap bitmap = null;
        private InputStream inputStream = null;
        private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        @Override
        protected Bitmap doInBackground(String... params) {


            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                inputStream = conn.getInputStream();
                double fullSize = conn.getContentLength(); // 總長度
                byte[] buffer = new byte[64]; // buffer ( 每次讀取長度 )
                int readSize = 0; // 當下讀取長度
                double sum = 0;
                while ((readSize = inputStream.read(buffer)) != -1)
                {
                    outputStream.write(buffer, 0, readSize);
                    sum += (readSize / fullSize) * 100; // 累計讀取進度
                    // Message message = handler.obtainMessage(1, sum);
                    // handler.sendMessage(message);
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {

            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] result = outputStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            img.setImageBitmap(bitmap);
        }
    }
}
