package com.application.webviewdemo.slice;

import com.application.webviewdemo.Form;
import com.application.webviewdemo.ResourceTable;
import com.application.webviewdemo.utils.PatternUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;
import ohos.agp.components.webengine.*;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.utils.TextTool;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;
import ohos.global.resource.Resource;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.net.Uri;
import ohos.utils.zson.ZSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

public class LocalPageSlice extends AbilitySlice {
    private static final HiLogLabel TAG = new HiLogLabel(0,0,"LocalPageSlice");
    private static final String FILE_PATH = "https://example.com/rawfile/index.html";
    private WebView webView;
    private Button cookieButton;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_local_page);
        webView = (WebView) findComponentById(ResourceTable.Id_local_webview);
        cookieButton = (Button) findComponentById(ResourceTable.Id_btn_cookie);
        webView.getWebConfig().setJavaScriptPermit(true);
        webView.load(FILE_PATH);
        webView.addJsCallback("JavaScriptInterface",new JavaScriptInterface(getContext()));
        webView.setWebAgent (new WebAgent() {
            @Override
            public ResourceResponse processResourceRequest(WebView webview, ResourceRequest request) {
                final String authority = "example.com";
                final String rawFile = "/rawfile/";
                final String local = "/local/";
                Uri requestUri = request.getRequestUrl();
                if (authority.equals(requestUri.getDecodedAuthority())) {
                    String path = requestUri.getDecodedPath();
                    if (TextTool.isNullOrEmpty(path)) {
                        return super.processResourceRequest(webview, request);
                    }
                    if (path.startsWith(rawFile)) {
                        // Access resource files according to custom rules
                        String rawFilePath = "entry/resources/rawfile/" + path.replace(rawFile, "");
                        String mimeType = URLConnection.guessContentTypeFromName(rawFilePath);
                        try {
                            Resource resource = getResourceManager().getRawFileEntry(rawFilePath).openRawFile();
                            ResourceResponse response = new ResourceResponse(mimeType, resource, null);
                            return response;
                        } catch (IOException e) {
                            HiLog.debug(TAG, "open raw file failed");
                        }
                    }
                    if (path.startsWith(local)) {
                        // Access local files according to custom rules
                        String localFile = getContext().getFilesDir() + path.replace(local, "/");
                        HiLog.debug(TAG, "open local file " + localFile);
                        File file = new File(localFile);
                        if (!file.exists()) {
                            HiLog.error(TAG, "File not exists");
                            return super.processResourceRequest(webview, request);
                        }
                        String mimeType = URLConnection.guessContentTypeFromName(localFile);
                        try {
                            InputStream inputStream = new FileInputStream(file);
                            ResourceResponse response = new ResourceResponse(mimeType, inputStream, null);
                            return response;
                        } catch (IOException e) {
                            HiLog.error(TAG, "Open local file failed");
                        }
                    }
                }
                return super.processResourceRequest(webview, request);
            }
        });

        cookieButton.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                webView.executeJs("javascript:callFuncInWeb()", new AsyncCallback<String>() {
                    @Override
                    public void onReceive(String msg) {
                        if (msg == null || msg.equals("\"\"")) {
                            msg = "Empty cookie";
                        }
                        ToastDialog toastDialog = new ToastDialog(LocalPageSlice.this);
                        Component component = LayoutScatter.getInstance(LocalPageSlice.this).parse(
                                ResourceTable.Layout_toast_layout, null, false);
                        Text toastText = (Text) component.findComponentById(ResourceTable.Id_toast_text);
                        toastText.setTextSize(50);
                        toastText.setText(msg);
                        toastDialog.setComponent(component).show();
                    }
                });
            }
        });
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    public class JavaScriptInterface implements JsCallback {
        Context context;

        public JavaScriptInterface(Context context) {
            this.context = context;
        }
        @Override
        public String onCallback(String jsonString) {
            ToastDialog toastDialog = new ToastDialog(context);
            Component component = LayoutScatter.getInstance(context).parse(ResourceTable.Layout_toast_layout, null, false);
            Text toastText = (Text) component.findComponentById(ResourceTable.Id_toast_text);
            Form form = null;
            try {
                form = ZSONObject.stringToClass(jsonString, Form.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String userName = form.getUserName();
            String password = form.getPassword();
            String email = form.getEmail();
            String phone = form.getPhone();
            if (validateName(userName) && validatePassword(password) && validateEmail(email) && validatePhone(phone)) {
                toastText.setText("SIGNUP SUCCESS !!");
                toastDialog.setComponent(component).show();
            }
            return jsonString;
        }
    }

    public boolean validateName(String input) {
        ToastDialog toastDialog = new ToastDialog(LocalPageSlice.this);
        Component component = LayoutScatter.getInstance(LocalPageSlice.this).parse(
                ResourceTable.Layout_toast_layout, null, false);
        Text toastText = (Text) component.findComponentById(ResourceTable.Id_toast_text);
        String toastMessage = null;
        if (input == null || input.length() < 2) {
            toastMessage = "Error - Empty name or less than 2 characters";
        } else if (PatternUtils.isConsistOfNumber(input) || PatternUtils.isContainSpecialCharacter(input)) {
            toastMessage = "Error - Invalid name : must consist of only alphabets";
        }
        if (toastMessage != null) {
            toastText.setText(toastMessage);
            toastDialog.setComponent(component).show();
            return false;
        }
        return true;
    }

    public boolean validatePassword(String input) {
        ToastDialog toastDialog = new ToastDialog(LocalPageSlice.this);
        Component component = LayoutScatter.getInstance(LocalPageSlice.this).parse(
                ResourceTable.Layout_toast_layout, null, false);
        Text toastText = (Text) component.findComponentById(ResourceTable.Id_toast_text);
        String toastMessage = null;
        if (input == null || input.length() < 5) {
            toastMessage = "Error - Empty password or less than 5 characters";
        } else if (!PatternUtils.isContainSpecialCharacter(input)) {
            toastMessage = "Error - Invalid password : must consist of atleast 5 characters including a special character";
        }
        if (toastMessage != null) {
            toastText.setText(toastMessage);
            toastDialog.setComponent(component).show();
            return false;
        }
        return true;
    }

    public boolean validateEmail(String input) {
        ToastDialog toastDialog = new ToastDialog(LocalPageSlice.this);
        Component component = LayoutScatter.getInstance(LocalPageSlice.this).parse(
                ResourceTable.Layout_toast_layout, null, false);
        Text toastText = (Text) component.findComponentById(ResourceTable.Id_toast_text);
        String toastMessage = null;
        if (input == null || input.length() < 3) {
            toastMessage = "Error - Empty email or too less characters";
        } else if (!(input.contains("@") && input.contains("."))) {
            toastMessage = "Error - Invalid email format";
        }
        if (toastMessage != null) {
            toastText.setText(toastMessage);
            toastDialog.setComponent(component).show();
            return false;
        }
        return true;
    }

    public boolean validatePhone(String input) {
        ToastDialog toastDialog = new ToastDialog(LocalPageSlice.this);
        Component component = LayoutScatter.getInstance(LocalPageSlice.this).parse(
                ResourceTable.Layout_toast_layout, null, false);
        Text toastText = (Text) component.findComponentById(ResourceTable.Id_toast_text);
        String toastMessage = null;
        if (input == null || input.length() < 10) {
            toastMessage = "Error - Empty phone number or less digits in the phone number";
        }
        if (toastMessage != null) {
            toastText.setText(toastMessage);
            toastDialog.setComponent(component).show();
            return false;
        }
        return true;
    }
}
