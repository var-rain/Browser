package newcode.soft.browser;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * AIG 创建于 2015/9/18.
 * 自定义WebView
 * 带有进度条的WebView
 */
public class ProgressWebView extends WebView {
//  声明进度条
    private ProgressBar progressbar;
    /**
     * 构造函数
     */
    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progressbar_style));
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 6, 0, 0));
        addView(progressbar);
        setWebChromeClient(new WebChromeClient());
    }
    /*
    内部类自定义WebChromeClient
     */
    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//          获取进度条加载情况
//            进度条加载完成时
            if (newProgress == 100) {
//              当进度条走完时自动隐藏进度条
                progressbar.setVisibility(GONE);
//              进度条没加载完成时
            } else {
//                判断进度条是否被隐藏
                if (progressbar.getVisibility() == GONE)
//                    显示进度条
                    progressbar.setVisibility(VISIBLE);
//                    设置进度条进度
                    progressbar.setProgress(15 + newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
