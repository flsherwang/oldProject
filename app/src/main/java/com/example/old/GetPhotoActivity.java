package com.example.old;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.example.application.OldApplication;
import com.example.entity.ImageItem;
import com.example.entity.IntentConstants;


public class GetPhotoActivity extends BaseActivity {
    private static final int TAKE_PICTURE = 0x000000;
    private String path = "";
    public static final String ACTION = "GetPhotoActivity";// 拍照完成发送广播
    private List<ImageItem> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_photo_popup);
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
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File vFile = new File(OldApplication.CACHE_PATH + "photo", String.valueOf(System.currentTimeMillis()) + ".jpg");
        if (!vFile.exists()) {
            vFile.getParentFile().mkdirs();
        } else {
            vFile.delete();
        }
        path = vFile.getAbsolutePath();
        Uri cameraUri = Uri.fromFile(vFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    // 返回已选择的图片信息
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
        switch (requestCode) {
            case TAKE_PICTURE:
                if (!TextUtils.isEmpty(path)) {
                    ImageItem item = new ImageItem();
                    item.sourcePath = path;
                    mDataList.add(item);
                    Intent intent = new Intent(ACTION);
                    intent.putExtra(IntentConstants.EXTRA_IMAGE_LIST, (Serializable) mDataList);
                    sendBroadcast(intent);
                }
                break;
        }
    }

    //	(Serializable) new ArrayList<ImageItem>(selectedImgs.values())
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return super.onTouchEvent(event);
    }
}
