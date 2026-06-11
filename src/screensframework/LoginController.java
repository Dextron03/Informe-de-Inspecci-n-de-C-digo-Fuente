/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package screensframework;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.PreparedStatement;
import screensframework.DBConnect.DBConnection;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * FXML Controller class
 *
 * @author Wil
 */
public class LoginController implements Initializable, ControlledScreen {
    ScreensController controlador;
    private Validaciones validation = new Validaciones();
    private Connection conexion;
    
    public TextField tfUsuario;
    public PasswordField tfPass;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void setScreenParent(ScreensController pantallaPadre) {
        controlador = pantallaPadre;
        
    }
    
    @FXML
    private void iniciarSesion(ActionEvent event){
        /********************************** 
         *         Area de validaciones 
         ***********************************/
        if (!validation.validarVacios(tfUsuario.getText(), "USUARIO")) {
            return;
        }
        
        if (!validation.validarMaximo(tfUsuario.getText(), "USUARIO", 20, 2)) {
            return;
        }
        
        /********************************** 
         *     Fin de las validaciones 
         ***********************************/
        
        //______________________________________________________
        /* SE HACE EL LLAMADO AL MODELO PARA ENTRAR AL SISTEMA */
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND pass = ?";
        
        try {
            conexion = DBConnection.getConnection();
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, tfUsuario.getText());
            pstmt.setString(2, DigestUtils.sha1Hex(tfPass.getText()));
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                tfUsuario.setText("");
                tfPass.setText("");
                controlador.setScreen(ScreensFramework.contenidoID);
            } else {
                mostrarAlerta("Login Fallido", "Este usuario no está registrado o la contraseña es incorrecta.", AlertType.ERROR);
            }
            
        } catch (SQLException e) {
            System.err.println("Error en login: " + e.getMessage());
            mostrarAlerta("Error de Conexión", "No se pudo conectar a la base de datos.", AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    @FXML
    private void irFormRegistro(ActionEvent event) {
        controlador.setScreen(ScreensFramework.registroID);
    }
    
    @FXML
    private void salir(ActionEvent event) {
        Platform.exit();
    }
}
