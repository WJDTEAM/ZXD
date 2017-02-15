package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by johe on 2017/2/15.
 *  "logoImg": String 用户LOGO
 "nickname": String 联系方式
 "plrq": String 评论日期
 "content": String 评论内容

 */

public class Comments {
    String logoImg;
    String nickname;
    String plrq;
    String content;
    String phone;

    @Override
    public String toString() {
        return "Comments{" +
                "logoImg='" + logoImg + '\'' +
                ", nickname='" + nickname + '\'' +
                ", plrq='" + plrq + '\'' +
                ", content='" + content + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPlrq() {
        return plrq;
    }

    public void setPlrq(String plrq) {
        this.plrq = plrq;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
