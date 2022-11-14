
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
    
    public void mov(){
    
        String str = entrada;           
        
        for(int i=0;i<str.length();i++){
            
            if(str.equals("declarar"))printline("mov");
            
            if(str.substring(1, 10).equals("64")){
                printline("rax");
                printline("rbx");
                printline("rcx");
                printline("rdx");                                       
            }
                                        
            printline("ax");
            printline("bx");            
            printline("cx");
            printline("dx");
        }
    }
    
    public void jump(){
    
        String str = entrada;           
        
        for(int i=0;i<str.length();i++){
            
            if(str.equals(">"))printline("jg");
            if(str.equals(">="))printline("jge");
            if(str.equals("<"))printline("jl");
            if(str.equals("<="))printline("jle");
            if(str.equals("=="))printline("je");
            if(str.equals("!="))printline("jne");
            
            for(int j=0;j<18;j++){
                printline("LC"+i);
            }
                                        
            printline("add");
            printline("mov");                        
        }
    }                 
    public void ascii(){
                        
        String str = entrada;
        
        printline(".text");
        
        for(int i=0;i<str.length();i++){
            
            switch (str){
                
                case "33":
                    printline("!");
                 break;                    
                 
                case "34":
                    printline("\"");
                  break;                                        
                case "35":
                    printline("#");
                  break;                                        
                case "36":
                    printline("$");
                   break;                                    
                case "37":
                    printline("%");
                   break;                     
                case "38":
                    printline("&");
                   break; 
                case "39":    
                    printline("'");
                    break;
                case "40":    
                    printline("(");
                    break;
                case "41":
                    printline(")");
                    break;
                case "42":    
                    printline("*");
                    break;
                case "43":    
                    printline("+");
                    break;
                case "44":    
                    printline(",");
                    break;
                case "45":    
                    printline("-");
                    break;
                case "46":    
                    printline(".");
                    break;
                case "47":    
                    printline("/");
                    break;
                case "48":    
                    printline("0");
                    break;
                case "49":    
                    printline("1");
                    break;
                case "50":    
                    printline("2");
                    break;
                case "51":    
                    printline("3");
                    break;                    
                case "52":
                    printline("4");
                    break;
                case "53":    
                    printline("5");
                    break;
                case "54":    
                    printline("6");
                    break;
                case "55":    
                    printline("7");
                    break;
                case "56":    
                    printline("8");
                    break;
                case "57":    
                    printline("9");
                    break;                                  
                    
            }
            
        }                                
    }
    
    
     public void call(){
                        
        String str = entrada;
        
        printline(".def global main");
        
        for(int i=0;i<str.length();i++){
            
            switch (str){
                
                case "33":
                    printline("05x10");
                 break;                    
                 
                case "34":
                    printline("05x18");
                  break;                                        
                case "35":
                    printline("05x1b");
                  break;                                        
                case "36":
                    printline("05x11");
                   break;                                    
                case "37":
                    printline("05x1c");
                   break;                     
                case "38":
                    printline("05x1f");
                   break; 
                case "39":    
                    printline("add");
                    break;
                case "40":    
                    printline("05x19");
                    break;
                case "41":
                    printline("05xb0");
                    break;
                case "42":    
                    printline("05xc0");
                    break;
                case "43":    
                    printline("05xd0");
                    break;
                case "44":    
                    printline("05xd2");
                    break;
                case "45":    
                    printline("05xe8");
                    break;
                    
            }
            
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

    private void printline(String a) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
  
    
}
