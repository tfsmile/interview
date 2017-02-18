package interview.tengfei.com.interview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2017/2/13.
 */

public class MainActivity extends Activity {
    private Button login_qq;
    private Button btn_LBS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_qq = (Button) findViewById(R.id.login_qq);
        btn_LBS = (Button) findViewById(R.id.btn_LBS);
        initView();
    }

    public void initView() {
        login_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent qqIntent = new Intent(MainActivity.this,QQActivity.class);
                startActivity(qqIntent);
            }
        });
        btn_LBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lbsIntent = new Intent(MainActivity.this,LBSActivity.class);
                startActivity(lbsIntent);
            }
        });
    }
}
