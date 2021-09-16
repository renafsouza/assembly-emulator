import java.io.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class Main {

    private Map<String,Macro> macros;
    private final String input;
    private BufferedReader bf;
    private FileWriter arqSaida;

    // Construtor
    public Main(String input) throws FileNotFoundException, IOException{
        this.input = input;
        this.macros = new HashMap<String, Macro>();
        this.bf = new BufferedReader(new FileReader(this.input + ".txt"));
        this.arqSaida = new FileWriter("saída.txt");
    }

    // Processa a macro
    public void processa() throws FileNotFoundException, IOException {
        String line = this.readLine("") ;
        while (line != null){
            line = line.replaceAll(";;.*", "");
            line = this.readLine(line);
        }
        this.arqSaida.close();//encerra escrita de output

    }
    
    public int defineMacro(List<String> lines, String[] words) throws FileNotFoundException, IOException{
        
        Macro macro = new Macro();
        this.macros.put(words[0], macro);

        int i = 0;
        String line = lines.get(i++);
        macro.setParametros(line);
        line = lines.get(i++);

        int depth = 1;
        while( depth>0 ){
            

            
            macro.addInstrucao(line);
            
            line = lines.get(i++);
            if(line.contains("ENDM")) depth--;
            if(line.contains("MACRO")) depth++;
        }
        
        return i;
    }

    public void defineMacro(String line, String[] words) throws FileNotFoundException, IOException{
        
        Macro macro = new Macro();
        this.macros.put(words[0], macro);

        macro.setParametros(line);
        line = this.bf.readLine();

        int depth = 1;
        while( depth>0 ){
            

            if(line.contains("ENDM")) depth--;
            if(line.contains("MACRO")) depth++;
            
            macro.addInstrucao(line);
            line = this.bf.readLine();
        }
        
    }

    public void expandMacro(Macro macro, String line, Map<String,String> context) throws FileNotFoundException, IOException{
        

        Map<String,String> newContext = new HashMap<String, String>();
        newContext.putAll(context);
        newContext.putAll(macro.getContext(line));

        ArrayList<String> instrucoes = macro.getInstrucoes(newContext);

        for(int i = 0; i<instrucoes.size();i++){
            String[] palavras = instrucoes.get(i) .split("\\s+");
            if(instrucoes.get(i).contains("MACRO")){
                i += this.defineMacro(instrucoes.subList(i,instrucoes.size()), palavras);
            }else if(!this.encontraChamada(instrucoes.get(i), palavras, newContext)){
                if(!instrucoes.get(i).contains("ENDM"))
                    arqSaida.write(instrucoes.get(i)  + "\n");
            };
        }
    }

    public boolean encontraChamada(String line, String[] palavras, Map<String,String> context)throws FileNotFoundException, IOException{
        boolean chamada = false;
        Macro macro;

        Map<String,Macro> newMap = new HashMap<String, Macro>();
        newMap.putAll(this.macros);
        Iterator iterator = newMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry macrosIterator = (Map.Entry) iterator.next();
            String[] words = line.split("\\s+");
            if(words[0].equals(macrosIterator.getKey().toString())){
                chamada = true;
                macro = this.macros.get(macrosIterator.getKey());
                this.expandMacro(macro, line, context);
            }
        }
        return chamada;
    }

    public String readLine(String line) throws FileNotFoundException, IOException{
        
        String[] palavras = line.split("\\s+");
        if(line.contains("MACRO")){
            this.defineMacro(line, palavras);
        }else{
            if(!this.encontraChamada(line, palavras, new HashMap<String, String>())){
                arqSaida.write(line + "\n");
            };
        }
        return this.bf.readLine();
    }

    // Método main
    public static void main(String[] args) throws FileNotFoundException,IOException{
        Main processador = new Main("./entrada");
        processador.processa();
    }

}