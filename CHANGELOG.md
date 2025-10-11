# Changelog

Todos los cambios importantes en este proyecto serán documentados en este archivo.

El formato esta basado en [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
y este proyecto adhiere a [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.4.6] - 2025-10-11

### Added
- Clase ScreenMenus que comparte logica para los menus.

### Changed
- Todo el codigo se reviso y cambio para eliminar redundancias, evitar errores y refactorizar.

## [0.4.5] - 2025-10-10

### Added
- Movimientos Melee.
- Movimiento melee Arañazo.
- Logica de ejecucion de distintos tipos de movimientos.

### Changed
- Fix de deteccion de colisiones de proyectiles.
- Fix bug movimiento proyectiles contra bordes del mapa.

## [0.4.4] - 2025-10-07

### Added
- Proyectiles balisticos y granadas.
- Gestor fisicas.

### Changed
- Raycast centralizado en gestor fisicas.
- Fix bug de la gestion de colisiones.

## [0.4.4] - 2025-10-04

### Added
- Radio de expansion para proyectiles.
- Hormiga Exploradora.
- Debug radio de afeccion del proyectil.

### Changed
- Forma en la que los proyectiles detonan.

## [0.4.3] - 2025-09-29

### Added
- Radio destruccion para proyectiles.
- Destruccion del mapa
- Movimiento sobre superficies inclinadas.
- Barra de carga para lanzar proyectiles.

## [0.4.2] - 2025-09-27

### Added
- Fuentes para Contador y Vida de los personajes.
- Clase para gestionar recursos globales.

### Changed
- Antigua fuente.
- SpriteRenderer y Batch ahora gestionados por recursosGlobales.

## [0.4.1] - 2025-09-21

### Changed
- Fix bugs implementacion gravedad.
- Interaccion proyectiles mapa.

## [0.4.0] - 2025-09-21

### Added
- Fisicas y gravedad.
- Mapa y su logica.
- Integracion de ambos en el resto del proyecto.

### Changed
- Clase personaje pasa a funcionar con fisicas e interactuar con mapa.
- Proyectiles pasa a funcionar con fisicas e interactuar con mapa.

## [0.3.9] - 2025-09-19

### Added
- Gestor de juego.
- Hormiga Obrera.
- Game over screen.

### Changed
- Clase personaje pasa a ser abstracta y heredara a los personajes particulares.
- Refactorizacion de GameScreen para funcionar con GestorJuego.
- Separacion de logica controles de personaje.
- Fix de errores en el cambio de turnos entre personajes y jugadores.

## [0.3.8] - 2025-09-18

### Added
- Gestor de Screen y Assets

### Changed
- Clase boton pasa a ser un enum con las regiones de las imagenes ya definidas.
- Todas las imagenes pasan a ser gestionadas con assetManager.

## [0.3.7] - 2025-09-18

### Added
- Raycast para evitar saltos por velocidad.
- Limites del borde.

### Changed
- Logica de movimiento para funcionar con raycast.

## [0.3.6] - 2025-09-10

### Added
- Clase Movimiento y Movimiento Rango con interfaz de movimiento.
- Creacion de proyectil Roca y movimiento LanzaRoca.

### Changed
- Reordenamiento de clases en paquetes.
- Optimizacion y escalamiento de codigo.
- Fix de bugs y malos funcionamientos.

## [0.3.5] - 2025-09-10

### Added
- Metodo gestionar proyectiles

### Changed
- Refactorizacion de codigo.
- Implementacion de camara que sigue al personaje.

## [0.3.4] - 2025-08-03

### Added
- Metodo en la clase utiles para descomponer atlas.

### Changed
- Refactorizacion de codigo.
- Reajuste de la clase fabricaBotones para que funcione con atlas.

## [0.3.3] - 2025-08-03

### Added
- Hitbox funcional entre entidades.
- Fondo de pantalla para el menu screen, opciones y juego.

### Changed
- Descripccion detallada de las dificultades de esta version en la wiki.
- Link del video demostrativo en el README.

## [0.3.2] - 2025-08-03

### Changed
- Reorganizacion de las clases y paquetes del proyecto.
- Factorizacion del codigo de las clases.

## [0.3.1] - 2025-08-02

### Added
- 2 personajes jugables.
- Logica de turnos.
- Miras para apuntar.
- Disparar proyectiles con espacio.

## [0.3.0] - 2025-07-31

### Added
- Menu principal con botones funcionales.
- Nueva pantalla de Opciones botones funcionales.
- Gestion de pantallas entre menu principal y opciones.
- Sprites temporales para los botones con eventos asignados.

### Changed
- Descripccion detallada de las dificultades de esta version en la wiki.

## [0.2.1] - 2025-07-02

### Changed 
- Correcciones de escritura del README y CHANGELOG.
- Nueva wiki utilizando la propuesta del proyecto. 

## [0.2.0] - 2025-06-03

### Added
- Sección "Estado actual del proyecto" al README.
- Instrucciones detalladas para clonar, compilar y ejecutar el proyecto.
- Enlace a la wiki del proyecto.
- Wiki del proyecto publicada con propuesta y documentación básica.

### Changed
- Redacción del README mejorada para cumplir con estándares.
- Estructura general del README reorganizada y corregida.

## [0.1.0] - 2025-05-26

### Added 
- Archivo README.md inicial.
- Archivo .gitignore.
- Archivo CHANGELOG.md.
- Wiki inicial del proyecto.

### Changed 
- Corrección de errores en el código base.
- Refactor inicial del proyecto.
- Mejora de la estructura del README.

## [0.0.0] - 2025-04-19

### Added 
- Creación inicial del proyecto.
