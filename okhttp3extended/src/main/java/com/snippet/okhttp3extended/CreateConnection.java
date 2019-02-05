package com.snippet.okhttp3extended;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class CreateConnection implements ResponseListener {

	private static String mResponse = null;
	private static ProgressDialog progressDialog;
	private static OkHttpClient okHttpClient;
	private static Response response;
	private static Request request;
	private static RequestBody requestBody;
	private static String temp;
	private static String baseUrl = temp;
	private static ResponseListener mResponseListener;
	private static Context mContext;
	static int count = 0;

	public CreateConnection(String baseUrl) {
		temp = baseUrl;
		baseUrl = temp;
		okHttpClient = new OkHttpClient();
		count++;
	}

	public void changeBaseUrl(String url) {
		baseUrl = url;
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
		baseUrl = baseUrl + url;
		ConnectWithServer connectWithServer = new ConnectWithServer();
		connectWithServer.execute(requestBody);
	}
	public void createRequest(String url, RequestBody requestBody) {
		baseUrl = baseUrl + url;
		ConnectWithServer connectWithServer = new ConnectWithServer();
		connectWithServer.execute(requestBody);
	}

	@Override
	public void onResponseReceived(String json) {
	}

	@Override
	public void onResponseFailed() {
	}

	public static class ConnectWithServer extends AsyncTask<RequestBody, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(RequestBody... requestBodies) {
			requestBody = new FormBody.Builder()
			.add("hello", "rishabh")
			.build();
			request = new Request.Builder()
			.url(baseUrl)
			.post(requestBodies[0])
			.build();
			baseUrl = temp;
			try {
				response = okHttpClient.newCall(request).execute();
				mResponse = response.body().string();
			} catch (IOException e) {
				e.printStackTrace();
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

interface ResponseListener {
	void onResponseReceived(String json);

	void onResponseFailed();
}