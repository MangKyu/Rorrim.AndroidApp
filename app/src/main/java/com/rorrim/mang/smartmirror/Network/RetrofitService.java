package com.rorrim.mang.smartmirror.Network;

import com.rorrim.mang.smartmirror.Model.JSonMessage;
import com.rorrim.mang.smartmirror.Model.User;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitService {

    @GET("/login")
    Call<User> getUser(
            @Query("id") String id
    );

    @GET("/get_json")
    Call<JSonMessage> getJson();

    /*
    @GET("/recieve_file")
    Call<ResponseBody> recieveFile(
            @Query("fileName") String fileName
    );
    */

    @GET("/recieve_file")
    Call<ResponseBody> recieveFile(
            @Query("fileName") String fileName
            //@Body String fileName
    );

    @POST("/recieve_image")
    Call<ResponseBody> recieveImage(
            @Url String imageUrl
    );


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
