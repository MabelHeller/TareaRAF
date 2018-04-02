/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import domain.Vehicles;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author Heller
 */
public class VehiclesFile {
    //atributos

    private RandomAccessFile randomAccessFile;
    private int regsQuantity;//cantidad de registros en el archivo
    private int regSize;//tama;o del registro
    private String myFilePath;//ruta

    //constructor
    public VehiclesFile(File file) throws IOException {
        //almaceno la ruta
        myFilePath = file.getPath();
        //indico el tama;o maximo
        this.regSize = 140;
        //una validacion sencilla
        if (file.exists() && !file.isFile()) {
            throw new IOException(file.getName() + "is an invalid file");
        } else {
            //crear la nueva instancia de RAF
            randomAccessFile = new RandomAccessFile(file, "rw");//en este archivo vamos a poder escribir y leer
            //necesitamos indicar cuantos registros tiene el archivo
            this.regsQuantity = (int) Math.ceil((double) randomAccessFile.length() / (double) regSize);
        }
    }

    //MUY IMPORTANTE cerrar nuestros archivos
    public void close() throws IOException {
        randomAccessFile.close();
    }

    //indicar la cantidad de registros de nuestro archivo
    public int fileSize() {
        return this.regsQuantity;
    }

    //insertar un nuevo resgistro en una posicion especifica
    public boolean putValue(int position, Vehicles vehicles) throws IOException {
        //primero verificar que sea valida la insercion
        if (position < 0 && position > this.regsQuantity) {
            System.err.println("1001 - Record position is out of bounds");
            return false;
        } else {
            if (vehicles.sizeInBytes() > this.regSize) {
                System.err.println("1002 - Record size is out of bounds");
                return false;
            } else {
                randomAccessFile.seek(position * this.regSize);
                randomAccessFile.writeUTF(vehicles.getName());
                randomAccessFile.writeInt(vehicles.getYear());
                randomAccessFile.writeFloat(vehicles.getMileage());
                randomAccessFile.writeBoolean(vehicles.isAmerican());
                randomAccessFile.write(vehicles.getSerie());
                return true;
            }
        }
    }//end method

    //insertar al final del archivo
    public boolean addEndRecord(Vehicles vehicles) throws IOException {
        boolean success = putValue(this.regsQuantity, vehicles);
        if (success) {
            ++this.regsQuantity;
        }
        return success;
    }

    //obtener un estudiante
    public Vehicles getVehicles(int position) throws IOException {
        //validar la posicion
        if (position >= 0 && position <= this.regsQuantity) {
            //colocamos el brazo en el lugar adecuado
            randomAccessFile.seek(position * this.regSize);
            //llevamos a cabo la lectura
            Vehicles studentTemp = new Vehicles();
            studentTemp.setName(randomAccessFile.readUTF());
            studentTemp.setYear(randomAccessFile.readInt());
            studentTemp.setMileage(randomAccessFile.readFloat());
            studentTemp.setAmerican(randomAccessFile.readBoolean());
            studentTemp.setSerie(randomAccessFile.read());
            if (studentTemp.getName().equalsIgnoreCase("deleted")) {
                return null;
            } else {
                return studentTemp;
            }
        } else {
            System.out.println("1003-position is out of brounds");
            return null;
        }
    }

    //eliminar estudiante
    public boolean deletedVehicles(int serie) throws IOException {
        Vehicles myVehicles;
        //buscar el estudiante
        for (int i = 0; i < this.regsQuantity; i++) {
            //obtener el estudiante de la posicion actual
            myVehicles = this.getVehicles(i);
            //preguntar si el estudiante que deseo eliminar 
            if (this.getVehicles(i) != null) {
                if (myVehicles.getSerie() == serie) {
                    //marcar como eliminado
                    myVehicles.setName("deleted");
                    return this.putValue(i, myVehicles);
                }
            }
        }
        return false;
    }

    //retornar una lista de estudiantes
    public ArrayList<Vehicles> getAllVehicles() throws IOException {
        ArrayList<Vehicles> vehiclesArray = new ArrayList<Vehicles>();
        for (int i = 0; i < this.regsQuantity; i++) {
            Vehicles sTemp = this.getVehicles(i);
            if (sTemp != null) {
                vehiclesArray.add(sTemp);
            }
        }//end for
        return vehiclesArray;
    }

}
