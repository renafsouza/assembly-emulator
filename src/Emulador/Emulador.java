/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emulador;
/**
 *
 * @author renafs
 */
public class Emulador {
    public Memory memory = new Memory();
    private String[] instructions;
    private int Step_Counter_memory = 0;
    public boolean finished = false;
    private VarTable varTable = new VarTable();
    
    public void reset(){
        this.finished = false;
        this.Step_Counter_memory = 0;
        this.memory = new Memory();
    }
    public void loadInstructions(String file){
        this.instructions = file.split("\\n");
    }
    
    public void run(){
        while(!this.finished){
            this.step();
        }
    }
    public void step(){
        String instruction = this.instructions[this.Step_Counter_memory++];
        System.out.println(instruction);
        System.out.println(this.Step_Counter_memory);

        String[] lineArguments = instruction.split(" ");

        //Switch pode virar um método
        switch(instruction){
            case "read":
                this.memory.setPalavra(12, this.Step_Counter_memory); 
            break;
            case "load":
                this.memory.setPalavra(13, this.Step_Counter_memory);
                break;
        }
        

        // Checando quantidade de argumentos na linha.
        if(lineArguments.length == 2) {
            varTable.checkVariable(lineArguments[1],this.Step_Counter_memory);
        } else if (lineArguments.length == 3) {
            varTable.checkVariable(lineArguments[1],this.Step_Counter_memory);
            varTable.checkVariable(lineArguments[2],this.Step_Counter_memory);                
        }

        if(instruction.matches("hlt"))
            this.finished = true;
    }
    
}
