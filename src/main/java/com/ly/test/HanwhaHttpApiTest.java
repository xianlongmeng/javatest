package com.ly.test;

import com.alibaba.fastjson.util.IOUtils;
import org.apache.http.*;
import org.apache.http.auth.*;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.auth.DigestSchemeFactory;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HanwhaHttpApiTest {
    public static void main(String[] args) {
        String url = "/stw-cgi/video.cgi?msubmenu=snapshot&action=view&Channel=1";
        String url1 = "http://10.15.102.193/stw-cgi/video.cgi?msubmenu=snapshot&action=view";
        String userName = "admin";
        String passWord = "adminadmin1";//User132@
        //httpSend(url, userName, passWord);
        downloadFileWithDigitAuth(url,userName,passWord);
    }

    public static void httpSend(String remoteUrlStr, String userName, String passWord) {
        CloseableHttpClient httpClient = null;
        try {

//            URIBuilder builder = new URIBuilder(url);
            URI url = new URI(remoteUrlStr);
            HttpHost target = new HttpHost("10.15.102.193", 80, "http");
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(new AuthScope(target.getHostName(), target.getPort()),
                    new UsernamePasswordCredentials(userName, passWord));
            //httpClient = HttpClients.custom().build();

                // 创建AuthCache对象
                AuthCache authCache = new BasicAuthCache();
                DigestScheme digestAuth = new DigestScheme();

                authCache.put(target, digestAuth);
                HttpClientContext localContext = HttpClientContext.create();
            localContext.setCredentialsProvider(credsProvider);
                localContext.setAuthCache(authCache);


            httpClient = HttpClients.createDefault();

                HttpGet httpget = new HttpGet(url);
                for (int i = 0; i < 3; i++) {
                    System.out.println(i);
                    CloseableHttpResponse response = httpClient.execute(target, httpget, localContext);
                    if (response.getStatusLine().getStatusCode()==401){
//                        Header[] headers = response.getHeaders("WWW-Authenticate");
//                        digestAuth.processChallenge(headers[0]);
//                        authCache.put(target, digestAuth);
//                        localContext.setAuthCache(authCache);
                        response.close();
                        continue;
                    }
                    HttpEntity entity = response.getEntity();

                    File file = new File("/Users/menglong/aa.jpeg");
                    file.getParentFile().mkdirs();
                    FileOutputStream os = new FileOutputStream(file);
                    entity.writeTo(os);

                    os.flush();
                    os.close();

                    response.close();
                    break;
                }

                httpClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void downloadFileWithDigitAuth(String url, String username, String password) {

        CloseableHttpClient httpClient=null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            HttpHost target = new HttpHost("10.15.102.193", 80, "http");
            HttpGet httpGet = new HttpGet(url);
            HttpContext httpContext = new BasicHttpContext();
            httpResponse = httpClient.execute(target,httpGet, httpContext);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
                Header authHeader = httpResponse.getFirstHeader(AUTH.WWW_AUTH);
                DigestScheme digestScheme = new DigestScheme();
                digestScheme.processChallenge(authHeader);
                UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
                httpGet.addHeader(digestScheme.authenticate(credentials, httpGet, httpContext));
                httpResponse.close();
                httpResponse = httpClient.execute(target,httpGet);
            }
            if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                File file = new File("/Users/menglong/aa.jpeg");
                file.getParentFile().mkdirs();
                FileOutputStream os = new FileOutputStream(file);
                httpResponse.getEntity().writeTo(os);
                os.close();
            }
        } catch (IOException | AuthenticationException | MalformedChallengeException e) {
            e.printStackTrace();
        }
        finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
