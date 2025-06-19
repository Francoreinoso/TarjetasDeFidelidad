package modelo;

public enum NivelFidelidad {
    BRONCE, PLATA, ORO, PLATINO;

    public static NivelFidelidad obtenerPorPuntos(int puntos) {
        if (puntos >= 3000) return PLATINO;
        if (puntos >= 1500) return ORO;
        if (puntos >= 500) return PLATA;
        return BRONCE;
    }
}