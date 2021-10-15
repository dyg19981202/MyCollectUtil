package cn.dfordog.baseretrofit.ui;

import java.io.Serializable;

public class SelectBean implements Serializable {
    private String title;
    private String num;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
