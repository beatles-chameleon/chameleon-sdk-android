package com.didi.chameleon.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.didi.chameleon.sdk.module.CmlCallback;
import com.didi.chameleon.sdk.utils.CmlLogUtil;
import com.didi.chameleon.web.container.CmlWebView;

import org.json.JSONException;
import org.json.JSONObject;

public class CmlWebViewActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CmlWeexViewActivity";

    private static final String URL_NORMAL = "http://172.24.30.151:8000/cml/h5/index";
//    private static final String URL_NORMAL = "http://10.179.17.54:8000/cml/h5/index";

    private TextView txtChangeTxt;
    private CmlWebView cmlWebView;

    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cml_web_view);

        txtChangeTxt = findViewById(R.id.txt_change_txt);
        cmlWebView = findViewById(R.id.cml_view);

        cmlWebView.onCreate();
        cmlWebView.render(URL_NORMAL, null); // 加载远程jsbundle
//        cmlWebView.render("file://local/cml-demo-say.js", null); // 加载assets目录里的jsbundle

        txtChangeTxt.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cmlWebView != null) {
            cmlWebView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cmlWebView != null) {
            cmlWebView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cmlWebView != null) {
            cmlWebView.onDestroy();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_change_txt:
                cmlWebView.invokeJsMethod("moduleDemo", "NaTellJS", getContent(),
                        new CmlCallback<String>(String.class) {
                            @Override
                            public void onCallback(@Nullable String data) {
                                CmlLogUtil.d(TAG, "data: " + data);
                            }
                        });
                break;
        }
    }

    /**
     * 这个方法只是为了产生一个json串，界面在每次点击时发生变化
     *
     * @return 返回一个json串，例 {"content":"测试1"}
     */
    private String getContent() {
        String content = "测试" + num++;

        JSONObject obj = new JSONObject();
        try {
            obj.put("content", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }
}
