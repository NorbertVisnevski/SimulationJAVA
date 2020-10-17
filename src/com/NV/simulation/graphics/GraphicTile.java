package com.NV.simulation.graphics;

import com.NV.simulation.MasterData;
import com.NV.simulation.map.Tile;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class GraphicTile extends Polygon {
    private Tile tile;

    public GraphicTile(Tile tile)
    {
        this.tile = tile;
        double x = tile.getPosition().x * GraphicSettings.TILE_WIDTH + (tile.getPosition().y % 2) * GraphicSettings.n + GraphicSettings.xStartOffset;
        double y = tile.getPosition().y * GraphicSettings.TILE_HEIGHT * 0.75 + GraphicSettings.yStartOffset;
        setPosition(x,y);
        setStrokeWidth(1);
        setStroke(Color.BLACK);
        updateToolTip();
        if(MasterData.tileEditController != null)
        {
            setOnMouseClicked(e->MasterData.tileEditController.setTile(tile, this));
        }
    }
    public GraphicTile(Tile tile, double x, double y)
    {
        this.tile = tile;
        setPosition(x,y);
        setStrokeWidth(1);
        setStroke(Color.BLACK);
        switch (tile.getTerrainType()) {
            case Tile.TerrainTypes.PLANES:
                setFill(new ImagePattern(TextureStorage.grassland, 0, 0, 1, 1, true));
                break;
            case Tile.TerrainTypes.WATER:
                setFill(new ImagePattern(TextureStorage.water, 0, 0, 1, 1, true));
                break;
            case Tile.TerrainTypes.MOUNTAINS:
                setFill(new ImagePattern(TextureStorage.mountain, 0, 0, 1, 1, true));
                break;
            case Tile.TerrainTypes.DESERT:
                setFill(new ImagePattern(TextureStorage.desert, 0, 0, 1, 1, true));
                break;
            case Tile.TerrainTypes.FOREST:
                setFill(new ImagePattern(TextureStorage.forest, 0, 0, 1, 1, true));
                break;
            case Tile.TerrainTypes.MARSH:
                setFill(new ImagePattern(TextureStorage.marsh, 0, 0, 1, 1, true));
                break;
            case Tile.TerrainTypes.FROZEN_WASTELAND:
                setFill(new ImagePattern(TextureStorage.frozenwasteland, 0, 0, 1, 1, true));
                break;
            default:
                setFill(Color.DEEPPINK);
        }
    }

    private void setPosition(double x, double y)
    {
        getPoints().addAll(
                x, y,
                x, y + GraphicSettings.r,
                x + GraphicSettings.n, y + GraphicSettings.r * 1.5,
                x + GraphicSettings.TILE_WIDTH, y + GraphicSettings.r,
                x + GraphicSettings.TILE_WIDTH, y,
                x + GraphicSettings.n, y - GraphicSettings.r * 0.5
        );
    }
    public void updateToolTip()
    {
        MasterData.stringBuilder.setLength(0);
        MasterData.stringBuilder.append("Tile\n");

        switch (tile.getTerrainType()) {
            case Tile.TerrainTypes.PLANES:
                setFill(new ImagePattern(TextureStorage.grassland,0,0,1,1,true));
                MasterData.stringBuilder.append("Type: Planes\n");
                break;
            case Tile.TerrainTypes.WATER:
                setFill(new ImagePattern(TextureStorage.water,0,0,1,1,true));
                MasterData.stringBuilder.append("Type: Water\n");
                break;
            case Tile.TerrainTypes.MOUNTAINS:
                setFill(new ImagePattern(TextureStorage.mountain,0,0,1,1,true));
                MasterData.stringBuilder.append("Type: Mountains\n");
                break;
            case Tile.TerrainTypes.DESERT:
                setFill(new ImagePattern(TextureStorage.desert,0,0,1,1,true));
                MasterData.stringBuilder.append("Type: Desert\n");
                break;
            case Tile.TerrainTypes.FOREST:
                setFill(new ImagePattern(TextureStorage.forest,0,0,1,1,true));
                MasterData.stringBuilder.append("Type: Forest\n");
                break;
            case Tile.TerrainTypes.MARSH:
                setFill(new ImagePattern(TextureStorage.marsh,0,0,1,1,true));
                MasterData.stringBuilder.append("Type: Marshland\n");
                break;
            case Tile.TerrainTypes.FROZEN_WASTELAND:
                setFill(new ImagePattern(TextureStorage.frozenwasteland,0,0,1,1,true));
                MasterData.stringBuilder.append("Type: Frozen wasteland\n");
                break;
            default:
                setFill(Color.DEEPPINK);
                MasterData.stringBuilder.append("Type not found\n");
        }
        if(tile.isImpassible())
        {
            MasterData.stringBuilder.append("Impassible terrain\n");
        }
        else
        {
            MasterData.stringBuilder.append("Travel difficulty: ");
            MasterData.stringBuilder.append(String.format("%.02f",tile.getTravelDifficulty()));
            MasterData.stringBuilder.append("\n");
        }
        MasterData.stringBuilder.append("Nutritious content: ");
        MasterData.stringBuilder.append(String.format("%.02f",tile.getNutritionContent()));
        MasterData.stringBuilder.append("\n");

        Tooltip.install(this, new Tooltip(MasterData.stringBuilder.toString()));
    }

    public void updateGraphics()
    {
        updateToolTip();
    }

    public void update(Tile tile)
    {
        this.tile = tile;
        updateGraphics();
    }
}
