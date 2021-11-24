package com.backinfile.gdxSupport;

import com.backinfile.portal.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.Ray;

import java.util.List;

public class GdxUtils {
    public static final Matrix4 IdentityMatrix = new Matrix4();
    public static final float THRESHOLD = 0.01f;
    public static final float THRESHOLD2 = 0.001f;

    private static final GlyphLayout glyphLayoutTmp = new GlyphLayout();
    private static final Matrix4 matrix4Tmp = new Matrix4();
    private static final Vector2 rotatedVector2Tmp = new Vector2(0.0F, 0.0F);
    private static final Vector3 positionTmp = new Vector3();

    public static void renderRotatedText(Batch batch, BitmapFont font, String text, float orginX, float orginY, float x,
                                         float y, float angle) {

        // 局部旋转
        rotatedVector2Tmp.set(x, y);
        rotatedVector2Tmp.rotateDeg(angle);

        // 获取transform
        matrix4Tmp.setToRotation(0, 0, 1, angle);
        matrix4Tmp.trn(orginX + rotatedVector2Tmp.x, orginY + rotatedVector2Tmp.y, 0);
        renderRotatedText(batch, font, text, matrix4Tmp);
    }

    public static void renderRotatedText(Batch batch, BitmapFont font, String text, Matrix4 mat) {
        batch.setTransformMatrix(mat);
        // 计算文字长宽
        glyphLayoutTmp.setText(font, text);
        font.draw(batch, text, -glyphLayoutTmp.width / 2, -glyphLayoutTmp.height / 2);
        batch.setTransformMatrix(IdentityMatrix);
    }

    public ModelInstance getTouchedInstance(OrthographicCamera camera, List<ModelInstance> instanceList, float radius, int screenX, int screenY) {
        Ray ray = camera.getPickRay(screenX, screenY);
        ModelInstance curInstance = null;
        float distance = -1;

        for (ModelInstance instance : instanceList) {
            instance.transform.getTranslation(positionTmp);
            float dist2 = ray.origin.dst2(positionTmp);
            if (distance >= 0f && dist2 > distance)
                continue;

            if (Intersector.intersectRaySphere(ray, positionTmp, radius, null)) {
                curInstance = instance;
                distance = dist2;
            }
        }
        return curInstance;
    }

    public static float lerpStep(float from, float to) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float target = MathUtils.lerp(from, to, 0.1f);
        Log.game.info("from {} to {} result {}", from, to, target);
        if (Math.abs(to - target) < THRESHOLD) {
            return to;
        }
        return target;
    }

    public static boolean isSame(float a, float b) {
        return Math.abs(a - b) < THRESHOLD;
    }
}
