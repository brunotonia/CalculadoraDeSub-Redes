package com.brunotonia.calculadoradesub_redes.calculadora;

import java.util.ArrayList;
import java.util.List;

public class Calculadora {

    private static String mascaras[] = {"0", "128", "192", "224", "240", "248", "252", "254"};
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
    private Integer inicioCidr = null;
    private List<Integer> cidr = null;
    private List<String> cidr_str = null;

    public Calculadora (Integer primeiroOcteto) {
        this.primeiroOcteto = primeiroOcteto;
        this.cidr = new ArrayList<Integer>();
        this.cidr_str = new ArrayList<String>();
        this.cidr.clear();
        this.cidr_str.clear();
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
        switch (classe) {
            case 1:
                inicioCidr = 8;
                break;
            case 2:
                inicioCidr = 16;
                break;
            case 3:
                inicioCidr = 32;
                break;
        }
        for (int i = 0; i < cidr_maximo; i++) {
            cidr.add(inicioCidr + i);
            cidr_str.add("/" + new Integer(inicioCidr + i).toString());
        }
    }

    public Long calculaQuantidadeSubRedes (Integer posicaoListaCidr) {
        return new Long (2^posicaoListaCidr);
    }

    public Long calculaQuantidadeHosts (Integer valorListaCidr) {
        return new Long ((2^(30-valorListaCidr+2))-2);
    }

    private String retornaMascaraClasseA (Integer valorListaCidr) {
        String mascara = "255.";
        if (valorListaCidr >= inicioCidr && valorListaCidr <= 16) {
            mascara += mascaras[inicioCidr - valorListaCidr] + ".0.0";
        } else if (valorListaCidr > 16 && valorListaCidr <= 24) {
            mascara += "255." + mascaras[(16-inicioCidr) - valorListaCidr] + ".0";
        } else {
            mascara += "255.255." + mascaras[(24-inicioCidr) - valorListaCidr];
        }
        return mascara;
    }

    private String retornaMascaraClasseB (Integer valorListaCidr) {
        String mascara = "255.255.";
        mascara += mascaras[inicioCidr - valorListaCidr] + ".0.0";
        return mascara;
    }

    private String retornaMascaraClasseC (Integer valorListaCidr) {
        String mascara = "255.";
        mascara += mascaras[inicioCidr - valorListaCidr] + ".0.0";
        return mascara;
    }

    public String retornaMascara (Integer valorListaCidr) {
        switch (classe)  {
            case 1:
                return retornaMascaraClasseA(valorListaCidr);
            case 2:
                return retornaMascaraClasseB(valorListaCidr);
            case 3:
                return retornaMascaraClasseC(valorListaCidr);
            default:
                return " . . . .";
        }
    }

}
