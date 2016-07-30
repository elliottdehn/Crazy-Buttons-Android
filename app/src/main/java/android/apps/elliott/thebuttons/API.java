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
    Call<ButtonResponse> getCount();

    @POST("plus")
    Call<ButtonResponse> increment();

    @POST("minus")
    Call<ButtonResponse> decrement();

    @POST("neutral")
    Call<ButtonResponse> neutralize();
}
