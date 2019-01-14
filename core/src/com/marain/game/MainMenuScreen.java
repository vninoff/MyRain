package com.marain.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;


// Переделали классы, добавили главное меню.
public class MainMenuScreen implements Screen{

    final Drop game;
    OrthographicCamera camera;

    // Конструктор класса.
    public MainMenuScreen(final Drop gam) {
        this.game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 400);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Очищаем экран.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Обновляем камеру.
        camera.update();
        // использовать система координат камеры
        game.batch.setProjectionMatrix(camera.combined);

        // Рисует серию изображений.
        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Drop!", 100, 150);
        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);

        game.batch.end();
        // Проверяем было ли прикосновение к экрану.
        // И если было, то запускаем экран игры и освобождаем ресурсы экрана меню.
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
