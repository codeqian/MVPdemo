package com.shouchuanghui.customerviewmodule.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shouchuanghui.commonmodule.base.BaseActivity;
import com.shouchuanghui.commonmodule.bean.BaseData;
import com.shouchuanghui.commonmodule.bean.BaseListData;
import com.shouchuanghui.commonmodule.config.ActivityNameConfig;
import com.shouchuanghui.commonmodule.config.IntentConfig;
import com.shouchuanghui.commonmodule.config.PreferenceConfig;
import com.shouchuanghui.commonmodule.util.DensityUtil;
import com.shouchuanghui.commonmodule.util.PermissionCompat;
import com.shouchuanghui.commonmodule.util.PreferenceUtil;
import com.shouchuanghui.commonmodule.util.ToastUtil;
import com.shouchuanghui.commonmodule.util.WindowUtil;
import com.shouchuanghui.commonmodule.util.glide.GlideUtil;
import com.shouchuanghui.commonmodule.view.GlideImageView;
import com.shouchuanghui.customerviewmodule.BaseQuickAdapter;
import com.shouchuanghui.customerviewmodule.CustomerRecyclerView;
import com.shouchuanghui.customerviewmodule.OnDataChangeListener;
import com.shouchuanghui.customerviewmodule.R;
import com.shouchuanghui.customerviewmodule.adapter.CommentListContentAdapter;
import com.shouchuanghui.customerviewmodule.adapter.FlipOverAdapter;
//import com.shouchuanghui.customerviewmodule.window.MessageDetailMenuWindow;
import com.shouchuanghui.httpmodule.bean.bbs.MessageDetail;
import com.shouchuanghui.httpmodule.bean.common.MessageComment;
import com.shouchuanghui.httpmodule.callback.MessageCB;
import com.shouchuanghui.httpmodule.service.MessageRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 帖子详情
 */
public class ArticleDetailActivity extends BaseActivity implements MessageCB,FlipOverAdapter.OnItemClickListener {
    private String TAG = ArticleDetailActivity.class.getSimpleName();
    private CommentListContentAdapter adapter;
    private FlipOverAdapter flipOverAdapter;
//    private MessageDetailMenuWindow messageDetailMenuWindow;

    private CustomerRecyclerView crv,crvPage;
    private TextView tvNumComment,tvNumZan,tvGiveinfo,pageTitle,title_t,
            comment_sum,read_sum,cate_name,tvName,createTime,community,content,tvSort
            ,commitBtn;
    private CheckBox cbHot,cbLookBuilder;
    private LinearLayout llContent,llCommentTop;
    private ImageView backBtn,thumbIcon,ivAvater,shareBtn,menuBtn;
    private RelativeLayout commentBackBtn,zanBtn;

    public String source = "1";
    public String messageId = "";
    private boolean isGive;                 //是否点过赞
    private int zanNumOriginal;             //点赞实际个数
    private String user_name;
    private int position;                   //位置
    private int currentPageIndex = -1;           //pageView的currentPage Index;
    private int page = 1;           //评论翻页
    private List<String> flipList = new ArrayList<>();
    public List<Uri> imageUrls = new ArrayList<>();
    public String firstContent;                         //用于分享的描述
    private boolean isHot=false;
    private boolean isSortDesc=false;
    private boolean onlyLookBuilder=false;
    private MessageDetail detailBean;

    private MessageRequest messageRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            source = bundle.getString(IntentConfig.SOURCE);
            messageId = bundle.getString(IntentConfig.ID);
            position = bundle.getInt(IntentConfig.POSITION);
            currentPageIndex = bundle.getInt(IntentConfig.PAGEVIEW_CURRENT_INDEX, -1);
        }

        setTitle("帖子详情");

        crv=findViewById(R.id.crv);
        crvPage=findViewById(R.id.crvPage);
        tvNumComment=findViewById(R.id.tvNumComment);
        tvNumZan=findViewById(R.id.tvNumZan);
        tvGiveinfo=findViewById(R.id.tvGiveinfo);
        pageTitle=findViewById(R.id.pageTitle);
        llContent=findViewById(R.id.llContent);
        backBtn=findViewById(R.id.backBtn);
        thumbIcon=findViewById(R.id.thumbIcon);
        title_t=findViewById(R.id.title_t);
        read_sum=findViewById(R.id.read_sum);
        comment_sum=findViewById(R.id.comment_sum);
        cate_name=findViewById(R.id.cate_name);
        ivAvater=findViewById(R.id.ivAvater);
        tvName=findViewById(R.id.tvName);
        createTime=findViewById(R.id.createTime);
        community=findViewById(R.id.community);
        tvSort=findViewById(R.id.tvSort);
        content=findViewById(R.id.content);
        cbHot=findViewById(R.id.cbHot);
        cbLookBuilder=findViewById(R.id.cbLookBuilder);
        commitBtn=findViewById(R.id.commitBtn);
        zanBtn=findViewById(R.id.zanBtn);
        commentBackBtn=findViewById(R.id.commentBackBtn);
        shareBtn=findViewById(R.id.shareBtn);
        llCommentTop=findViewById(R.id.llCommentTop);
        menuBtn=findViewById(R.id.menuBtn);

        flipOverAdapter = new FlipOverAdapter(flipList, this);
        crvPage.setAdapter(flipOverAdapter);
        flipOverAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                page=position + 1;
                messageRequest.getCommentList(source,messageId,page,isHot,isSortDesc,onlyLookBuilder,false);
            }
        });

        adapter = new CommentListContentAdapter(new ArrayList<MessageComment>(), messageId);
        crv.setAdapter(adapter);

        messageRequest=new MessageRequest(subscription,this);
        messageRequest.getMessageDetail(messageId);
        messageRequest.getCommentList(source,messageId,page,isHot,isSortDesc,onlyLookBuilder,true);

        setListener();
    }

    private void setTitle(String _msg){
        TextView _pageTitle=findViewById(R.id.pageTitle);
        _pageTitle.setText(_msg);
    }

    @Override
    public void setDetail(BaseData<MessageDetail> data){
        detailBean=data.getData();
//        System.out.println("setDetail："+detailBean.getGive_state()+"_"+detailBean.getComment_pages());
        int maxPage = Integer.valueOf(detailBean.getComment_pages());
        flipList.clear();
        for (int i = 1; i<= maxPage; i++) {
            flipList.add("第"+i+"页");
        }

        title_t.setText(detailBean.getTitle());
        comment_sum.setText(detailBean.getComment_sum());
        read_sum.setText(detailBean.getRead_sum());
        cate_name.setText("#"+detailBean.getCate_name()+"#");
        GlideUtil.load(ivAvater,detailBean.getAvatar());
        tvName.setText(detailBean.getNickname());
        createTime.setText(detailBean.getCreatetime());
        community.setText(detailBean.getCommunity());

        isGive = "1".equals(detailBean.getGive_state());
        if(isGive){
            thumbIcon.setImageDrawable(getResources().getDrawable(R.mipmap.thumbed_b));
        }
        if (!TextUtils.isEmpty(detailBean.getComment_sum())) {
            try {
                int commentSum = Integer.valueOf(detailBean.getComment_sum());
                if (commentSum > 99)
                    commentSum = 99;
//                if(commentSum==0){
//                    tvNumComment.setVisibility(View.GONE);
//                }else{
                    tvNumComment.setVisibility(View.VISIBLE);
                    tvNumComment.setText(Integer.valueOf(commentSum)+"");
//                }
            }catch (Exception e) {
            }
        }
        if (!TextUtils.isEmpty(detailBean.getGive_sum())) {
            try {
                int giveSum = Integer.valueOf(detailBean.getGive_sum());
                zanNumOriginal = giveSum;
                if (giveSum > 99)
                    giveSum = 99;
//                if(giveSum==0){
//                    tvNumZan.setVisibility(View.GONE);
//                }else{
                    tvNumZan.setVisibility(View.VISIBLE);
                    tvNumZan.setText(Integer.valueOf(giveSum)+"");
//                }
            } catch (Exception e) {
            }
        }
        if (!TextUtils.isEmpty(detailBean.getGive_info())) {
            System.out.println(TAG+":"+detailBean.getGive_info());
            String[] names = detailBean.getGive_info().split("、");
            if (names.length < zanNumOriginal)
                tvGiveinfo.setText(detailBean.getGive_info() + "等" + zanNumOriginal + "人点赞");
            else
                tvGiveinfo.setText(detailBean.getGive_info());
        }
        initView(data);
    }

    @Override
    public void setCommit(boolean isRef, BaseListData<MessageComment> data){
        List<MessageComment> list=data.getData();
        if (isRef) {
            crv.setRefreshing(false);
        }
        adapter.notifyDataChangedAfterLoadMore(isRef, list);
    }

    @Override
    public void endFresh(){
        crv.setRefreshing(false);
        adapter.notifyDataChangedAfterLoadMore(true);
    }

    @Override
    public void getZan(String types,int position){
        System.out.println(TAG+" getZan");
        if ("2".equals(types)) {//type=2论坛
            //刷新文章攒数量
            String giveInfo = "";
            if (isGive) {         //取消点赞
                zanNumOriginal--;
                if (zanNumOriginal == 0) {
                    giveInfo = detailBean.getGive_info().replaceFirst(user_name, "");
                } else {
                    giveInfo = detailBean.getGive_info().replaceFirst(user_name+"、", "");
                }
                isGive = false;
                detailBean.setGive_state("0");
            } else {                //点赞
                zanNumOriginal++;
                if (zanNumOriginal == 1)
                    giveInfo = user_name;
                else
                    giveInfo = user_name+"、"+detailBean.getGive_info();
                isGive = true;
                detailBean.setGive_state("1");
            }
            detailBean.setGive_info(giveInfo);
            if(isGive){
                thumbIcon.setImageDrawable(getResources().getDrawable(R.mipmap.thumbed_b));
            }else{
                thumbIcon.setImageDrawable(getResources().getDrawable(R.mipmap.thumb_b));
            }
            //刷新点赞数
            int zanNum=0;
            if (zanNumOriginal > 99) {
                zanNum=99;
            }
            zanNum=zanNumOriginal;
            tvNumZan.setText(Integer.valueOf(zanNum)+"");
            refreshGiveInfo(zanNumOriginal, giveInfo);
        } else {
            //刷新评论
            onCommentZanRequestFinish(position);
        }
    }

    @Override
    public void onPageClick(int page) {
        this.page=page;
        messageRequest.getCommentList(source,messageId,page,isHot,isSortDesc,onlyLookBuilder,true);
    }

    public void initView(BaseData<MessageDetail> data) {
        setTitle(page + "/" + data.getData().getComment_pages());
        flipOverAdapter.notifyDataSetChanged();

        if (data.getData().getContents() != null && data.getData().getContents().size() > 0) {
            llContent.removeAllViews();
            imageUrls.clear();
            firstContent = "";
            for (MessageDetail.ContentsBean contentsBean : data.getData().getContents()) {
//                Ir.from(this), R.layout.item_message_detail, null, false);
                content.setText(contentsBean.getNote());
                if (!TextUtils.isEmpty(contentsBean.getNote()) && TextUtils.isEmpty(firstContent)) {
                    firstContent = contentsBean.getNote();
                }
                if (contentsBean.getUpload_files() != null && contentsBean.getUpload_files().size() > 0) {
                    for (MessageDetail.ContentsBean.UploadFilesBean img : contentsBean.getUpload_files()) {
//                        if ("0".equals(img.getTypes())) {//图片
                            GlideImageView imageView = new GlideImageView(this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.topMargin = DensityUtil.dpToPx(this, 12);
                            layoutParams.bottomMargin = DensityUtil.dpToPx(this, 12);
                            imageView.setLayoutParams(layoutParams);
                            GlideUtil.load(imageView, img.getImg_url());
                            llContent.addView(imageView);
                            imageUrls.add(Uri.parse(img.getImg_url()));
                            final int index = imageUrls.size() - 1;
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(BeautyImageGalleryActivity.PAGE_INDEX, index);
                                    bundle.putSerializable(BeautyImageGalleryActivity.LIST_PATH_KEY, (Serializable) imageUrls);
                                    doIntent(BeautyImageGalleryActivity.class, bundle);
                                }
                            });
//                        }
                    }
                }
            }
        }
    }

    public void refreshGiveInfo(int zanNum, String giveNames) {
        if (!TextUtils.isEmpty(giveNames)) {
            String[] names = giveNames.split("、");
            if (names.length < zanNum)
                tvGiveinfo.setText(giveNames + "等" + zanNum + "人点赞");
            else
                tvGiveinfo.setText(giveNames);
        }
    }

    public void onCommentZanRequestFinish(int position) {
        MessageComment messageComment = adapter.getData().get(position);
        if ("0".equals(messageComment.getGive_state())) {
            adapter.getData().get(position).setGive_sum(String.valueOf(Integer.valueOf(messageComment.getGive_sum()) + 1));
            adapter.getData().get(position).setGive_state("1");
        } else {
            adapter.getData().get(position).setGive_sum(String.valueOf(Integer.valueOf(messageComment.getGive_sum()) - 1));
            adapter.getData().get(position).setGive_state("0");
        }
        adapter.notifyItemChanged(position + 1);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        backBtn.setOnClickListener(ButtonClick);
        commitBtn.setOnClickListener(ButtonClick);
        tvSort.setOnClickListener(ButtonClick);
        cate_name.setOnClickListener(ButtonClick);
        zanBtn.setOnClickListener(ButtonClick);
        commentBackBtn.setOnClickListener(ButtonClick);
        shareBtn.setOnClickListener(ButtonClick);
        menuBtn.setOnClickListener(ButtonClick);
        crv.setOnDataChangeListener(new OnDataChangeListener() {
            @Override
            public void onRefresh() {
                messageRequest.getMessageDetail(messageId);
                page=1;
                messageRequest.getCommentList(source,messageId,page,isHot,isSortDesc,onlyLookBuilder,true);
            }

            @Override
            public void onLoadMore() {
                page++;
                messageRequest.getCommentList(source,messageId,page,isHot,isSortDesc,onlyLookBuilder,false);
            }
        });
        cbHot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isHot=isChecked;
                if(isChecked){
                    cbHot.setTextColor(getResources().getColor(R.color.red));
                }else{
                    cbHot.setTextColor(getResources().getColor(R.color.text_gray));
                }
                messageRequest.getCommentList(source,messageId,page,isHot,isSortDesc,onlyLookBuilder,true);
            }
        });
        cbLookBuilder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onlyLookBuilder=isChecked;
                messageRequest.getCommentList(source,messageId,page,isHot,isSortDesc,onlyLookBuilder,true);
            }
        });
        pageTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (vm.getFlipList().size() - 1 <= 0) {//无法翻页
//                    return;
//                }
//                if (binding.crvPage.getVisibility() == View.GONE) {
//                    binding.vBlackBackground.setVisibility(View.VISIBLE);
//                    binding.crvPage.setVisibility(View.VISIBLE);
//                    flipOverAdapter.setCurrentPage(vm.page.get());
////                } else {
////                    binding.vBlackBackground.setVisibility(View.GONE);
//                    binding.crvPage.setVisibility(View.GONE);
//                }
            }
        });
    }

    private View.OnClickListener ButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.backBtn) {
                finish();
            } else if (v.getId() == R.id.menuBtn) {//三点菜单
//                if (menuItem.getItemId() == R.id.more) {
//                    showMessageDetailMenuWindow();
//                }
            } else if (v.getId() == R.id.zanBtn) {//点赞
                messageRequest.onZan("2",messageId,0);
            } else if (v.getId() == R.id.commentBackBtn) {//回到评论首部
                scrollToTop();
            } else if (v.getId() == R.id.shareBtn) {//分享
                showShareWindow();
            } else if (v.getId() == R.id.tvSort) {
                isHot=false;
                isSortDesc=!isSortDesc;
                if(isSortDesc){
                    tvSort.setText("倒序");
                }else{
                    tvSort.setText("正序");
                }
                messageRequest.getCommentList(source,messageId,page,isHot,isSortDesc,onlyLookBuilder,true);
            } else if (v.getId() == R.id.commitBtn) {//评论
                //是否登录
                String token = PreferenceUtil.getString(PreferenceConfig.TOKEN, "");
                if (TextUtils.isEmpty(token)) {//未登录
                    doIntentClassName(ActivityNameConfig.LOGIN_ACTIVITY, null);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.BBS_ID, messageId);
//                doIntent(ReplyActivity.class, bundle);
            } else if (v.getId() == R.id.cate_name) {
//                打开分类页
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.CAT_ID, detailBean.getCate_id());
                bundle.putString(IntentConfig.NAME, detailBean.getCate_name());
//                baseCallBck.doIntent(MessageCenterModuleActivity.class, bundle);
            }
        }
    };

    /**
     * 分享
     */
    public void showShareWindow() {
        if (PermissionCompat.getInstance().checkAboutSharePermission(ArticleDetailActivity.this)) {
//            UMWeb web = new UMWeb(vm.detailBean.getShare_url());
//            web.setTitle(vm.detailBean.getTitle());//标题
//            if (vm.imageUrls.size() > 0) {
//                UMImage image = new UMImage(this, vm.imageUrls.get(0).getPath());
//                web.setThumb(image);  //缩略图
//            }
//            if (vm.firstContent.length() > 30) {
//                vm.firstContent = vm.firstContent.substring(0, 30);
//            }
//            web.setDescription(vm.firstContent);//描述
//            new ShareAction(this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
//                    .withMedia(web).setCallback(MessageDetailActivity.this)
//                    .open();
        }
    }

    /**
     * 回到顶部
     */
    public void scrollToTop() {
        crv.getRv().scrollToPosition(1);
        if (adapter.getData().size() > 0) {
            int[] location = new int[2];
            RecyclerView.ViewHolder vh = crv.getRv().findViewHolderForAdapterPosition(1);
            if (vh == null) {
                llCommentTop.getLocationInWindow(location);
                crv.getRv().scrollBy(0, location[1] - DensityUtil.dpToPx(this, 44) - WindowUtil.getStatusBarHeight(this));
            } else {
                vh.itemView.getLocationInWindow(location);
                crv.getRv().scrollBy(0, location[1] - DensityUtil.dpToPx(this, 64) - WindowUtil.getStatusBarHeight(this));
            }

        } else {
            ToastUtil.showToast("暂无评论，快点来发表评论吧");
        }
    }
}
