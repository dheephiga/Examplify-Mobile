package com.uitests.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import com.uitests.model.ExamTracking;
public interface TrackingService {
    @POST("/tracking/link")
    Call<String> createTrackingTicket(@Query("rollNumber") int rollNumber, @Query("subCode") String subjectCode, @Query ("answerScriptId") String answerScript);
}
