package hud;

import Fisicas.Camara;
import Gameplay.Movimientos.Movimiento;
import Gameplay.Movimientos.MovimientoRango;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.viewport.Viewport;
import entidades.personajes.AtributosPersonaje.BarraCarga;
import entidades.personajes.Personaje;
import managers.GestorAssets;
import utils.Constantes;
import utils.RecursosGlobales;

public class Hud {
    private final BitmapFont fuenteContador, fuenteVida;
    private final GlyphLayout layout;

    public Hud() {
        this.fuenteContador = GestorAssets.get(Constantes.FONT_CONTADOR, BitmapFont.class);
        this.fuenteVida = GestorAssets.get(Constantes.FONT_VIDA, BitmapFont.class);
        this.layout = new GlyphLayout();
    }

    public void mostrarVida(Personaje personaje) {
        if (!personaje.getActivo()) return;

        String texto = String.valueOf(personaje.getVida());
        layout.setText(fuenteVida, texto);

        float posX = personaje.getX() + personaje.getSprite().getWidth() / 2f - layout.width / 2f;
        float posY = personaje.getY() + personaje.getSprite().getHeight() + 20f;
        float offset = 1f;

        fuenteVida.setColor(0, 0, 0, 1f);
        fuenteVida.draw(RecursosGlobales.batch, texto, posX - offset, posY - offset);
        fuenteVida.draw(RecursosGlobales.batch, texto, posX + offset, posY - offset);
        fuenteVida.draw(RecursosGlobales.batch, texto, posX - offset, posY + offset);
        fuenteVida.draw(RecursosGlobales.batch, texto, posX + offset, posY + offset);

        fuenteVida.setColor(1, 1, 1, 1f);
        fuenteVida.draw(RecursosGlobales.batch, texto, posX, posY);
    }

    public void mostrarContador(float contador, Camara camara) {
        String texto = String.format("%.2f", contador);
        layout.setText(fuenteContador, texto);

        OrthographicCamera camera = camara.getCamera();
        Viewport viewport = camara.getViewport();

        float camX = camera.position.x;
        float camY = camera.position.y;

        float offsetX = viewport.getWorldWidth() / 2f - 200f;
        float offsetY = viewport.getWorldHeight() / 2f - 50f;

        fuenteContador.draw(RecursosGlobales.batch, texto, camX + offsetX, camY + offsetY);
    }

    public void mostrarBarraCarga(Personaje personaje) {
        if (!personaje.getActivo() || !personaje.isDisparando()) return;

        Movimiento mov = personaje.getMovimientoSeleccionado();
        if (!(mov instanceof MovimientoRango)) return;

        BarraCarga barra = personaje.getBarraCarga();
        if (barra.getCargaActual() <= 0f) return;

        float x = personaje.getX();
        float y = personaje.getY() - 7f;
        float ancho = personaje.getSprite().getWidth();
        float alto = 5f;

        barra.render(x, y, ancho, alto);
    }

    public void dispose() { }
}
