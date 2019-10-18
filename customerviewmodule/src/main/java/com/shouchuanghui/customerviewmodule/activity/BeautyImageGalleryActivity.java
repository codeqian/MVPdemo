package com.shouchuanghui.customerviewmodule.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.shouchuanghui.customerviewmodule.R;
import com.shouchuanghui.customerviewmodule.adapter.SimplePagerAdapter;
import com.shouchuanghui.customerviewmodule.view.TouchImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create by huscarter@163.com. on 9/17/2015.
 * <p>
 * 大图展示;点击退出大图,长按弹出保存选项
 * <p>
 * 接收参数：<br/>
 * (List<Uri>) bundle.getSerializable(LIST_PATH_KEY);// 大图Uri集合
 * (int)bundle.getInt(Config.PAGE_INDEX);// 首张展示的位置
 * <p>
 */
public class BeautyImageGalleryActivity extends AppCompatActivity {
    private static final String TAG = "BeautyImageGalleryActivity";

    private static final int WIDTH = 720;// 规定图片的宽度
    private static final int HEIGHT = 1280;// 规定图片的高度
    private int w_width = 0;// 屏幕的宽度
    private int w_height = 0;// 屏幕的高度

    /**
     * 检查存储请求,华为的requestCode不能大于128
     */
    public final static int REQUEST_EXTERNAL_STORAGE = 99;
    /**
     * 存储权限
     */
    public static String[] PERMISSION_FILE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    // 用于保存图片，此集合中的bitmap不需要管理，fresco会管理
    private Bitmap[] cache_bitmaps;

    public static final String PAGE_INDEX = "PAGE_INDEX";

    /**
     * 将图片的URI集合放到bundle中的key值,用法：</br>
     * bundle.putSerializable(BeautyImageGalleryActivity.LIST_PATH_KEY,List<uri>)
     */
    public static final String LIST_PATH_KEY = "list_uri_key";
    protected Toolbar toolbar;
    private Activity activity;
    private List<Uri> list_uri = new ArrayList<>();
    /**
     * the view to show the guide message.
     */
    private List<View> list_view = new ArrayList<>();
    /**
     * the container view.
     */
    private ViewPager viewpager;
    private PagerAdapter adapter;
    private int current_item = 0;

    private View ll_option;
    private TextView tv_save;
    private TextView tv_cancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏

        setContentView(R.layout.activity_beauty_image_gallery);
        activity = this;

        initView();
        getData();
        setListener();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new SimplePagerAdapter(list_view, activity);
        viewpager.setAdapter(adapter);
        ll_option = findViewById(R.id.ll_option);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_cancle = (TextView) findViewById(R.id.tv_cancle);
        //iv_btn=(ImageView)findViewById(R.id.iv_btn);

        //getWindowSize();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        try {
            list_uri.addAll((List<Uri>) bundle.getSerializable(LIST_PATH_KEY));
            current_item = bundle.getInt(PAGE_INDEX, 0);
            if (list_uri.size() == 0) {
                tv_save.setVisibility(View.GONE);
                return;
            }
            cache_bitmaps = new Bitmap[list_uri.size()];
            for (int i = 0; i < list_uri.size(); i++) {
                final int position = i;

                final TouchImageView zdv = new TouchImageView(activity);
                zdv.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (ll_option.getVisibility() == View.INVISIBLE) {
                            ll_option.setVisibility(View.VISIBLE);
                        }
                        return true;
                    }
                });
                zdv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ll_option.getVisibility() == View.VISIBLE) {
                            ll_option.setVisibility(View.INVISIBLE);
                        }
                    }
                });

                loadImage(list_uri.get(position), zdv, position);

                list_view.add(zdv);
            }

            for (View view : list_view) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }

            adapter.notifyDataSetChanged();
            viewpager.setCurrentItem(current_item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载图片和缓存图片的bitmap
     *
     * @param uri
     * @param iv
     * @param position
     */
    private void loadImage(final Uri uri, final ImageView iv, final int position) {
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.no_pic)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(iv.getContext()).asBitmap().load(uri).apply(options).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                iv.setImageBitmap(resource);
                cache_bitmaps[position] = resource;
            }
        });
    }

    private void getWindowSize() {
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        w_width = size.x;
        w_height = size.y;
    }

    private void setListener() {
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage(cache_bitmaps[viewpager.getCurrentItem()]);
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_option.setVisibility(View.INVISIBLE);
            }
        });
        viewpager.addOnPageChangeListener(new ViewPagerOnPageChangeListener());
    }

    private void saveImage(Bitmap bitmap) {
        if (bitmap == null) {
            Toast.makeText(activity, "保存失败", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!permissionCheck()) {
            return;
        }
        if (!existSDCard()) {
            Toast.makeText(activity, "没有SD卡", Toast.LENGTH_SHORT).show();
            return;
        }
        String dir_path = Environment.getExternalStorageDirectory() + File.separator + "Download" + File.separator;
        String file_name = (new Date()).getTime() + ".jpg";
        File dir = new File(dir_path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir_path, file_name);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Toast.makeText(activity, "保存地址:" + dir_path + file_name, Toast.LENGTH_LONG).show();
            ll_option.setVisibility(View.INVISIBLE);

            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(getContentResolver(),
                        file.getAbsolutePath(), file_name, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 权限监测
     *
     * @return
     */
    private boolean permissionCheck() {

        for (String str : PERMISSION_FILE) {
            int check = ContextCompat.checkSelfPermission(activity, str);
            boolean is_granted = (check == PackageManager.PERMISSION_GRANTED);
            if (!is_granted) { // 如果没有授予，则请求权限，返回false
                ActivityCompat.requestPermissions(
                        activity,
                        PERMISSION_FILE,
                        REQUEST_EXTERNAL_STORAGE
                );
                return false;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (ll_option.getVisibility() == View.VISIBLE) {
            ll_option.setVisibility(View.INVISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    private boolean existSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }


    private class ViewPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {
        public void onPageSelected(int position) {
            //
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            //
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            //
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        for (Bitmap bitmap : cache_bitmaps) {
//            if (!bitmap.isRecycled()) {
//                bitmap.recycle();
//            }
//        }
    }

}
