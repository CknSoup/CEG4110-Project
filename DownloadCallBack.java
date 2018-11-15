package com.example.ceg4110.ceg4110group13project;

import android.net.NetworkInfo;

public interface DownloadCallBack<T> {
    interface progress {
        int ERROR = -1;
        int CONNECT_SUCCESS = 0;
        int GET_INPUT_STREAM_SUCCESS = 1;
        int PROCESS_INPUT_STREAM_IN_PROGRESS = 2;
        int PROCESS_INPUT_STREAM_SUCCESS = 3;
    }

    void updateFromDownload(T result);

    NetworkInfo getActiveNetworkInfo();

    void onProgressUpdate(int progressCode, int percentComplete);

    void finishDownloading();
}
