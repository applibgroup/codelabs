package com.application.webviewdemo.slice;

import com.application.webviewdemo.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.components.Text;
import ohos.agp.components.webengine.*;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.PixelMap;

public class WebPageSlice extends AbilitySlice {
    private static final HiLogLabel TAG = new HiLogLabel(0,0,"WebPageSlice");
    private Text webPageHeader;
    private WebView webView;
    private Image backButton;
    private Image forwardButton;
    private Navigator navigator;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_web_page);
        webPageHeader = (Text) findComponentById(ResourceTable.Id_webpage_header);
        backButton = (Image) findComponentById(ResourceTable.Id_goback);
        forwardButton = (Image) findComponentById(ResourceTable.Id_forward);
        webView = (WebView) findComponentById(ResourceTable.Id_webview);
        webView.getWebConfig().setJavaScriptPermit(true);
        webView.setWebAgent ( new WebAgent() {
            @Override
            public void onLoadingPage(WebView webview, String url, PixelMap favicon) {
                super.onLoadingPage(webview, url, favicon);
                // Custom processing when the page starts to load
                HiLog.debug(TAG,"onLoadingPage");
                webPageHeader.setText("Loading...");
            }

            @Override
            public void onPageLoaded(WebView webview, String url) {
                super.onPageLoaded(webview, url);
                // Customized processing after the page is loaded
                HiLog.debug(TAG,"onPageLoaded");
                webPageHeader.setText("Loading completed !!");
            }

            @Override
            public void onLoadingContent(WebView webview, String url) {
                super.onLoadingContent(webview, url);
                // Custom processing when loading resources
                HiLog.debug(TAG,"onLoadingContent");
                //webPageHeader.setText("Please wait. Content loading");
            }

            @Override
            public void onError(WebView webview, ResourceRequest request, ResourceError error) {
                super.onError(webview, request, error);
                // Custom processing when an error occurs
                HiLog.debug(TAG,"onError");
                webPageHeader.setText("Page Load ERROR !!");
            }
        });
        webView.load("https://developer.huawei.com/en/");
        navigator = webView.getNavigator();
        setNavButtonListeners();
    }

    private void setNavButtonListeners() {
        backButton.setClickedListener((component) -> {
            if (navigator.canGoBack()) {
                navigator.goBack();
            }
        });

        forwardButton.setClickedListener((component) -> {
            if (navigator.canGoForward()) {
                navigator.goForward();
            }
        });
    }


}
