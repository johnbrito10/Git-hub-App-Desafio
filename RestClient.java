package com.example.professor.githubpesquisalistview;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import com.example.professor.githubpesquisalistview.Items;
import com.example.professor.githubpesquisalistview.User;
import com.example.professor.githubpesquisalistview.ConstantUtil;
import com.example.professor.githubpesquisalistview.StringUtil;

public class RestClient {
    private static ApiInterface mApiInterface;

   public static ApiInterface getClient() {

        if(mApiInterface == null){

            OkHttpClient okClient = new OkHttpClient();
            okClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    return response;
                }
            });

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(ConstantUtil.BASEURL)
                    .addConverter(String.class, new StringUtil())
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mApiInterface = client.create(ApiInterface.class);

        }
        return mApiInterface;
    }

    public interface ApiInterface {

        @Headers("User-Agent: Retrofit2.0Tutorial-App")
        @GET("/search/users")
        Call<User> getUsersNamedTom(@Query("q") String name);

        @POST("/user/create")
        Call<Items> createUser(@Body String name, @Body String email);

        @PUT("/user/{id}/update")
        Call<Items> updateUser(@Path("id") String id , @Body Items user);
    }
}