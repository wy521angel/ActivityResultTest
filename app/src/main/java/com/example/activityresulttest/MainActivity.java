package com.example.activityresulttest;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ImageView photo;
    private GetPhotoObserver getPhotoObserver;
    private GetPhotoLiveData getPhotoLiveData;
    private String[] titles = {"跳转到下一页获取数据返回", "请求权限", "拍照", "自定义返回值", "使用Lifecycle脱离Activity解耦",
            "配合LiveData的使用"};

    private ActivityResultLauncher<Intent> startActivity =
            prepareCall(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getData() != null) {
                Toast.makeText(MainActivity.this, result.getData().getStringExtra("DATA"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    });

    private ActivityResultLauncher<String> requestPermission =
            prepareCall(new ActivityResultContracts.RequestPermission(),
                    new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            Toast.makeText(MainActivity.this, "获取权限结果：" + result, Toast.LENGTH_SHORT).show();
        }
    });

    private ActivityResultLauncher<Void> getPhoto =
            prepareCall(new ActivityResultContracts.TakePicturePreview(),
                    new ActivityResultCallback<Bitmap>() {
        @Override
        public void onActivityResult(Bitmap result) {
            photo.setImageBitmap(result);
        }
    });

    private ActivityResultLauncher<Void> getPhotoCustom = prepareCall(new GetPhotoForDrawable(),
            new ActivityResultCallback<Drawable>() {
        @Override
        public void onActivityResult(Drawable result) {
            photo.setImageDrawable(result);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photo = findViewById(R.id.photo);
        getPhotoObserver = new GetPhotoObserver(getActivityResultRegistry(),
                new GetPhotoObserver.OnPhotoBackListener() {
            @Override
            public void photoBack(Bitmap result) {
                photo.setImageBitmap(result);
            }
        });
        getLifecycle().addObserver(getPhotoObserver);

        getPhotoLiveData = new GetPhotoLiveData(getActivityResultRegistry());
        getPhotoLiveData.observeForever(new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                photo.setImageBitmap(bitmap);
            }
        });


        listView = findViewById(R.id.list);
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                titles);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity.launch(new Intent(MainActivity.this, OtherActivity.class).putExtra("DATA2", "跳转到了第二页"));
                        break;
                    case 1:
                        requestPermission.launch(Manifest.permission.READ_PHONE_STATE);
                        break;
                    case 2:
                        getPhoto.launch(null);
                        break;
                    case 3:
                        getPhotoCustom.launch(null);
                        break;
                    case 4:
                        getPhotoObserver.getPhoto();
                        break;
                    case 5:
                        getPhotoLiveData.getPhotoLauncher.launch(null);
                        break;
                }
            }
        });
    }


}
