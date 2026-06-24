package screensframework;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Pruebas unitarias de la clase {@link Validaciones}.
 *
 * Estas pruebas cubren los caminos VALIDOS de cada metodo (los que devuelven
 * true y NO abren ningun dialogo). Los caminos negativos de Validaciones
 * invocan JOptionPane.showMessageDialog, que es modal y bloquearia la
 * ejecucion automatica; por eso no se prueban aqui directamente. Para cubrir
 * los caminos negativos se recomienda extraer la logica de validacion de la
 * capa de interfaz (Swing).
 */
public class ValidacionesTest {

    private final Validaciones validacion = new Validaciones();

    @BeforeClass
    public static void configurarEntorno() {
        // Modo headless: si algun camino llegara a invocar un JOptionPane,
        // lanzara una excepcion en vez de bloquear la suite esperando un clic.
        System.setProperty("java.awt.headless", "true");
    }

    @Test
    public void usuarioNoVacioEsValido() {
        assertTrue("Un usuario no vacio debe pasar la validacion",
                validacion.validarVacios("admin", "USUARIO"));
    }

    @Test
    public void longitudDentroDelRangoEsValida() {
        // "admin" tiene 5 caracteres, dentro del rango [2, 20]
        assertTrue("Una longitud dentro del rango debe ser valida",
                validacion.validarMaximo("admin", "USUARIO", 20, 2));
    }

    @Test
    public void correoConFormatoValidoEsAceptado() {
        assertTrue("Un correo con formato correcto debe ser aceptado",
                validacion.validarCorreo("usuario@dominio.com"));
    }

    @Test
    public void passwordsIgualesSonValidas() {
        assertTrue("Dos contrasenas iguales deben ser validas",
                validacion.validaPassword("Secreta123", "Secreta123"));
    }

    @Test
    public void soloLetrasAceptaTextoAlfabetico() {
        assertTrue("Un texto solo con letras debe ser aceptado",
                validacion.soloLetras("Producto"));
    }

    @Test
    public void soloNumerosAceptaDigitos() {
        assertTrue("Una cadena de digitos debe ser aceptada",
                validacion.soloNumeros("12345"));
    }
}
