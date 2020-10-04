package com.NV.simulation.graphics;

class GraphicSettings {
    public static final int xStartOffset = 0; // offsets the entire field to the right
    public static final int yStartOffset = 20; // offsets the entire fiels downwards

    public final static double r = 32; // the inner radius from hexagon center to outer corner
    public final static double n = Math.sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis
    public final static double TILE_HEIGHT = 2 * r;
    public final static double TILE_WIDTH = 2 * n;
}
