package com.snippet.okhttp3extended;

import java.io.IOException;

public interface ResponseListener {
	void onResponseReceived(String json);

	void onIOExeption(IOException io);

	void onResponseFailed();
}
