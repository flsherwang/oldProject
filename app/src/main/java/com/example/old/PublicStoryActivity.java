package com.example.old;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.adapter.ImagePublishAdapter;
import com.example.application.OldApplication;
import com.example.entity.ImageItem;
import com.example.entity.IntentConstants;
import com.example.util.Base64Coder;
import com.example.util.GetBitmapUsingOptions;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 发布故事页
 * Created by Administrator on 2017/2/7.
 */

public class PublicStoryActivity extends BaseActivity {
    @ViewInject(R.id.gridView)
    private GridView gridView;
    private ImagePublishAdapter mAdapter;
    public List<ImageItem> mDataList = new ArrayList<>();// 保存的图片信息
    private final static String casheFilePath = OldApplication.CACHE_PATH + "caseRecordPic";
    public final static String casheBigFilePath = casheFilePath + "/bigPic";// 保存大图路径
    public final static String casheSmallFilePath = casheFilePath + "/smallPic";// 保存小图路径
    public List<Long> picName = new ArrayList<>();// 保存缓存的图片名
    List<String> smallPicList = new ArrayList<>();
    List<String> BigPicList = new ArrayList<>();
    List<String> uploadedImageList = new ArrayList<>();
    Integer positionImage = 0;
    private Receiver receiver = new Receiver();
    private File file, file2, file3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_story);
        OldApplication.getInstance().addActivity(this);
        ViewUtils.inject(this);
        initView();

    }

    private void initView() {
        regReceiver();
        initBackView();
        initTitleView(getResources().getString(R.string.title_public_story));
        initRightMenuView(getResources().getString(R.string.submit));
        initRightMenuView1(getResources().getString(R.string.preview));
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new ImagePublishAdapter(this, mDataList);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 当点击的是+按钮时
                if (position == mDataList.size()) {
                    startActivity(new Intent(context, GetPhotoActivity.class));
                }
                // 查看已选择的图片
                else {
                    Intent intent = new Intent(context, ImageZoomActivity.class);
                    intent.putExtra(IntentConstants.EXTRA_IMAGE_LIST, (Serializable) mDataList);
                    intent.putExtra(IntentConstants.EXTRA_CURRENT_IMG_POSITION, position);
                    startActivityForResult(intent, 100);
                }
            }
        });
    }

    //开始提交,先上传图片,递归操作
    private void SubmitApply() {
        positionImage = 0;
        uploadedImageList.clear();
        uploadImageRecursive();
    }

    //上传原因
    private void uploadRefundApplyInfo() {
//        RequestParams params = new RequestParams();
//        params.addQueryStringParameter("orderuuid", getIntent().getLongExtra("uuid", 0) + "");
//        params.addQueryStringParameter("type", 0 + "");
//        params.addQueryStringParameter("refundResonId", refundResonId + "");
//        params.addQueryStringParameter("refundRemark", remark_tv.getText().toString());
        String refundImages = "";
        for (int i = 0; i < uploadedImageList.size(); i++) {
            refundImages = refundImages + uploadedImageList.get(i);
            if (i + 1 != uploadedImageList.size()) {
                refundImages = refundImages + ",";
            }
        }
//        params.addQueryStringParameter("refundImages", refundImages);
//        HttpGetDataUtil.sendSimpleData(RefundApplyActivity.this, params, R.string.url_prefix_common, R.string.apply_refund, "正在提交申请...", new SimpleCallBack() {
//            @Override
//            public void onSuccess(Object msg) {
//                showToast("退款申请已提交,客服人员会尽快和您联系");
//                Intent intent = new Intent();
//                intent.putExtra("position", getIntent().getIntExtra("position", -1));
//                intent.setAction("orderPayed");
//                intent.putExtra("operationType", 1);
//                sendBroadcast(intent);
//                WelfareApplication.getInstance().toFinish(RefundApplyActivity.class);
//                OrderDetialActivity.orderDetialActivity.finish();
//            }
//        });
    }


    /**
     * 递归传图
     *
     * @param
     * @return -1表示有问题其他表示完成
     */
    private void uploadImageRecursive() {
        //停止条件

        if (null == positionImage || positionImage < 0 || positionImage >= BigPicList.size()) {
            //上传原因
            uploadRefundApplyInfo();
            //删除缓存图片
            File bigImageDir = new File(OldApplication.CACHE_PATH + "caseRecordPic/bigPic");
            File smallImageDir = new File(OldApplication.CACHE_PATH + "caseRecordPic/smallPic");
            if (bigImageDir.exists()) {
                for (File file : bigImageDir.listFiles()) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }

            if (smallImageDir.exists()) {
                for (File file : smallImageDir.listFiles()) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
            return;
        }

        if (0 < BigPicList.size()) {
            //上传图片
            final JSONObject jsonObject = new JSONObject();
            try {
                String uri = BigPicList.get(positionImage);
                //上传图片
                Bitmap tBitmap = null;

                InputStream in = new BufferedInputStream(new URL(uri).openStream(), 1024);
                final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
                BufferedOutputStream out = new BufferedOutputStream(dataStream, 1024);
                byte[] b = new byte[1024];
                int read;
                while ((read = in.read(b)) != -1) {
                    out.write(b, 0, read);
                }
                out.flush();
                in.close();
                out.close();
                byte[] data = dataStream.toByteArray();
                tBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                data = null;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                tBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                stream.flush();
                stream.close();

                //把流转化为数组
                byte[] bytes = stream.toByteArray();
                String fileName = uri.substring(uri.lastIndexOf("/") + 1, uri.length());
                String file = new String(Base64Coder.encode(bytes));
                RequestParams requestParams = new RequestParams();
                String jsonStr = fileName + "," + file;
                jsonObject.put(fileName, jsonStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //
//            RequestParams imageParams = new RequestParams();
//            imageParams.addBodyParameter("images", jsonObject.toJSONString());
            //Log.d( jsonObject.toJSONString() );
//            Log.e("jsonObject.toJSONString().size():" + jsonObject.toJSONString().length());
//            HttpGetDataUtil.sendSimpleData(RefundApplyActivity.this, imageParams, R.string.url_prefix_common, R.string.upload_image_base64, "正在上传第" + (positionImage + 1) + "张图", new SimpleCallBack() {
//                @Override
//                public void onSuccess(Object msg) {
//                    JSONObject returnObj = JSONObject.parseObject(msg.toString());
//                    Log.e(msg.toString());
//                    // showToast( "第" + positionImage + "张图片上传成功");
//                    Set<String> keySet = returnObj.keySet();
//                    for (String str : keySet) {
//                        uploadedImageList.add(str);// jsonObject.getString( str ) );
//                    }
//                    positionImage++;
//                    uploadImageRecursive();
//                }
//            });

        }
    }


    /**
     * 创建缓存已选择图片文件夹
     */
    private void createFileDir() {
        file = new File(casheFilePath);
        file2 = new File(casheSmallFilePath);
        file3 = new File(casheBigFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!file2.exists()) {
            file2.mkdirs();
        }
        if (!file3.exists()) {
            file3.mkdirs();
        }
    }

    /**
     * 处理压缩图片方法
     */
    private void handlePic() {
        // removeTempFromPref();
        // 把选中的图片保存到文件夹中
        if (mDataList.size() > 0) {
            // Log.e("wang", "wang=" + mDataList.toString());
            for (int i = 0; i < mDataList.size(); i++) {
                // 当不是已经上传的图片才做压缩处理
                if (!("http".equals(mDataList.get(i).sourcePath.substring(0, 4)))) {
                    final Long currentTime = System.currentTimeMillis();// 当前时间(以当前时间戳命名图片)
                    picName.add(currentTime);
                    File fromFile = new File(mDataList.get(i).sourcePath);// 原图地址
                    File toFile = new File(casheBigFilePath + "/" + currentTime + ".jpg");// 压缩后保存的图片地址
                    copyfile(fromFile, toFile, false);// 拷贝
                    getImage(casheBigFilePath + "/" + currentTime + ".jpg", currentTime);// 按比例压缩图片
                    compress(casheBigFilePath + "/" + currentTime + ".jpg", currentTime);// 压缩图片到1M以下
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        mDataList = new ArrayList<>();
        picName = new ArrayList<>();
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    /**
     * 拷贝图片文件
     *
     * @param fromFile
     * @param toFile
     * @param rewrite
     */
    public static void copyfile(File fromFile, File toFile, Boolean rewrite) {

        if (!fromFile.exists()) {
            return;
        }

        if (!fromFile.isFile()) {
            return;
        }
        if (!fromFile.canRead()) {
            return;
        }
        if (!toFile.getParentFile().exists()) {
            toFile.getParentFile().mkdirs();
        }
        if (toFile.exists() && rewrite) {
            toFile.delete();
        }

        try {
            FileInputStream fosfrom = new FileInputStream(fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);

            byte[] bt = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            // 关闭输入、输出流
            fosfrom.close();
            fosto.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 按比例压缩图片
     *
     * @param srcPath
     * @return
     */
    private void getImage(String srcPath, Long currentTime) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 设置图片的宽高
        float hh = 100f;
        float ww = 100f;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        compressImage(bitmap, currentTime);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 压缩质量
     * 修改建议,判断图片大小,根据大小计算压缩质量,一次性压缩,不要循环压缩
     *
     * @param image
     * @return
     */
    private void compressImage(Bitmap image, long currentTime) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

        //性能优化,直接压缩成500K,不用循环
        int imageSize = baos.toByteArray().length;
        if (0 != imageSize) {
            int options = (50000 * 1024) / baos.toByteArray().length;
            if (options < 100) {
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            }
        }

//        while (baos.toByteArray().length / 1024 > 1000) { // 循环判断如果压缩后图片是否大于1000kb,大于继续压缩
//            baos.reset();// 重置baos即清空baos
//            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
//            options -= 10;// 每次都减少10
//        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        saveBitmapBySmallImage(currentTime, bitmap);
    }

    /**
     * 保存Bitmap方法
     */
    public void saveBitmapBySmallImage(long currentTime, Bitmap bitmap) {

        File smallPicDir = new File(casheSmallFilePath);
        if (!smallPicDir.exists()) {
            smallPicDir.mkdir();
        }

        File myCaptureFile = new File(casheSmallFilePath + "/" + currentTime + ".jpg");// 处理小图
        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 压缩原图到1M以下
     *
     * @param filePath
     * @param currentTime
     */
    private void compress(String filePath, long currentTime) {
        Bitmap bitmap = GetBitmapUsingOptions.execute(filePath);
        saveBitmapByBigImage(currentTime, bitmap);
    }

    /**
     * 保存压缩后的大图
     */

    public void saveBitmapByBigImage(long currentTime, Bitmap bitmap) {

        File myCaptureFile = new File(casheBigFilePath + "/" + currentTime + ".jpg");// 保存压缩处理后的大图
        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 上传图片和文字
     *
     * @throws IOException
     */
    private void addServiceProductPic() {

        handlePic();
        addService();
    }


    private void addService() {
        int len = mDataList.size();

        for (int i = 0; i < len; i++) {
            smallPicList.add(i, "file://" + "/" + casheSmallFilePath + "/" + picName.get(i) + ".jpg");
            BigPicList.add(i, "file://" + "/" + casheBigFilePath + "/" + picName.get(i) + ".jpg");
        }
    }

    /**
     * 接收广播
     */
    class Receiver extends BroadcastReceiver {

        @SuppressWarnings("unchecked")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ImageChooseActivity.ACTION) || intent.getAction().equals(GetPhotoActivity.ACTION)) {
                mDataList.addAll((List<ImageItem>) intent.getSerializableExtra(IntentConstants.EXTRA_IMAGE_LIST));// 选择相册图片或者拍照发送的数据
                mAdapter.notifyDataSetChanged();
            }

        }
    }

    /**
     * 监听广播
     */
    private void regReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ImageChooseActivity.ACTION);
        filter.addAction(GetPhotoActivity.ACTION);
        registerReceiver(receiver, filter);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                mDataList = new ArrayList<>();
                if (intent != null) {
                    mDataList.addAll((List<ImageItem>) intent.getSerializableExtra(IntentConstants.EXTRA_IMAGE_LIST));// 删除选择的图片后发送的数据
                }
                mAdapter = new ImagePublishAdapter(this, mDataList);
                gridView.setAdapter(mAdapter);
            }
        }
    }

}
