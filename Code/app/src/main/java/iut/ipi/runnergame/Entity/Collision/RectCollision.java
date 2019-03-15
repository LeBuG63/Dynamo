package iut.ipi.runnergame.Entity.Collision;

import android.content.Context;

public class RectCollision {
    public float left;
    public float top;
    public float width;
    public float height;

    public RectCollision(float left, float top, float width, float height) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }

    public void set(float x, float y, float w, float h) {
        left = x;
        top = y;
        width = w ;
        height = h;
    }
}