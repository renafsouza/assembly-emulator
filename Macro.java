import java.util.*;

public class Macro {

    private String[] parametrosFormais; 
    private String[] parametrosReais; 
    private ArrayList<String> instrucoes;
    private final String registers = "DX,AX,DS";
    private int ninho;
    private int nivel;
    String oldParametrosReais = ""; 
    String oldParametrosFormais = "";

    
    public Macro(){
        this.instrucoes = new ArrayList<>();
    }

    public void addInstrucao(String linha){
        //System.out.println("\nAddInstrução... \n" + linha);
        this.instrucoes.add(linha);
    }
    
    public void setParametrosFormais(String line){ 
        String[] words = line.replaceAll(","," ").split("\\s+");
        this.parametrosFormais = Arrays.copyOfRange(words, 2, words.length);
    }

    public String getParametrosFormais(){
        String parametrosFormais = "";
        for(int i = 0; i < this.parametrosFormais.length; i++){
            parametrosFormais = parametrosFormais + this.parametrosFormais[i] + ";";
        }
        return parametrosFormais;
    }

    public void setParametrosReais(String line){ 
        String[] words = line.replaceAll(","," ").split("\\s+");
        this.parametrosReais = Arrays.copyOfRange(words, 1, words.length);
    }

    public String getParametrosReais(){
        String parametrosReais = "";
        for(int i = 0; i < this.parametrosReais.length; i++){
            parametrosReais = parametrosReais + this.parametrosReais[i] + ";";
        }
        return parametrosReais;
    }
    
    public ArrayList<String> getInstrucoes(String callInstruction, Map<String, Macro> newMap){ // Substitui parâmetros formais pelos reais
        String[] words = callInstruction.replaceAll(","," ").split("\\s+");
        String[] parametrosReais = Arrays.copyOfRange(words, 1, words.length);
        ArrayList<String> ins = new ArrayList<>(this.instrucoes);
        System.out.println("\nParametros Formais concatenados: " + Arrays.toString(parametrosFormais) + "\n");
        System.out.println("\nParametros Reais concatenados: " + Arrays.toString(parametrosReais) + "\n");
        
        for(int i=0; i<this.instrucoes.size(); i++){ // Percorre cada instrução da macro

            
            String[] instNoComma = ins.get(i).replaceAll(","," ").split("\\s+");
            String vars[] = Arrays.copyOfRange(instNoComma, 1, instNoComma.length);

            for(int j=0; j<vars.length; j++){ // Percorre cada variavel da instrução

                if(registers.contains(vars[j])){ //Se a variável é um registrador, pula.
                    //System.out.println("\nYes, Register...\n" + vars[j]);
                }
                else{
                    boolean flagParametrosReal = false;
                    for(int k=0; k<parametrosFormais.length; k++){ // Percorre cada parametro formal
                        if(vars[j].equals(this.parametrosFormais[k])){
                          
                            ins.set(i, ins.get(i).replaceAll(this.parametrosFormais[k], parametrosReais[k]));
                            //System.out.println("Depois: " + this.parametrosFormais[k]);
                            flagParametrosReal = true;
                        }
                    }
                    if(!flagParametrosReal && nivel != 0){

                        setOldParametros(newMap, vars[j]);
                        if(!oldParametrosFormais.equals("")){
                            String[] wordsFormais = oldParametrosFormais.replaceAll(";"," ").split("\\s+");
                            String[] wordsReais = oldParametrosReais.replaceAll(";"," ").split("\\s+");
                            
                            System.out.println("\nWordsFormas: " + Arrays.toString(wordsFormais) + "\n");
                            System.out.println("\nWordsReais: " + Arrays.toString(wordsReais) + "\n");
                            System.out.println("\nVar: " + Arrays.toString(vars) + "\n");

                            for(int l = wordsFormais.length - 1; l >= 0; l--){
                                System.out.println("\nPilha: " + wordsFormais[l] + "\n");
                                if(vars[j].equals(wordsFormais[l])){
                                    System.out.println("\nEntrando aqui: é " + wordsReais[l]);
                                    ins.set(i, ins.get(i).replaceAll(vars[j], wordsReais[l]));
                                }
                            }
                        }
                        
                    }
                }   
            }
            
        }

        return ins;
    }
    
    public int setOldParametros(Map<String, Macro> newMap, String var){

        int i = nivel-1;
        while(i >= 0){    // Percorre cada nível acima
            for (Map.Entry<String, Macro> entry : newMap.entrySet()) { // Procura por Macros da mesma ninhada
                //String key = entry.getKey();
                Macro value = entry.getValue();
                //System.out.println("\nNinho value: " + value.getNinho());
                if(value.ninho == this.ninho){
                    if(value.nivel == i){
                        System.out.println("\nNivel value: " + value.getNivel() +" i value: " + i + " var: "+ var+ " Parametros: "+Arrays.toString(value.parametrosFormais));
                        String parametroFormal = "";
                        for(int j = 0; j < value.parametrosFormais.length; j++){
                            parametroFormal = value.parametrosFormais[j];
                            if(var.equals(parametroFormal)){
                                this.oldParametrosFormais = value.getParametrosFormais();
                                this.oldParametrosReais = value.getParametrosReais();
                                return 1;
                            }
                        }
                            
                    }
                }   
            }  
            i--; 
        }
        return 0;
    }

    public void setNinho(int i){
        ninho = i;
    }

    public int getNinho(){
        return ninho;
    }

    public void setNivel(int i){
        nivel = i;
    }

    public int getNivel(){
        return ninho;
    }
}