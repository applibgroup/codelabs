package com.application.webviewdemo.slice;

import com.application.webviewdemo.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.hiviewdfx.HiLogLabel;

public class MainAbilitySlice extends AbilitySlice {
    private static final HiLogLabel TAG = new HiLogLabel(0,0,"MainAbilitySlice");
    Button webPageButton;
    Button localPageButton;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        webPageButton = (Button) findComponentById(ResourceTable.Id_btn_web_page);
        localPageButton = (Button) findComponentById(ResourceTable.Id_btn_local_page);
        webPageButton.setClickedListener((component) -> {
            present(new WebPageSlice(), new Intent());
        });
        localPageButton.setClickedListener((component) -> {
            present(new LocalPageSlice(), new Intent());
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
}
