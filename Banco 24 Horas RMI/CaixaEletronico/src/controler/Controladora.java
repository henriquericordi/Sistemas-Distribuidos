/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import view.TelaInicial;

/**
 *
 * @author cc45966446830
 */
public class Controladora {

    private TelaInicial telaInicial;

    public Controladora() {
        telaInicial = new TelaInicial();
        telaInicial.setVisible(true);
    }

}