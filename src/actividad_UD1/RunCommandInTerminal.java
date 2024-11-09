/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad_UD1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * @version 1.0
 * @author Eugenio Gimeno Dolz
 */
public class RunCommandInTerminal {

    public static void main(String[] args) {

        System.out.print("Escriba el comando a ejecutar: ");
        String newCommand = requestCommand();
        if (searchPipe(newCommand)) {
            runCommandWithPipes(commandQueue(newCommand));
        } else {
            runSimpleCommand(newCommand);
        }
    }

    private static String requestCommand() {
        Scanner lector = new Scanner(System.in);
        Boolean isValidInput = false;
        String command = null;
        while (!isValidInput) {
            command = lector.nextLine();
            if (command.length() > 0) {
                isValidInput = true;
            } else {
                System.out.println("Debe de introducir un comando.");
            }
        }
        return command;
    }
    
    private static ArrayList<String> commandQueue(String command) {
        String[] commands = command.split("\\|");
        ArrayList<String> commandQueue = new ArrayList<>();
        for (int i = 0; i < commands.length; i++) {
            commandQueue.add(commands[i]);
        }
        return commandQueue;
    }

    private static boolean searchPipe(String command) {
        for (int i = 0; i < command.length(); i++) {
            char search = command.charAt(i);
            if (search == '|') {
                return true;
            }
        }
        return false;
    }

    private static void runSimpleCommand(String command) {
        String[] newCommandParts = command.split(" ");
        ProcessBuilder newBuilder = new ProcessBuilder();
        newBuilder.command(newCommandParts);
        Process newProcess = null;
        BufferedReader newProcessBuilder = null;
        try {
            newProcess = newBuilder.start();
            newProcessBuilder = new BufferedReader(new InputStreamReader(newProcess.getInputStream()));
            String line;
            System.out.println("El resultado del comando es: ");
            while ((line = newProcessBuilder.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = newProcess.waitFor();
            System.out.println("\nEl comando se ejecutó con éxito con código de salida: " + exitCode);
        } catch (IOException ioe) {
            System.out.println("Imposible ejecutar el comando. " + ioe.getMessage());
        } catch (InterruptedException ie) {
            System.out.println("La ejecución del comando se ha interrumpido. " + ie.getMessage());
        } finally {
            if (newProcessBuilder != null) {
                try {
                    newProcessBuilder.close();
                } catch (IOException ioe) {
                    System.out.println("No se ha podido cerrar el buffer de lectura. " + ioe.getMessage());
                }
            } else if (newProcess != null) {
                newProcess.destroy();
            }
        }
    }

    private static void runCommandWithPipes(ArrayList<String> commands) {
        StringBuilder buidCommand = new StringBuilder();
        for (String command : commands) {
            buidCommand.append(command.trim()).append(" | ");
        }
        String finalCommand = buidCommand.toString().replaceAll(" \\| $", "");
        ProcessBuilder newprocessBuilder = new ProcessBuilder("bash", "-c", finalCommand);
        System.out.println("El resultado del comando es: ");
        Process newProcess = null;
        BufferedReader newProcessBuilder = null;
        try {
            newProcess = newprocessBuilder.start();
            newProcessBuilder = new BufferedReader(new InputStreamReader(newProcess.getInputStream()));
            String line;
            while ((line = newProcessBuilder.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = newProcess.waitFor();
            System.out.println("\nEl comando se ejecutó con éxito con código de salida: " + exitCode);
        } catch (IOException ioe) {
            System.out.println("Imposible ejecutar el comando. " + ioe.getMessage());
        } catch (InterruptedException ie) {
            System.out.println("La ejecución del comando se ha interrumpido. " + ie.getMessage());
        } finally {
            if (newProcessBuilder != null) {
                try {
                    newProcessBuilder.close();
                } catch (IOException ioe) {
                    System.out.println("No se ha podido cerrar el buffer de lectura. " + ioe.getMessage());
                }
            } else if (newProcess != null) {
                newProcess.destroy();
            }
        }
    }
}
