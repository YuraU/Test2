package com.yura.test2.entity;

public class Table {
    public enum Type {
        Square, Round
    }

    public String ID;
    public String NAME;
    public int X;
    public int Y;
    public int WIDTH;
    public int HEIGHT;
    public int angle;
    public Type TYPE;

    public Table(Type type, String id, String name, int x, int y, int width, int height, int angle){
        this.TYPE = type;
        this.ID = id;
        this.NAME = name;
        this.X = x;
        this.Y = y;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.angle = angle;
    }
}
