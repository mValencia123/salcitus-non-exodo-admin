package mivo.pm6e1.salvitus;

public class Envio {

    String fecha;
    int agua;
    int pet;
    int usuarios;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getAgua() {
        return agua;
    }

    public void setAgua(int agua) {
        this.agua = agua;
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



    public Envio(String fecha, int agua, int pet, int usuarios) {
        this.fecha = fecha;
        this.agua = agua;
        this.pet = pet;
        this.usuarios = usuarios;
    }

    public Envio(){

    }
}
