/*MIT License

Copyright (c) 2018 Milena dos Santos Macedo, Carlos André Cordeiro da Silva, 
Adrielly Calado Sales, Lucas Mendes Cavalcanti.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package br.edu.ifpe.model.hibernate;

import br.edu.ifpe.model.classes.Bike;
import br.edu.ifpe.model.classes.Endereco;
import br.edu.ifpe.model.classes.Usuario;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Lucas Mendes <lucas.mendes147@live.com>
*/

public class UsuarioHibernateTest {

    private static final Endereco ENDERECO = new Endereco("estado", "cidade",
            "cep", "bairro", "logradouro");

    private static final Endereco ENDERECO1 = new Endereco("e", "c",
            "cc", "b", "l");
    
    private static final UsuarioHibernate USUARIOHIBERNATE
            = UsuarioHibernate.getInstance();

    private static Usuario usuario1 = new Usuario(
             "senha", "nome", "28961303066", "sexo",
            LocalDate.now(), ENDERECO1, "telefone", "email",
            new ArrayList<Bike>());

    private static final Usuario USUARIO2 = new Usuario(
             "senha1", "nome1", "28952871049", "sexo1",
            LocalDate.now(), ENDERECO, "telefone1", "email1",
            new ArrayList<Bike>());

    @BeforeClass
    public static void deveInserirUsuario() {
        List<Bike> bikes = new ArrayList();
        bikes.add(new Bike(new BigDecimal(25), "modelo", "tipo", "cor",usuario1));
        bikes.add(new Bike(new BigDecimal(45), "modelo1", "tipo1", "cor1",usuario1));
        bikes.add(new Bike(new BigDecimal(65), "modelo2", "tipo2", "cor2",usuario1));
        usuario1.setBikes(bikes);
        
        List<Bike> bikes2 = new ArrayList();
        bikes2.add(new Bike(new BigDecimal(11), "m", "t", "c",USUARIO2));
        bikes2.add(new Bike(new BigDecimal(111), "mo", "ti", "co",USUARIO2));
        bikes2.add(new Bike(new BigDecimal(111), "mod", "tip", "coor",USUARIO2));
        USUARIO2.setBikes(bikes2);
        
        USUARIOHIBERNATE.inserir(usuario1);
        USUARIOHIBERNATE.inserir(USUARIO2);
    }
    

    @Test
    public void deveRecuperarTodosUsuariosTest() {
       ArrayList<Usuario> usuariosRecuperadosDoBanco
                = (ArrayList<Usuario>) USUARIOHIBERNATE.listarTodos();
        
        assertTrue("TC001",usuariosRecuperadosDoBanco.contains(USUARIO2));
        assertTrue("TC002",usuariosRecuperadosDoBanco.contains(usuario1));
    }
    
    @Test
    public void deveRecuperarPorCpfUsuarioTest() {
        assertEquals("TC003", USUARIO2,
                USUARIOHIBERNATE.recuperar("28952871049"));
    }

    @Test
    public void deveAletarUsuarioTest() {
        usuario1.setEmail("eee");
        usuario1.setSenha("llll");
        USUARIOHIBERNATE.alterar(usuario1);
        assertEquals("TC003", usuario1,
                USUARIOHIBERNATE.recuperar("28961303066"));
    }

    @AfterClass
    public static void deveLimparOBanco() {
        USUARIOHIBERNATE.deletar(usuario1);
        USUARIOHIBERNATE.deletar(USUARIO2);
    }
}
