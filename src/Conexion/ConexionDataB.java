/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;


public class ConexionDataB {

    private static ConexionDataB instancia;
    private Connection cn;

    private ConexionDataB() {
        // Configuración de la conexión a la base de datos
        // ...
    }

    public static synchronized ConexionDataB getInstancia() {
        if (instancia == null) {
            instancia = new ConexionDataB();
        }
        return instancia;
    }

    public Connection conectar() throws Exception {
        if (cn == null || cn.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "Yosmal.28");
                System.out.println("Conectado");
            } catch (Exception e) {
                System.out.println("Error de conexión a la base de datos: " + e);
                
            }
        }
        return cn;
    }

    public void cerrarConexion() throws Exception {
        if (cn != null && !cn.isClosed()) {
            cn.close();
            System.out.println("Conexión cerrada");
        }
    }
    
    public void actualizarUsuario(String idUsuario, String nombre, String apellido, String telefono, String correo, String usuario, String contrasena) {
        String consulta = "UPDATE User SET Nombre = ?, Apellido = ?, Telefono = ?, Correo = ?, Usuario = ?, Contrasena = ? WHERE ID = ?";
        try (Connection conexion = conectar();
             PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, telefono);
            ps.setString(4, correo);
            ps.setString(5, usuario);
            ps.setString(6, contrasena);
            ps.setString(7, idUsuario);

            ps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar el usuario: " + e.getMessage());
        }
    }
}
