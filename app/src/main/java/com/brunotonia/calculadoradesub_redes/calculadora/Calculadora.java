package com.brunotonia.calculadoradesub_redes.calculadora;

import java.util.ArrayList;
import java.util.List;

public class Calculadora {

    private static String mascaras[] = {"255", "252", "248", "240", "224", "192", "128", "0"};
    private static Integer cidr_maximo = 30;
    private static Integer ipClasseAminimo = 1;
    private static Integer ipClasseAmaximo = 126;
    private static Integer ipLoopback = 127;
    private static Integer ipClasseBminimo = 128;
    private static Integer ipClasseBmaximo = 191;
    private static Integer ipClasseCminimo = 192;
    private static Integer ipClasseCmaximo = 223;

    private Integer primeiroOcteto;
    private Integer classe;
    private List<Integer> cidr = null;

    public Calculadora (Integer primeiroOcteto) {
        this.primeiroOcteto = primeiroOcteto;
        this.cidr = new ArrayList<Integer>();
        this.cidr.clear();
    }

    public String determinaClasseIP () {
        if (primeiroOcteto <= 0) {
            classe = 0;
            return "Endereço Inválido";
        } else if (primeiroOcteto >= ipClasseAminimo && primeiroOcteto <= ipClasseAmaximo) {
            classe = 1;
            return "Classe A";
        } else if (primeiroOcteto >= ipClasseBminimo && primeiroOcteto <= ipClasseBmaximo) {
            classe = 2;
            return "Classe B";
        } else if (primeiroOcteto == ipLoopback) {
            classe = 4;
            return "Endereço de Loopback";
        } else if (primeiroOcteto >= ipClasseCminimo && primeiroOcteto <= ipClasseCmaximo) {
            classe = 3;
            return "Classe C";
        } else {
            classe = 5;
            return "Classe Indeterminada";
        }
    }

    public void povoaListaCidr() {
        Integer inicio = null;
        switch (classe) {
            case 1:
                inicio = 8;
                break;
            case 2:
                inicio = 16;
                break;
            case 3:
                inicio = 32;
                break;
        }
        for (int i = 0; i < cidr_maximo; i++) {
            cidr.add(inicio + i);
        }
    }

    public Long calculaQuantidadeSubRedes (Integer posicaoListaCidr) {
        return new Long (2^posicaoListaCidr);
    }

    public Long calculaQuantidadeHosts (Integer valorListaCidr) {
        return new Long ((2^(30-valorListaCidr+2))-2);
    }



}
