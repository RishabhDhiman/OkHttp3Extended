package com.snippet.okhttp3extended;

public interface ResponseListener {
	void onResponseReceived(String json);

	void onResponseFailed();
}
