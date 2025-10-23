package entidades.PowerUps;

import Gameplay.Gestores.GestorColisiones;
import Gameplay.Gestores.GestorRutas;
import com.badlogic.gdx.graphics.Texture;
import entidades.personajes.Personaje;
import managers.GestorAssets;

public class CajaVida extends PowerUp {

    public CajaVida(float x, float y, GestorColisiones gestorColisiones) {
        super(x, y, GestorAssets.get(GestorRutas.CAJA_VIDA, Texture.class), gestorColisiones);

    }

    @Override
    public void aplicarEfecto(Personaje personaje) {
        if (!activo) return;
        personaje.aumentarVida(25);
        desactivar();
    }
}
