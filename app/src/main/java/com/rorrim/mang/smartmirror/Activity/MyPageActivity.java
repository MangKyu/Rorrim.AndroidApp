package com.rorrim.mang.smartmirror.Activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rorrim.mang.smartmirror.Auth.AuthManager;
import com.rorrim.mang.smartmirror.R;
import com.rorrim.mang.smartmirror.databinding.ActivityMypageBinding;

public class MyPageActivity extends Activity {

    private ActivityMypageBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mypage);
        binding.setActivity(this);
        binding.setUser(AuthManager.getInstance().getUser());
    }

    public void addImage(View view){
        Intent intent = new Intent(MyPageActivity.this, MusicActivity.class);
        //Intent intent = new Intent(MyPageActivity.this, CalendarActivity.class);
        startActivity(intent);
    }
    /*
    public void addImage(View view){
        Glide.with(binding.getActivity())//AuthManager.getInstance().getUser().getImageUrl()
                .load("http://203.252.166.206:5000/get_image.jpg?fileName=ULIV1PWvtnYMj44Qzam7XVkSPx22.jpg")
                //.apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                //.skipMemoryCache(false)
                //)
                .into(binding.mypageFaceIv);
    }

    @BindingAdapter({"bind:imageUrl", "bind:error"})
    public static void loadImage(ImageView imageView, String url, Drawable errorDrawable){
        Glide.with(this)
                .load(url)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .skipMemoryCache(false)
                )
                .into(imageView);
    }
    */

    /*
    public void recieveImage(View view){

        RetrofitService retrofitService = RetrofitClient.getInstance().getRetrofit().create(RetrofitService.class);

        Call<ResponseBody> call = retrofitService.recieveFile("test.jpg");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // you  will get the reponse in the response parameter
                if(response.isSuccessful()) {
                    boolean writtenToDisk = writeResponseBodyToDisk(response.body());
                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("MyPage Activity", "error loading from login");
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            //File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + "Future Studio Icon.png");
            File file = new File("drawable://"+"profile.jpg");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

*/
}
