package com.marain.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Drop extends Game {

    SpriteBatch batch; // для отображения текстур
    BitmapFont font; // для отображения шрифтов

    @Override
    public void create() {
        // Создаем объекты для текстур и шрифтов.
        batch = new SpriteBatch();
        font = new BitmapFont();

        // Устанавливаем экран игры.
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
    }
}
