package com.example.juegoplataformafxhollow;

import java.util.ArrayList;
import java.util.List;

public class Nivel2 {

    public static class DatosNivel {
        public List<Plataforma> plataformas = new ArrayList<>();
        public List<Entidad> entidades = new ArrayList<>();
        public ItemMeta meta;
        public double metaX;   
    }

    public static DatosNivel crearNivel() {
        DatosNivel datos = new DatosNivel();


        datos.plataformas.add(new Plataforma(0, 540, 800, 60));          
        datos.plataformas.add(new Plataforma(200, 450, 140, 20));
        datos.plataformas.add(new Plataforma(420, 400, 140, 20));
        datos.plataformas.add(new Plataforma(650, 350, 150, 20));
        datos.plataformas.add(new Plataforma(850, 300, 200, 20));
        datos.plataformas.add(new Plataforma(1150, 260, 180, 20));
        datos.plataformas.add(new Plataforma(1400, 240, 200, 20));


        datos.entidades.add(new EnemigoTerrestre(300, 500, 60, 60, 1.6));
        datos.entidades.add(new EnemigoTerrestre(700, 350, 60, 60, 1.8));
        datos.entidades.add(new EnemigoVolador(500, 150, 60, 60, 1.5));
        datos.entidades.add(new EnemigoVolador(1050, 120, 60, 60, 1.2));


        ItemMeta meta = new ItemMeta(1500, 200, 40, 40);
        datos.meta = meta;

        ItemVida vida = new ItemVida(670, 300, 30, 30);
        datos.entidades.add(vida);
        ItemVida vida2 = new ItemVida(1170, 200, 30, 30);
        datos.entidades.add(vida2);


        datos.metaX = meta.getX();

        return datos;
    }
}
