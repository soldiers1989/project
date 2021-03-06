package com.ryx.payment.ruishua.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ryx.payment.ruishua.R;
import com.ryx.payment.ruishua.bean.AdBeanMap;
import com.ryx.quickadapter.inter.NoDoubleClickListener;
import com.ryx.quickadapter.inter.OnListItemClickListener;
import com.ryx.quickadapter.recyclerView.HelperAdapter;
import com.ryx.quickadapter.recyclerView.HelperViewHolder;
import com.ryx.swiper.utils.MapUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by XCC on 2017/3/20.
 * 首页广告位适配器
 */

public class MainAdGridAdapter extends HelperAdapter<AdBeanMap> {
    private OnListItemClickListener mOnItemClickListener = null;
    /**
     * @param data     数据源
     * @param context  上下文
     * @param layoutId 布局Id
     */
    public MainAdGridAdapter(List<AdBeanMap> data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }
    public void setOnItemClickListener(OnListItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    protected void HelperBindData(final HelperViewHolder viewHolder, final int position, final AdBeanMap item) {
        if (null != item&&item.getMap()!=null) {
           Map<String,String> aditemMap= item.getMap();
             View   view = viewHolder.itemView;
            String title=    MapUtil.get(aditemMap,"title","");
            viewHolder.setText(R.id.home_ad_item_title, title);
//            int id = view.getResources().getIdentifier(item.getRes(), "drawable", view.getContext().getPackageName());
//            GlideUtils.getInstance().load(view.getContext(), id, (ImageView) viewHolder.getView(R.id.ivIcon));
            String imgurl=    MapUtil.get(aditemMap,"imgurl","");
//            int defaultColor=GlideUtils.getInstance().getRandomDefaultColor();
            Glide.with(view.getContext())
                    .load(imgurl)
                    .placeholder(R.drawable.home_ad_defaultimg)//显示加载时的图片
                    .error(R.drawable.home_ad_defaultimg)//加载失败默认显示的图片
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//磁盘缓存
                    .dontAnimate()//无动画
                    .into((ImageView)viewHolder.getView(R.id.home_ad_item_imgview));
//            GlideUtils.getInstance().load(view.getContext(),imgurl,(ImageView)viewHolder.getView(R.id.home_ad_item_imgview));
        }
        viewHolder.getItemView().setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(viewHolder.getItemView(), position, item);
            }
        });
    }

}
