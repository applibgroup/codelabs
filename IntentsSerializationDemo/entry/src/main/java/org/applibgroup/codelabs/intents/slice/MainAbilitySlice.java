/*
 * Copyright (C) 2020-21 Application Library Engineering Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.applibgroup.codelabs.intents.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.utils.IntentConstants;
import ohos.utils.net.Uri;
import org.applibgroup.codelabs.intents.ResourceTable;
import org.applibgroup.codelabs.intents.serialization.Book;

/*
 * MainAbilitySlice
 *
 * @author Kanak Sony
 * @version 1.0
 */
public class MainAbilitySlice extends AbilitySlice {

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        (findComponentById(ResourceTable.Id_startNewActivity))
                .setClickedListener(component -> startNewAbilityIntent());

        (findComponentById(ResourceTable.Id_launchMap))
                .setClickedListener(component -> showMap());

        (findComponentById(ResourceTable.Id_dialCall))
                .setClickedListener(component -> dialPhone());

        (findComponentById(ResourceTable.Id_launchBrowser))
                .setClickedListener(component -> launchBrowser());

        (findComponentById(ResourceTable.Id_chooseFile))
                .setClickedListener(component -> chooseFile());

    }

    /**
     * Method to start a new Ability using Intent
     * It demonstrates explicit intent
     */
    private void startNewAbilityIntent() {
        Intent intent = new Intent();
        // Use the OperationBuilder class of Intent to construct an Operation
        // object and set the deviceId (left empty if a local ability is required),
        // bundleName, and abilityName attributes for the object.
        Operation operation = new Intent.OperationBuilder()
                .withDeviceId("")
                .withBundleName("org.applibgroup.codelabs.intents")
                .withAbilityName("org.applibgroup.codelabs.intents.SerializedDataAbility")
                .build();

        // Set the created Operation object to the Intent as its operation attribute.
        intent.setOperation(operation);
        Book book = new Book();
        book.setAuthor("Robert Cecil Martin");
        book.setBookName("Clean Code");
        book.setPublishTime(123456);
        intent.setParam("book", book);
        startAbility(intent);
    }

    /**
     * Method to invoke File Chooser functionality using Intent
     * It demonstrates implicit intent
     */
    private void chooseFile() {
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withAction(IntentConstants.ACTION_CHOOSE)
                .build();
        intent.setOperation(operation);
        startAbility(intent);
    }

    /**
     * Method to launch Browser using Intent
     * It demonstrates implicit intent
     */
    private void launchBrowser() {
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withUri(Uri.parse("https://developer.huawei.com/consumer/en/"))
                .withAction(IntentConstants.ACTION_SEARCH)
                .build();
        intent.setOperation(operation);
        startAbility(intent);
    }

    /**
     * Method to show Map using Intent
     * It demonstrates implicit intent
     */
    private void showMap() {
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withUri(Uri.parse("https://www.bing.com/maps?osid=2a30d810-1bf3-4f35-8bdd-beec763d31fc&cp=35.97007~120.0761&lvl=15&imgid=0a554701-4e57-4140-baea-b6a5a95c65ed&v=2&sV=2&form=S00027"))//https://goo.gl/maps/H4TX1F6Phps2j4dt5"))//https://maps.google.com/?q=40.431908,116.570374"))//26.988300,75.860001"))
                .withAction(IntentConstants.ACTION_SEARCH)
                .build();
        intent.setOperation(operation);
        startAbility(intent);
    }

    /**
     * Method to invoke Dial functionality using Intent
     * It demonstrates implicit intent
     */
    private void dialPhone() {
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withAction(IntentConstants.ACTION_DIAL)
                .build();
        intent.setOperation(operation);
        startAbility(intent);
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
