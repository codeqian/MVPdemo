package com.shouchuanghui.customerviewmodule.activity;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.shouchuanghui.customerviewmodule.R;

/**
 * Create by huscarter@163.com. on 9/17/2015.
 * <p>
 * 大图预览，提供选择功能，如果不需要选择功能，请使用{@link com.jushi.gallery.activity.BeautyImageGalleryActivity}<br/>
 * <p>
 * 接收参数：<br/>
 * 大图Uri集合:(Uri) bundle.getParcelable(PATH_KEY),
 * <p>
 */
public class BeautyImageSelectActivity extends AppCompatActivity {
    private String TAG = BeautyImageSelectActivity.class.getSimpleName();

    public static final int BEAUTY_IMAGE_SELECT_REQUEST = 110;

    /**
     * 将图片的URI集合放到bundle中的key值,用法：</br>
     * bundle.putSerializable(BeautyImageGalleryActivity.LIST_PATH_KEY,List<uri>)
     */
    public static final String PATH_KEY = "path_key";
    public static final String CHECK_COUNT = "check_count";
    public static final String POSITION = "position";
    public static final String CHECKED = "has_checked";
    public static final String MAX_COUNT = "max_count";

    private Toolbar toolbar;
    private CheckBox cb;
    private View rl_bottom;
    private TextView tv_tips;
    private ImageView zdv;
    private VideoView vv;
    /**
     * 大图展示的uri
     */
    private Uri uri = null;
    /**
     * 已经选择的数量
     */
    private int has_count = 0;
    /**
     * 总共需要选择的数量
     */
    private int max_count = 0;
    /**
     * 在相册是否已选择
     */
    private boolean has_checked = false;

    /**
     * 适配器传递出来的位置
     */
    private int position = 0;

    private Bundle bundle;

    private int type;//判断图片与视频


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏

        setContentView(R.layout.activity_beauty_image_select);

        initView();
        getData();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        cb = (CheckBox) findViewById(R.id.cb);
        rl_bottom = findViewById(R.id.rl_bottom);
        tv_tips = (TextView) findViewById(R.id.tv_tips);
        zdv = (ImageView) findViewById(R.id.zdv);
        vv = (VideoView) findViewById(R.id.vv);
    }

    private void getData() {
        bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        uri = (Uri) bundle.getParcelable(PATH_KEY);
        has_count = bundle.getInt(CHECK_COUNT, 0);

        has_checked = bundle.getBoolean(CHECKED, false);
        max_count = bundle.getInt(MAX_COUNT, 4);
        position = bundle.getInt(POSITION, -1);
        type = bundle.getInt("type", ImageGalleryActivity.TYPE_IMAGE);
        tv_tips.setText(has_count + "/" + max_count);

        cb.setEnabled((max_count > has_count));

        cb.setChecked(has_checked);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

//        JLog.i(TAG,"width:"+size.x+",height:"+size.y);

//        Glide.with(this)
//                .load(uri)
//                .into(zdv);
        if (type == ImageGalleryActivity.TYPE_IMAGE) {

            Glide.with(this)
                    .load(uri)
                    .into(zdv);
            vv.setVisibility(View.GONE);
            rl_bottom.setVisibility(View.VISIBLE);
        } else if (type == ImageGalleryActivity.TYPE_VIDEO) {
            vv.setVisibility(View.VISIBLE);
            rl_bottom.setVisibility(View.GONE);
            vv.setVideoURI(uri);
            vv.setMediaController(new MediaController(this));
            vv.start();
        }

        setListener();
    }


    private void setListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!has_checked) {
                        tv_tips.setText((has_count + 1) + "/" + max_count);
                    } else {
                        tv_tips.setText(has_count + "/" + max_count);
                    }
                } else {
                    if (!has_checked) {
                        tv_tips.setText(has_count + "/" + max_count);
                    } else {
                        tv_tips.setText((has_count - 1) + "/" + max_count);
                    }
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (cb.isChecked() != has_checked) {
            Intent intent = new Intent();
            bundle.putBoolean(CHECKED, cb.isChecked());
            bundle.putInt(POSITION, position);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
        }
        super.onBackPressed();
    }

}
