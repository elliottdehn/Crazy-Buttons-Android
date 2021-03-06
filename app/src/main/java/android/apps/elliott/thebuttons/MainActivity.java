package android.apps.elliott.thebuttons;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Callback<ButtonResponse> {

    private Button increment;
    private Button decrement;
    private Button neutral;
    private API api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        increment = (Button) findViewById(R.id.button_increment);
        decrement = (Button) findViewById(R.id.button_decrement);
        neutral = (Button) findViewById(R.id.button_neutralize);

        increment.setOnClickListener(this);
        decrement.setOnClickListener(this);
        neutral.setOnClickListener(this);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API.class);
        api.getCount().enqueue(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_decrement:
                decrementCounter();
                break;
            case R.id.button_increment:
                incrementCounter();
                break;
            case R.id.button_neutralize:
                neutralizeNextCommand();
                break;
        }
    }

    private void incrementCounter() {
        api.increment().enqueue(this);
    }

    private void decrementCounter() {
        api.decrement().enqueue(this);
    }

    private void neutralizeNextCommand(){
        api.neutralize().enqueue(this);
    }

    /**
     * Invoked for a received HTTP response.
     * <p/>
     * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
     * Call {@link Response#isSuccessful()} to determine if the response indicates success.
     *
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<ButtonResponse> call, Response<ButtonResponse> response) {
        ButtonResponse body = response.body();
        neutral.setText(body.count() + "");
        if(body.action() == null) api.getCount().enqueue(this); //find a better way to poll the server. Pushing is a good start.
    }

    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     *
     * @param call
     * @param t
     */
    @Override
    public void onFailure(Call<ButtonResponse> call, Throwable t) {
        Log.e("Debug", "Error: ", t);
    }
}
