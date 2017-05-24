package hhh.com.okhttpdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static hhh.com.okhttpdemo.R.id.get_Asynchronous;
import static hhh.com.okhttpdemo.R.id.get_Synchronization;
import static hhh.com.okhttpdemo.R.id.post_Asynchronous;

public class MainActivity extends Activity implements View.OnClickListener {

//    Executors.newFixedThreadPool(4);
    //线城池
    private ExecutorService executorService= Executors.newFixedThreadPool(3);
    private Button get_synchronization;
    private Button post_Synchronization;
    private Button get_asynchronous;
    private Button post_asynchronous;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get_synchronization = (Button) findViewById(get_Synchronization);
        post_Synchronization = (Button) findViewById(R.id.post_Synchronization);
        get_asynchronous = (Button) findViewById(get_Asynchronous);
        post_asynchronous = (Button) findViewById(post_Asynchronous);

        get_synchronization.setOnClickListener(this);
        post_Synchronization.setOnClickListener(this);
        get_asynchronous.setOnClickListener(this);
        post_asynchronous.setOnClickListener(this);

        //线程池
// private ExecutorService executorService = Executors.newFixedThreadPool(3);
    }




    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case get_Synchronization:
                get_Synchronization();
            break;
            case get_Asynchronous:

                get_Asynchronous();
                break;
            case R.id.post_Synchronization:
                post_Synchronization();
                break;
            case post_Asynchronous:
                post_Asynchronous();
                break;
        }
    }


    private void get_Synchronization() {

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                try {

                    OkHttpClient client = new OkHttpClient();

                    Request request=new Request.Builder().url("http://publicobject.com/helloworld.txt").build();

                    Response response = client.newCall(request).execute();

                    System.out.println("response =" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void get_Asynchronous() {

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                Request request=new Request.Builder().url("http://publicobject.com/helloworld.txt").build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        System.out.println("response = " + response.body().string());
                    }
                });
            }
        });
    }

    String url="http://publicobject.com/helloworld.txt";
    private void post_Synchronization() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder().add("username", "8541").add("password", "111111").add("postkey", "1503d").build();

                    Request request = new Request.Builder().url(url).post(formBody).build();
                    Response response = client.newCall(request).execute();
                    System.out.println("response = " + response.body().string());


                } catch (IOException e) {

                    e.printStackTrace();
                }

            }
        });
    }
    private void post_Asynchronous() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody=new FormBody.Builder().add("username","8451").add("password","111111").add("postkey","1503d").build();
                Request request = new Request.Builder().url(url).post(requestBody).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.println("response = " + response.body().string());
                    }
                });
            }
        });
    }



}
