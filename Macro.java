
import java.util.*;

public class Macro {
    private String[] parametros;
    private ArrayList<String> instrucoes;
    
    public Macro(){
        this.instrucoes = new ArrayList<>();
    }

    public void addInstrucao(String linha){
        this.instrucoes.add(linha);
    }
    
    public void setParametros(String line){ 
        String[] words = line.replaceAll(","," ").split("\\s+");
        this.parametros = Arrays.copyOfRange(words, 2, words.length);
    }
    
    public ArrayList<String> getInstrucoes(String callInstruction){
        String[] words = callInstruction.replaceAll(","," ").split("\\s+");
        String[] parametrosReais = Arrays.copyOfRange(words, 1, words.length);
        ArrayList<String> ins = new ArrayList<>(this.instrucoes);
        
        for(int i=0; i<this.instrucoes.size(); i++){
            for(int j=0; j<parametrosReais.length; j++){
                ins.set(i, ins.get(i).replaceAll(this.parametros[j], parametrosReais[j]));
            }
        }
        return ins;
    }
}