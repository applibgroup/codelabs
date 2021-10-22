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

package org.applibgroup.codelabs.intents.serialization;

import ohos.utils.Parcel;
import ohos.utils.Sequenceable;

/*
 * Book implements Sequenceable
 * @author Kanak Sony
 * @version 1.0
 */

public class Book implements Sequenceable {
    private String bookName;
    private String author;
    private int publishTime;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(int publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public boolean marshalling(Parcel outParcel) {
        return outParcel.writeString(bookName)
                && outParcel.writeString(author)
                && outParcel.writeInt(publishTime);
    }

    @Override
    public boolean unmarshalling(Parcel inParcel) {
        setBookName(inParcel.readString());
        setAuthor(inParcel.readString());
        setPublishTime(inParcel.readInt());
        return true;
    }

    public static final Sequenceable.Producer<Book> PRODUCER = new Producer<Book>() {
        public Book createFromParcel(Parcel inParcel) {
            Book bookObject = new Book();
            bookObject.unmarshalling(inParcel);
            return bookObject;
        }
    };
}
