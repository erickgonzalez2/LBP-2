/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneradorIntermedio {

    private String entrada;
    private String salida;
    private int n_etiquetas = 0; 
    private ArrayList <Integer> finCiclo;

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public GeneradorIntermedio() {
    }

    public GeneradorIntermedio(String entrada,String salida,ArrayList<Integer>finCiclo) {
        this.entrada = entrada;
        this.salida = salida;
        this.finCiclo = finCiclo;
    }

    public void generar() {

        FileReader file = null;
        try {

            file = new FileReader(entrada);
            BufferedReader buffer = new BufferedReader(file);

            String nombre = salida;            
            nombre += "_generacion_intermedia.txt";

            String bfread;
            String temp = "";
            String condicionCiclo = "";
            String pasoCiclo = "";
            String inicioCiclo = "";

            FileWriter filewriter = new FileWriter(nombre);
            BufferedWriter bufferwriter = new BufferedWriter(filewriter);
            PrintWriter printwriter = new PrintWriter(bufferwriter);

            printwriter.write("//Generación de código intermedia\n");
            
            int nlines = 0;
            boolean llaveCiclo = false;

            while ((bfread = buffer.readLine()) != null) {
                
                nlines++;
                
                if(!finCiclo.isEmpty()){
                    
                    if(finCiclo.contains(nlines)){
                        
                        
                        printwriter.write(pasoCiclo+"\n");
                        pasoCiclo="";
                        
                        
                        printwriter.write(condicionCiclo+" ? ");
                        printwriter.write(" goto "+"L"+(n_etiquetas-1)+": goto L" +n_etiquetas+"\n\n");
                        printwriter.write("L"+n_etiquetas);
                        
                        condicionCiclo = "";
                        llaveCiclo = true;
                    }
                    
                }
                

                for (int i = 0; i < bfread.length(); i++) {
                    
                    

                    if (bfread.charAt(i) != ' ' && !llaveCiclo) {
                        temp += bfread.charAt(i);
                    }

                    if (bfread.charAt(i) == ' ' || i == bfread.length() - 1) {

                        //ANALIZAREMOS QUE TIPO DE INSTRUCCION ES
                        //DECLARACION DE VARIABLES
                        if (temp.equals("declarar") ||temp.equals("asignacion") ) {
                            printwriter.write("");
                        } 
                        
                        else if(temp.contains("imprimir")){
                            temp="";
                            printwriter.write(bfread);
                            break;
                        }
                        
                        else if (temp.equals("entero") || temp.equals("flotante") || temp.equals("cadena") || temp.equals("caracter")
                                || temp.equals("bool")) {
                            printwriter.write("");
                        }
                        
                        //REALIZACION DE NOTACION POLACA
                        else if (temp.equals("declarar_a") || temp.equals("asignacion_a")) {
                                                        
                            
                            String notacion = notacionPolaca(bfread.substring(i, bfread.length() - 1));
                            
                            for(int j = 0;j<notacion.length();j++){
                                
                                printwriter.write(notacion.charAt(j));
                        
                            }
                            temp="";
                            break;
                        } 
                        
                        else if(temp.contains("ciclo_for")){
                            
                            
                            int band = -1;
                            
                            for(int j = 0;j<temp.length();j++){
                                
                                if(band==0)inicioCiclo+=temp.charAt(j);
                                
                                else if(band==1&& temp.charAt(j)!=';')condicionCiclo+=temp.charAt(j);
                                
                                else if(band==2 && temp.charAt(j)!=')' && temp.charAt(j)!='{')pasoCiclo+=temp.charAt(j);
                                
                                if(temp.charAt(j)==';')band++;
                                
                                if(temp.charAt(j)=='(')band++;
                        
                                
                            }
                            printwriter.write(inicioCiclo+"\n\n");
                            inicioCiclo="";
                            n_etiquetas++;
                            printwriter.write("L"+n_etiquetas+":");
                            n_etiquetas++;
                            pasoCiclo+=";";
                            temp="";
                            break;
                        }
                        
                        else if(temp.contains("ciclo_mientras")){
                            
                            
                            int band = -1;
                            
                            for(int j = 0;j<temp.length();j++){                                                                                                                               
                                
                                if(band==0 && temp.charAt(j)!=')' && temp.charAt(j)!='{')condicionCiclo+=temp.charAt(j);
                                                                                                
                                if(temp.charAt(j)=='(')band++;                        
                                
                            }
                            
                            printwriter.write("\n");                            
                            n_etiquetas++;
                            printwriter.write("L"+n_etiquetas+":");
                            n_etiquetas++;                            
                            temp="";
                            break;
                            
                            
                            
                            
                        }
                        
                        
                        
                        
                        else {
                            printwriter.write(temp);
                               temp = "";
                        }
                        temp = "";

                    }
                        
                }

                printwriter.write("\n");
                if(llaveCiclo)llaveCiclo=false;
                        
            }
            printwriter.close();
            bufferwriter.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GeneradorIntermedio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeneradorIntermedio.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(GeneradorIntermedio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private String notacionPolaca(String linea) {

        String infija = "";
        char [] tn;
        String variable = "";
        String notacion = "";
        boolean leer = false;

        for (int i = 0; i < linea.length(); i++) {

            if (leer && linea.charAt(i)!=';') {
                infija += linea.charAt(i);
            }

            if (linea.charAt(i) != ' ' && linea.charAt(i) != '=' && !leer) {
                variable += linea.charAt(i);
            }

            if (linea.charAt(i) == '=') {
                leer = true;
            }

        }
        
        notacion = (String)infixToPrefix(infija.toCharArray());

        String result = variable + (" = ") + notacion;
        return result;

    }

    static boolean isalpha(char c) {
        if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
            return true;
        }
        return false;
    }

    static boolean isdigit(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        }
        return false;
    }

    static boolean isOperator(char c) {
        return (!isalpha(c) && !isdigit(c));
    }

    static int getPriority(char C) {
        if (C == '-' || C == '+') {
            return 1;
        } else if (C == '*' || C == '/') {
            return 2;
        } else if (C == '^') {
            return 3;
        }

        return 0;
    }

    // Reverse the letters of the word
    static String reverse(char str[], int start, int end) {

        // Temporary variable to store character
        char temp;
        while (start < end) {

            // Swapping the first and last character
            temp = str[start];
            str[start] = str[end];
            str[end] = temp;
            start++;
            end--;
        }
        return String.valueOf(str);
    }

     static String infixToPostfix(char[] infix1)
  {
    String infix = '(' + String.valueOf(infix1) + ')';
 
    int l = infix.length();
    Stack<Character> char_stack = new Stack<>();
    String output="";
 
    for (int i = 0; i < l; i++)
    {
 
      // If the scanned character is an
      // operand, add it to output.
      if (isalpha(infix.charAt(i)) || isdigit(infix.charAt(i)))
        output += infix.charAt(i);
        
      else if(infix.charAt(i)==' ')output+=infix.charAt(i);
 
      // If the scanned character is an
      // ‘(‘, push it to the stack.
      else if (infix.charAt(i) == '(')
        char_stack.add('(');
 
      // If the scanned character is an
      // ‘)’, pop and output from the stack
      // until an ‘(‘ is encountered.
      else if (infix.charAt(i) == ')')
      {
        while (char_stack.peek() != '(')
        {
          output += char_stack.peek();
          char_stack.pop();
        }
 
        // Remove '(' from the stack
        char_stack.pop();
      }
 
      // Operator found
      else {
        if (isOperator(char_stack.peek()))
        {
          while ((getPriority(infix.charAt(i)) <
                  getPriority(char_stack.peek()))
                 || (getPriority(infix.charAt(i)) <=
                     getPriority(char_stack.peek())
                     && infix.charAt(i) == '^'))
          {
            output += char_stack.peek();
            char_stack.pop();
          }
 
          // Push current Operator on stack
          char_stack.add(infix.charAt(i));
        }
      }
    }
    while(!char_stack.empty()){
          output += char_stack.pop();
    }
    return output;
  }
 
    static String infixToPrefix(char[] infix) {
        /*
         * Reverse String Replace ( with ) and vice versa Get Postfix Reverse Postfix *
         */
        int l = infix.length;

        // Reverse infix
        String infix1 = reverse(infix, 0, l - 1);
        infix = infix1.toCharArray();

        // Replace ( with ) and vice versa
        for (int i = 0; i < l; i++) {

            if (infix[i] == '(') {
                infix[i] = ')';
                i++;
            } else if (infix[i] == ')') {
                infix[i] = '(';
                i++;
            }
        }

        String prefix = infixToPostfix(infix);

        // Reverse postfix
        prefix = reverse(prefix.toCharArray(), 0, l - 1);

        return prefix;
    }

}
