package com.myp.hhcinema.ui.moviessession;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myp.hhcinema.R;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.config.ConditionEnum;
import com.myp.hhcinema.entity.CinemaBo;
import com.myp.hhcinema.entity.FavourBO;
import com.myp.hhcinema.entity.MoviesByCidBO;
import com.myp.hhcinema.entity.MoviesSessionBO;
import com.myp.hhcinema.entity.OrderNumBO;
import com.myp.hhcinema.entity.SessionBO;
import com.myp.hhcinema.jpush.MessageEvent;
import com.myp.hhcinema.mvp.MVPBaseActivity;
import com.myp.hhcinema.ui.moviesseattable.SeatTableActivity;
import com.myp.hhcinema.ui.personorder.PersonOrderActivity;
import com.myp.hhcinema.ui.personorder.notpaymessage.NotPayMessageActivity;
import com.myp.hhcinema.ui.userlogin.LoginActivity;
import com.myp.hhcinema.util.ImageUtils;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.util.StringUtils;
import com.myp.hhcinema.util.TimeUtils;
import com.myp.hhcinema.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.myp.hhcinema.widget.lgrecycleadapter.LGViewHolder;
import com.myp.hhcinema.widget.superadapter.CommonAdapter;
import com.myp.hhcinema.widget.superadapter.ViewHolder;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager;
import meijia.com.srdlibrary.liushibuju.BaseAdapter;
import meijia.com.srdlibrary.liushibuju.TagLayout;


/**
 * 选择场次
 */

public class SessionActivity extends MVPBaseActivity<SessionContract.View, SessionPresenter>
        implements SessionContract.View, View.OnClickListener,
        AdapterView.OnItemClickListener {

    @Bind(R.id.cinema_name)
    TextView cinemaName;
    @Bind(R.id.cinema_address)
    TextView cinemaAddress;
    @Bind(R.id.cinema_distance)
    TextView cinemaDistance;
    @Bind(R.id.viewpager)
    RecyclerView recyle;
    @Bind(R.id.movies_name)
    TextView moviesName;
    @Bind(R.id.movies_message)
    TextView moviesMessage;
    @Bind(R.id.item_layout)
    LinearLayout itemLayout;
    @Bind(R.id.session_list)
    ListView sessionList;
    @Bind(R.id.view_pager_parent)
    RelativeLayout viewPagerParent;
    @Bind(R.id.no_session_text)
    TextView noSessionText;
    @Bind(R.id.no_layout)
    LinearLayout noLayout;
    @Bind(R.id.date_recyle)
    RecyclerView dateRecyle;
    @Bind(R.id.forver_title)
    TextView forverTitle;
    @Bind(R.id.forver_message)
    TextView forverMessage;
    @Bind(R.id.forver_layout)
    LinearLayout forverLayout;
    @Bind(R.id.ivCall)
    ImageView ivCall;
    @Bind(R.id.taglayout)
    TagLayout taglayout;

    CinemaBo cinemaBo;
    List<MoviesByCidBO> moviesByCidBOs;
    MoviesByCidBO movies;   //当前滑动到的影片
    List<SessionBO.ScreenPlanListBo> screenPlanListBos = null;  //当前显示的场次列表


    private MoviesByCidBO distailMovie;

    int distion = Integer.MAX_VALUE;   //  记录首页点击的电影下标
    List<MoviesSessionBO> sessionBOs;    //当前选中日期的场次列表
    private int isone = 1;

    private String[] tagData;//标签

    @Override
    protected int getLayout() {
        return R.layout.act_session_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        cinemaBo = MyApplication.cinemaBo;
        setTitle(cinemaBo.getCinemaName());
        if (cinemaBo.getSpecialTile() != null && !cinemaBo.getSpecialTile().equals("")){
            tagData = cinemaBo.getSpecialTile().split(",");
        }
        showProgress("加载中...");
        initvion();
        ivCall.setOnClickListener(this);
        distailMovie = (MoviesByCidBO) getIntent().getExtras().getSerializable("movies");
        moviesByCidBOs = MyApplication.movies;
        initViewPager();
        scollMovie();
        mPresenter.forvoreInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 滑动到指定电影
     */
    private void scollMovie() {
        for (int i = 0; i < moviesByCidBOs.size(); i++) {
            if (moviesByCidBOs.get(i).getCineMovieNum().equals(distailMovie.getCineMovieNum())) {
                distion = i;
            }
        }
        recyle.smoothScrollToPosition(distion);
    }


    /**
     * 初始化控件并设值
     */
    private void initvion() {
        sessionList.setOnItemClickListener(this);
        cinemaName.setText(cinemaBo.getCinemaName());
        cinemaAddress.setText(cinemaBo.getAddress());
        if (cinemaBo.getDistance() != null) {
            double distance = Double.parseDouble(cinemaBo.getDistance());
            if (distance < 1000) {
                cinemaDistance.setText((int) distance + "m");
            } else {
                DecimalFormat df = new DecimalFormat("#.00");
                cinemaDistance.setText(df.format(distance / 1000) + "km");
            }
        }else {
            cinemaDistance.setText("请重新定位");
        }
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dateRecyle.setLayoutManager(manager);
        if (tagData != null && tagData.length>0) {
            taglayout.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return tagData.length;
                }

                @Override
                public View getView(int position, ViewGroup parent) {
                    TextView view = (TextView) LayoutInflater.from(SessionActivity.this).inflate(R.layout.item_tag, parent, false);
                    view.setText(tagData[position]);
                    return view;
                }
            });
        }
    }

    AlertDialog dialog;

    /**
     * 获取未完成订单
     */
    @Override
    public void getCheckOrder(final OrderNumBO orderNumBO) {
        isone = 1;
        if ("0".equals(orderNumBO.getOrder())) {  //没有未完成订单
            Bundle bundle = new Bundle();
            bundle.putSerializable("movies", movies);
            bundle.putSerializable("session", sessionBOs.get(position));
            bundle.putSerializable("isVip", orderNumBO);
            gotoActivity(SeatTableActivity.class, bundle, false);
        } else {
            LayoutInflater factory = LayoutInflater.from(this);//提示框
            final View view = factory.inflate(R.layout.dialog_order_pay, null);//这里必须是final的
            TextView cancle = (TextView) view.findViewById(R.id.off_commit);
            TextView commit = (TextView) view.findViewById(R.id.commit);
            dialog = new AlertDialog.Builder(this).create();
            cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    showProgress("加载中...");
                    mPresenter.orderCancle(orderNumBO.getOrderNum());
                }
            });
            commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //事件
                    dialog.dismiss();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order", null);
                    gotoActivity(NotPayMessageActivity.class, bundle,false);
                }
            });
            dialog.setView(view);
            dialog.show();
        }
    }


    /**
     * 取消未完成订单
     */
    @Override
    public void getOrderCancle(OrderNumBO orderNumBO) {
        if (orderNumBO.getIsVip() == null) {
        }
        isone = 1;
        if (!StringUtils.isEmpty(orderNumBO.getOrderNum())) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("movies", movies);
            bundle.putSerializable("session", sessionBOs.get(position));
            bundle.putSerializable("isVip", orderNumBO);
            gotoActivity(SeatTableActivity.class, bundle, false);
        }
    }

    /**
     * 获取优惠信息
     */
    @Override
    public void getFoverList(List<FavourBO> favourBOs) {
        if (favourBOs == null || favourBOs.size() == 0) {
            forverLayout.setVisibility(View.GONE);
            return;
        }
        forverLayout.setVisibility(View.VISIBLE);
        FavourBO favourBO = favourBOs.get(0);
        forverTitle.setText(favourBO.getTitle() + " | ");
        forverMessage.setText(favourBO.getContent());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_layout:   //城市信息

                break;
            case R.id.ivCall:
                if (MyApplication.cinemaBo != null) {
                    callPhone();
                }
                break;
                default:
                    break;
        }
    }

    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+MyApplication.cinemaBo.getContact()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public void onRequestError(String msg) {
        isone = 1;
        stopProgress();
        LogUtils.showToast(msg);

    }

    @Override
    public void onRequestEnd() {
        stopProgress();
    }

    /**
     * 设置电影图片滑动控件
     */
    private void initViewPager() {
        GalleryLayoutManager layoutManager1 = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
        layoutManager1.attach(recyle, 0);
        layoutManager1.setItemTransformer(new ScaleTransformer());
        layoutManager1.setOnItemSelectedListener(new GalleryLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {
                if (distion == 0 || distion == position) {
                    if (sessionList != null) {
                        sessionList.setVisibility(View.GONE);
                    }
                    setSessionData(position);
                    setImgBG(moviesByCidBOs.get(position).getPicture());
                    distion = 0;
                }
            }
        });
        LGRecycleViewAdapter<MoviesByCidBO> adapter = new LGRecycleViewAdapter<MoviesByCidBO>(moviesByCidBOs) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_movies_img;
            }

            @Override
            public void convert(LGViewHolder holder, MoviesByCidBO s, int position) {
                ImageView moviesImg = (ImageView) holder.getView(R.id.movies_img);
                if (StringUtils.isEmpty(s.getPicture())) {
                    moviesImg.setImageResource(R.color.white);
                } else {
                    Picasso.with(SessionActivity.this).load(s.getPicture()).into(moviesImg);
                }
            }
        };
        recyle.setAdapter(adapter);
        adapter.setOnItemClickListener(R.id.movies_img, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                recyle.smoothScrollToPosition(position);
            }
        });
    }


    /**
     * 设置背景模糊图片
     */
    private void setImgBG(final String headerImageUrl) {
        if (StringUtils.isEmpty(headerImageUrl)) {
            recyle.setBackgroundColor(getResources().getColor(R.color.act_bg01));
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = ImageUtils.getBitMBitmap(headerImageUrl);
                    Message msg = Message.obtain();
                    msg.what = 0;
                    msg.obj = bitmap;
                    handler.sendMessage(msg);
                }
            }).start();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Bitmap newBitmap = ImageUtils.fastBlur((Bitmap) msg.obj, 0.3f, 25, true);
                if (newBitmap != null && recyle != null) {
                    recyle.setBackground(new BitmapDrawable(newBitmap));
                }
            }
        }
    };

    /**
     * 根据滑动到的电影设置场次信息
     */
    private void setSessionData(int position) {
        movies = moviesByCidBOs.get(position);
        if (StringUtils.isEmpty(movies.getUniqueName())) {
            moviesName.setText(movies.getMovieName());
        } else {
            moviesName.setText(movies.getUniqueName());
        }
        moviesMessage.setText(movies.getSummary());
        mPresenter.loadMoviesSession(cinemaBo.getCinemaId(), movies.getId());
    }

    /**
     * 返回场次数据
     */
    @Override
    public void getData(List<SessionBO.ScreenPlanListBo> sessionBOs) {
        this.screenPlanListBos = sessionBOs;
        setSessionDateAdapter();
        setSessionAdapter(sessionBOs.get(0).getDate(), sessionBOs.get(0).getList());
    }

    /**
     * 设置时间列表
     */
    private void setSessionDateAdapter() {
        SessionDateAdapter adapter = new SessionDateAdapter(this, screenPlanListBos);
        adapter.setOnItemClick(new SessionDateAdapter.OnItemClick() {
            @Override
            public void getMovieSession(int position, String date, List<MoviesSessionBO> list) {
                setSessionAdapter(date, list);
            }
        });
        dateRecyle.setAdapter(adapter);
    }


    CommonAdapter<MoviesSessionBO> adapter;


    /**
     * 设置场次适配器
     * <p>
     */
    private void setSessionAdapter(String date, List<MoviesSessionBO> list) {
        sessionBOs = list;
        if (list == null || list.size() == 0) {
            sessionList.setVisibility(View.GONE);
            noLayout.setVisibility(View.VISIBLE);
            noSessionText.setText(TimeUtils.string2Pander(date, "MM.dd") + " 暂无场次");
        } else {
            sessionList.setVisibility(View.VISIBLE);
            noLayout.setVisibility(View.GONE);
        }
        adapter = new CommonAdapter<MoviesSessionBO>(this,
                R.layout.item_session_layout, list) {
            @Override
            protected void convert(ViewHolder helper, MoviesSessionBO item, int position) {
                helper.setText(R.id.start_time, item.getStartTime().split(" ")[1].substring(0, 5));
                helper.setText(R.id.stop_time, item.getEndTime().split(" ")[1].substring(0, 5) + "散场");
                helper.setText(R.id.movie_langvige, item.getMovieLanguage());
                helper.setText(R.id.movie_ting, item.getHallName());
                if (item.getPartnerPrice() == null) {
                    helper.setText(R.id.movie_price, item.getMarketPrice());
                } else {
                    if (MyApplication.isLogin == ConditionEnum.NOLOGIN) {  //未登录进入登陆
                        helper.setText(R.id.movie_price, item.getPartnerPrice());
                    } else if (MyApplication.isLogin == ConditionEnum.LOGIN) {   //已登录正式跳转
                        if (item.getLeftScreenLimitNum() == null) {
                            helper.setText(R.id.movie_price, item.getMarketPrice());
                        } else {
                            if (item.getGlobalLeftNum() > 0) {
                                if (item.getLeftScreenLimitNum() > 0) {
                                    helper.setText(R.id.movie_price, item.getPartnerPrice());
                                } else {
                                    helper.setText(R.id.movie_price, item.getMarketPrice());
                                }
                            } else {
                                helper.setText(R.id.movie_price, item.getMarketPrice());
                            }

                        }
                    }
                }
                if (item.getGlobalCanBuyNum() != null && item.getGlobalCanBuyNum()>0) {
                    helper.setText(R.id.cinema_price, "会员价：" + item.getGlobalPreferPrice());
                } else {
                    helper.setText(R.id.cinema_price, "会员价：" + item.getPreferPrice());
                }
                helper.getView(R.id.moives_type).setVisibility(View.VISIBLE);
                switch (item.getMovieDimensional()) {
                    case "2D":
                        helper.setImageResource(R.id.moives_type, R.drawable.img_2d);
                        break;
                    case "3D":
                        helper.setImageResource(R.id.moives_type, R.drawable.img_3d);
                        break;
                    default:
                        helper.getView(R.id.moives_type).setVisibility(View.GONE);
//                        helper.setImageResource(R.id.moives_type, R.drawable.img_3dmax);
                        break;
                }
            }
        };
        sessionList.setAdapter(adapter);
    }

    int position = 0;  //记录选中的下标

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (MyApplication.isLogin == ConditionEnum.NOLOGIN) {  //未登录进入登陆
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 1);
        } else if (MyApplication.isLogin == ConditionEnum.LOGIN) {
            if (isone == 1) {
                this.position = position;
                mPresenter.checkOrder();
                isone = 2;
            }

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (resultCode) {
            case 1:
                mPresenter.loadMoviesSession(cinemaBo.getCinemaId(), movies.getId());
                break;

        }
    }

}