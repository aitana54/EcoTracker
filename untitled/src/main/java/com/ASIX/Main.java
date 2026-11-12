package com.ASIX;


import java.sql.*;
import java.util.Scanner;

public class Main {
    public static Scanner input = new Scanner(System.in);
    static String url = "jdbc:mysql://localhost:3306/login_users_crud";
    static String user = "root";
    static String pass = "";
    public static void main(String[] args) {
        try {
            Connection conn = Connect(url,user,pass);
            init_menu(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection Connect(String url, String user, String pass) throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
    public static void init_menu(Connection conn){
        System.out.println("1-Login" +"\n"+
                                   "2-Registre\n" +
                                   "3-Exit");
        String option = input.nextLine();
        switch (option){
            case "1":
                String[] loginresult=Login(conn,input);
                if(loginresult[0].equals("true")){
                    if (loginresult[1].equals("1")){
                        AdminPannel(conn);
                        init_menu(conn);
                    }
                    System.out.println("login successfull");
                }else{
                    System.out.println("NO LOGIN :(");
                }
                init_menu(conn);
                break;
            case "2":
                Register(conn);
                init_menu(conn);
                break;
            case "3":
                System.exit(0);
                break;
            default:
                System.out.println("invalid option");
                break;
        }
    }
    public static String[] Login(Connection conn, Scanner input){
        String[] result= {"false","0"};
        System.out.println("Usuari:");
        String username = input.nextLine();
        System.out.println("Contrassenya");
        String passwd = input.nextLine();
        String login = "select username, passwd_hash, banned from login_users_crud.users where username = ? and passwd_hash = ?";
        try (PreparedStatement stmt = conn.prepareStatement(login)) {
            stmt.setString(1, username);
            stmt.setString(2, String.valueOf(passwd.hashCode()));
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                if (res.getBoolean("banned")==false){
                    result[0] = "true";
                    if (res.getString("username").equals("admin")){
                        result[1]="1";
                    }else {
                        result[1]="0";
                    }
                        return result;
                }else {
                    return result;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void Register(Connection conn) {
        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password1 = input.nextLine();
        System.out.print("Repeat Password: ");
        String password2 = input.nextLine();
        String email="";
        boolean emailcheck=true;
        while (emailcheck){
            System.out.print("enter email: ");
            email = input.nextLine();
            if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
                emailcheck=false;
            }

        }

        if (!password1.equals(password2)) {
            System.out.println("Passwords do not match. Try again.");
            return;
        }

        String insert = "INSERT INTO users (username, passwd_hash,creation_date,email,banned) VALUES (?, ?,sysdate(),?,false)";
        try (PreparedStatement stmt = conn.prepareStatement(insert)) {
            stmt.setString(1, username);
            stmt.setString(2, String.valueOf(password1.hashCode()));
            stmt.setString(3, email);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User registered successfully.");
            } else {
                System.out.println("Registration failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void AdminPannel(Connection conn){
        System.out.println("1-Modificar Usuari\n" +
                                   "2-Eliminar Usuari\n" +
                                   "3-Mostrar usuaris\n" +
                                   "4-Crear Usuari\n" +
                                   "5-Sortir");
        String option = input.nextLine();
        switch (option){
            case "1":
                modificarUsuario(conn);
                AdminPannel(conn);
                break;
            case "2":
                eliminarUsuario(conn);
                AdminPannel(conn);
                break;
            case "3":
                mostrarUsuarios(conn);
                AdminPannel(conn);
                break;
            case "4":
                crearUsuario(conn);
                AdminPannel(conn);
                break;
            case "5":
                init_menu(conn);
                break;
            default:
                System.out.println("Opcion no valida");
                AdminPannel(conn);
                break;
        }
    }

    public static boolean modificarUsuario(Connection conn) {
        try {
            System.out.print("Introduce el ID del usuario que quieres modificar: ");
            int id = Integer.parseInt(input.nextLine());

            System.out.print("Nuevo nombre de usuario: ");
            String nuevoUsername = input.nextLine();

            System.out.print("Nuevo email: ");
            String nuevoEmail = input.nextLine();

            System.out.print("Nueva contraseña: ");
            String nuevaPass = input.nextLine();
            String nuevaPassHash = String.valueOf(nuevaPass.hashCode());

            System.out.print("¿Está baneado? (true/false): ");
            boolean nuevoBanned = Boolean.parseBoolean(input.nextLine());

            String update = "UPDATE users SET username = ?, email = ?, passwd_hash = ?, banned = ? WHERE id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(update)) {
                stmt.setString(1, nuevoUsername);
                stmt.setString(2, nuevoEmail);
                stmt.setString(3, nuevaPassHash);
                stmt.setBoolean(4, nuevoBanned);
                stmt.setInt(5, id);

                int filasAfectadas = stmt.executeUpdate();
                return filasAfectadas > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al modificar usuario: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("ID inválido.");
        }
        return false;
    }
    public static boolean crearUsuario(Connection conn) {
        try {
            System.out.print("Nombre de usuario: ");
            String username = input.nextLine();

            System.out.print("Correo electrónico: ");
            String email = input.nextLine();

            System.out.print("Contraseña: ");
            String passwd = input.nextLine();
            String passwdHash = String.valueOf(passwd.hashCode());

            java.sql.Date creationDate = new java.sql.Date(System.currentTimeMillis());

            String insert = "INSERT INTO users (username, passwd_hash, creation_date, email, banned) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(insert)) {
                stmt.setString(1, username);
                stmt.setString(2, passwdHash);
                stmt.setDate(3, creationDate);
                stmt.setString(4, email);
                stmt.setBoolean(5, false); // Por defecto, no baneado

                int filasAfectadas = stmt.executeUpdate();
                return filasAfectadas > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al crear usuario: " + e.getMessage());
        }
        return false;
    }
    public static boolean eliminarUsuario(Connection conn) {
        try {
            System.out.print("ID del usuario a eliminar: ");
            int id = Integer.parseInt(input.nextLine());

            String delete = "DELETE FROM users WHERE id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(delete)) {
                stmt.setInt(1, id);
                int filasAfectadas = stmt.executeUpdate();
                return filasAfectadas > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("ID inválido.");
        }
        return false;
    }
    public static void mostrarUsuarios(Connection conn) {
        String consulta = "SELECT id, username, email, creation_date, banned FROM users";

        try (Statement stmt = conn.createStatement();
             ResultSet res = stmt.executeQuery(consulta)) {

            System.out.println("ID | Usuario | Email | Fecha de creación | Baneado");
            System.out.println("------------------------------------------------------");

            while (res.next()) {
                int id = res.getInt("id");
                String username = res.getString("username");
                String email = res.getString("email");
                Date creationDate = res.getDate("creation_date");
                boolean banned = res.getBoolean("banned");

                System.out.printf("%d | %s | %s | %s | %s%n",
                                  id, username, email, creationDate.toString(), banned ? "Sí" : "No");
            }
        } catch (SQLException e) {
            System.err.println("Error al mostrar usuarios: " + e.getMessage());
        }
    }


}