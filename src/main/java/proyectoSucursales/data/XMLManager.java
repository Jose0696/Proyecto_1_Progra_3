/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoSucursales.data;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class XMLManager {
    private String path;
    private static XMLManager theInstance;
    public static XMLManager instance(){
        if (theInstance==null){ 
            theInstance=new XMLManager("proyectoSucursales.xml");
        }
        return theInstance;
    }
    
    public XMLManager(String p) {
            path=p;         
    }  
    
    public Data load() throws Exception{
        JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
        FileInputStream is = new FileInputStream(path);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Data result = (Data) unmarshaller.unmarshal(is);
        is.close();
        return result;        
    }
    
    public void store(Data data)throws Exception{
        JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);  
        FileOutputStream os = new FileOutputStream(path);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.marshal(data, os);
        os.flush();
        os.close();     
    } 
}