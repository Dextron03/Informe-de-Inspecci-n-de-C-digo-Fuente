package screensframework;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Pruebas unitarias del cifrado de contrasenas (RNF-01).
 *
 * Login y Registro almacenan/comparan las contrasenas con
 * DigestUtils.sha1Hex(...). Estas pruebas verifican ese mismo algoritmo:
 * que sea determinista, que valores distintos produzcan hashes distintos y
 * que el hash nunca coincida con el texto plano.
 */
public class AutenticacionPasswordTest {

    @Test
    public void hashSha1EsDeterministaYConocido() {
        // SHA-1 conocido de "admin" (mismo algoritmo usado por Login/Registro)
        String esperado = "d033e22ae348aeb5660fc2140aec35850c4da997";
        assertEquals("El hash SHA-1 de 'admin' debe ser estable y conocido",
                esperado, DigestUtils.sha1Hex("admin"));
    }

    @Test
    public void contrasenasDistintasGeneranHashDistinto() {
        assertNotEquals("Contrasenas distintas no deben producir el mismo hash",
                DigestUtils.sha1Hex("clave1"), DigestUtils.sha1Hex("clave2"));
    }

    @Test
    public void elHashNoCoincideConElTextoPlano() {
        String pass = "MiClave2026";
        assertNotEquals("La contrasena nunca debe almacenarse en texto plano",
                pass, DigestUtils.sha1Hex(pass));
    }
}
