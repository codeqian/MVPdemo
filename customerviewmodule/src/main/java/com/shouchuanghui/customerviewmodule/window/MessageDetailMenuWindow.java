//package com.shouchuanghui.customerviewmodule.window;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.PopupWindow;
//
//import com.shouchuanghui.commonmodule.util.DensityUtil;
//import com.shouchuanghui.commonmodule.util.WindowUtil;
//import com.shouchuanghui.customerviewmodule.R;
//
//import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
//
//public class MessageDetailMenuWindow extends PopupWindow {
//    private MessageDetailMenuWindowListener listener;
//
//    public MessageDetailMenuWindow(final Context context, MessageDetailMenuWindowListener messageDetailMenuWindowListener, String myInfo) {
//        this.listener = messageDetailMenuWindowListener;
//        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.window_message_detail_menu, null, false);
//        setContentView(binding.getRoot());
//        setWidth(MATCH_PARENT);
//        binding.tvCancel.setOnClickListener(v -> dismiss());
//        setOnDismissListener(() -> WindowUtil.setBackgroundAlpha((Activity) context, 1f));
//        setListener();
//        setHeight(DensityUtil.dpToPx(context, 152f));
//        if ("1".equals(myInfo)) {
//            binding.tvDelete.setVisibility(View.VISIBLE);
//            binding.vDelete.setVisibility(View.VISIBLE);
//
//            binding.tvShield.setVisibility(View.GONE);
//            binding.vShield.setVisibility(View.GONE);
//        } else {
//            binding.tvDelete.setVisibility(View.GONE);
//            binding.vDelete.setVisibility(View.GONE);
//
//            binding.tvShield.setVisibility(View.VISIBLE);
//            binding.vShield.setVisibility(View.VISIBLE);
//        }
//    }
//
//    private void setListener() {
//        binding.tvDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //删除帖子
//                listener.onBbsDelClick();
//            }
//        });
//        binding.tvShield.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //拉黑
//                listener.onBbsBlackClick();
//            }
//        });
//        binding.tvReport.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onBbsReportClick();
//            }
//        });
//    }
//
//    @Override
//    public void showAtLocation(View parent, int gravity, int x, int y) {
//        // 设置PopupWindow是否能响应外部点击事件
//        setOutsideTouchable(true);
//        setTouchable(true);
//        super.showAtLocation(parent, gravity, x, y);
//        WindowUtil.setBackgroundAlpha((Activity) parent.getContext(), 0.5f);
//    }
//
//    public interface MessageDetailMenuWindowListener {
//        void onBbsBlackClick();
//        void onBbsReportClick();
//        void onBbsDelClick();
//    }
//
//}
