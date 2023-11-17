package net.ausiasmarch.tareas.helper;

import java.util.Random;

public class DataGenerationHelper {

    private static final String[] aNombre = { "Alberto", "Marta", "Carlos" };

    private static final String[] aApellido = { "García", "López", "González" };

    public static int getRandomInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max-min) + 1) + min;
        return randomNum;
    }

    public static String getRadomNombre() {
        return aNombre[(int) (Math.random() * aNombre.length)];
    }

    public static String getRadomApellido() {
        return aApellido[(int) (Math.random() * aApellido.length)];
    }

    public static String doNormalizeString(String cadena) {
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String cadenaSinAcentos = cadena;
        for (int i = 0; i < original.length(); i++) {
            cadenaSinAcentos = cadenaSinAcentos.replace(original.charAt(i), ascii.charAt(i));
        }
        return cadenaSinAcentos;
    }

    private static Random random = new Random();

    // Define arrays for different word categories
    private static String[] inicialProyecto = {"EcoVation", "Conctando Mentes", "Misión Salud", "Arte en movimiento", "Código Solidario", "Rutas verdes"};
    private static String[] restoProyecto = {"Sostenibilidad en Acción", "Innovación Colaborativa", "Tecnología para el Bienestar", "Proyecto Cultural Comunicativo", "Desarrollo de Software"};

    private static String[] inicialTarea = { "Cambio", "Diseñar", "Rediseñar", "Modificar" };
    private static String[] restoTarea = { "página 07", "texto 47", " página 45", "texto 107", "texto 62"};


    public static String generateNombreProyecto() {
        String inicialProyecto = inicialProyecto[random.nextInt(inicialProyecto.length)];
        String restoProyecto = restoProyecto[random.nextInt(restoProyecto.length)];
        return inicialProyecto + ": " + restoProyecto; 
    }

    public static String generateNombreTarea() {
        String inicialTarea = inicialTarea[random.nextInt(inicialTarea.length)];
        String restoTarea = restoTarea[random.nextInt(restoTarea.length)];
        return inicialTarea + " " + restoTarea;
    }

    public static String getSpeech(int amount) {
        String sentences = "";
        for (int i = 0; i < amount; i++) {
            String sentence = generateSentence();
            sentences += sentence.substring(0, 1).toUpperCase() + sentence.substring(1) + ". ";
        }
        return sentences;
    }

    public static LocalDateTime getRadomDate(){
        long minDay = LocalDate.of(2020, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2023, 10, 31).toEpochDay();
        long randomDay = ProyectoLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay).atTime(getRandomInt(0, 23), getRandomInt(0, 59), getRandomInt(0, 59));
    }

}
