package com.brunotonia.calculadoradesub_redes.calculadora;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Calculadora {

    private static String mascaras[] = {"0", "128", "192", "224", "240", "248", "252", "254", "255"};
    private static Integer cidr_maximo = 30;
    private static Integer ipClasseAminimo = 1;
    private static Integer ipClasseAmaximo = 126;
    private static Integer ipLoopback      = 127;
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
        determinaClasseIP();
        povoaListaCidr();
    }

    public String determinaClasseIP () {
        if (primeiroOcteto <= 0) {
            classe = 0;
            return "Endereço Inválido";
        } else if (primeiroOcteto >= ipClasseAminimo && primeiroOcteto <= ipClasseAmaximo) {
            classe = 1;
            return "Classe A";
        } else if (primeiroOcteto == ipLoopback) {
            classe = 4;
            return "Endereço de Loopback";
        } else if (primeiroOcteto >= ipClasseBminimo && primeiroOcteto <= ipClasseBmaximo) {
            classe = 2;
            return "Classe B";
        } else if (primeiroOcteto >= ipClasseCminimo && primeiroOcteto <= ipClasseCmaximo) {
            classe = 3;
            return "Classe C";
        } else if (primeiroOcteto > ipClasseCmaximo && primeiroOcteto < 255){
            classe = 5;
            return "Classe Indeterminada";
        } else {
            classe = 0;
            return "Endereço Inválido";
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
                inicioCidr = 24;
                break;
            default:
                inicioCidr = 30;
                break;
        }
        for (int i = 0; i < (cidr_maximo - inicioCidr + 1); i++) {
            cidr.add(inicioCidr + i);
            cidr_str.add("/" + new Integer(inicioCidr + i).toString());
        }
        //cidr_str.add("");
    }

    public Double calculaQuantidadeSubRedes (Integer posicaoListaCidr) {
        return Math.pow(2, posicaoListaCidr);
    }

    public Double calculaQuantidadeHosts (Integer valorListaCidr) {
        Double d = Math.pow(2, 32 - valorListaCidr) -2;
        return d;
    }

    private String retornaMascaraClasseA (Integer posicaoListaCidr) {
        String mascara = "255.";
        if (posicaoListaCidr >= 0 && posicaoListaCidr <= 8) {
            mascara += mascaras[posicaoListaCidr] + ".0.0";
        } else if (posicaoListaCidr > 8 && posicaoListaCidr <= 16) {
            mascara += "255." + mascaras[posicaoListaCidr - 8] + ".0";
        } else {
            mascara += "255.255." + mascaras[posicaoListaCidr - 16];
        }
        return mascara;
    }

    private String retornaMascaraClasseB (Integer posicaoListaCidr) {
        String mascara = "255.255.";
        if (posicaoListaCidr >= 0 && posicaoListaCidr <= 8) {
            mascara += mascaras[posicaoListaCidr] + ".0";
        } else if (posicaoListaCidr > 8) {
            mascara += "255." + mascaras[posicaoListaCidr - 8];
            System.out.println(posicaoListaCidr - 8);
        }
        return mascara;
    }

    private String retornaMascaraClasseC (Integer posicaoListaCidr) {
        String mascara = "255.255.255.";
        return mascara + mascaras[posicaoListaCidr];
    }

    public String retornaMascara (Integer posicaoListaCidr) {
        switch (classe)  {
            case 1:
                return retornaMascaraClasseA(posicaoListaCidr);
            case 2:
                return retornaMascaraClasseB(posicaoListaCidr);
            case 3:
                return retornaMascaraClasseC(posicaoListaCidr);
            default:
                return " . . . .";
        }
    }

    public List<String> getCidr_str() {
        return cidr_str;
    }

    public List<Integer> getCidr() {
        return cidr;
    }
}
