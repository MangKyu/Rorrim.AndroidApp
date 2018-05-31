package com.rorrim.mang.smartmirror.Adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rorrim.mang.smartmirror.Model.Calendar;
import com.rorrim.mang.smartmirror.Model.Music;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AdapterBindings {

    private static final BitmapFactory.Options options = new BitmapFactory.Options();
    private static int MAX_IMAGE_SIZE = 170;

    @BindingAdapter("bind:item")
    public static void bindItem(RecyclerView recyclerView, ObservableArrayList<Music> musicList) {
        MusicAdapter adapter = (MusicAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItem(musicList);
        }
    }

    @BindingAdapter("bind:item")
    public static void bindCal(RecyclerView recyclerView, ObservableArrayList<Calendar> calendarList) {
        CalendarAdapter adapter = (CalendarAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItem(calendarList);
        }
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView imageView, String imageUrl){
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .skipMemoryCache(false)
                )
                .into(imageView);
    }

    @BindingAdapter({"bind:albumImage"})
    public static void getAlbumImage(ImageView imageView, String albumId){
        //private static Bitmap getAlbumImage(Context context, int album_id, int MAX_IMAGE_SIZE) {
        // NOTE: There is in fact a 1 pixel frame in the ImageView used to
        // display this drawable. Take it into account now, so we don't have to
        // scale later.
        ContentResolver res = imageView.getContext().getContentResolver();
        Uri uri = Uri.parse("content://media/external/audio/albumart/" + albumId);
        //uri = Uri.parse("android.resource://"+imageView.getContext().getPackageName()+"/drawable/album.png");

        int scale = 0;
        if (options.outHeight > MAX_IMAGE_SIZE || options.outWidth > MAX_IMAGE_SIZE) {
            scale = (int) Math.pow(2, (int) Math.round(Math.log(MAX_IMAGE_SIZE / (double) Math.max(options.outHeight, options.outWidth)) / Math.log(0.5)));
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = scale;

        if (uri != null) {
            ParcelFileDescriptor fd = null;
            try {
                fd = res.openFileDescriptor(uri, "r");


                // Compute the closest power-of-two scale factor
                // and pass that to sBitmapOptionsCache.inSampleSize, which will
                // result in faster decoding and better quality

                //크기를 얻어오기 위한옵션 ,
                //inJustDecodeBounds값이 true로 설정되면 decoder가 bitmap object에 대해 메모리를 할당하지 않고, 따라서 bitmap을 반환하지도 않는다.
                // 다만 options fields는 값이 채워지기 때문에 Load 하려는 이미지의 크기를 포함한 정보들을 얻어올 수 있다.

                BitmapFactory.decodeFileDescriptor(
                        fd.getFileDescriptor(), null, options);

                Bitmap b = BitmapFactory.decodeFileDescriptor(
                        fd.getFileDescriptor(), null, options);

                if (b != null) {
                    // finally rescale to exactly the size we need
                    if (options.outWidth != MAX_IMAGE_SIZE || options.outHeight != MAX_IMAGE_SIZE) {
                        Bitmap tmp = Bitmap.createScaledBitmap(b, MAX_IMAGE_SIZE, MAX_IMAGE_SIZE, true);
                        b.recycle();
                        b = tmp;
                    }
                }
                imageView.setImageBitmap(b);
                return;
            } catch (FileNotFoundException e) {
                imageView.setImageDrawable(null);
                //imageView.setImageDrawable(Drawable.createFromPath("android.resource://"+imageView.getContext().getPackageName()+"/drawable/album.png"));

            } catch(IllegalStateException e){
                imageView.setImageDrawable(null);
            }

            finally {
                try {
                    if (fd != null)
                        fd.close();
                } catch (IOException e) {
                }
            }
        }
        //imageView.setImageDrawable(Drawable.createFromPath("android.resource://"+imageView.getContext().getPackageName()+"/drawable/album.png"));
        //return null;
    }


}