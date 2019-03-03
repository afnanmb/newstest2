package com.example.afnan.newstest2.api;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apiclient {

    // final means that the values are constant and cannot be changed
    //we put the url that we get the news from
    public static final String Base_URL="https://newsapi.org/v2/";

    //Retrofit turns your HTTP API into a Java interface.
    public static Retrofit retrofit;

    public static Retrofit getApiClient(){

        if(retrofit==null){
            //Calling baseUrl is required before calling build(). All other methods are optional.
            //.baseUrl :Set the API base URL.
            //.client:The HTTP client used for requests.
            //.build():Create the Retrofit instance using the configured values.
            //.addConverterFactory:Add converter factory for serialization and deserialization of objects.
            //https://square.github.io/retrofit/2.x/retrofit/retrofit2/Retrofit.Builder.html
            //GsonConverterFactory.create():https://futurestud.io/tutorials/retrofit-2-adding-customizing-the-gson-converter
            retrofit = new Retrofit.Builder().baseUrl(Base_URL)
                    .client(getunsafeOkHttpClient().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
    public static OkHttpClient.Builder getunsafeOkHttpClient(){

        try {
            //we made this for security by using JSSE (Java Secure Socket Extension)
            //شرح JSSE
            //https://docs.oracle.com/javase/8/docs/technotes/guides/security/jsse/JSSERefGuide.html
            // Create a trust manager that does not validate certificate chains
            //TrustManager[]:https://docs.oracle.com/javase/7/docs/api/javax/net/ssl/TrustManager.html
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }    }
}
