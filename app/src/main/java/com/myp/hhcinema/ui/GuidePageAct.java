package com.myp.hhcinema.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.myp.hhcinema.R;
import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.base.BaseActivity;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.config.ConditionEnum;
import com.myp.hhcinema.entity.AdBO;
import com.myp.hhcinema.entity.UserBO;
import com.myp.hhcinema.ui.main.MainActivity;
import com.myp.hhcinema.util.SPUtils;
import com.myp.hhcinema.util.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import rx.Subscriber;

/**
 * Created by wuliang on 2017/6/7.
 * <p>
 * 引导页
 */

public class GuidePageAct extends BaseActivity {


    String uuid;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.adImage)
    ImageView adImage;
    @Bind(R.id.countdown_text)
    TextView countdown_text;

    private boolean firstTimeUse = false;  //判断是否第一次进入app,默认为否
    private GalleryPagerAdapter adapter;

    /***
     * 默认广告停留时间为3秒
     */
    private static int COUNTDOWN_TIME = 3;
    private Timer timer;

    /**
     * 引导页面图片
     */
    private int[] guide_images = {
            R.drawable.guide1,
            R.drawable.guide2,
            R.drawable.guide3
    };

    private SPUtils spUtils;
    private SPUtils spUtilsProdect;

    private boolean login = false;

    @Override
    protected int getLayout() {
        return R.layout.act_guide_page;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spUtils = new SPUtils("firstOpen");
        //清除卖品
        spUtilsProdect = new SPUtils("ProdectItemNum");
        spUtilsProdect.clear();
        MyApplication.goodsId.clear();
        image.setVisibility(View.GONE);
        image.setImageDrawable(getResources().getDrawable(R.drawable.start));
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
        } else {
            uuid = MyApplication.spUtils.getString("uuid", "");
            if (StringUtils.isEmpty(uuid)) {   //未登陆过
                if (spUtils.getBoolean("isFirstOpen")){ //第一次进入，展示引导页
                    initGuideGallery();
                }else {
                    image.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 停止刷新
                            getAdImage();
                        }
                    }, 3000); // 3秒后发送消息，停止刷新
                }
            } else {
                uuidLogin();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 停止刷新
                        getAdImage();
                    }
                }, 3000); // 3秒后发送消息，停止刷新
            }
        }
    }

    private void initGuideGallery() {
        pager.setVisibility(View.VISIBLE);
        image.setVisibility(View.GONE);
        adapter = new GalleryPagerAdapter();
        pager.setAdapter(adapter);
    }

    public class GalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return guide_images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView item = new ImageView(GuidePageAct.this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            item.setLayoutParams(lp);
            item.setScaleType(ImageView.ScaleType.FIT_XY);
            //为了家载大图片出现oom,使用bitmap进行加载
            Glide.with(GuidePageAct.this).load(guide_images[position]).asBitmap().into(item);
            //item.setImageResource(guide_images[position]);
            if (position == guide_images.length-1) {
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        spUtils.put("isFirstOpen", false);
                        gotoActivity(MainActivity.class, true);
                    }
                });
            }
            container.addView(item);
            return item;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }
    }

    /**
     * 广告
     */
    private void getAdImage(){
        HttpInterfaceIml.getAdImage().subscribe(new Subscriber<AdBO>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                gotoActivity(MainActivity.class, true);
            }

            @Override
            public void onNext(AdBO s) {
                if (s != null && s.getAdPictures() != null){
                    countdown_text.setVisibility(View.VISIBLE);
                    countdown_text.setText(COUNTDOWN_TIME + " 跳过");
                    setAD_Img(s.getAdPictures(),s.getImageLink());
                }else {
                    adImage.setVisibility(View.GONE);
                    countdown_text.setVisibility(View.GONE);
                }
            }
        });
    }
    private void setAD_Img(String imgUrl, final String imgAddress) {
        Picasso.with(GuidePageAct.this).load(imgUrl)
                .into(adImage);
        adImage.setVisibility(View.VISIBLE);

        countdown_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(MainActivity.class, true);
                if (timer != null)
                    timer.cancel();
            }
        });
        adImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgAddress.equals("10086")){
                    return;
                }
                if (imgAddress.length() < 1) return;

                if(imgAddress.equals("")){
                    return;
                }

                Intent intent = new Intent(GuidePageAct.this, WebViewActivity.class);
                intent.putExtra("url",imgAddress);
                startActivityForResult(intent,1);

                if (timer != null)
                    timer.cancel();
            }
        });
        timer = new Timer();
        timer.schedule(task, 1000, 1000);
    }
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            handler.sendEmptyMessage(0);
        }
    };

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            COUNTDOWN_TIME--;
            if (COUNTDOWN_TIME <= 0) {
                timer.cancel();
                gotoActivity(MainActivity.class, true);
            }
            try {
                countdown_text.setText(COUNTDOWN_TIME + " 跳过");
            }catch (Exception e) {
               e.printStackTrace();
            }

        }
    };

    /**
     * 使用uuid登陆
     */
    private void uuidLogin() {
        HttpInterfaceIml.userLogin(null, null, uuid).subscribe(new Subscriber<UserBO>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                MyApplication.isLogin = ConditionEnum.NOLOGIN;
                MyApplication.spUtils.remove("uuid");
//                gotoActivity(MainActivity.class, true);
            }

            @Override
            public void onNext(UserBO userBO) {
                MyApplication.user = userBO;
                MyApplication.spUtils.put("uuid", userBO.getUuid());
                MyApplication.isLogin = ConditionEnum.LOGIN;
//                gotoActivity(MainActivity.class, true);
            }
        });
    }
}