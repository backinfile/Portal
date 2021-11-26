package com.backinfile.portal;

import com.alibaba.fastjson.JSONObject;
import com.backinfile.portal.view.actor.CardSize;
import com.backinfile.support.FontCharacterCollection;
import com.backinfile.support.reflection.Reflections;
import com.backinfile.support.reflection.Timing;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Res {
    public static TextureRegionDrawable TEX_BLACK;
    public static TextureRegionDrawable TEX_WHITE;
    public static TextureRegionDrawable TEX_GRAY;
    public static TextureRegionDrawable TEX_DARK_GRAY;
    public static TextureRegionDrawable TEX_LIGHT_GRAY;
    public static TextureRegionDrawable TEX_HALF_BLACK;
    public static TextureRegionDrawable TEX_BLACK_MASK;
    public static TextureRegionDrawable EMPTY_DRAWABLE;

    public static BitmapFont DefaultFontSmall;
    public static BitmapFont DefaultFontLarge;
    public static BitmapFont DefaultFont;
    public static BitmapFont DefaultFontLargeLarge;

    public static final String CLASSES_FILE_PATH = "data/classes.txt";

    private static final FontCharacterCollection fontCharacterCollection = new FontCharacterCollection();
    private static final Map<String, Texture> textureMap = new HashMap<>(); // path->image
    private static final Map<LocalString.LocalImagePathString, TextureRegionDrawable> cardImageMap = new HashMap<>();


    private static final Set<Texture> textureToDispose = new HashSet<>();

    public static void init() {
        String localStringConf = Gdx.files.internal("local/zh.json").readString("utf8");
        fontCharacterCollection.put(localStringConf);
        LocalString.init(localStringConf);

        initImage();
        initFont(); // 一定要在所有string加载后执行

        initReflectionFile();
    }

    private static void initReflectionFile() {
        if (!Settings.inDev()) {
            return;
        }

        String path = "android/assets/" + CLASSES_FILE_PATH;
        Gdx.files.local(path).writeString(Reflections.getAllClassName(Settings.PACKAGE_NAME), false);
    }


    public static TextureRegionDrawable getTexture(LocalString.LocalImagePathString imagePathString) {
        if (cardImageMap.containsKey(imagePathString)) {
            return cardImageMap.get(imagePathString);
        }
        Log.res.warn("missing texture for {}", JSONObject.toJSONString(imagePathString));
        return EMPTY_DRAWABLE;
    }

    public static Sprite newSprite(LocalString.LocalImagePathString imagePathString) {
        return new Sprite(getTexture(imagePathString).getRegion());
    }

    private static void initImage() {
        TEX_WHITE = buildTextureFromPixmap(newColorPixmap(8, 8, Color.WHITE));
        TEX_BLACK = buildTextureFromPixmap(newColorPixmap(8, 8, Color.BLACK));
        TEX_GRAY = buildTextureFromPixmap(newColorPixmap(8, 8, Color.GRAY));
        TEX_DARK_GRAY = buildTextureFromPixmap(newColorPixmap(8, 8, Color.DARK_GRAY));
        TEX_LIGHT_GRAY = buildTextureFromPixmap(newColorPixmap(8, 8, Color.LIGHT_GRAY));
        TEX_HALF_BLACK = buildTextureFromPixmap(newColorPixmap(8, 8, new Color(0, 0, 0, 0.5f)));
        TEX_BLACK_MASK = buildTextureFromPixmap(newColorPixmap(8, 8, new Color(0, 0, 0, 0.3f)));
        EMPTY_DRAWABLE = TEX_GRAY;


        // 加载所有texture
        for (LocalString.LocalImagePathString imageString : LocalString.getAllImagePathStrings()) {
            String path = imageString.path;
            if (!textureMap.containsKey(path)) {
                Texture texture = null;
                try {
                    texture = new Texture(path);
                } catch (Exception e) {
                    Log.res.warn("load texture: {} error", path);
                    texture = TEX_GRAY.getRegion().getTexture();
                }
                texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                textureMap.put(path, texture);
            }
        }
        for (LocalString.LocalImagePathString imageString : LocalString.getAllImagePathStrings()) {
            if (cardImageMap.containsKey(imageString)) {
                continue;
            }
            Texture texture = textureMap.get(imageString.path);
            int[] slice = imageString.slice;
            if (slice != null && slice.length == 4) {
                int dw = texture.getWidth() / slice[0];
                int dh = texture.getHeight() / slice[1];
                TextureRegion textureRegion = new TextureRegion(texture, dw * slice[2], dh * slice[3], dw, dh);
                cardImageMap.put(imageString, new TextureRegionDrawable(textureRegion));
            } else {
                cardImageMap.put(imageString, new TextureRegionDrawable(texture));
            }
        }
        Log.res.info("load texture {}", textureMap.size());
    }

    private static Pixmap newColorPixmap(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        return pixmap;
    }

    private static TextureRegionDrawable buildTextureFromPixmap(Pixmap pixmap) {
        Texture texture = new Texture(pixmap, true);
        TextureRegion region = new TextureRegion(texture);
        pixmap.dispose();
        textureToDispose.add(texture);
        return new TextureRegionDrawable(region);
    }

    @Timing
    private static void initFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/SourceHanSans-Medium.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontCharacterCollection.put(FreeTypeFontGenerator.DEFAULT_CHARS);
        parameter.characters = fontCharacterCollection.getAll();
        parameter.borderWidth = 0f;
        parameter.shadowColor = new Color(0, 0, 0, 0.25f);
        parameter.shadowOffsetX = 2;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;


//        parameter.size = (int) (fontSize * ScreenSize.CARD_SMALL_SCALE);
//        DefaultFontSmall = generator.generateFont(parameter);
        parameter.size = (int) (ScreenSize.FONT_DEFAULT_SIZE);
        DefaultFont = generator.generateFont(parameter);
//        parameter.size = (int) (fontSize * ScreenSize.CARD_LARGE_SCALE);
//        DefaultFontLarge = generator.generateFont(parameter);
//        parameter.size = (int) (fontSize * ScreenSize.CARD_LARGE_LARGE_SCALE);
//        DefaultFontLargeLarge = generator.generateFont(parameter);
        generator.dispose();
    }

    public static BitmapFont getNearBitmapFont(CardSize cardSize) {
        switch (cardSize) {
            case Normal:
                return DefaultFont;
            case Small:
                return DefaultFontSmall;
            case Large:
                return DefaultFontLarge;
            case LargeLarge:
                return DefaultFontLargeLarge;
        }
        return DefaultFont;
    }


    public static void dispose() {
        for (Texture texture : textureMap.values()) {
            texture.dispose();
        }
        for (Texture texture : textureToDispose) {
            texture.dispose();
        }
        if (DefaultFontSmall != null) {
            DefaultFontSmall.dispose();
        }
        if (DefaultFont != null) {
            DefaultFont.dispose();
        }
        if (DefaultFontLarge != null) {
            DefaultFontLarge.dispose();
        }
        if (DefaultFontLargeLarge != null) {
            DefaultFontLargeLarge.dispose();
        }
    }
}
