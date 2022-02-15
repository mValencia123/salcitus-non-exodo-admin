package mivo.pm6e1.salvitus;


public class SetConsultas {
    String fecha;
    int agua;
    int pet;
    int usuarios;

    public int getAgua() {
        return agua;
    }

    public void setAgua(int agua) {
        this.agua = agua;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getPet() {
        return pet;
    }

    public void setPet(int pet) {
        this.pet = pet;
    }

    public int getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(int usuarios) {
        this.usuarios = usuarios;
    }



    public SetConsultas( int agua, String fecha, int pet, int usuarios) {
        this.agua = agua;
        this.fecha = fecha;
        this.pet = pet;
        this.usuarios = usuarios;
    }

    public SetConsultas(){

    }

}
