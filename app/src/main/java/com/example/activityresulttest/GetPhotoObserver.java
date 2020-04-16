package com.example.activityresulttest;

import android.graphics.Bitmap;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public class GetPhotoObserver implements DefaultLifecycleObserver {

    private ActivityResultRegistry registry;
    private ActivityResultLauncher<Void> getPhotoLauncher;
    private OnPhotoBackListener photoBackListener;

    public GetPhotoObserver(ActivityResultRegistry registry,
                            OnPhotoBackListener photoBackListener) {
        this.registry = registry;
        this.photoBackListener = photoBackListener;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        getPhotoLauncher = registry.register("key",
                new ActivityResultContracts.TakePicturePreview(),
                new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (photoBackListener != null) {
                    photoBackListener.photoBack(result);
                }
            }
        });
    }

    public void getPhoto() {
        getPhotoLauncher.launch(null);
    }


    interface OnPhotoBackListener {
        void photoBack(Bitmap result);
    }

}
