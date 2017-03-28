package com.example.old;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.application.OldApplication;
import com.example.entity.ImageItem;
import com.example.entity.IntentConstants;
import com.lidroid.xutils.ViewUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class GetPhotoActivity extends BaseActivity {
    private static final int TAKE_PICTURE = 0x000000;
    private String path = "";
    public static final String ACTION = "GetPhotoActivity";// 拍照完成发送广播
    private List<ImageItem> mDataList = new ArrayList<>();
    Uri cameraUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_photo_popup);
        OldApplication.getInstance().addActivity(this);
        ViewUtils.inject(this);
        initView();
    }

    private void initView() {
        TextView bt1 = (TextView) findViewById(R.id.photograph);
        TextView bt2 = (TextView) findViewById(R.id.selectPhoto);
        TextView bt3 = (TextView) findViewById(R.id.cancel);
        // 拍照
        bt1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                takePhoto();
            }
        });
        // 从相册选择
        bt2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageBucketChooseActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // 取消
        bt3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * 拍照
     */
    public void takePhoto() {
        File vFile = new File(OldApplication.CACHE_PATH + "photo", String.valueOf(System.currentTimeMillis()) + ".jpg");
        if (!vFile.exists()) {
            vFile.getParentFile().mkdirs();
        } else {
            vFile.delete();
        }
        path = vFile.getAbsolutePath();
        cameraUri = Uri.fromFile(vFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        startActivityForResult(intent, TAKE_PICTURE);

    }

    // 返回已选择的图片信息
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      OldApplication.getInstance().toFinish(GetPhotoActivity.class);
        switch (requestCode) {
            case TAKE_PICTURE:
                ContentResolver resolver = getContentResolver();
                //照片的原始资源地址
//                Uri originalUri = data.getData();
                try {
                    //使用ContentProvider通过URI获取原始图片
                    Bitmap photo = MediaStore.Images.Media.getBitmap(resolver,  cameraUri);
                    if (photo != null) {
                        //为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                        ImageItem item = new ImageItem();
                        item.sourcePath = path;
                        mDataList.add(item);
                        Intent intent = new Intent(ACTION);
                        intent.putExtra(IntentConstants.EXTRA_IMAGE_LIST, (Serializable) mDataList);
                        sendBroadcast(intent);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return super.onTouchEvent(event);
    }
}
