package com.preet.androidtemplate.core.network;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class UnsafeOkHttpClient {

    public static OkHttpClient getUnsafeOkHttpClient(OkHttpClient.Builder builder) {
        try {

            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(
                                X509Certificate[] chain, String authType) throws CertificateException {}

                        @Override
                        public void checkServerTrusted(
                                X509Certificate[] chain, String authType) throws CertificateException {}

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            builder.sslSocketFactory(
                    sslSocketFactory,
                    (X509TrustManager) trustAllCerts[0]
            );

            builder.hostnameVerifier((hostname, session) -> true);

            return builder.build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

