package com.example.activityresulttest;

import android.graphics.Bitmap;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

public class GetPhotoLiveData extends LiveData<Bitmap> {

    private ActivityResultRegistry registry;
    public ActivityResultLauncher<Void> getPhotoLauncher;

    @Override
    protected void onActive() {
        super.onActive();
        getPhotoLauncher = registry.register("key",
                new ActivityResultContracts.TakePicturePreview(),
                new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                setValue(result);
            }
        });

    }

    @Override
    protected void onInactive() {
        super.onInactive();
        getPhotoLauncher.unregister();
    }

    public GetPhotoLiveData(ActivityResultRegistry registry) {
        this.registry = registry;
    }

}
