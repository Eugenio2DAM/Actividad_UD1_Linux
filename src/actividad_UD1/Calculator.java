/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad_UD1;

import java.io.IOException;

/**
 * @version 1.0
 * @author Eugenio Gimeno Dolz
 */
public class Calculator {

    public static void main(String[] args) {
        ProcessBuilder newBuilder = new ProcessBuilder();
        newBuilder.command("gnome-calculator");
        Process newProcess;
        try{
            newProcess = newBuilder.start();
            newProcess.waitFor();
            System.out.println("La calculadora se ha cerrado satisfactoriamente.");
        }catch(IOException ioe){
            System.out.println("No se ha podido iniciar la calculadora." + ioe.getMessage());
        }catch(InterruptedException ie){
            System.out.println("Se ha interrumpido la calculadora." + ie.getMessage());
        }
    }
    
}
