package com.shouchuanghui.customerviewmodule.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.shouchuanghui.commonmodule.util.MediaUtil;
import com.shouchuanghui.customerviewmodule.R;
import com.shouchuanghui.customerviewmodule.adapter.ImageAdapter;
import com.shouchuanghui.httpmodule.bean.common.ImageData;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Create by huscarter@163 on 9/17/2015.
 * <p>
 * To select mult pic from gallery,it will return a bundle with the ArrayList<ImageData>.<br/>
 * You can set the max_select_pic with the .
 * <p>
 * 接收传值:<br/>
 * (int)  bundle:bundle.put("count",4)
 * <p>
 * 返回传值:<br/>
 * bundle.putStringArrayList("datas",ArrayList<ImageData>)
 */
public class ImageGalleryActivity extends AppCompatActivity {
    public static final int IMAGE_SELECT_REQUEST = 11010;
    public static final int IMAGE_SELECT_RESULT = 11011;

    public static final int TYPE_IMAGE = 0;//照片
    public static final int TYPE_VIDEO = 1;//视频

    protected Toolbar toolbar;
    private GridLayoutManager layout_manager;
    private RecyclerView rv;
    private Activity activity;
    private ImageAdapter adapter;
    private List<ImageData> list = new ArrayList<>();
    private int type = TYPE_IMAGE;

    /**
     * 如果有特殊字符不提取
     */
    private Pattern pattern = Pattern.compile("^(.*[\\@\\!\\#\\$\\%\\^\\&\\*\\~\\'\\`]+.*|.*\\.gif)$");
    private static final String TAG = ImageGalleryActivity.class.getSimpleName();

    /**
     * The number of columns in the grid
     */
    private static final int NUM = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏

        setContentView(R.layout.activity_image_gallery);

        activity = this;
        initView();

        //toolbar.setTitle(getString(R.string.select_image));
        toolbar.setNavigationIcon(R.mipmap.title_arrow);
        toolbar.inflateMenu(R.menu.menu_complete);
        toolbar.setOnMenuItemClickListener(new ToolbarOnMenuItemClickListener());

        layout_manager = new GridLayoutManager(activity, NUM);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(layout_manager);
        //rv.addItemDecoration(new DividerItemDecoration());
        adapter = new ImageAdapter(activity, list, true);
        adapter.setLayout_params(getImageItemLayoutParam());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getInt("type");
            adapter.setCount(bundle.getInt("count"));
            adapter.setType(type);
        }
        rv.setAdapter(adapter);


        if (type == TYPE_IMAGE) {
            getImageDataFromSdCard();
        } else if (type == TYPE_VIDEO){
            getVideoDataFromSdCard();
        }

        setListener();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rv = (RecyclerView) findViewById(R.id.rv);
    }

    private RelativeLayout.LayoutParams getImageItemLayoutParam() {
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int w_width = size.x;
        //int w_height = size.y;
        int margin_size = 1;
        int img_width = (w_width - (margin_size * 6)) / NUM;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(img_width, img_width - 20);
        lp.setMargins(margin_size, margin_size, margin_size, margin_size);
        return lp;
    }

    private void setListener() {
        //
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageGalleryActivity.this.finish();
            }
        });
    }

    private class ToolbarOnMenuItemClickListener implements Toolbar.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.i_ok) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("datas", (Serializable) adapter.getSelects());
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
            return false;
        }
    }

    /**
     * to get image data from content
     */
    private void getImageDataFromSdCard() {
        try {
            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
            final String order = MediaStore.Images.Media._ID;

            CursorLoader loader = new CursorLoader(activity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, order);
            Cursor cursor = loader.loadInBackground();
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ImageData data = new ImageData();
                    int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    String path = cursor.getString(dataColumnIndex);
                    //@66`_1T]11U[]7G7{[3%`QI==@66`_1T]11U[]7G7{[3�I.png
//                    JLog.i(TAG,"img path:"+path);
                    if (!pattern.matcher(path).matches()) {
                        data.setPath(path);
                        list.add(data);
                    }
                }
                // show newest photo at beginning of the list
                Collections.reverse(list);
                adapter.notifyDataSetChanged();
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getVideoDataFromSdCard(){
        try {
            final String[] columns = { MediaStore.Video.Thumbnails._ID
                    , MediaStore.Video.Thumbnails.DATA
                    ,MediaStore.Video.Media.DURATION
                    ,MediaStore.Video.Media.SIZE
                    ,MediaStore.Video.Media.DISPLAY_NAME
                    ,MediaStore.Video.Media.DATE_MODIFIED};
            final String order = MediaStore.Video.Media._ID;

            CursorLoader loader = new CursorLoader(activity, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null, null, order);
            Cursor cursor = loader.loadInBackground();
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    // 获取视频的路径
                    int videoId = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                    int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                    long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE))/1024; //单位kb
                    if(size<0){
                        //某些设备获取size<0，直接计算
                        Log.e("dml","this video size < 0 " + path);
                        size = new File(path).length()/1024;
                    }
                    String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                    Log.d(TAG, "videoId = " + videoId + "\npath = " + path
                            +"\nduration = " + duration+"\nsize = "+size+"\ndisplayName = " + displayName);

                    ImageData data = new ImageData();
//                    int dataColumnIndex = cursor.getColumnIndex(MediaStore.Video.Media.DATA);
//                    String path = cursor.getString(dataColumnIndex);
//                    Log.i(TAG,"img path:"+path);
                    if (!pattern.matcher(path).matches()) {
                        //获取第一帧图片
                        String imagePath = MediaUtil.getImageForVideo(path);
                        data.setPath(imagePath);
                        data.setVideoPath(path);
                        list.add(data);
                    }
                }
                // show newest photo at beginning of the list
                Collections.reverse(list);
                adapter.notifyDataSetChanged();
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case BeautyImageSelectActivity.BEAUTY_IMAGE_SELECT_REQUEST:
                if (data == null) {
                    return;
                }
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        int position = bundle.getInt(BeautyImageSelectActivity.POSITION, -1);
                        boolean b = bundle.getBoolean(BeautyImageSelectActivity.CHECKED, false);
                        if (position != -1) {
                            adapter.setSelect(position, b);
                            adapter.notifyItemChanged(position);
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
}
