package com.example.application;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.example.old.MainActivity;
import com.example.old.R;
import com.example.util.StringUtils;
import com.lidroid.xutils.util.LogUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OldApplication extends Application{

    /**
     * 当前屏幕宽度
     */
    public static int screenW = 0;
    /**
     * 当前屏幕高度
     */
    public static int screenH = 0;
    /**
     * 默认时间格式
     */
    public SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.CHINA);
    /**
     * 默认资料缓存路径
     */
    public static String pathSDCard = "";
    public static String CachePath = "/storage/sdcard0/Android/data/com.example.old/cache";
    public static String CACHE_PATH = "/storage/emulated/0/old/";

    /**
     * 默认文件缓存路径
     */
    private static String FilePath = "/storage/sdcard0/Android/data/com.example.old/files/fs";
    /**
     * 异常路径
     */
    public String EXCEPTION_PATH = CachePath + "/exception/";
    /**
     * 数据库路径
     */
    public String DB_PATH = CachePath + "/db/";
    /**
     * apk路径
     */
    public String APK_PATH = CachePath + "/apk/";
    /**
     * 缓存资料路径
     */
    public String DATA_CACHE_PATH = CachePath + "/datacache";
    /**
     * 图片缓存路径
     */
    public String IMG_CACHE_PATH = CachePath + "/imgcache";
    /**
     * 图片备份缓存路径
     */
    public String IMG_RCACHE_PATH = FilePath + "/imgcache";
    /**
     * 验证码缓存路径
     */
    public String VALIDATE_IMG_PATH = CachePath + "/validateimg/";

    // 请求端口
//    public String PayHost = "http://www.laifucard.net";
//    public String PayHost = "https://www.laifucard.com";

//    	public String HostName = "https://www.laifucard.com";//正式服务器
//    public String HostName = "https://www.laifucard.net";//测试服务器
//    	public String HostName = "http://192.168.0.115:8080";//本地服务器
 

    public static OldApplication instance = null;
    private static DisplayImageOptions options;

    /**
     * 屏幕是否点亮
     */
    public static boolean isScreenOn = true;

    /**
     * 不显示显示lock界面【splash界面和login界面】
     */
    public static boolean notShowLock = false;
    /**
     * 显示亮屏的次数
     */
    public static int ligthCount = 0;

    /**
     * 程序活动列表
     */
    public List<Activity> activityList = new ArrayList<Activity>();
    public static boolean indicatorClickable = true;

    public static OldApplication getInstance() {
        if (instance == null) {
            instance = new OldApplication();
        }
        return instance;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onCreate() {
        super.onCreate();

        String sdpath = "";
        LogUtils.e( "lwjtest: Environment.MEDIA_MOUNTED" + Environment.getExternalStorageState());
        if( Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED ) ){
            pathSDCard = Environment.getExternalStorageDirectory().getAbsolutePath();
            CachePath = pathSDCard + "/Android/data/com.example.old/cache";
            LogUtils.e( "lwjtest: mounted" + pathSDCard );
            //尝试创建文件夹,如果失败则使用系统缓存
            try{
                DATA_CACHE_PATH = CachePath + "/datacache";
                //创建目录
                File file = new File( DATA_CACHE_PATH );
                if ( !file.exists() ){
                    Boolean status = file.mkdirs();
                }
            } catch ( RuntimeException e ){
                pathSDCard = getCacheDir().getPath();// getContentResolver().  Environment.getDownloadCacheDirectory().getAbsolutePath();
                CachePath = pathSDCard;
                LogUtils.e( "lwjtest: not mounted" + pathSDCard );
            }
        }else {
            pathSDCard = getCacheDir().getPath();// getContentResolver().  Environment.getDownloadCacheDirectory().getAbsolutePath();
            CachePath = pathSDCard;
            LogUtils.e( "lwjtest: not mounted" + pathSDCard );
        }

        CACHE_PATH = pathSDCard + "/old/";
        //创建文件夹
        File file = new File( CachePath );
        if ( !file.exists() ){
            file.mkdirs();
        }
        //初始化
        FilePath = sdpath + "/Android/data/com.example.old/files/fs";
        //创建目录
        file = new File( FilePath );
        if ( !file.exists() ){
            file.mkdirs();
        }
        /**
         * 异常路径
         */
        EXCEPTION_PATH = CachePath + "/exception/";
        //创建目录
        file = new File( EXCEPTION_PATH );
        if ( !file.exists() ){
            file.mkdirs();
        }
        /**
         * 数据库路径
         */
        DB_PATH = CachePath + "/db/";
        //创建目录
        file = new File( DB_PATH );
        if ( !file.exists() ){
            file.mkdirs();
        }
        /**
         * apk路径
         */
        APK_PATH = CachePath + "/apk/";
        //创建目录
        file = new File( APK_PATH );
        if ( !file.exists() ){
            file.mkdirs();
        }
        /**
         * 缓存资料路径
         */
        DATA_CACHE_PATH = CachePath + "/datacache";
        //创建目录
        file = new File( DATA_CACHE_PATH );
        if ( !file.exists() ){
            file.mkdirs();
        }
        /**
         * 图片缓存路径
         */
        IMG_CACHE_PATH = CachePath + "/imgcache";
        //创建目录
        file = new File( IMG_CACHE_PATH );
        if ( !file.exists() ){
            file.mkdirs();
        }
        /**
         * 图片备份缓存路径
         */
        IMG_RCACHE_PATH = FilePath + "/imgcache";
        //创建目录
        file = new File( IMG_RCACHE_PATH );
        if ( !file.exists() ){
            file.mkdirs();
        }
        /**
         * 验证码缓存路径
         */
        VALIDATE_IMG_PATH = CachePath + "/validateimg/";
        //创建目录
        file = new File( VALIDATE_IMG_PATH );
        if ( !file.exists() ){
            file.mkdirs();
        }
        //ExceptionHandle.getInstance().init(this);
        // 百度推送初始化
        // Push: 以apikey的方式登录，一般放在主Activity的onCreate中。
        // 这里把apikey存放于manifest文件中，只是一种存放方式，
        // 您可以用自定义常量等其它方式实现，来替换参数中的Utils.getMetaValue(PushDemoActivity.this,
        // "api_key")
        // 请将AndroidManifest.xml 128 api_key 字段值修改为自己的 api_key 方可使用 ！！
        try {
            PushManager.startWork(getApplicationContext(),
                    PushConstants.LOGIN_TYPE_API_KEY,
                    StringUtils.getMetaValue(this, "api_key"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 文字样式初始化
//		CalligraphyConfig.initDefault("fonts/FZLTXHK.TTF", R.attr.fontPath);

        // 异常截取
        // ExceptionHandle error = ExceptionHandle.getInstance();
        // error.init(getApplicationContext());

        // 得到屏幕的宽度和高度
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenW = dm.widthPixels;
        screenH = dm.heightPixels;

        //不打印log
//		 LogUtils.allowD = false;

/**先注释掉看看 -- lwj 2016-07-28
        try {
            CachePath = getApplicationContext().getExternalCacheDir().getPath();
            FilePath = getApplicationContext().getExternalFilesDir("/fs/").getPath();
        } catch (Exception e) {
            e.printStackTrace();//没有内存卡时会崩溃
        }
 **/

        // 图片下载处理
        initImageLoader(getApplicationContext());

//        //百度移动统计
//        // 测试时，可以使用1秒钟session过期，这样不断的间隔1S启动退出会产生大量日志。
//        StatService.setSessionTimeOut(1);
//         /*
//         * 设置启动时日志发送延时的秒数<br/> 单位为秒，大小为0s到30s之间<br/> 注：请在StatService.setSendLogStrategy之前调用，否则设置不起作用
//         * 如果设置的是发送策略是启动时发送，那么这个参数就会在发送前检查您设置的这个参数，表示延迟多少S发送。<br/> 这个参数的设置暂时只支持代码加入，
//         * 在您的首个启动的Activity中的onCreate函数中使用就可以。<br/>
//         */
//        StatService.setLogSenderDelayed(0);
//        // 调试百度移动统计SDK的Log开关，可以在Eclipse中看到sdk打印的日志，发布时去除调用，或者设置为false
//        StatService.setDebugOn(false);
//        StatService.setAppChannel(this, "System", true);
////		StatService.onEvent(this, "app_start", "pass",1);
//        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
//        //注意该方法要再setContentView方法之前实现
//        /**
//         * 1 baidumapsdk﹕ Authentication Error errorcode: 203 uid: -1 appid -1 msg: APP类型错误
//         *    这种情况说明百度地图 APP类型没对 请到http://lbsyun.baidu.com/apiconsole/key  设置
//         * 2 230错误（APP scode校验失败)
//         *    指纹没有配置正确
//         */
//
//        try {
//            SDKInitializer.initialize(getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        /**
//         *百度定位
//         */
//        BaiduLocation.getInstance().start(getApplicationContext());
    }

    /**
     * @param activity 设定文件
     * @return void 返回类型
     * @throws
     * @Title: addActivity
     * @Description: TODO(添加栈)
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public Activity getLast() {
        if (activityList != null && activityList.size() > 0) {
            return activityList.get(activityList.size() - 1);
        }
        return null;
    }

    /**
     * @return void 返回类型
     * @throws
     * @Title: exit
     * @Description: TODO(退出程序)
     */
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
//        BaiduLocation.getInstance().stop();
    }

    /**
     * @param @param clazz 设定文件
     * @return void 返回类型
     * @throws
     * @Title: finishOthers
     * @Description: TODO(结束除自己之外的其它类)
     */
    public void finishOthers(Class clazz) {
        for (int i = 0; i < activityList.size(); i++) {
            if (!clazz.getName().equals(activityList.get(i).getClass().getName())) {
                activityList.get(i).finish();
                activityList.remove(i);
            }

        }
    }

    /**
     * @param @param clazz 设定文件
     * @return void 返回类型
     * @throws
     * @Title: toFinish
     * @Description: TODO(关闭某个类)
     */
    public void toFinish(Class clazz) {
        for (int i = 0; i < activityList.size(); i++) {
            if (clazz.getName().equals(activityList.get(i).getClass().getName())) {
                activityList.get(i).finish();
                activityList.remove(i);
            }
        }
    }

    /**
     * @param @param  clazz
     * @param @return 设定文件
     * @return boolean 返回类型
     * @throws
     * @Title: hasClazz
     * @Description: TODO(判断队列中是否有某个类)
     */
    public boolean hasClazz(Class clazz) {
        boolean flag = false;
        for (int i = 0; i < activityList.size(); i++) {
            if (clazz.getName().equals(activityList.get(i).getClass().getName())) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * @param @param  displayer
     * @param @return 设定文件
     * @return DisplayImageOptions 返回类型
     * @throws
     * @Title: getImageOptions
     * @Description: TODO(默认图片加载选项)
     */
    public DisplayImageOptions getImageOptions(BitmapDisplayer displayer) {
        if (displayer == null) {
            if (options == null) {
                options = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.ic_launcher).showImageForEmptyUri(R.mipmap.ic_launcher).showImageOnFail(R.mipmap.ic_launcher)
                        .resetViewBeforeLoading(true).cacheInMemory(true).cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true).build();
            }
        } else {
            return new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.ic_launcher).showImageForEmptyUri(R.mipmap.ic_launcher).showImageOnFail(R.mipmap.ic_launcher)
                    .resetViewBeforeLoading(true).cacheInMemory(true).cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                    .displayer(displayer).build();
        }
        return options;
    }

    private DisplayImageOptions billsOptions = null;

    public DisplayImageOptions getBillImageOptions(BitmapDisplayer displayer) {
        if (billsOptions == null) {
            billsOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.ic_launcher).showImageForEmptyUri(R.mipmap.ic_launcher).showImageOnFail(R.mipmap.ic_launcher)
                    .resetViewBeforeLoading(true).cacheInMemory(true).cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                    .displayer(displayer).build();
        }
        return billsOptions;
    }

    /**
     * @param @param context 设定文件
     * @return void 返回类型
     * @throws
     * @Title: initImageLoader
     * @Description: TODO(初始化图片加载)
     */
    public void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPoolSize(5).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .diskCache(new UnlimitedDiscCache(new File(IMG_CACHE_PATH), new File(IMG_RCACHE_PATH), new Md5FileNameGenerator())).diskCacheSize(50 * 1024 * 1024)
                        //不要打印加载图片的日志
                .tasksProcessingOrder(QueueProcessingType.LIFO).defaultDisplayImageOptions(DisplayImageOptions.createSimple()).build();
        //.tasksProcessingOrder(QueueProcessingType.LIFO).defaultDisplayImageOptions(DisplayImageOptions.createSimple()).writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
    }

    public boolean isMainFront() {
        if (MainActivity.class.getName().equals(getLast().getClass().getName())) {
            return true;
        }
        return false;
    }
}
