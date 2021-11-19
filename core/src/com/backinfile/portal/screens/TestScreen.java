package com.backinfile.portal.screens;

import com.backinfile.gdxSupport.DefaultScreen;
import com.backinfile.support.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class TestScreen extends DefaultScreen {
    private PerspectiveCamera camera;
    private CameraInputController cameraInputController;
    private Environment environment;
    private ModelBuilder modelBuilder;
    private ModelBatch modelBatch;

    private Model model;
    private ModelInstance instance;
    private final ColorAttribute colorAttribute;

    public TestScreen() {
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(10, 10, 10);
        camera.lookAt(0, 0, 0);
        camera.update();

        ModelBuilder modelBuilder = new ModelBuilder();
//        model = modelBuilder.createBox(5f, 5f, 5f,
//                new Material(ColorAttribute.createDiffuse(Color.SKY)),
//                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
//        instance = new ModelInstance(model);


        modelBuilder.begin();
        modelBuilder.node();
        colorAttribute = ColorAttribute.createDiffuse(Color.SKY);
        {
            MeshPartBuilder meshPartBuilder = modelBuilder.part("up", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.GOLD)));
            meshPartBuilder.triangle(new Vector3(0, 1, 0), new Vector3(0, 1, 5), new Vector3(5, 1, 5));
        }
        {
            MeshPartBuilder meshPartBuilder = modelBuilder.part("up", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal, new Material(colorAttribute));
            meshPartBuilder.rect(Vector3.Zero, new Vector3(0, 0, 5), new Vector3(5, 0, 5), new Vector3(5, 0, 0), new Vector3(0, 1, 0));
        }
        model = modelBuilder.end();
        instance = new ModelInstance(model);


        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        modelBatch = new ModelBatch();
    }

    @Override
    public void show() {
        if (cameraInputController == null) {
            cameraInputController = new CameraInputController(camera);
        }

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(cameraInputController);
        inputMultiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    instance.materials.get(0).set(ColorAttribute.createDiffuse(Color.WHITE));
                    Log.game.info("asd");
                }
                return super.keyDown(keycode);
            }
        });
        Gdx.input.setInputProcessor(inputMultiplexer);
    }


    @Override
    public void fixRender() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(camera);
        modelBatch.render(instance, environment);
        modelBatch.end();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

    }

    @Override
    public void dispose() {
        model.dispose();
    }
}
