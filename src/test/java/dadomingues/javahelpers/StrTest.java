package dadomingues.javahelpers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StrTest {

    @Test
    public void deve_preencher_com_zeros_a_esquerda() {
        String padded = Str.numPad("123",11);
        assertEquals("00000000123", padded);
    }

    @Test
    public void deve_converter_nome_upper_camel_case() {
        String nome = Str.name("Bom dia Brasil!", Str.NamingFlavors.UCAMEL);
        assertEquals("BomDiaBrasil", nome);
    }

    @Test
    public void deve_converter_nome_lower_camel_case() {
        String nome = Str.name("Bom dia Brasil!", Str.NamingFlavors.LCAMEL);
        assertEquals("bomDiaBrasil", nome);
    }

    @Test
    public void deve_converter_nome_snake_case() {
        String nome = Str.name("Bom dia Brasil!", Str.NamingFlavors.SNAKE);
        assertEquals("bom_dia_brasil", nome);
    }

    @Test
    public void deve_converter_nome_kebab_case() {
        String nome = Str.name("Bom dia Brasil!", Str.NamingFlavors.KEBAB);
        assertEquals("bom-dia-brasil", nome);
    }

    @Test
    public void deve_converter_nome_constant_case() {
        String nome = Str.name("Bom dia Brasil!", Str.NamingFlavors.CONSTANT);
        assertEquals("BOM_DIA_BRASIL", nome);
    }

    @Test
    public void deve_escapar_contrabara_e_aspas() {
        String nome = Str.escape("Uma quebra de linha \n 'xyz' ", Str.EscapeTypes.STRING);
        assertEquals("Uma quebra de linha \\n \'xyz\' ", nome);
    }

    @Test
    public void deve_pegar_iniciais() {
        String nome = Str.initials("Daniel Araújo Domingues");
        assertEquals("DAD", nome);
    }

    @Test
    public void deve_escapar_sql() {
        String nome = Str.escape("select * from table where text = 'string'", Str.EscapeTypes.SQL);
        assertEquals("select * from table where text = \\'string\\'", nome);
    }

    @Test
    public void deve_truncar() {
        String nome = Str.lTrim("aaaaaaaDanielbbb", 'a');
        assertEquals("Danielbbb", nome);
        nome = Str.rTrim("aaaDanielbbbb", 'b');
        assertEquals("aaaDaniel", nome);
        nome = Str.trim("cccDanielccccc", 'c');
        assertEquals("Daniel", nome);
    }

}
