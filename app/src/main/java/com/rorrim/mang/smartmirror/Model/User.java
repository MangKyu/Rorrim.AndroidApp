package com.rorrim.mang.smartmirror.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class User {

    //Gson이 Json키를 필드에 매핑하기 위해서 필요한데 두개가 같은 경우는 안해도 되지만 소스코드의
    //난독화를 해결하기 위해 사용하는 것이 좋다.
    @SerializedName("id")
    private String id;

    @SerializedName("pw")
    private String pw;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;

    @SerializedName("birth")
    private Date birth;


    public User(String id,String pw, String email, String phone, Date birth){
        this.id = id;
        this.pw = pw;
        this.email = email;
        this.phone = phone;
        this.birth = birth;
    }



    @Override
    public String toString() {
        return "User [id=" + id + ", email =" + email + ", phone =" + phone + ", birth = " + birth;
    }

}
