package com.brunotonia.calculadoradesub_redes.calculadora;

import java.util.List;

public class Calculadora {

    private static String mascaras[] = {"255", "252", "248", "240", "224", "192", "128", "0"};
    private static Integer ipClasseAminimo = 1;
    private static Integer ipClasseAmaximo = 126;
    private static Integer ipLoopback = 127;
    private static Integer ipClasseBminimo = 128;
    private static Integer ipClasseBmaximo = 191;
    private static Integer ipClasseCminimo = 192;
    private static Integer ipClasseCmaximo = 223;

    private Integer primeiroOcteto;
    private List<Integer> cidr = null;

    public Calculadora (Integer primeiroOcteto) {
        this.primeiroOcteto = primeiroOcteto;
    }

    public String determinaClasseIP () {
        if (primeiroOcteto <= 0) {
            return "Endereço Inválido";
        } else if (primeiroOcteto >= ipClasseAminimo && primeiroOcteto <= ipClasseAmaximo) {

            return "Classe A";
        } else if (primeiroOcteto >= ipClasseBminimo && primeiroOcteto <= ipClasseBmaximo) {

            return "Classe B";
        } else if (primeiroOcteto == ipLoopback) {

            return "Endereço de Loopback";
        } else if (primeiroOcteto >= ipClasseCminimo && primeiroOcteto <= ipClasseCmaximo) {

            return "Classe C";
        } else {
            return "Classe Indeterminada";
        }
    }

}
