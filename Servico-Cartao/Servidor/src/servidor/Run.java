/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.IOException;

/**
 *
 * @author Rafael Braz
 */
public class Run {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            new Servidor(12345).executar();
        } catch (IOException ex) {
            System.err.println("Servidor não pode ser iniciado! " + ex.toString());
        }
    }

}
