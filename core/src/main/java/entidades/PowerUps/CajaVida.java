package entidades.PowerUps;

import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Texture;
import entidades.personajes.Personaje;
import managers.GestorAssets;
import utils.Constantes;

public class CajaVida extends PowerUp {

    public CajaVida(float x, float y, GestorColisiones gestorColisiones) {
        super(x, y, GestorAssets.get(Constantes.CAJA_VIDA, Texture.class), gestorColisiones);

    }

    @Override
    public void aplicarEfecto(Personaje personaje) {
        if (!activo) return;
        personaje.aumentarVida(25);
        desactivar();
    }
}
