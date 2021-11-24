package com.backinfile.portal.gen;

import com.backinfile.portal.Log;
import com.backinfile.portal.ScreenSize;
import com.backinfile.support.reflection.Timing;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;

public class GenImage {
    private static final String PATH_PREFIX = "android/assets/image/gen/";

    @Timing("gen image")
    public static void gen() {
        genCardBoarder(Color.WHITE, "cardBorder");
        genCardBoarder(Color.FIREBRICK, "cardBorderVirus");
        genCardBoarder(Color.BLUE, "cardBorderCommand");
        genCardBoarder(Color.LIGHT_GRAY, "cardBorderData");
        genCardBoarder(Color.ROYAL, "cardBorderPlugin");
        genCardBoarder(Color.SCARLET, "cardBorderError");
        genVirus();
        genCardDecorate();
    }

    private static void genCardBoarder(Color color, String name) {
        int width = ScreenSize.SCREEN_CARD_WIDTH;
        int height = ScreenSize.SCREEN_CARD_HEIGHT;
        int borderWidth = (int) (ScreenSize.SCREEN_CARD_WIDTH * ScreenSize.CARD_BOARD_WIDTH_RATE);
        int borderWidth2 = borderWidth * 2;

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setBlending(Pixmap.Blending.None);

        drawRoundCornerBorder(pixmap, color, width, height, borderWidth);
//        pixmap.setColor(Color.WHITE);
//        drawBorder(pixmap, width - borderWidth2, height - borderWidth2, borderWidth, borderWidth, borderWidth);

//        pixmap.setColor(Color.WHITE);
//        pixmap.fillRectangle(borderWidth2, height * 5 / 6, width / 10, borderWidth2);

        writeImageFile(pixmap, name);
        pixmap.dispose();
        Log.res.info("gen image: " + name);
    }

    private static void genVirus() {
        int width = ScreenSize.SCREEN_CARD_WIDTH;
        int height = ScreenSize.SCREEN_CARD_HEIGHT;
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.SKY);
        pixmap.fill();

        writeImageFile(pixmap, "virus");
        pixmap.dispose();
        Log.res.info("gen image: virus");
    }

    private static void genCardDecorate() {
        int size = ScreenSize.SCREEN_CARD_DECORATE_SIZE;

        Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0.5f, 0.5f, 0.5f, 0.5f));
        pixmap.fill();

        writeImageFile(pixmap, "cardDecorate");
        pixmap.dispose();
        Log.res.info("gen image: cardDecorate");
    }

    private static void drawRoundCornerBorder(Pixmap pixmap, Color color, int width, int height, int borderWidth) {
        pixmap.setColor(color);
        pixmap.fillRectangle(borderWidth, 0, width - borderWidth * 2, borderWidth);
        pixmap.fillRectangle(0, borderWidth, borderWidth, height - borderWidth * 2);
        pixmap.fillRectangle(width - borderWidth, borderWidth, borderWidth, height - borderWidth * 2);
        pixmap.fillRectangle(borderWidth, height - borderWidth, width - borderWidth * 2, borderWidth);
        pixmap.fillCircle(borderWidth, borderWidth, borderWidth);
        pixmap.fillCircle(width - borderWidth, borderWidth, borderWidth);
        pixmap.fillCircle(borderWidth, height - borderWidth, borderWidth);
        pixmap.fillCircle(width - borderWidth, height - borderWidth, borderWidth);
    }

    private static void drawBorder(Pixmap pixmap, int width, int height, int borderWidth, int offsetX, int offsetY) {
        pixmap.fillRectangle(offsetX, offsetY, width, borderWidth);
        pixmap.fillRectangle(offsetX, offsetY, borderWidth, height);
        pixmap.fillRectangle(width + offsetX - borderWidth, offsetY, borderWidth, height);
        pixmap.fillRectangle(offsetX, height - borderWidth + offsetY, width, borderWidth);
    }

    private static void fillPoly(Pixmap pixmap, int... poses) {
        int pointNumber = poses.length / 2;
        int startX = poses[poses.length - 2];
        int startY = poses[poses.length - 1];
        for (int i = 0; i < pointNumber - 3 + 1; i++) {
            int ax = poses[i * 2];
            int ay = poses[i * 2 + 1];
            int bx = poses[i * 2 + 2];
            int by = poses[i * 2 + 3];
            pixmap.fillTriangle(ax, ay, bx, by, startX, startY);
        }

    }

    private static Pixmap newColorPixmap(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        return pixmap;
    }

    private static void writeImageFile(Pixmap pixmap, String name) {
        PixmapIO.writePNG(Gdx.files.local(PATH_PREFIX + name + ".png"), pixmap);
    }
}
