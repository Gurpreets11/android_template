package com.preet.androidtemplate.core.network;

import android.content.Context;


import com.preet.androidtemplate.R;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class SSLCertificateHelper {

    public static class SSLConfig {
        public SSLSocketFactory sslSocketFactory;
        public X509TrustManager trustManager;

        public SSLConfig(SSLSocketFactory factory, X509TrustManager manager) {
            this.sslSocketFactory = factory;
            this.trustManager = manager;
        }
    }

    public static SSLConfig getSSLConfig(Context context) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            InputStream certInput = context.getResources().openRawResource(R.raw.server);
            Certificate certificate = certificateFactory.generateCertificate(certInput);
            certInput.close();

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("server", certificate);

            TrustManagerFactory tmf =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            X509TrustManager trustManager =
                    (X509TrustManager) tmf.getTrustManagers()[0];

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new javax.net.ssl.TrustManager[]{trustManager}, null);

            return new SSLConfig(sslContext.getSocketFactory(), trustManager);

        } catch (Exception e) {
            throw new RuntimeException("SSL setup failed", e);
        }
    }
}

