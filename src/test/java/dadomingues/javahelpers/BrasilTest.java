package dadomingues.javahelpers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BrasilTest {

    @Test
    public void deve_converter_telefone_fixo_sem_ddd() {
        String mascarado = Brasil.toDefinedFormat("21111838", Brasil.DefinedFormat.TELEFONE);
        assertEquals("2111-1838", mascarado);
    }

    @Test
    public void deve_converter_telefone_celular_sem_ddd() {
        String mascarado = Brasil.toDefinedFormat("999998281", Brasil.DefinedFormat.TELEFONE);
        assertEquals("99999-8281", mascarado);
    }

    @Test
    public void deve_converter_telefone_celular_com_ddd() {
        String mascarado = Brasil.toDefinedFormat("13999998281", Brasil.DefinedFormat.TELEFONE);
        assertEquals("(13) 99999-8281", mascarado);
    }

    @Test
    public void deve_converter_telefone_com_pais() {
        String mascarado = Brasil.toDefinedFormat("5513999998281", Brasil.DefinedFormat.TELEFONE);
        assertEquals("(+55 13) 99999-8281", mascarado);
    }

    @Test
    public void deve_converter_telefone_com_pais_e_sujo() {
        String mascarado = Brasil.toDefinedFormat("+55.(13)9.9999-82.81", Brasil.DefinedFormat.TELEFONE);
        assertEquals("(+55 13) 99999-8281", mascarado);
    }

    @Test
    public void deve_converter_cep_antigo_sujo() {
        String mascarado = Brasil.toDefinedFormat("11.0.40", Brasil.DefinedFormat.CEP);
        assertEquals("11040", mascarado);
    }

    @Test
    public void deve_converter_cep_padrao() {
        String mascarado = Brasil.toDefinedFormat("11040010", Brasil.DefinedFormat.CEP);
        assertEquals("11040-010", mascarado);
    }

    @Test
    public void deve_converter_cep_padrao_sujo() {
        String mascarado = Brasil.toDefinedFormat("11040/010", Brasil.DefinedFormat.CEP);
        assertEquals("11040-010", mascarado);
    }

    @Test
    public void deve_converter_cpf() {
        String mascarado = Brasil.toDefinedFormat("12345678901", Brasil.DefinedFormat.CPF);
        assertEquals("123.456.789-01", mascarado);
    }

    @Test
    public void deve_converter_cpf_sujo() {
        String mascarado = Brasil.toDefinedFormat("123/456/789.01", Brasil.DefinedFormat.CPF);
        assertEquals("123.456.789-01", mascarado);
    }

    @Test
    public void deve_calcular_dvmod11() {
        char dv = Utils.intToChar(Brasil.calculaDvMod11("325054288",Brasil.pesoCPF));
        assertEquals('1', dv);
    }

    @Test
    public void deve_calcular_digito1() {
        String base = "325054288";
        int digito = Brasil.calculaDvMod11(base,Brasil.pesoCPF);
        assertEquals(1, digito);
    }

    @Test
    public void deve_calcular_digito2() {
        String base = "3250542881";
        int digito = Brasil.calculaDvMod11(base,Brasil.pesoCPF);
        assertEquals(6, digito);
    }

    @Test
    public void deve_validar_cpf() {
        boolean valido = Brasil.isCpf("32505428816");
        assertEquals(true, valido);
    }

    @Test
    public void nao_deve_validar_cpf() {
        boolean valido = Brasil.isCpf("32505428810");
        assertEquals(false, valido);
    }

    @Test
    public void deve_validar_cnpj() {
        boolean valido = Brasil.isCnpj("33014556000196"); // americanas
        assertEquals(true, valido);
    }

    @Test
    public void deve_calcular_dv_cpf() {
        String dv = Brasil.calculaDvCpf("325054288");
        assertEquals("16", dv);
    }

    @Test
    public void deve_calcular_dv_cnpj() {
        String dv = Brasil.calculaDvCnpj("330145560001");
        assertEquals("96", dv);
    }

    @Test
    public void deve_validar_cep() {
        boolean valido = Brasil.isCep("12345678");
        assertEquals(true, valido);
    }

    @Test
    public void nao_deve_validar_cep() {
        boolean valido = Brasil.isCep("123456");
        assertEquals(false, valido);
    }

    @Test
    public void deve_validar_placa() {
        boolean valido = Brasil.isPlacaVeiculo("ABC1234");
        assertEquals(true, valido);
    }

    @Test
    public void deve_validar_placa_lowercase() {
        boolean valido = Brasil.isPlacaVeiculo("abc1234");
        assertEquals(true, valido);
    }

    @Test
    public void deve_validar_placa_seguranca() {
        boolean valido = Brasil.isPlacaVeiculo("ABC1C34");
        assertEquals(true, valido);
    }

    @Test
    public void nao_deve_validar_placa_zerada() {
        boolean valido = Brasil.isPlacaVeiculo("ABC0000");
        assertEquals(false, valido);
    }

    @Test
    public void deve_validar_placa_digitos_trocados() {
        boolean valido = Brasil.isPlacaVeiculo("AB1C2DE");
        assertEquals(false, valido);
    }

}
