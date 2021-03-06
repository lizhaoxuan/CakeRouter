package com.example.cakerouter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cake.router.CakeRouter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class RouterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router);

        CakeRouter cakeRouter = new CakeRouter.Builder("eleme")
                .build();
//        test();
//        String url = "eleme://com.demo.zhaoxuanli.listdemo.router.RouterTestActivity?str@str=abcdefg&int@i=10&char@c=p";
//        String url = "eleme://main?str@str=abcdefg&int@i=10&char@c=p";
        String url = createUrl();
        Log.d("TAG", "url:" + url);
        cakeRouter.dispatch(this, url);
    }

    private String createUrl() {
        String url = "";
        try {
            url = new CakeRouter.Schema("eleme", "main")
                    .putExtar("str", "abcdefg")
                    .putExtar("int", 10)
                    .putExtar("char", 'p')
                    .putExtar("name", new NameClass("cakeRouter"))
                    .build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return url;
    }


    private void test() {
        String str = "eleme://a=me.ele.crowdsource.view&login.LoginActivity?key@i[]=[1,2,3,4]";
        Log.d("TAG", "index:" + str.indexOf("://"));
        Log.d("TAG", "aaa:" + str.substring(0, 5));
        Log.d("TAG", "bbb:" + str.substring(8));

        String str2 = "me.ele.crowdsource.view.login.LoginActivity&ios.LoginController";
        String[] strArray = str2.split("&");
        Log.d("TAG", strArray[0] + "  " + strArray[1]);

        String urlEncoder = "";
        String urlDecoder = "";
        try {
            urlEncoder = URLEncoder.encode(str, "UTF-8");
            Log.d("TAG", urlEncoder);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            urlDecoder = URLDecoder.decode(urlEncoder, "UTF-8");
            Log.d("TAG", urlDecoder);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
