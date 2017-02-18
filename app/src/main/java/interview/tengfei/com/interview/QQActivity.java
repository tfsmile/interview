package interview.tengfei.com.interview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import interview.tengfei.com.R;

public class QQActivity extends AppCompatActivity {

    private Button login, logout;
    private Tencent mTencent;
    private static final String APPID = "1105771437";
//    private static final String APPID = "1105908715";
    private QQLoginListener mListener;
    private UserInfo mInfo;
    private ImageView figure;
    private TextView nickName;
    private String name, figureurl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);

        initView();
    }

    private void initView() {
        figure = (ImageView) findViewById(R.id.iv_figure);
        nickName = (TextView) findViewById(R.id.tv_nickname);

        mListener = new QQLoginListener();
        // ʵ����Tencent
        if (mTencent == null) {
            mTencent = Tencent.createInstance(APPID, this);
        }

        // �����¼
        login = (Button) findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QQLogin();
            }
        });
        // ����˳�
        logout = (Button) findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTencent.isSessionValid()) {
                    mTencent.logout(QQActivity.this);
                    figure.setImageResource(R.drawable.categories_icon);
                    nickName.setText("QQ �ǳ�");
                }
            }
        });
    }

    /**
     * ��¼����
     */
    private void QQLogin() {
        //���session�����ã����¼������˵���Ѿ���¼
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", mListener);
        }
    }

    // ʵ�ֵ�¼�ɹ����Ľӿ�
    private class QQLoginListener implements IUiListener {

        @Override
        public void onComplete(Object object) { //��¼�ɹ�
            //��ȡopenid��token
            initOpenIdAndToken(object);
            //��ȡ�û���Ϣ
            getUserInfo();
        }

        @Override
        public void onError(UiError uiError) {  //��¼ʧ��
        }

        @Override
        public void onCancel() {                //ȡ����¼
        }
    }

    private void initOpenIdAndToken(Object object) {
        JSONObject jb = (JSONObject) object;
        try {
            String openID = jb.getString("openid");  //openid�û�Ψһ��ʶ
            String access_token = jb.getString("access_token");
            String expires = jb.getString("expires_in");

            mTencent.setOpenId(openID);
            mTencent.setAccessToken(access_token, expires);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getUserInfo() {
        QQToken token = mTencent.getQQToken();
        mInfo = new UserInfo(QQActivity.this, token);
        mInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object object) {
                JSONObject jb = (JSONObject) object;
                try {
                    name = jb.getString("nickname");
                    figureurl = jb.getString("figureurl_qq_2");  //ͷ��ͼƬ��url
                    nickName.setText(name);
                    Glide.with(QQActivity.this).load(figureurl).into(figure);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
            }

            @Override
            public void onCancel() {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mTencent.onActivityResultData(requestCode, resultCode, data, mListener);
    }
}

