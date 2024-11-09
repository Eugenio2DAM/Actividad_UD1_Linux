/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad_UD1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @version 1.0
 * @author Eugenio Gimeno Dolz
 */ 
public class GettingIP {
    public static void main(String[] args) {
        Process newProcess = null;
        BufferedReader newBufferedReader = null;
        ProcessBuilder newProcessBuilder = new ProcessBuilder();
        newProcessBuilder.command("ip", "addr");

        System.out.println("Ejecutando ip addr");

        try {
            newProcess = newProcessBuilder.start();
            InputStream newInputStream = newProcess.getInputStream();
            newBufferedReader = new BufferedReader(new InputStreamReader(newInputStream));
            String line;
            String ipAddress = null;

            while ((line = newBufferedReader.readLine()) != null) {
                if (line.trim().contains("inet ") && !line.trim().contains("inet6") && !line.contains("127.0.0.1")) {
                    String[] parts = line.trim().split("\\s+");
                    if (parts.length > 1) {
                        ipAddress = parts[1];
                        break;
                    }
                }
            }

            if (ipAddress != null) {
                System.out.println("La dirección IP activa es: " + ipAddress);
            } else {
                System.out.println("No se encontró una dirección IP válida.");
            }

            newBufferedReader.close();
        } catch (IOException ioe) {
            System.out.println("Imposible iniciar el proceso: " + ioe.getMessage());
        } catch (Exception e) {
            System.out.println("Imposible realizar la lectura del buffer: " + e.getMessage());
        } finally {
            if (newBufferedReader != null) {
                try {
                    newBufferedReader.close();
                } catch (IOException ioe) {
                    System.out.println("No se ha podido cerrar el Buffer: " + ioe.getMessage());
                }
            }
        }
        
        int returnValue;
        try {
            returnValue = newProcess.waitFor();
            System.out.print("El proceso tiene un resultado de: " + returnValue);
        } catch (InterruptedException ie) {
            System.out.println("El proceso ha sido interrumpido: " + ie.getMessage());
        }
        System.out.println();
    }
}


