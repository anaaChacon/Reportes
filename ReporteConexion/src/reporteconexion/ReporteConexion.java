/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporteconexion;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterConfiguration;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.view.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Anabel
 */
public class ReporteConexion{

    /**
     * @param args the command line arguments
     */
   public static void main(String[] args){
    try{
        Class.forName("com.mysql.jdbc.Driver");
        Connection conexion = (Connection)
        DriverManager.getConnection("jdbc:mysql://localhost:3306/infomesdi","root", "campus");
        
        JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile("report1.jasper");
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, conexion);

        Exporter exporter =  new JRPdfExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new java.io.File("reporteSesion1PDF.pdf")));
        SimplePdfExporterConfiguration configuration = new
        SimplePdfExporterConfiguration();
        exporter.setConfiguration(configuration);
        exporter.exportReport();
        
        Exporter exporter1 = new HtmlExporter();
        exporter1.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter1.setExporterOutput(new SimpleHtmlExporterOutput(new
        java.io.File("reporteSesion1PDF.html")));
        SimpleHtmlExporterConfiguration configuration2 = new SimpleHtmlExporterConfiguration();
        exporter1.setConfiguration(configuration2);
        exporter1.exportReport();
        
        // JFrame con una vista del pdf generado
            JFrame frame = new JFrame("Reporte");
            frame.getContentPane().add(new JRViewer(jasperPrint));
            frame.pack();
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        
        

        } catch (ClassNotFoundException | SQLException | JRException ex) {
            Logger.getLogger(ReporteConexion.class.getName()).log(Level.SEVERE,null, ex);
        }
        

    }
    
}
