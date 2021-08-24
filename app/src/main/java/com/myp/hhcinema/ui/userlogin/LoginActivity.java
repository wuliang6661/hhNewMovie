package com.myp.hhcinema.ui.userlogin;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.tools.utils.UIHandler;
import com.myp.hhcinema.R;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.config.ConditionEnum;
import com.myp.hhcinema.entity.UserBO;
import com.myp.hhcinema.entity.threelandingBo;
import com.myp.hhcinema.jpush.MessageEvent;
import com.myp.hhcinema.mvp.MVPBaseActivity;
import com.myp.hhcinema.ui.phonecode.phonecode2;
import com.myp.hhcinema.ui.userforwordpass.VerifyActivity;
import com.myp.hhcinema.ui.userregister.RegisterActivity;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.util.MD5;
import com.myp.hhcinema.util.RegexUtils;
import com.myp.hhcinema.util.StringUtils;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

import static android.R.attr.action;

/**
 * MVPPlugin
 * �wuliang
 * <p>
 * 登陆页面
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter>
        implements LoginContract.View, View.OnClickListener, PlatformActionListener, Handler.Callback {


    @Bind(R.id.phone_edit)
    EditText phoneEdit;
    @Bind(R.id.password_edit)
    EditText passwordEdit;
    @Bind(R.id.login_button)
    Button loginButton;
    @Bind(R.id.forword_password)
    TextView forwordPassword;
    @Bind(R.id.free_registration)
    TextView freeRegistration;
    @Bind(R.id.weibo)
    ImageView weibo;
    @Bind(R.id.qq)
    ImageView qq;
    @Bind(R.id.weixin)
    ImageView weixin;

    String phone;
    String password;
    LoginUtils utils;
    private ProgressDialog progressDialog;
    private int MSG_ACTION_CCALLBACK=0;

    private  int style;
    private String weibouserId;
    private String qquserId;
    private String weixinuserId;
    private String wxUserId;
    private String wbUserId;
    private String qqUserId;
    private String loadVersition;
    private String userId;
    private String userName;
    private String userIcon;
    private String userGender;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        utils = new LoginUtils(this);
        loginButton.setOnClickListener(this);
        freeRegistration.setOnClickListener(this);
        forwordPassword.setOnClickListener(this);
        weibo.setOnClickListener(this);
        qq.setOnClickListener(this);
        weixin.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.act_login;
    }

    @Override
    public void onRequestError(String msg) {
        LogUtils.showToast(msg);
    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void getUser(UserBO data) {
        Intent intent = new Intent();
        intent.putExtra("user", data);
        //登陆之后将所有的公共登陆状态设为登陆
        MyApplication.user = data;
        MyApplication.spUtils.put("uuid", data.getUuid());
        MyApplication.isLogin = ConditionEnum.LOGIN;
        setResult(1, intent);
        //登录时向极光绑定别名
        JPushInterface.resumePush(getApplicationContext());
        setTagAndAlias();
        EventBus.getDefault().post(new MessageEvent("PlayfulWebFragment", "yes"));//发给StoreFragment
        finish();

    }

    @Override
    public void getUserid(threelandingBo s,int styles) {
        if(s.getStatus()==1){
            Intent intent = new Intent();
            intent.putExtra("user", s.getData());
            MyApplication.user = s.getData();
            MyApplication.spUtils.put("uuid", s.getData().getUuid());
            MyApplication.isLogin = ConditionEnum.LOGIN;
            setResult(1, intent);
            EventBus.getDefault().post(new MessageEvent("PlayfulWebFragment", "yes"));//发给StoreFragment
            finish();
        }else {
            if(s.getStatus()==0){
                LogUtils.showToast(s.getMessage());
            }else {
                Intent phonecode=  new Intent(LoginActivity.this,phonecode2.class);
                phonecode.putExtra("style", styles);
                phonecode.putExtra("userId", userId);
                phonecode.putExtra("userName", userName);
                phonecode.putExtra("userIcon", userIcon);
                phonecode.putExtra("userGender", userGender);
                startActivity(phonecode);
            }

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                phone = phoneEdit.getText().toString().trim();
                password = passwordEdit.getText().toString().trim();
                if (isLogin()) {
                    mPresenter.loadUserLogin(phone, MD5.strToMd5Low32(password));
                }
                break;
            case R.id.free_registration:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.forword_password:
                gotoActivity(VerifyActivity.class, false);
                break;
            case R.id.weibo:    //新浪微博登陆
                Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
                style=1;
                sina.setPlatformActionListener(this);
                sina.SSOSetting(false);
                authorize(sina, 3);
                break;
            case R.id.qq:    //QQ登陆
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                style=2;
                qq.setPlatformActionListener(this);
                qq.SSOSetting(false);
                authorize(qq, 2);
                break;
            case R.id.weixin:   //微信登陆
                if(isWeixinAvilible(this)) {
                    Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                    wechat.setPlatformActionListener(this);
                    wechat.SSOSetting(false);
                    style=3;
                    authorize(wechat, 1);
                }else{
                    Toast.makeText(this, "您还没有安装微信，请先安装微信客户端",Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }
    private void authorize(Platform plat, int type) {
        switch (type) {
            case 1:
                showProgressDialog("正在打开微信");
                break;
            case 2:
                showProgressDialog("正在打开QQ");
                break;
            case 3:
                showProgressDialog("正在打开微博");
                break;
        }
        if (plat.isValid()) { //如果授权就删除授权资料
            plat.removeAccount();
        }
        plat.showUser(null);//授权并获取用户信息
    }



    private void showProgressDialog(String s) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(s);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    /**
     * 验证是否可登陆
     *
     * @return
     */
    private boolean isLogin() {
        if (!RegexUtils.isMobileExact(phone)) {
            LogUtils.showToast("请输入正确的手机号码！");
            return false;
        }
        if (StringUtils.isEmpty(password)) {
            LogUtils.showToast("请输入密码!");
            return false;
        }
        if (password.length() < 6 || password.length() > 20) {
            LogUtils.showToast("密码长度要在6-20位!");
            return false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (resultCode) {
            case 1:   //注册页面返回
                UserBO userBO = (UserBO) data.getSerializableExtra("user");
                phoneEdit.setText(userBO.getMobile());
                break;
        }
        if (requestCode == Constants.REQUEST_API) {
            Tencent.handleResultData(data, utils.loginListener);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        progressDialog.dismiss();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        progressDialog.dismiss();
    }
    @Override
    public boolean handleMessage(Message message) {
        if (progressDialog != null)
            progressDialog.dismiss();
        switch (message.arg1) {
            case 1: { // 成功
                Platform platform = (Platform) message.obj;
                userId = platform.getDb().getUserId();//获取用户账号
                userName = platform.getDb().getUserName();//获取用户名字
                userIcon = platform.getDb().getUserIcon();//获取用户头像
                Log.d("sdfkasjdfkl", "setDatas: "+userIcon);
                userGender = platform.getDb().getUserGender();
                if (style == 1) {
                    wxUserId = null;
                    wbUserId = userId;
                    qqUserId = null;
                }
                if (style == 2) {
                    wxUserId = null;
                    wbUserId = null;
                    qqUserId = userId;
                }
                if (style == 3) {
                    wxUserId = userId;
                    wbUserId = null;
                    qqUserId = null;
                }
                mPresenter.userLoginid(wxUserId, wbUserId, qqUserId,style);
            }
            break;

        }
        return false;
    }

    /**
     * 设置标签与别名
     */
    private void setTagAndAlias() {
        /**
         *这里设置了别名，在这里获取的用户登录的信息
         *并且此时已经获取了用户的userId,然后就可以用用户的userId来设置别名了
         **/
        //false状态为未设置标签与别名成功
        //if (UserUtils.getTagAlias(getHoldingActivity()) == false) {
        Set<String> tags = new HashSet<String>();
        //这里可以设置你要推送的人，一般是用户uid 不为空在设置进去 可同时添加多个
        if (MyApplication.user != null && !MyApplication.user.getId().equals("")) {
//            JPushInterface.setAlias(LoginActivity.this, 2,info.getPhone());
            tags.add(MyApplication.user.getMobile());//设置tag
            //上下文、别名【Sting行】、标签【Set型】、回调
            JPushInterface.setAliasAndTags(LoginActivity.this, MyApplication.user.getMobile(), tags,
                    mAliasCallback);
        }
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    //这里可以往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    //UserUtils.saveTagAlias(getHoldingActivity(), true);
                    logs = "Set tag and alias success极光推送别名设置成功";
                    Log.e("TAG", logs);
                    break;
                case 6002:
                    //极低的可能设置失败 我设置过几百回 出现3次失败 不放心的话可以失败后继续调用上面那个方面 重连3次即可 记得return 不要进入死循环了...
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.极光推送别名设置失败，60秒后重试";
                    Log.e("TAG", logs);
                    break;
                default:
                    logs = "极光推送设置失败，Failed with errorCode = " + code;
                    Log.e("TAG", logs);
                    break;
            }
        }
    };
}