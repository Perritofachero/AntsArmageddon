# Changelog

Todos los cambios importantes en este proyecto serán documentados en este archivo.

El formato esta basado en [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
y este proyecto adhiere a [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.5.4] - 2025-10-29

### Changed
- Se cambiaron las clases que no deben ser extendidas a final.
- Se cambiaron las clases que no deben ser instancidas, y pasan a tener un constructor privado.
- Se pusieron los metodos de las clases padres que no deben ser reescritos como final.
- Se reescribieron variables y metodos para cumplir con los estandares.
- Se agregaron limites de velocidad, salto, tamaño, etc al juego.

## [0.5.3] - 2025-10-28

# Added
- Pantalla de pausa funcional.
- Pantalla de game over screen funcional.
- Nuevos eventos en eventos boton.

### Changed
- Fix de bug de cuando un personaje se tocaba un borde se desactivava y no terminaba el turno.
- Utilizamos un show condicional en el game screen para tener una pausa real.
- Agregamos un mensaje de ganador al game over y un boton volver, para que se pueda seguir en el juego.
- Screen manager para que funcione con la pausa.
- Correccion de metodos y refactorizacion.
- Fix de bug de salto de personajes.
- Correcta asignacion de pesos a los personajes.

## [0.5.2] - 2025-10-26

# Added
- Todas las animaciones de los personajes.
- Distintos sonidos para los botones.
- Implementacion de un opciones screen funcional.
- Gestor sonido para gestionar los sonidos del juego.
- Clase explosion para reproducir una animacion cuando los proyectiles explotan.
- Animacion del power up vida.
- Tutorial screen con las reglas del juego.
- Musica de fondo.
- Sonido de explosion cuando un proyectil impacta o explota.

### Changed
- Los personajes ahora tienen peso para recibir menos o mas knockback.
- Los personajes tienen estadisticas balanceadas para incentivar sus usos.
- Se cambiaron algunos sonidos de botones.

## [0.5.1] - 2025-10-23

# Added
- Implementacion de animaciones en distintas clases.
- Clase Gestor de animaciones para refactorizar todas las clases que utilicen animaciones.
- Clase path manager para rutas del proyecto.
- Movimiento pasar turno para pasar un turno.
- Enum anidado en la clase personaje para sus distintos estados.
- HUD para los movimientos seleccionados del personaje.
- Distinto colores de vida para diferenciar los personajes de distintos jugadores.
- Sistema de juego totalmente implementado, tanto turnos como conteo de personajes por jugador para determinar ganador.
- Fix de bug para cuando no se escogen personajes, o se escoge una para un jugador y no para otro randomizando los personajes
en ambos casos.

### Changed
- Clase personaje para evitar que pueda realizar acciones mientras hace otras, como disparar mientras salta entre otras.
- Clase gestor turno para implementar el sistema de turnos del worms.
- Clase gestor juego para implementar el sistema de turnos del gestor turno.
- Configuracion partida para evitar bugs por selecciones de personajes.
- Screens para que la camara este seteada en projectionMatrix, para evitar desfases con el fondo y otros errores.
- Sobrecarga de metodo ejecutar en movimientos rango y melee, para evitar reiterar codigo y mantener encapsulada su logica en otras clases.
- Metodo de la clase camara para seguir posiciones, asi puede seguir la posicion de los proyectiles.
- Clase gestor proyectiles para que contenga el metodo ultimo proyectil disponible asi la camara puede seguirlo.
- Clase jugador ahora contiene un atributo con un color, para mostrar en el hud de sus personajes.
- Clase personaje tiene un id del jugador al que pertenece, para mostrar su color en el hud e implementaciones de redes mas adelante.
- Fix de bugs en fisicas personaje de movimiento.
- Fix de bug de hitbox en clase entidad y proyectil. 
- Fix de bug de prioridad en el input processor de gamescreen.

## [0.5.0] - 2025-10-19

# Added
- Clase configuracion partida, para que pre game screen inicie las partidas segun su configuracion.
- Metodo para manejar el movimiento de las granadas con raycast.
- Metodo para crear botones ciclicos y "hormiga" en fabrica botones utilizando comsumer.

### Changed
- Movimiento granada ahora utiliza mover granada con raycast.
- Game screen y gestor juego ahora funcionan segun las configuraciones de pre game screen.
- Los personajes ahora tienen atributos de turno, para mas adelante iterrumpirlos o jugar con ellos para power ups.

## [0.4.10] - 2025-10-18

### Changed
- Fix de tropiezo cuando el personaje bajaba pendientes.
- Implementacion de metodos de accion en la clase personaje.
- Separacion de responsabilidad del procesar entrada del gestor juego.
- Limitacion de acciones del personaje en ciertas condiciones.
- Parametrizacion del knockback que causan los proyectiles.
- Randomizacion de los posbiles spawns de personajes y power ups.
- Separacion del render del movimiento melee a gestor juego.
- Separacion de barra de carga de personaje a HUD.
- Implementacion de area jugable, para que el personaje pueda caer del mapa.

## [0.4.9] - 2025-10-17

### Added
- Implementacion inicial del menu pre juego.
- Nuevos botones.

### Changed
- Fabrica botones ahora tiene una sobrecarga para pasar o no sonido.

## [0.4.8] - 2025-10-15

### Added
- Fisicas personaje.
- Fisicas de Knockback.
- Gestor de spawn.
- Power ups
- Spawn de personajes randomizado y power ups por turno

## [0.4.7] - 2025-10-14

### Added
- Power Ups.
- Power Up vida.
- Render Debug Hitboxes centralizado.

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
