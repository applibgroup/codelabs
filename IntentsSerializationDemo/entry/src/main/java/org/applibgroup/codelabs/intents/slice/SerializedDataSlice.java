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
import ohos.agp.components.Text;
import org.applibgroup.codelabs.intents.ResourceTable;
import org.applibgroup.codelabs.intents.serialization.Book;

/*
 * SerializedDataSlice
 * @author Kanak Sony
 * @version 1.0
 */
public class SerializedDataSlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_serialize);
        if (intent.hasParameter("book")) {
            Book book = intent.getSequenceableParam("book");
            ((Text) findComponentById(ResourceTable.Id_text_author)).
                    setText(book.getAuthor());
            ((Text) findComponentById(ResourceTable.Id_text_book)).
                    setText(book.getBookName());
            ((Text) findComponentById(ResourceTable.Id_text_pubtime)).
                    setText(book.getPublishTime() + "");
        }
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
