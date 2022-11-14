
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author erick
 */
public class GeneradorObjeto {
    
    private String entrada;
    private String salida;
    private String codigoObjeto;

    public GeneradorObjeto(String entrada, String salida) {
        this.entrada = entrada;
        this.salida = salida;
    }
    
    
    public void ascii(){
        
        
        
        String str = entrada;
        
        for(int i=0;i<str.length();i++){
            
           
            
        }
        
        
        
        
    }
    
    public void compilar(){
                      
        FileReader file = null;
        
        try{
            
        file = new FileReader(entrada);
        BufferedReader buffer = new BufferedReader(file);
        String nombre = salida;         
        nombre += ".cpp";
        String bfread;
        String temp = "";
        
        
        FileWriter filewriter = new FileWriter(nombre);
        BufferedWriter bufferwriter = new BufferedWriter(filewriter);
        PrintWriter printwriter = new PrintWriter(bufferwriter);
        
        
         printwriter.write("#include <iostream>\n\n"
                 + "#include <wchar.h>\n\n"
                 + "#include <locale.h>\n\n"
                 + "using namespace std;\n\n");
         
         while((bfread = buffer.readLine())!=null){
          
         bfread+=" ";    
             
         if(bfread.contains("principal")){
             printwriter.write("int main()\n\n");
             if(bfread.contains("{"))printwriter.write("{");
         }
         
         
         else if(bfread.contains("declarar")||bfread.contains("asignacion")||bfread.contains("declarar_a")
                 ||bfread.contains("asignacion_a")){
                                       
             for(int i = 0;i<bfread.length();i++){
                                                   
                 if(bfread.charAt(i)!=' '){
                        temp+=bfread.charAt(i);
                  }
                                                                
                 
                 else{                                          
                     
                     if(temp.equals("declarar")||temp.equals("asignacion")||temp.equals("declarar_a")||temp.equals("asignacion_a")){
                         printwriter.write("");
                         temp = "";
                     }
                     
                     else if(temp.equals("entero")){
                         printwriter.write("int ");
                         temp = "";
                     }
                     else if(temp.equals("flotante")){
                         printwriter.write("float ");
                         temp = "";
                     }
                     else if(temp.equals("cadena")){
                         printwriter.write("string ");
                         temp = "";
                     }
                     else if(temp.equals("caracter")){
                         printwriter.write("char ");
                         temp = "";
                     }
                     else if(temp.equals("bool")){
                         printwriter.write("bool ");
                         temp = "";
                     }                                                                                    
                     else {
                         printwriter.write(temp);
                         temp = "";
                     }
                     
                 }                                  
             }                                       
         }
         
         else if(bfread.contains("imprimir")){                         
             
             boolean leer = false;             
             
             for(int i = 0; i<bfread.length();i++){
                 
                 if(bfread.charAt(i)=='(')leer= true;
                 
                 if(bfread.charAt(i)!='('&& bfread.charAt(i)!=')' && leer){
                     
                     temp+=bfread.charAt(i);                     
                 }                                                   
             }             
             
             printwriter.write("cout<<"+temp);
             temp="";
         }     
         
         else if(bfread.contains("leer")){                         
             
             boolean leer = false;             
             
             for(int i = 0; i<bfread.length();i++){
                 
                 if(bfread.charAt(i)=='(')leer= true;
                 
                 if(bfread.charAt(i)!='('&& bfread.charAt(i)!=')' && leer){
                     
                     temp+=bfread.charAt(i);                     
                 }                                                   
             }             
             
             printwriter.write("cin>>"+temp);
             temp="";
         }
         
         else if(bfread.contains("ciclo_for")){
             
             boolean leer = false;
             
             printwriter.write("for");
             
             for(int i = 0;i<bfread.length();i++){
                 
                 
                 
                 if(bfread.charAt(i)=='(')leer = true;
                 
                 if(leer)printwriter.write(bfread.charAt(i));
                 
             }             
         }
         
         else if(bfread.contains("ciclo_mientras")){
             
             boolean leer = false;
             
             printwriter.write("while");
             
             for(int i = 0;i<bfread.length();i++){
                 
                 
                 
                 if(bfread.charAt(i)=='(')leer = true;
                 
                 if(leer)printwriter.write(bfread.charAt(i));
                 
             }             
         }
         else if(bfread.contains("si") || bfread.contains("sino")||bfread.contains("sino si")  ){
             
             boolean leer = false;
             
             for(int i = 0;i<bfread.length();i++){
                      
                 if(!Character.isAlphabetic(bfread.charAt(i)) && !leer){
                     
                     if(temp.contains("sino si"))printwriter.write("else if");
                     
                     else if(temp.contains("sino"))printwriter.write("else");
                     
                     else if(temp.contains("si"))printwriter.write("if");
                     
                     temp="";
                     leer =true;
                 }
                 
                 
                 if(!leer){
                     temp+=bfread.charAt(i);
                 }
                 
                 if(leer){
                     temp+=bfread.charAt(i);
                 }
                 
                 
                 
             }
             printwriter.write(temp);
             temp="";
         }
         
         else if(bfread.contains("{")){
             printwriter.write(bfread);
         }         
         
         else if(bfread.contains("}")){
             printwriter.write(bfread);
         }
         
         
                     
             
             
         printwriter.write("\n");
        }
         printwriter.close();
         bufferwriter.close();
        }
        catch(IOException e){
            
        }
        
        
        
    }
    
    public void objeto(){
        
        try {
            Runtime.getRuntime().exec("cmd /c g++ "+salida+".cpp && ren a.exe "+salida+".exe  && g++ -S -o "+salida+"Objeto.txt "+salida+".cpp && erase "+salida+".cpp");
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(GeneradorObjeto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
  
    
}
