package pp.facerecognizer;

import java.util.List;

public interface UploadFileAsyncTaskCallback<T> {
    interface Progress {
        int ERROR = -1;
        int CONNECT_SUCCESS = 0;
        int GET_INPUT_STREAM_SUCCESS = 1;
        int PROCESS_INPUT_STREAM_IN_PROGRESS = 2;
        int PROCESS_INPUT_STREAM_SUCCESS = 3;
    }

    void onResponse(T result);
    void finishDownloading(List<T> result);
}