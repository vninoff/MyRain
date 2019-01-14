package com.marain.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

public class MyRain extends ApplicationAdapter {
    OrthographicCamera camera;
	SpriteBatch batch;
	Texture dropImage;
	Texture bucketImage;
	Sound dropSound;
	Music rainMusic;
    Rectangle bucket;
    Vector3 touchPos;
    Array<Rectangle> raindrops;
    long lastDropTime;
	
	@Override
	public void create () {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();

		touchPos = new Vector3();

		dropImage = new Texture("droplet.png");
        bucketImage = new Texture("bucket.png");

        dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3"));

        // Запускаем и зацикливаем фоновую музыку.
        rainMusic.setLooping(true);
        rainMusic.play();

        // Создаем прямоугольник для Ведра.
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;

        // Создаем экземпляр массива капель и порождаем первую каплю.
        raindrops = new Array<Rectangle>();
        spawnRaindrop();


	}

	// Вспомогательная функция для отображения капли в верхней позиции.
	private void spawnRaindrop() {
	    Rectangle raindrop = new Rectangle();
	    raindrop.x = MathUtils.random(0, 800-64);
	    raindrop.y = 480;
	    raindrop.width = 64;
	    raindrop.height = 64;
	    raindrops.add(raindrop);
	    lastDropTime = TimeUtils.nanoTime();
    }

	@Override
	public void render () {
	    // Очищаем экран.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



		// Обновляем камеру.
		camera.update();
        // использовать система координат камеры
		batch.setProjectionMatrix(camera.combined);

        // Рисует серию изображений.
		batch.begin();

		batch.draw(bucketImage, bucket.x, bucket.y); // отображаем ведро


        for (Rectangle raindrop: raindrops) {
            batch.draw(dropImage, raindrop.x, raindrop.y); // отображает капли
        }

		batch.end();


		// Заправшиваем координаты нажатия мыши / или пальца.
        if(Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = (int) (touchPos.x - 64 / 2);
        }

        // Запрашиваем нажатие клавиш на клавиатуре.
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > 800 - 64) bucket.x = 800 - 64;

        // Проверяем сколько времени прошло с прошлой капли и если нужно, то создаем новую каплю.
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

        // Сделаем, чтобы капли двигались с постоянной скоростью 200пикселей в секунду.
        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0) iter.remove();

            // Проверяем пересекает ли капля ведро
            if (raindrop.overlaps(bucket)) {
                dropSound.play();
                iter.remove();
            }
        }
	}
	
	@Override
	public void dispose () {
		super.dispose();

		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();

		batch.dispose();
	}
}
