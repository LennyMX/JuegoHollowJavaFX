package com.example.juegoplataformafxhollow;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.Scene;

import java.util.*;

public class Game {
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final int width;
    private final int height;

    private Jugador jugador;
    private List<Entidad> entidades;
    private List<Plataforma> plataformas;
    private Set<KeyCode> keys = new HashSet<>();
    private ArchivoJuego archivoJuego;

    private AnimationTimer loop;
    private double metaX;


    public Game(int width, int height) {
        this.width = width;
        this.height = height;
        this.canvas = new Canvas(width, height);
        this.gc = canvas.getGraphicsContext2D();
        init();
    }

    public Canvas getCanvas() { return canvas; }

    private void init() {
        archivoJuego = new ArchivoJuego("datos/progresos.txt");
        entidades = new ArrayList<>();
        plataformas = new ArrayList<>();

        // Jugador
        jugador = new Jugador(50, 450, 40, 60);
        entidades.add(jugador);
        
        Nivel2.DatosNivel n = Nivel2.crearNivel();
        plataformas.addAll(n.plataformas);
        entidades.addAll(n.entidades);
        entidades.add(n.meta);

        metaX = n.metaX;
        
        loop = new AnimationTimer() {
            private long last = 0;

            @Override
            public void handle(long now) {
                if (last == 0) last = now;
                double delta = (now - last) / 1e9;

                actualizar(delta);
                dibujar();
                last = now;
            }
        };

        // Cargar progreso previo
        try {
            ArchivoJuego.Progreso p = archivoJuego.cargar();
            if (p != null) {
                jugador.setPuntaje(p.puntaje);
                jugador.x = p.x;
                jugador.y = p.y;
            }
        } catch (Exception ignored) {}
    }

    public void setupInput(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            keys.add(e.getCode());

            if (e.getCode() == KeyCode.S) {
                guardar();
            }

            if (e.getCode() == KeyCode.R) {
                reiniciarNivel();
            }
            if (e.getCode() == KeyCode.L) {
                cargar();
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, e -> keys.remove(e.getCode()));
    }

    public void start() { loop.start(); }

    private void actualizar(double delta) {

        if (!jugador.isVivo() || jugador.isGanado()) return;

        // Movimiento
        if (keys.contains(KeyCode.LEFT)) jugador.moverIzquierda();
        if (keys.contains(KeyCode.RIGHT)) jugador.moverDerecha();
        if (keys.contains(KeyCode.SPACE)) jugador.saltar();

        // Actualizar entidades
        for (Entidad en : entidades) en.update();

        // Gravedad y colisiones con plataformas
        jugador.applyGravity();
        boolean onPlatform = false;

        for (Plataforma p : plataformas) {
            if (jugador.getBounds().intersects(p.getBounds())) {
                jugador.landOn(p);
                onPlatform = true;
            }
        }
        if (!onPlatform) jugador.setEnSuelo(false);
        
        for (Entidad en : entidades) {
            if (en instanceof Enemigo) {
                if (jugador.getBounds().intersects(en.getBounds())) {
                    jugador.setVivo(false);
                }
            }
        }
        for (Entidad e : entidades) {
            if (e instanceof ItemMeta) {
                if (jugador.getBounds().intersects(e.getBounds())) {
                    jugador.setGanado(true);
                    jugador.addPuntaje(1000);
                    loop.stop();
                    System.out.println("¡Nivel completado!");
                }
            }
        }
        Iterator<Entidad> it = entidades.iterator();
        while (it.hasNext()) {
            Entidad e = it.next();

            if (e instanceof ItemVida) {
                if (jugador.getBounds().intersects(e.getBounds())) {
                    jugador.addPuntaje(1);
                    it.remove();         
                    System.out.println("+1 vida / punto");
                }
            }
        }
    }

    private void dibujar() {
        // Fondo
        gc.setFill(Color.web("#1e1e1e"));
        gc.fillRect(0, 0, width, height);
        
        gc.setFill(Color.SADDLEBROWN);
        for (Plataforma p : plataformas) p.draw(gc);
        
        for (Entidad e : entidades) e.draw(gc);
        
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(18));
        gc.fillText("Puntaje: " + jugador.getPuntaje(), 20, 80);
        gc.fillText("Presiona 'S' para guardar", 20, 100);
        
        if (!jugador.isVivo()) {
            gc.setFill(Color.color(0,0,0,0.6));
            gc.fillRect(0, 0, width, height);
            gc.setFill(Color.RED);
            gc.setFont(Font.font(38));
            gc.fillText("¡Has perdido!", width/2 - 80, height/2);
        }
        
        if (jugador.isGanado()) {
            gc.setFill(Color.color(0,0,0,0.6));
            gc.fillRect(0, 0, width, height);
            gc.setFill(Color.LIGHTGREEN);
            gc.setFont(Font.font(38));
            gc.fillText("¡Nivel completado!", width/2 - 100, height/2);
        }
    }

    private void guardar() {
        try {
            ArchivoJuego.Progreso p = new ArchivoJuego.Progreso(
                    1,                        
                    jugador.getPuntaje(),
                    "player",
                    jugador.x,
                    jugador.y
            );

            archivoJuego.guardar(p);

            System.out.println("Progreso guardado.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void cargar() {
        try {
            ArchivoJuego.Progreso p = archivoJuego.cargar();
            if (p != null) {

                jugador.setPuntaje(p.puntaje);
                jugador.x = p.x;
                jugador.y = p.y;

                System.out.println("Progreso cargado:");
                System.out.println("Nivel = " + p.nivel);
                System.out.println("Puntaje = " + p.puntaje);
                System.out.println("Posición = (" + p.x + ", " + p.y + ")");
            } else {
                System.out.println("No hay archivo para cargar.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void reiniciarNivel() {
        System.out.println("🔄 Reiniciando nivel...");

        loop.stop();        
        init();             
        start();            
    }
}
