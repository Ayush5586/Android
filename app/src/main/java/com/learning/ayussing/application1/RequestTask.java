package com.learning.ayussing.application1;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by ayussing on 6/12/2015.
 */
class RequestTask extends AsyncTask<String, String, String> {

    Context ctx;
    TextView tv;
    public RequestTask(Context c, TextView tv1)
    {
        ctx = c;
        tv = tv1;
    }
    @Override
    protected String doInBackground(String... uri) {
        HttpClient httpclient = new DefaultHttpClient();
        int a = 0;
        HttpResponse response;
        StringBuilder responseString = new StringBuilder();
        try {
            response = httpclient.execute(new HttpGet(uri[0]));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                responseString.append(out.toString());
                out.close();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            responseString.append(e.toString());
        } catch (IOException e) {
            responseString.append(e.toString());
        } catch (Exception e) {
            responseString.append(e.toString());
        }
        finally {
            return responseString.toString();
        }

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        tv.setText(result);
    }
}