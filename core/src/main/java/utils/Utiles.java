package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;

public class Utiles {

    public static void descomponerAtlas(String atlasPath, String outputFolder) {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(atlasPath));
        HashMap<Texture, Pixmap> pixmaps = new HashMap<>();

        for (TextureAtlas.AtlasRegion region : atlas.getRegions()) {
            Pixmap fullPixmap;

            if (pixmaps.containsKey(region.getTexture())) {
                fullPixmap = pixmaps.get(region.getTexture());
            } else {
                region.getTexture().getTextureData().prepare();
                fullPixmap = region.getTexture().getTextureData().consumePixmap();
                pixmaps.put(region.getTexture(), fullPixmap);
            }

            Pixmap pixmapRegion = new Pixmap(region.getRegionWidth(), region.getRegionHeight(), Pixmap.Format.RGBA8888);
            pixmapRegion.drawPixmap(fullPixmap,
                region.getRegionX(), region.getRegionY(),
                region.getRegionWidth(), region.getRegionHeight(),
                0, 0,
                region.getRegionWidth(), region.getRegionHeight()
            );

            PixmapIO.writePNG(Gdx.files.local(outputFolder + "/" + region.name + ".png"), pixmapRegion);
            pixmapRegion.dispose();
        }

        for (Pixmap p : pixmaps.values()) p.dispose();

        atlas.dispose();
        System.out.println("Atlas descompuesto correctamente");
    }



}
