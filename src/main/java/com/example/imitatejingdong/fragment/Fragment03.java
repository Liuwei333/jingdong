package com.example.imitatejingdong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.example.imitatejingdong.R;

/**
 * Created by Administrator on 2018/5/17.
 */

public class Fragment03 extends Fragment {

    private Button btn1;
   private Button btn2;
   private Button btn3;
   private WebView webView;
    private WebView webView1;


    String url="https://h5.m.jd.com/active/faxian/list/article-list.html";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.fragment03, null);

        btn1 =  view.findViewById(R.id.btn1);

           btn3 =  view.findViewById(R.id.btn3);

        webView1 = new WebView(getActivity());
        //实例化webview组件
        webView = view.findViewById(R.id.webview);
        //webView.setWebViewClient(new WebViewClient());

        //加载assets目录下的html文件"file:///android_asset/js_android.html"
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        //映射.可以调用js里面的方法
        webView.addJavascriptInterface(new JSInterface(), "jsi");

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

        //java调用js方法 的点击事件, webView.loadUrl("javascript:androidCallJs()");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("javascript:androidCallJs()");
            }
        });


        //加载页面的点击事件
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView1.loadUrl("http://www.baidu.com");
                getActivity().setContentView(webView1);
                //  Toast.makeText(MainActivity.this,"ee",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    private final class JSInterface{
        /**
             * 注意这里的@JavascriptInterface注解， target是4.2以上都需要添加这个注解，否则无法调用
             * @param text
              */
        @JavascriptInterface
        public void showToast(String text){
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        }
        @JavascriptInterface
        public void showJsText(String text){
            webView.loadUrl("javascript:jsText('"+text+"')");
        }
    }
}
