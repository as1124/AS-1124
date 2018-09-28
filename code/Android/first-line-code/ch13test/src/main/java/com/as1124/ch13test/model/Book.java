package com.as1124.ch13test.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author as-1124
 */
public class Book implements Parcelable {

    private String name;

    private int price;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static final Parcelable.Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            Book book = new Book();
            book.setName(source.readString());
            book.setPrice(source.readInt());
            return book;
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
