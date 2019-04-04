package dadomingues.javahelpers;

import org.apache.commons.lang3.StringUtils;

public class Brasil {

    enum DefinedFormat {
        TELEFONE,
        CEP,
        CPF,
        CNPJ
    }

    public static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    public static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};


    public static boolean isPlacaVeiculo(String placa) {
        if (placa.length()!=7) return false;
        if (!placa.toUpperCase().matches("[A-Z]{3}[0-9][A-Z0-9][0-9]{2}")) return false;
        if (placa.toUpperCase().substring(3).matches("0[A0]00")) return false;
        return true;
    }

    public static boolean isCep(String cep) {
        if (cep.matches("[0-9]{8}")) return true;
        return false;
    }

    /**
     * Reference: https://www.devmedia.com.br/validando-o-cpf-em-uma-aplicacao-java/22097
     * @param cpf
     * @return
     */
    public static boolean isCpf(String cpf) {
        if (cpf==null || cpf.length() != 11) return false;
        // se todos os dígito iguais, não é valido.
        for (int i=0;i<=9;i++) {
            if (StringUtils.repeat(Integer.toString(i), 11).equals(cpf)) {
                return false;
            }
        }
        String dv = calculaDvCpf(cpf.substring(0,9));
        return ((dv.charAt(0) == cpf.charAt(9)) && (dv.charAt(1) == cpf.charAt(10)));

    }

    public static boolean isCnpj(String cnpj) {
        if (cnpj==null || cnpj.length() != 14) return false;
        String dv = calculaDvCnpj(cnpj.substring(0,12));
        return ((dv.charAt(0) == cnpj.charAt(12)) && (dv.charAt(1) == cnpj.charAt(13)));

    }

    public static String calculaDvCpf(String base) {
        String dv;
        dv  = Integer.toString(calculaDvMod11(base.substring(0,9),pesoCPF));
        base += dv;
        dv += Integer.toString(calculaDvMod11(base.substring(0,10),pesoCPF));
        return dv;
    }

    public static String calculaDvCnpj(String base) {
        String dv;
        dv  = Integer.toString(calculaDvMod11(base.substring(0,12),pesoCNPJ));
        base += dv;
        dv += Integer.toString(calculaDvMod11(base.substring(0,13),pesoCNPJ));
        return dv;
    }


    public static int calculaDvMod11(String base, int[] peso) {
        int[] num = Utils.stringToIntArray(base);
        return calculaDvMod11(num, peso);
    }

    public static int calculaDvMod11(int[] base, int[] peso) {
        int soma = 0;
        for (int i=base.length-1; i >= 0; i-- ) {
            soma += (base[i])*(peso[peso.length-base.length+i]);
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }


    public static String toDefinedFormat(String unmasked, DefinedFormat format) {
        switch (format) {
            case TELEFONE: return toTelefone(unmasked);
            case CEP: return toCep(unmasked);
            case CPF: return toCpf(unmasked);
            case CNPJ: return toCnpj(unmasked);
        }
        return unmasked;
    }

    private static String toTelefone(String unmasked) {
        String temp = unmasked.replaceAll("[^0-9]","");
        switch (temp.length()) {
            case 8:  return Str.mask(temp,"####-####");
            case 9:  return Str.mask(temp,"#####-####");
            case 10: return Str.mask(temp,"(##) ####-####");
            case 11: return Str.mask(temp,"(##) #####-####");
            case 12: return Str.mask(temp,"(+## ##) ####-####");
            case 13: return Str.mask(temp,"(+## ##) #####-####");
            default: return unmasked;
        }
    }

    private static String toCep(String unmasked) {
        String temp = unmasked.replaceAll("[^0-9]","");
        switch (temp.length()) {
            case 5:  return Str.mask(temp,"#####");
            case 8:  return Str.mask(temp,"#####-###");
            default: return unmasked;
        }
    }

    private static String toCpf(String unmasked) {
        String temp = unmasked.replaceAll("[^0-9]","");
        if (temp.length()==11) return Str.mask(temp,"###.###.###-##");
        return unmasked;
    }

    private static String toCnpj(String unmasked) {
        String temp = unmasked.replaceAll("[^0-9]","");
        if (temp.length()==14) return Str.mask(temp,"##.###.###/####-##");
        return unmasked;
    }

}
