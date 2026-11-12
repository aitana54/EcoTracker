package com.asix.ecotracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    //variables and objects
    @FXML
    private Button co2_chart_home_btn,activity_display_home_btn,activities_home_btn,co2_chart_to_home_btn,
            activities_new_act_btn,activities_del_act_btn,activities_to_home_btn,new_activity_to_activity_menu,
            del_act_menu_btn,login_register,display_act_to_home,home_exit_btn;
    @FXML
    private WebView co2_chart_web_view_data;
    @FXML
    private ChoiceBox<String> new_act_choice_box_cat,del_act_choice_box;

    @FXML
    private TextField login_user,login_passwd,register_username,register_passwd,register_passwd2, new_act_text_area_name, new_act_quantity_field;

    @FXML
    private TextArea new_act_text_area_desc;

    @FXML
    private TableView<Sostenibilitat> del_act_table;
    @FXML
    private TableColumn<Sostenibilitat, Integer> idColumn;
    @FXML
    private TableColumn<Sostenibilitat, String> nombreColumn,fechaColumn,categoriaColumn;
    @FXML
    private TableColumn<Sostenibilitat, Double> kmColumn,kgColumn,horesColumn,co2Column,teletreballColumn;


    //variables show_Activity
    @FXML
    private TableView<Sostenibilitat> taula_activitats;
    @FXML
    private TableColumn<Sostenibilitat, String> nomShowColumn,dataShowColumn,categoriaShowColumn;
    @FXML
    private TableColumn<Sostenibilitat, Double> kmShowColumn,kgShowColumn,horesShowColumn, teletreballShowColumn, descripcioShowColumn,co2ShowColumn;
    @FXML
    private Label total_co2_estalviat;
    @FXML
    private Button showHomeButton, showExportCSV,showExportTXT;



    // for scene changes
    private Stage stage;
    private static Scene scene;
    private Parent root;

    //Connection
    private static Connection conn;

    //queries
    static String choicebox_categories="select nombre_categoria from echotracker.categories;";
    static String choicebox_ids="select id from sostenibilitat;";
    static String delAct="DELETE FROM sostenibilitat WHERE id = ?;\n";

    //regex
    String empty_textfield= "[^\\d\\w\s]"; // comprova que no hu hagui ningun caracter o numero
    String is_number = "\\d+"; // comprova que nomes hi hagui numeros

    //functions
    /**
     * inicialitza totes aquelles funcions que es necessari que s'implementin al principi de tot, just despres de que el programa s'executi
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialize triggered");
        try {
            conn = connect_to_bd();
            co2_data_chart();
            fillCategories();
            show_act_config();
            activity_display();
            Del_act_config();
            Llista_Sostenibilitat();
            fillIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void popupINFO(String message, String header) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informació");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait(); // Mostra el popup i espera fins que l'usuari tanqui la finestra
    }
    /**
     * Aquesta funció servira per indicar missatges rapids a l'usuari per a alertar de algun possible error
     * @param message
     */
    public void popupWARNING(String message, String header) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Informació");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait(); // Mostra el popup i espera fins que l'usuari tanqui la finestra
    }
    /**
     * Aquesta funció servira per indicar missatges rapids a l'usuari per a alertar a l'usuari d'un error
     * @param message
     */
    public static void popupERROR(String message, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait(); // Mostra el popup i espera fins que l'usuari tanqui la finestra
    }

    //navigation
    /**
     * Canvia entre formularis segons el fitxer FXML especificat. Carrega el nou formulari i actualitza la interfície gràfica.
     * @param fxmlFile
     * @param event
     */
    public void formswitch(ActionEvent event,String fxmlFile) throws IOException {

        try {
            root = FXMLLoader.load(getClass().getResource(fxmlFile));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load FXML file: " + fxmlFile);
        }
    }

    /**
     * Gestiona la navegació entre formularis depenent del botó que s'hagi clicat.
     * @param event
     * @throws IOException
     */
    public void sceneBuild(ActionEvent event) throws IOException {

        if (event.getSource() == co2_chart_home_btn){ // home --> c02 chart
            System.out.println("home --> c02 chart");
            formswitch(event, "co2_chart.fxml");
        }else if(event.getSource() == activity_display_home_btn){ // home --> activity display
            System.out.println("home --> activity display");
            formswitch(event,"show_activities.fxml");
        }else if (event.getSource() == activities_home_btn) { // home --> activities
            System.out.println("home --> activities");
            formswitch(event,"activities_menu.fxml");
        }else if (event.getSource() == co2_chart_to_home_btn) { // co2chart --> home
            System.out.println("co2chart --> home");
            formswitch(event,"hello-view.fxml");
        }else if (event.getSource() == activities_to_home_btn) { // activities --> home
            System.out.println("activities --> home");
            formswitch(event,"hello-view.fxml");
        }else if (event.getSource() == activities_new_act_btn) { // activities --> new activity
            System.out.println("activities --> new activity");
            formswitch(event,"new_activity.fxml");
        }else if (event.getSource() == new_activity_to_activity_menu) { // new activities --> activity
            System.out.println("new activities --> activity");
            formswitch(event,"activities_menu.fxml");
        }else if (event.getSource() == del_act_menu_btn) { // del activities --> activity
            System.out.println("del activities --> activity");
            formswitch(event,"activities_menu.fxml");
        }else if (event.getSource() == activities_del_act_btn) { // activities --> del activity
            System.out.println("new activities --> activity");
            formswitch(event,"del_act.fxml");
        }else if (event.getSource() == login_register) { // login-->register
            System.out.println("login-->register");
            formswitch(event,"register.fxml");
        }else if (event.getSource() == home_exit_btn) { // menu-->login del_act_to_menu
            System.out.println("menu-->login");
            formswitch(event,"login.fxml");

        }else if (event.getSource() == showHomeButton){ // show_activies --> home
            System.out.println("show_activies --> activity display");
            formswitch(event,"hello-view.fxml");
        }

    }
    public String generateCO2ChartHtml() {
        return """
        <html>
        <head>
            <style>
                body { font-family: Arial, sans-serif; margin: 10px; }
                table { border-collapse: collapse; width: 100%; margin-top: 20px;
                        box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
                th { background-color: #4CAF50; color: white; padding: 10px; text-align: left; }
                td { padding: 10px; border-bottom: 1px solid #ddd; }
                tr:nth-child(even) { background-color: #f2f2f2; }
                tr:hover { background-color: #e0e0e0; }
                .co2-cell { font-weight: bold; color: #d32f2f; }
            </style>
        </head>
        <body>
            <table>
                <tr><th>Activitat</th><th>CO₂ estalviat (1kg/H/Km)</th></tr>
                <tr><td>Ciclisme</td><td class="co2-cell">0.24</td></tr>
                <tr><td>Teletreball</td><td class="co2-cell">0.225</td></tr>
                <tr><td>Reciclar 1 kg de paper</td><td class="co2-cell">1.0</td></tr>
                <tr><td>Estalvi llum</td><td class="co2-cell">0.15</td></tr>
            </table>
        </body>
        </html>
        """;
    }

    public void co2_data_chart() {
        if (co2_chart_web_view_data == null || co2_chart_web_view_data.getEngine() == null) {
            System.out.println("WebView or WebEngine is null");
            return;
        }
        co2_chart_web_view_data.getEngine().loadContent(generateCO2ChartHtml());
    }

    public static Connection connect_to_bd() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/echotracker", "root", "");
    }
    @FXML
    public void fillCategories(){
        System.out.println("filling categories");
        if (new_act_choice_box_cat == null){
            System.out.println("ChoiceBox is null");
            return;
        }
        try(PreparedStatement stmt = conn.prepareStatement(choicebox_categories)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()){
                System.out.println("result: "+ result.getString("nombre_categoria"));
                new_act_choice_box_cat.getItems().add(result.getString("nombre_categoria"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    public void fillIds(){
        System.out.println("filling ids");
        if (del_act_choice_box == null){
            System.out.println("ChoiceBox is null");
            return;
        }
        try(PreparedStatement stmt = conn.prepareStatement(choicebox_ids)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()){
                System.out.println("result: "+ result.getString("id"));
                del_act_choice_box.getItems().add(result.getString("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void store_data_in_db() {
        // Validate that a category has been selected
        if (new_act_choice_box_cat.getValue() == null) {
            popupERROR("Por favor, selecciona una categoría.", "Error de datos");
            return;
        }

        // Validate that fields are not empty and that quantity is a number
        if (!new_act_quantity_field.getText().matches(is_number) ||
                new_act_quantity_field.getText().matches(empty_textfield) ||
                new_act_text_area_desc.getText().matches(empty_textfield) ||
                new_act_text_area_name.getText().matches(empty_textfield)) {
            popupERROR("Campos incompletos o erróneos.", "Error de datos");
            return;
        }

        String categoria = new_act_choice_box_cat.getValue();
        String nombre = new_act_text_area_name.getText();
        String descripcion = new_act_text_area_desc.getText();
        String cantidad = new_act_quantity_field.getText();

        // Determine the appropriate SQL insert statement based on the category
        String insert_query = "INSERT INTO sostenibilitat (nombre, fecha, categoria, km,kg, llums_hora_estimada, teletreball, descripcio,co2_total) " +
                "VALUES (?, sysdate(), (SELECT categoria_id FROM categories WHERE nombre_categoria = ?), ?, ?, ?, ?,?,(select c02_estimat*? from categories where nombre_categoria = ?));";

        // Initialize parameters
        String km = null;
        String llums_hora_estimada = null;
        String teletreball = null;
        String kg = null;

        switch (categoria) {
            case "Ciclisme":
                km = cantidad;
                break;
            case "Estalvi llum":
                llums_hora_estimada = cantidad;
                break;
            case "Teletreball":
                teletreball = cantidad;
                break;
            case "Reciclar 1 kg de paper":
                // Assuming 'Reciclar 1 kg de paper' corresponds to 'llums_hora_estimada'
                kg = cantidad;
                break;
            default:
                popupERROR("Categoría no reconocida.", "Error de datos");
                return;
        }

        try (PreparedStatement stmt = conn.prepareStatement(insert_query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, categoria);
            stmt.setString(3, km);
            stmt.setString(4, kg);
            stmt.setString(5, llums_hora_estimada);
            stmt.setString(6, teletreball);
            stmt.setString(7, descripcion);
            if (km!=null){stmt.setString(8, km);}
            if (llums_hora_estimada!=null){stmt.setString(8, llums_hora_estimada);}
            if (teletreball!=null){stmt.setString(8, teletreball);}
            if (kg!=null){stmt.setString(8, kg);}
            stmt.setString(9, categoria);
            System.out.println(insert_query);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                popupINFO("Actividad registrada correctamente", "");
                new_act_text_area_name.clear();
                new_act_text_area_desc.clear();
                new_act_quantity_field.clear();
            } else {
                popupERROR("Algo ha ido mal en la inserción de datos", "");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void LOGIN(ActionEvent event) {

        String login = "select username, passwd from users where username = ? and passwd = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(login)) {
            stmt.setString(1, login_user.getText());
            stmt.setString(2, String.valueOf(login_passwd.getText().hashCode()));
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                Parent newRoot = FXMLLoader.load(HelloController.class.getResource("hello-view.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(newRoot));
                stage.show();
            }else{
                popupERROR("No existe ese usuario o no has puesto \nbien el usuario o la contraseña","");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void register(){
        if (register_username.getText().isEmpty()||
                register_passwd.getText().isEmpty() ||
                register_passwd2.getText().isEmpty()){
            popupERROR("Campos vacios","");
            return;
        }
        if (!register_passwd.getText().equals(register_passwd2.getText())){
            popupERROR("Las contraseñas no son iguales","");
            return;
        }
        String register = "insert into users (username, passwd) values(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(register)) {
            stmt.setString(1, register_username.getText());
            stmt.setString(2, String.valueOf(register_passwd2.getText().hashCode()));
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                popupINFO("Usuari registrat correctament","");
                Parent newRoot = FXMLLoader.load(HelloController.class.getResource("login.fxml"));
                Scene currentScene = scene;
                currentScene.setRoot(newRoot);
            }else{
                popupERROR("Error en el registro","");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Llista_Sostenibilitat() throws SQLException {
        if (del_act_table == null){
            System.out.println("tabla del is null");
            return;
        }
        String query = "SELECT s.id, s.nombre, s.fecha, c.nombre_categoria, s.km, s.kg, s.llums_hora_estimada,s.teletreball, " +
                "s.descripcio, s.co2_total FROM sostenibilitat as s, categories as c where s.categoria = c. categoria_id;";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Sostenibilitat sostenibilidad = new Sostenibilitat(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getTimestamp("fecha"),
                        rs.getString("nombre_categoria"),
                        rs.getDouble("km"),
                        rs.getDouble("kg"),
                        rs.getDouble("llums_hora_estimada"),
                        rs.getInt("teletreball"),
                        rs.getString("descripcio"),
                        rs.getDouble("co2_total")
                );
                del_act_table.getItems().add(sostenibilidad);
            }
        }
    }

    public void Del_act_config() {
        if (idColumn != null) {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        }
        if (nombreColumn != null) {
            nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        }
        if (fechaColumn != null) {
            fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        }
        if (categoriaColumn != null) {
            categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        }
        if (kmColumn != null) {
            kmColumn.setCellValueFactory(new PropertyValueFactory<>("km"));
        }
        if (kgColumn != null) {
            kgColumn.setCellValueFactory(new PropertyValueFactory<>("kg"));
        }
        if (horesColumn != null) {
            horesColumn.setCellValueFactory(new PropertyValueFactory<>("llumsHoraEstimada")); // Usa el nombre correcto del getter
        }
        if (teletreballColumn != null) {
            teletreballColumn.setCellValueFactory(new PropertyValueFactory<>("teletreball"));
        }
        if (co2Column != null) {
            co2Column.setCellValueFactory(new PropertyValueFactory<>("co2Total"));
        }
    }



    public void DelAct(){
        if ( del_act_choice_box.getValue() == null) {
            popupERROR("Por favor, selecciona un ID.", "Error de datos");
            return;
        }

        String register = del_act_choice_box.getValue();

        try (PreparedStatement stmt = conn.prepareStatement(delAct)) {

            stmt.setString(1, register);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                popupINFO("Actividad con ID:"+ register+" Ha sido Borrada","");
            }else{
                popupERROR("No existe ese usuario o no has puesto \nbien el usuario o la contraseña","");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }        

    //Show_Activities

    /**
     * Metode que mostra les activitats fetas i l'estalvi de Co2
     * @throws SQLException
     */
    public void activity_display() throws SQLException {
        if (taula_activitats == null){
            System.out.println("table activity display is null");
            return;
        }
        if (total_co2_estalviat == null){
            System.out.println("label co2 null");
            return;
        }
        float co2_tot= 0.0F;
        String query = "SELECT s.nombre, s.fecha, c.nombre_categoria, s.km, s.kg, s.llums_hora_estimada,s.teletreball, " +
                "s.descripcio, s.co2_total FROM sostenibilitat as s, categories as c where s.categoria = c. categoria_id;";
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery(query)) {

            while (rs.next()) {
                Sostenibilitat sostenibilidad = new Sostenibilitat(
                        rs.getString("nombre"),
                        rs.getTimestamp("fecha"),
                        rs.getString("nombre_categoria"),
                        rs.getDouble("km"),
                        rs.getDouble("kg"),
                        rs.getDouble("llums_hora_estimada"),
                        rs.getInt("teletreball"),
                        rs.getString("descripcio"),
                        rs.getDouble("co2_total")
                );
                co2_tot+=Float.valueOf((float) rs.getDouble("co2_total"));
                taula_activitats.getItems().add(sostenibilidad);
            }
            total_co2_estalviat.setText(String.valueOf(co2_tot));
        }
    }

    /**
     * Metode per exportar la taula a CSV
     */
    public void exportCSV(){
        String userHome = System.getProperty("user.home");
        String rutaDescarregaCSV = userHome + File.separator + "Downloads" + File.separator + "co2Activities.csv";

        String rutaCSVMySQL = rutaDescarregaCSV.replace("\\", "/");


        String queryExportCSV = "SELECT s.nombre, s.fecha, c.nombre_categoria, s.km, s.kg, s.llums_hora_estimada, s.teletreball, " +
                "s.descripcio, s.co2_total " +
                "INTO OUTFILE '" + rutaCSVMySQL + "' " +
                "FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\n' " +
                "FROM sostenibilitat AS s, categories AS c WHERE s.categoria = c.categoria_id;";

        try(PreparedStatement stmt = conn.prepareStatement(queryExportCSV)){
            stmt.executeQuery(queryExportCSV);
            File archivo = new File(rutaCSVMySQL);
            if (!archivo.exists()){
                popupERROR("L'arxiu no s'ha generat correctament", "");
            }else{
                popupINFO("L'arxiu s'ha generat correctament", "");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metode per exportar la taula a TXT
     */
    public void exportTXT(){
        String userHome = System.getProperty("user.home");
        String rutaDescarregaTXT = userHome + File.separator + "Downloads" + File.separator + "co2Activities.txt";

        String rutaTXTMySQL = rutaDescarregaTXT.replace("\\", "/");

        String queryExportTXT = "SELECT s.nombre, s.fecha, c.nombre_categoria, s.km, s.kg, s.llums_hora_estimada, s.teletreball, " +
                "s.descripcio, s.co2_total " +
                "INTO OUTFILE '" + rutaTXTMySQL + "' " +
                "FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\n' " +
                "FROM sostenibilitat AS s, categories AS c WHERE s.categoria = c.categoria_id;";

        try(PreparedStatement stmt = conn.prepareStatement(queryExportTXT)){
            stmt.executeQuery(queryExportTXT);
            File archivo = new File(rutaTXTMySQL);
            if (!archivo.exists()){
                popupERROR("L'arxiu no s'ha generat correctament", "");
            }else{
                popupINFO("L'arxiu s'ha generat correctament", "");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void show_act_config(){
        if (nomShowColumn != null) {
            nomShowColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        }
        if (dataShowColumn != null) {
            dataShowColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        }
        if (categoriaShowColumn != null) {
            categoriaShowColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        }
        if (kmShowColumn != null) {
            kmShowColumn.setCellValueFactory(new PropertyValueFactory<>("km"));
        }
        if (kgShowColumn != null) {
            kgShowColumn.setCellValueFactory(new PropertyValueFactory<>("kg"));
        }
        if (horesShowColumn != null) {
            horesShowColumn.setCellValueFactory(new PropertyValueFactory<>("llumsHoraEstimada")); // Usa el nombre correcto del getter
        }
        if (teletreballShowColumn != null) {
            teletreballShowColumn.setCellValueFactory(new PropertyValueFactory<>("teletreball"));
        }
        if (descripcioShowColumn != null) {
            descripcioShowColumn.setCellValueFactory(new PropertyValueFactory<>("descripcio"));
        }
        if (co2ShowColumn != null) {
            co2ShowColumn.setCellValueFactory(new PropertyValueFactory<>("co2Total"));
        }
    }

}