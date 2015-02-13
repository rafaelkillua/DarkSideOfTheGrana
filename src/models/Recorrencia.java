package models;

/**
 * Enum Recorrencia Cria constantes das recorrencias.
 * 
 * @author Grupo
 * @version 1.0
 */
public enum Recorrencia {
    NENHUMA(0), DIARIO(1), SEMANAL(7), MENSAL(30), BIMESTRAL(60), TRIMESTRAL(90), SEMESTRAL(180), ANUAL(365);
    
    private final int valor;

    private Recorrencia(int valor) {
        this.valor = valor;
    }
    
    /**
     * @return Retorna o valor do enum da recorrencia.
     */
    public int getValor(){
        return valor;
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
