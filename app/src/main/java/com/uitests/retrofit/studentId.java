package com.uitests.retrofit;
import com.uitests.model.Student;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface studentId{

    //    @GET("student/{id}")
      //  Call<Student> getStudentById(@Path("id") int studentId);

        @GET("api/student/{id}")
        Call<Student> getStudentById(@Path("id") int studentId);



}