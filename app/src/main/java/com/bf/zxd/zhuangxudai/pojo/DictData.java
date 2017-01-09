package com.bf.zxd.zhuangxudai.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by johe on 2017/1/9.
 */

public class DictData extends RealmObject implements Parcelable {
    @PrimaryKey
    private Integer dict_id=0;

    String dict_code;
    String dict_desc;

    public Integer getDict_id() {
        return dict_id;
    }

    public void setDict_id(Integer dict_id) {
        this.dict_id = dict_id;
    }

    public String getDict_code() {
        return dict_code;
    }

    public void setDict_code(String dict_code) {
        this.dict_code = dict_code;
    }

    public String getDict_desc() {
        return dict_desc;
    }

    public void setDict_desc(String dict_desc) {
        this.dict_desc = dict_desc;
    }

    @Override
    public String toString() {
        return "DictData{" +
                "dict_id=" + dict_id +
                ", dict_code='" + dict_code + '\'' +
                ", dict_desc='" + dict_desc + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.dict_id);
        dest.writeString(this.dict_code);
        dest.writeString(this.dict_desc);

    }

    public DictData() {
    }

    protected DictData(Parcel in) {
        this.dict_id = in.readInt();
        this.dict_code = in.readString();
        this.dict_desc = in.readString();

    }

    public static final Parcelable.Creator<DictData> CREATOR = new Parcelable.Creator<DictData>() {
        @Override
        public DictData createFromParcel(Parcel source) {
            return new DictData(source);
        }

        @Override
        public DictData[] newArray(int size) {
            return new DictData[size];
        }
    };

}
