package com.hongsam.famstrory.data;

public class KeywordItem {
    private String keyword;
    private boolean isClick;

    public KeywordItem() { }

    public KeywordItem(String keyword) {
        this.keyword = keyword;
        isClick = false;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public boolean getIsClick() {
        return isClick;
    }

    public void setIsClick(boolean click) {
        isClick = click;
    }
}
