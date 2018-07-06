package com.fivefivelike.mybaselibrary.mywebview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fivefivelike.mybaselibrary.utils.FileUtil;

import java.io.File;

/**
 * Created by liugongce on 2017/11/30.
 */

public class WebUtils {
    /**
     * 一键设置webview
     *
     * @param webView
     */
    public static void webviewRegister(WebView webView, final Context context) {
        //允许访问文件
        WebSettings webSettings = webView.getSettings();
        //
        webSettings.setDomStorageEnabled(true);
        //支持缩放，默认为true。
        webSettings.setSupportZoom(false);
        //调整图片至适合webview的大小
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //设置默认编码
        webSettings.setDefaultTextEncodingName("utf-8");
        ////设置自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //允许访问文件
        webSettings.setAllowFileAccess(true);
        //开启javascript
        webSettings.setJavaScriptEnabled(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //提高渲染的优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //支持内容重新布局
        // NORMAL：正常显示，没有渲染变化。
        // SINGLE_COLUMN：把所有内容放到WebView组件等宽的一列中。 //这个是强制的，把网页都挤变形了
        // NARROW_COLUMNS：可能的话，使所有列的宽度不超过屏幕宽度。 //好像是默认的
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //关闭webview中缓存
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //获取触摸焦点
        webView.requestFocusFromTouch();
        //http/https混合加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 保存表单数据
        webSettings.setSaveFormData(true);

        //设置跨域cookie读取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    url = request.getUrl().toString();
                } else {
                    url = request.toString();
                }
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                    return false;
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(intent);
                    return true;
                }
            }
        });
    }


    /**
     * WebView清空缓存
     */
    public static void clearCache(Context context) {
        CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        context.deleteDatabase("webview.db");
        context.deleteDatabase("webviewCache.db");
        context.deleteDatabase("webviewCookiesChromium.db");
        context.deleteDatabase("webviewCookiesChromiumPrivate.db");
        //WebView 缓存文件
        File webviewCacheDir = new File(context.getCacheDir().getAbsolutePath() + "/webviewCacheChromium");
        //删除webview 缓存目录
        if (webviewCacheDir.exists()) {
            FileUtil.deleteDirWihtFile(webviewCacheDir);
        }
    }


    /**
     * 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
     * 如要点击一张图片在弹出的页面查看所有的图片集合,则获取的值应该是个图片数组
     *
     * @param webView
     */
    public static void addWebImageClickJs(WebView webView) {
        webView.loadUrl("javascript:(function(){"
                + "var objs = document.getElementsByTagName(\"img\");"
                + "var paths = new Array(); "
                + "var index = 0; "
                + "for(var i=0;i<objs.length;i++)"
                + "{"
                + "   if(objs[i].getAttribute(\"src\"))"
                + "       {"
                + "           ++index;  "
                + "           paths[index]=objs[i].src;  "
                + "           objs[i].onclick=function(){"
                + "           window.injectedObject.imageClick(this.src,index);"
                + "           }"
                + "         }"
                + "}"
                + "window.injectedObject.allPaths(paths);"
                + "})()");

        // 遍历所有的a节点,将节点里的属性传递过去(属性自定义,用于页面跳转)
        webView.loadUrl("javascript:(function(){" +
                "var objs =document.getElementsByTagName(\"a\");" +
                "for(var i=0;i<objs.length;i++)" +
                "{" +
                "objs[i].onclick=function(){" +
                "window.injectedObject.textClick(this.getAttribute(\"type\"),this.getAttribute(\"item_pk\"));}" +
                "}" +
                "})()");

    }

    public static void onResume(WebView webView) {
        webView.onResume();
    }


    public static void onPause(WebView webView) {
        webView.onPause();
    }

    public static void onDestroy(WebView webView) {
        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.loadUrl("about:blank");
            webView.stopLoading();
            webView.setWebViewClient(null);
            webView.setWebChromeClient(null);
            webView.destroy();
            webView = null;
        }
    }
}
