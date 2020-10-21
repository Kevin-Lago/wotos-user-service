package com.wotos.wotosuserservice.model;

public class LocalUserViewModel {

    private String username;
    private boolean dark_mode;
    public LocalUserViewModel(String username, boolean dark_mode) {
        this.username = username;
        this.dark_mode = dark_mode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isDark_mode() {
        return dark_mode;
    }

    public void setDark_mode(boolean dark_mode) {
        this.dark_mode = dark_mode;
    }
}
