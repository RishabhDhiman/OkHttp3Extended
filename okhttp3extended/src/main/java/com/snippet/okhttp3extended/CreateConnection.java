package com.snippet.okhttp3extended;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/*
 *
 * Created By Rishabh Dhiman on 8/2/2019
 *
 */

public class CreateConnection implements ResponseListener {

	private static String mResponse = null;
	private static ProgressDialog progressDialog;
	private static OkHttpClient okHttpClient;
	private static Response response;
	private static Request request;
	private static RequestBody requestBody;
	private static String temp;
	private static String mBaseUrl = temp;
	private static ResponseListener mResponseListener;
	private static Context mContext;
	static int count = 0;

	public CreateConnection(String baseUrl) {
		temp = baseUrl;
		mBaseUrl = temp;
		okHttpClient = new OkHttpClient();
        count++;
	}

	public void setTimeOuts(@NonNull int connectTimeout ,@NonNull int writeTimeout ,@NonNull int readTimeout ,@NonNull TimeUnit timeUnit)
	{
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.connectTimeout(connectTimeout, timeUnit) // connect timeout
				.writeTimeout(writeTimeout, timeUnit) // write timeout
				.readTimeout(readTimeout, timeUnit);
		okHttpClient = builder.build();
	}

	public void changeBaseUrl(String url) {
		mBaseUrl = url;
	}

	public String getRequestedUrl()
	{
		return mBaseUrl;
	}
	private static void network(Context context) {
		progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Loading");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.show();
	}


	public void createRequest(String url, RequestBody requestBody, Context context) {
		network(context);
		mContext = context;
		mBaseUrl = mBaseUrl + url;
		ConnectWithServer connectWithServer = new ConnectWithServer();
		connectWithServer.execute(requestBody);
	}
	public void createRequest(String url, RequestBody requestBody)throws IOException {
		mBaseUrl = mBaseUrl+ url;
		ConnectWithServer connectWithServer = new ConnectWithServer();
		connectWithServer.execute(requestBody);
	}

	@Override
	public void onResponseReceived(String json) {
	}

	@Override
	public void onResponseFailed() {
	}

    @Override
    public void onIOExeption(IOException io) {

    }

    private static class ConnectWithServer extends AsyncTask<RequestBody, Void, Void>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(RequestBody... requestBodies){
			requestBody = new FormBody.Builder()
					.add("hello", "rishabh")
					.build();
			request = new Request.Builder()
					.url(mBaseUrl)
					.post(requestBodies[0])
					.build();
			mBaseUrl = temp;
            try {
                response = okHttpClient.newCall(request).execute();
                mResponse = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                mResponseListener.onIOExeption(e);
            }
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			if ((mResponse == null)) {
				mResponseListener.onResponseFailed();
			} else {
				mResponseListener.onResponseReceived(mResponse);
			}
			mResponse = null;
			if(mContext!=null)
				progressDialog.dismiss();
		}
	}

	public void setResponseListener(ResponseListener responseListener) {
		mResponseListener = responseListener;
	}
}