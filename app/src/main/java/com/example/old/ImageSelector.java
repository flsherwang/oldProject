package com.example.old;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.application.OldApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ImageSelector extends BaseActivity {
    private LinearLayout photograph, photo_album, cancel;
    public static final int NONE = 0;
    public static final int PHOTOHRAPH = 1;// 拍照
    public static final int PHOTOZOOM = 2; // 缩放
    public static final int PHOTORESOULT = 3;// 结果
    // public static final String IMAGE_UNSPECIFIED = "image/*";
    private final String filePath = OldApplication.pathSDCard + "/laifu/temp.jpg";
    private int width = 0, height = 0, type;
    private Uri uri;
    File picture = null;
    private String path = "";
    private String copyPath = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_selecter);
        initView();

    }

    private void initView() {
        Intent intent = getIntent();
        width = intent.getIntExtra("width", 0);
        height = intent.getIntExtra("height", 0);
        File file = new File(filePath);

        if (!file.exists()) {
            file.mkdirs();
        }
        photo_album = (LinearLayout) findViewById(R.id.photo_album);
        photo_album.setOnClickListener(new View.OnClickListener() {// 相册

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                 Intent intent = new Intent(Intent.ACTION_PICK, null);
//                 intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                 IMAGE_UNSPECIFIED);
//                 startActivityForResult(intent, PHOTOZOOM);
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), PHOTOZOOM);

            }
        });

        photograph = (LinearLayout) findViewById(R.id.photograph);
        photograph.setOnClickListener(new View.OnClickListener() {// 拍照

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File vFile = new File( OldApplication.pathSDCard + "/laifu/", "temp.jpg");

                if (!vFile.exists()) {
                    vFile.getParentFile().mkdirs();
                } else {
                    vFile.delete();
                }
                path = vFile.getAbsolutePath();
                Log.e("wang", "path=" + path.toString());
                Uri cameraUri = Uri.fromFile(vFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                startActivityForResult(intent, PHOTOHRAPH);
                // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new
                // File(Config.CACHE_PATH, "temp.jpg")));
                // startActivityForResult(intent, PHOTOHRAPH);
            }
        });

        cancel = (LinearLayout) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    //这段代码会导致原图被截图,理论上应该将截图产生的图片另外存一个地方
    private void startPhotoZoom(Uri uri) {
        this.uri = uri;
        //拷图
        copyPath = getAbsoluteImagePath( uri );//getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath();
        copyPath = copyPath.substring( 0, copyPath.lastIndexOf( "." ) ) + "laifucut" + copyPath.substring( copyPath.lastIndexOf( "." ), copyPath.length() );

        String fromPath = getAbsoluteImagePath( uri );
        String pathTmp = fromPath.substring( 0, fromPath.lastIndexOf( "/" ) + 1 );
        String fileNameTmp = fromPath.substring( fromPath.lastIndexOf( "/" ) + 1, fromPath.length() );
        File fromFile = new File( pathTmp, fileNameTmp );

        pathTmp = copyPath.substring( 0, copyPath.lastIndexOf( "/" ) + 1 );
        fileNameTmp = copyPath.substring( copyPath.lastIndexOf( "/" ) + 1, copyPath.length() );
        File toFile = new File( OldApplication.pathSDCard, fileNameTmp );
        copyPath = OldApplication.pathSDCard + "/" + fileNameTmp;
        Uri myuri = Uri.fromFile( toFile );// Uri.parse( copyPath );

        try{
            File dirFile = new File( OldApplication.pathSDCard );
            if ( !dirFile.exists() ){
                dirFile.mkdir();
            }
            if ( !toFile.exists() ){
                toFile.createNewFile();
            }

            FileInputStream fosfrom = new FileInputStream( fromFile );
            FileOutputStream fosto = new FileOutputStream( toFile );

            byte[] bt = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            // 关闭输入、输出流
            fosto.flush();
            fosfrom.close();
            fosto.close();

        }catch ( Exception e ){
            e.printStackTrace();
        }


        //打开剪辑页面
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType( myuri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);// 输出是X方向的比例
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高，切忌不要再改动下列数字，会卡死
        intent.putExtra("outputX", width);// 输出X方向的像素
        intent.putExtra("outputY", height);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, myuri );
        intent.putExtra("return-data", false);// 设置为不返回数据
        startActivityForResult(intent, PHOTORESOULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == NONE)
            return;

        // 拍照
        if (requestCode == PHOTOHRAPH) {
            // 设置文件保存路径这里放在跟目录下
            picture = new File(path);
            if (width > 0) {
                type = 1;
                startPhotoZoom(Uri.fromFile(picture));
            } else {
                setResult(RESULT_OK, new Intent().putExtra("uri", Uri.fromFile(picture).toString()));
                finish();
            }
        }
        if (data == null)
            return;

        // 读取相册缩放图片
        if (requestCode == PHOTOZOOM) {
            if (width > 0) {
                type = 2;
                startPhotoZoom(data.getData());
            } else {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(data.getData(), filePathColumn, null, null, null);
                cursor.moveToFirst();
                setResult(RESULT_OK, new Intent().putExtra("uri", Uri.fromFile(new File(cursor.getString(cursor.getColumnIndex(filePathColumn[0])))).toString()));
                cursor.close();
                finish();
            }
        }
        // 处理结果
        if (requestCode == PHOTORESOULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                if (type == 1) {
                    setResult(RESULT_OK, new Intent().putExtra("uri", copyPath.toString()).putExtra("type",type));
                } else if (type == 2) {
                    setResult(RESULT_OK, new Intent().putExtra( "uri", "file:///" + copyPath ) );
                }
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    protected String getAbsoluteImagePath(Uri uri)
    {
        String[] proj = {MediaStore.Images.Media.DATA};
        if ( uri.getScheme().toString().compareTo( "content" ) == 0 ){
            Cursor actualimagecursor = getContentResolver().query( uri, proj, null, null, null ); //managedQuery(uri, proj, null, null, null);

            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            actualimagecursor.moveToFirst();

            return  actualimagecursor.getString(actual_image_column_index);
        }else if( uri.getScheme().toString().compareTo( "file" ) == 0 ){
            return uri.toString().replace( "file:", "" );
        }
        return "";
    }
}