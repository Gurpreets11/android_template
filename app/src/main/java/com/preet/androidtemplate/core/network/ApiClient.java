package com.preet.androidtemplate.core.network;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static String BASE_URL = "https://salestracking.in/"; //"http://10.20.30.198:3000/";
    private static Retrofit retrofit;
    private static ApiService apiService;

    private ApiClient() {
        // private constructor to prevent instantiation
    }

    public static synchronized ApiService getApiService(Context context) {

        if (apiService == null) {
            SSLCertificateHelper.SSLConfig sslConfig = SSLCertificateHelper.getSSLConfig(context);


            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(
                            sslConfig.sslSocketFactory,
                            sslConfig.trustManager
                    )
                    .hostnameVerifier((hostname, session) -> true) // optional
                    .readTimeout(90, TimeUnit.SECONDS)
                    .connectTimeout(90, TimeUnit.SECONDS);
            httpClient.addInterceptor(new AuthInterceptor(context));
            httpClient.addInterceptor(new UnauthorizedInterceptor(context)); // handle 401
            httpClient.addInterceptor(interceptor).build();

            OkHttpClient client = httpClient.build();
//        OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient(httpClient);
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

}
