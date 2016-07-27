package android.apps.elliott.thebuttons;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by elliottdehn on 7/19/2016.
 */
public interface API {

    public static final String BASE_URL = "https://crazybuttons.herokuapp.com/";

    @GET
    Call<ResponseBody> getCount();

    @POST("plus")
    Call<ResponseBody> increment();

    @POST("minus")
    Call<ResponseBody> decrement();

    @POST("neutral")
    Call<ResponseBody> neutralize();
}
