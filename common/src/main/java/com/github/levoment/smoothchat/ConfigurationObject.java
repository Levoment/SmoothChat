package com.github.levoment.smoothchat;

public class ConfigurationObject {
    public boolean SmoothChat;
    public float TransitionTimeFloat;
    public boolean LeftToRight;
    public boolean Upward;

    public ConfigurationObject() {
        this.SmoothChat = true;
        this.TransitionTimeFloat = 0.5f;
        this.Upward = true;
        this.LeftToRight = false;
    }
}
