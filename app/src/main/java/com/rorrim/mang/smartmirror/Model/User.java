package com.rorrim.mang.smartmirror.Model;

import com.google.gson.annotations.SerializedName;
import com.rorrim.mang.smartmirror.Interface.Connectable;

import java.util.Date;

public class User implements Connectable{

    //Gson이 Json키를 필드에 매핑하기 위해서 필요한데 두개가 같은 경우는 안해도 되지만 소스코드의
    //난독화를 해결하기 위해 사용하는 것이 좋다.
    @SerializedName("uid")
    private String uid;

    @SerializedName("email")
    private String email;

    @SerializedName("imageUrl")
    private String imageUrl;

    public User(String uid, String email){
        this.uid = uid;
        this.email = email;
        this.imageUrl = baseURL + imageURL+ uid+".jpg" ;
        //this.imageUrl = baseURL + imageURL + "1.jpg";
        //this.imageUrl = baseURL + "/test.jpg";
    }

    public String getUid(){
        return uid;
    }

    public String getEmail(){
        return email;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    @Override
    public String toString() {
        return "User [uid=" + uid + ", email =" + email;
    }

}
