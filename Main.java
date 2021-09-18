import java.io.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class Main {


    private int contadorMacroNivel = 0;
    private int contadorNinho = 0;

    private Map<String,Macro> macros;
    private final String input;
    private BufferedReader bf;
    private FileWriter arqSaida;


    // Método main
    public static void main(String[] args) throws FileNotFoundException,IOException{
        Main processador = new Main("./entrada");
        processador.processa();
    }

    // Construtor
    public Main(String input) throws FileNotFoundException, IOException{
        this.input = input;
        this.macros = new HashMap<String, Macro>();
        this.bf = new BufferedReader(new FileReader(this.input + ".txt"));
        this.arqSaida = new FileWriter("saída.txt");
        // stringParametrosFormais[0] = "";
    }

    // Processa a macro
    public void processa() throws FileNotFoundException, IOException {
        String line = this.readLine("",true) ;
        while (line != null){
            line = line.replaceAll(";;.*", "");
            line = this.readLine(line, true);
        }
        this.arqSaida.close();//encerra escrita de output

    }
    
    public void defineMacro(String line, String[] words) throws FileNotFoundException, IOException{
        Macro macro = new Macro();
        this.macros.put(words[0], macro);

        macro.setNinho(this.contadorNinho);
        macro.setNivel(this.contadorMacroNivel);

        macro.setParametrosFormais(line);
        line = this.readLine("", false);
        while(!line.contains("ENDM")){
            if(line.contains("MACRO")){
                contadorMacroNivel++;
                line = this.readLine(line, false);
            }
            else{
                macro.addInstrucao(line);
                line = this.readLine(line, false);
            } 
        }
        contadorNinho++;
        contadorMacroNivel = 0;
    }

    public void expandMacro(Macro macro, String line, String[] words)throws FileNotFoundException, IOException{
        
        Map<String, Macro> newMap =  macros;
        macro.setParametrosReais(line);
        ArrayList<String> instrucoes = macro.getInstrucoes(line, newMap);
        for(int i = 0; i<instrucoes.size();i++){
            arqSaida.write(instrucoes.get(i) + "\n");
        }
    }

    public boolean encontraChamada(String line, String[] palavras)throws FileNotFoundException, IOException{
        boolean chamada = false;
        Macro macro;
        Iterator iterator = this.macros.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry macrosIterator = (Map.Entry) iterator.next();
            if(line.contains(macrosIterator.getKey().toString())){
                chamada = true;
                macro = this.macros.get(macrosIterator.getKey());
                this.expandMacro(macro, line, palavras);
            }
        }
        return chamada;
    }

    public String readLine(String line, boolean write) throws FileNotFoundException, IOException{
        String[] palavras = line.split("\\s+");
        if(line.contains("MACRO")){
            this.defineMacro(line, palavras);
        }else{
            if(!this.encontraChamada(line, palavras) && write){ // Por que não buscar chamada apenas se a linha tiver label?
                arqSaida.write(line + "\n");
            };
        }
        return this.bf.readLine();
    }
}