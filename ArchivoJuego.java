package com.example.juegoplataformafxhollow;
import java.io.*;

public class ArchivoJuego {

    private final String ruta;

    public ArchivoJuego(String ruta) {
        this.ruta = ruta;
        File f = new File(ruta).getParentFile();
        if (f != null && !f.exists()) f.mkdirs();
    }
    public void guardar(Progreso p) throws IOException {
        try (FileWriter fw = new FileWriter(ruta)) {
            fw.write("nivel=" + p.nivel + "\n");
            fw.write("puntaje=" + p.puntaje + "\n");
            fw.write("jugador=" + p.nombre + "\n");
            fw.write("x=" + p.x + "\n");
            fw.write("y=" + p.y + "\n");
        }
    }
    public Progreso cargar() throws IOException {
        File file = new File(ruta);
        if (!file.exists()) return null;

        int nivel = 1;
        int puntaje = 0;
        String nombre = "player";
        double x = 50;
        double y = 450;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("nivel=")) nivel = Integer.parseInt(line.split("=")[1].trim());
                if (line.startsWith("puntaje=")) puntaje = Integer.parseInt(line.split("=")[1].trim());
                if (line.startsWith("jugador=")) nombre = line.split("=")[1].trim();
                if (line.startsWith("x=")) x = Double.parseDouble(line.split("=")[1].trim());
                if (line.startsWith("y=")) y = Double.parseDouble(line.split("=")[1].trim());
            }
        }

        return new Progreso(nivel, puntaje, nombre, x, y);
    }
    
    public static class Progreso {
        public int nivel;
        public int puntaje;
        public String nombre;
        public double x, y;

        public Progreso(int nivel, int puntaje, String nombre, double x, double y) {
            this.nivel = nivel;
            this.puntaje = puntaje;
            this.nombre = nombre;
            this.x = x;
            this.y = y;
        }
    }
}
