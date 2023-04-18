/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private static final String SERVER_HOST = "localhost"; // Adresse IP ou nom d'hôte du serveur
    private static final int SERVER_PORT = 12345; // Port du serveur

    public static void main(String[] args) {
        try {
            // Connexion au serveur
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            System.out.println("Connecté au serveur.");

            // Création des flux d'entrée et de sortie pour la communication
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            // Lecture du nom d'utilisateur du client
            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Veuillez entrer votre nom d'utilisateur : ");
            String username = userInputReader.readLine();
            out.println(username); // Envoi du nom d'utilisateur au serveur

            // Boucle de communication avec le serveur
            String message;
            while (true) {
                // Lecture du message du serveur
                message = in.readLine();
                if (message == null) {
                    System.out.println("La connexion au serveur a été fermée.");
                    break;
                }
                System.out.println("Message du serveur : " + message);
                
                // Envoi d'un message au serveur
                System.out.print("Veuillez entrer votre message (ou \"quit\" pour quitter) : ");
                String input = userInputReader.readLine();
                out.println(input); // Envoi du message au serveur

                if ("quit".equalsIgnoreCase(input)) {
                    System.out.println("Déconnexion du serveur.");
                    break;
                }
            }

            // Fermeture des flux et de la socket
            in.close();
            out.close();
            userInputReader.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

