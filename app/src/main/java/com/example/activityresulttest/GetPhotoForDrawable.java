package com.example.activityresulttest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GetPhotoForDrawable extends ActivityResultContract<Void, Drawable> {
    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Void input) {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }

    @Override
    public Drawable parseResult(int resultCode, @Nullable Intent intent) {
        if (resultCode != Activity.RESULT_OK || intent == null) return null;
        Bitmap bitmap = intent.getParcelableExtra("data");
        return new BitmapDrawable(bitmap);
    }
}
