package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.asteroidsystem.AsteroidControlSystem;
import dk.sdu.mmmi.cbse.asteroidsystem.AsteroidPlugin;
import dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;
import dk.sdu.mmmi.cbse.bulletsystem.BulletPlugin;
import dk.sdu.mmmi.cbse.collisiondetectionsystem.CollisionDetection;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.enemysystem.EnemyControlSystem;
import dk.sdu.mmmi.cbse.lifeprocessersystem.LifeProcesser;
import dk.sdu.mmmi.cbse.managers.GameInputProcessor;
import dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
import dk.sdu.mmmi.cbse.playersystem.PlayerPlugin;
import dk.sdu.mmmi.cbse.enemysystem.EnemyPlugin;

import java.util.ArrayList;
import java.util.List;

public class Game implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;

    private final GameData gameData = new GameData();
    private List<IEntityProcessingService> entityProcessors = new ArrayList<>();
    private List<IPostEntityProcessingService> entityPostProcessors = new ArrayList<>();
    private List<IGamePluginService> entityPlugins = new ArrayList<>();
    private World world = new World();

    @Override
    public void create() {

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        IGamePluginService playerPlugin = new PlayerPlugin();
        IGamePluginService enemyPlugin = new EnemyPlugin();
        IGamePluginService asteroidPlugin = new AsteroidPlugin();
        IGamePluginService bulletPlugin = new BulletPlugin();

        IEntityProcessingService playerProcess = new PlayerControlSystem();
        IEntityProcessingService enemyProcess = new EnemyControlSystem();
        IEntityProcessingService asteroidProcess = new AsteroidControlSystem();
        IEntityProcessingService bulletProcess = new BulletControlSystem();

        entityPostProcessors.add(new CollisionDetection());
        entityPostProcessors.add(new LifeProcesser());

        entityPlugins.add(playerPlugin);
        entityPlugins.add(enemyPlugin);
        entityPlugins.add(bulletPlugin);
        entityPlugins.add(asteroidPlugin);

        entityProcessors.add(playerProcess);
        entityProcessors.add(bulletProcess);
        entityProcessors.add(enemyProcess);
        entityProcessors.add(asteroidProcess);
        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : entityPlugins) {
            iGamePlugin.start(gameData, world);
        }
    }

    @Override
    public void render() {

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

        gameData.getKeys().update();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : entityProcessors) {
            entityProcessorService.process(gameData, world);
        }

        for(IPostEntityProcessingService postEntityProcessingService : entityPostProcessors) {
            postEntityProcessingService.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {

            sr.setColor(1, 1, 1, 1);

            sr.begin(ShapeRenderer.ShapeType.Line);

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();

            for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++) {
                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }

            sr.end();
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
    public void dispose() {
    }
}