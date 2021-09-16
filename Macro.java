
import java.util.*;

public class Macro {
    private String[] parametros;
    private ArrayList<String> instrucoes;
    public boolean exists;
    
    public Macro(boolean exists){
        this.exists = false;
        this.instrucoes = new ArrayList<>();
    }
    public Macro(){
        this.exists = true;
        this.instrucoes = new ArrayList<>();
    }

    public void addInstrucao(String linha){
        this.instrucoes.add(linha);
    }
    
    public void setParametros(String line){ 
        String[] words = line.replaceAll(","," ").split("\\s+");
        this.parametros = Arrays.copyOfRange(words, 2, words.length);
    }

    public Map<String,String> getContext(String callInstruction){
        Map<String,String> context = new HashMap<String, String>();
        String[] words = callInstruction.replaceAll(","," ").split("\\s+");
        String[] parametrosReais = Arrays.copyOfRange(words, 1, words.length);
        for(int i=0;i<parametrosReais.length;i++){
            context.put(this.parametros[i],parametrosReais[i]);
        }
        return context;
    }
    
    public ArrayList<String> getInstrucoes(Map<String, String> context){
        ArrayList<String> ins = new ArrayList<>(this.instrucoes);
        
        for(int i=0; i<this.instrucoes.size(); i++){

            Iterator iterator = context.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry varIterator = (Map.Entry) iterator.next();
                ins.set(i, ins.get(i).replaceAll(varIterator.getKey().toString(), context.get(varIterator.getKey())));
            }
        }
        return ins;
    }
}