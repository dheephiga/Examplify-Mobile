package com.uitests.retrofit;
import com.uitests.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface studentApi {
        @GET("/student/all")
        Call<List<Student>> getAllData();

        @GET("/subject/code/{studentId}")
        Call<String> getSubjectCode(@Path("studentId") int studentId);
}
