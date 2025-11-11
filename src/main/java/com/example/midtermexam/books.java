package com.example.midtermexam;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class books implements Parcelable {
     static String NameofBook;
     static String genre;

    protected books(Parcel in) {
    }

    public static final Creator<books> CREATOR = new Creator<books>() {
        @Override
        public books createFromParcel(Parcel in) {
            return new books(in);
        }

        @Override
        public books[] newArray(int size) {
            return new books[size];
        }
    };

    public static String getNameofBook() {
        return NameofBook;
    }


    public static void setNameofBook(String nameofBook) {
        NameofBook = nameofBook;
    }

    public static String getGenre() {
        return genre;
    }

    public static void setGenre(String genre) {
        books.genre = genre;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    public books() {

    }

}
