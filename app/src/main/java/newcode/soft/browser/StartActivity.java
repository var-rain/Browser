package newcode.soft.browser;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.Selection;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * AIG 创建于 2015/9/17.
 */
public class StartActivity extends Activity {

    private boolean isJavaScript = true;
    private String loadUri = "http://www.baidu.com";
    private String url;

    private ProgressWebView webView;
    private WebSettings webSettings;
    private ImageButton imageButton;
    private EditText editText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.start_layout);
//        判断SDK版本是否是4.4或以上版本,是,则支持沉浸式状态栏
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT)
        {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        //获取WebView的id
        webView = (ProgressWebView) findViewById(R.id.webview);
        imageView = (ImageView) findViewById(R.id.title_icon);
        imageView.setImageResource(R.drawable.title_icon);
        //获取WebView的设置工具
        webSettings = webView.getSettings();
        imageButton = (ImageButton) findViewById(R.id.button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = editText.getText().toString();
                if (url != null) {
                    loadUri = "http://" + url;
                    reloadUrl(loadUri);
                }
            }
        });

        editText = (EditText) findViewById(R.id.edittext);
        editText.setSingleLine(true);
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                设置EditText可编辑
                editText.setEnabled(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
//                将光标方到字符串最后
                Selection.selectAll(editText.getText());
                return false;
            }
        });

        //设定支持ViewPort
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        //设置缩放支持
        webSettings.setSupportZoom(true);
        //隐藏缩放按钮
        webSettings.setDisplayZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);
        //是否支持JavaScript
        webSettings.setJavaScriptEnabled(isJavaScript);
        //是否显示横向滚动条
        webView.setHorizontalScrollBarEnabled(false);
        //是否显示垂直滚动条
        webView.setVerticalScrollBarEnabled(false);
        //设置网页浏览客户端在本身(不跳到其他浏览器)
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                当uri改变时获取uri
                loadUri = webView.getUrl();
                editText.setText(loadUri);
            }
        });
        //浏览器启动加载的网页
        reloadUrl(loadUri);

    }
    /*
    刷新功能
     */
    public void reloadUrl(String uri){
//        重新加载Uri,相当于刷新功能
        webView.loadUrl(uri);
//        设置EditText不可编辑
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
    }
}
