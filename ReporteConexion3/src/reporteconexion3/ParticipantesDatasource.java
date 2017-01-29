/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporteconexion3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

/**
 *
 * @author Anabel
 */
public class ParticipantesDatasource implements JRDataSource{
    
    private List<Participante> listaParticipantes = new ArrayList<>();
    private int indiceParticipanteActual = -1;

    
    @Override
    public boolean next() throws JRException {
        return ++indiceParticipanteActual<listaParticipantes.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;
        
        if("id".equals(jrf.getName())) {
            valor = listaParticipantes.get(indiceParticipanteActual).getId();
        } else if("nombre".equals(jrf.getName())) {
            valor = listaParticipantes.get(indiceParticipanteActual).getNombre();
        } else if("username".equals(jrf.getName())) {
            valor = listaParticipantes.get(indiceParticipanteActual).getUsername(); 
        } else if("password".equals(jrf.getName())) {
            valor = listaParticipantes.get(indiceParticipanteActual).getPassword();
        } else if("comentarios".equals(jrf.getName())) {
            valor = listaParticipantes.get(indiceParticipanteActual).getComentarios();
        }
        
        return valor; 
    }
    
     public void addParticipante(Participante participante){
        this.listaParticipantes.add(participante);
    }
     
    public static void main(String[]args){
        
        try {
            List<Participante> listaPariticipantes = new ArrayList<Participante>();

            for (int i = 1; i<= 12; i++) {
                Participante p = new Participante(i, "Particpante " + i, "Usuario " + i, "Pass" + i, "Comentarios para " + i);
                p.setPuntos(i);
                listaPariticipantes.add(p);               
        } 
            
            JasperReport reporte = (JasperReport)
            JRLoader.loadObjectFromFile("report3.jasper");
            
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("autor", "Ana");
            parametros.put("titulo", "Reporte Participantes");
                        
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros,  new JRBeanCollectionDataSource(listaPariticipantes));
            
            Exporter exporter = new JRPdfExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new java.io.File("reporteSesion3PDF.pdf")));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(configuration);
            exporter.exportReport();
      
        } catch (JRException ex) {
            Logger.getLogger(ParticipantesDatasource.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
}
