package com.rorrim.mang.smartmirror.Network;

import com.rorrim.mang.smartmirror.Model.Data;
import com.rorrim.mang.smartmirror.Model.User;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitService {

    @GET("/login")
    Call<User> getUser(
            @Query("id") String id
    );

    @GET("/get_json")
    Call<LinkedList<Data>> getData();

    /*
    //Start with / neans Absolute route
    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<User>> repoContributors(
            @Path("owner") String owner,
            @Path("repo") String repo);

    //Start without / means Relative route
    @GET("repos/{owner}/{repo}/contributors")
    Call<List<User>> repoContri(@Url String url);

    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Call<User> getAnswers(@Query("tagged") String tags);
    */
}
