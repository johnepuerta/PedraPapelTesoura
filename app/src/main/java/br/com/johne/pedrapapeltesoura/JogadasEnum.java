package br.com.johne.pedrapapeltesoura;

public enum JogadasEnum{

    PAPEL   (0, 1, "Papel"),
    PEDRA   (1, 2, "Pedra"),
    TESOURA (2, 0, "Tesoura"),
    PERDEDOR(3, 4, ""),
    ;

    int codigo;
    int quemVenco;
    String descricao;

    JogadasEnum(int codigo, int quemVenco, String descricao) {
        this.codigo = codigo;
        this.quemVenco = quemVenco;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public int getQuemVenco() {
        return quemVenco;
    }

    public static JogadasEnum fromString(String valor){
        return JogadasEnum.fromValue(Integer.parseInt(valor));
    }

    public static JogadasEnum fromValue(int valor){
        for(JogadasEnum jog : JogadasEnum.values()){
            if(jog.getCodigo() == valor){
                return jog;
            }
        }
        return null;
    }

    public static boolean isVenceJogada(JogadasEnum jog1, JogadasEnum jog2){
        return jog1.getQuemVenco() == jog2.getCodigo() || jog2.equals(JogadasEnum.PERDEDOR);
    }

}
