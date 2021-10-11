/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emulador;

import java.util.Arrays;

/**
 *
 * @author renafs
 */
public class Emulador {
    public Memory memory = new Memory();
    private String[] instructions;
    public int Step_Counter_memory = 0;
    public boolean finished = false;
    private VarTable varTable = new VarTable();

    private short AX = 0;
    private short DX = 0;
    private short SP = 0;
    private short SI = 0;
    private short IP = 0;
    private short SR = 0;


    // Flags
    private boolean ZF = false;
    
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
        System.out.println(this.Step_Counter_memory+" - "+instruction);

        String[] words = instruction.split("(\\s|,)+");
        String mnemonico = words[0];

        String [] opds = Arrays.copyOfRange(words,1,3);


        short param1 = calculateOpd(opds[0]);
        short param2 = calculateOpd(opds[1]);

        System.out.println("Param 1: "+param1);
        System.out.println("Param 2: "+param2);

        switch(mnemonico){
            case "jmp":
                this.Step_Counter_memory = param1;
            break;
            case "jz":
                if(this.getFlag("zf"))
                    this.Step_Counter_memory = param1;
            break;
            case "jnz":
                if(!this.getFlag("zf"))
                    this.Step_Counter_memory = param1;
            break;
            case "read":
                this.memory.setPalavra(12, this.Step_Counter_memory); 
            break;
            case "load":
                this.memory.setPalavra(13, this.Step_Counter_memory);
                break;
            case "hlt":
                this.finished = true;
        }

    }
    boolean getBit( int k,int n) {
        n=16-n;
        return (n>>k)%2==1;
    }
    public int setBit(int target,int bit,  boolean value) {
        int mask = 1 << bit;
        System.out.println(target | mask);
        return target | mask;
    }
    
    
    public boolean getFlag(String flag){
        switch(flag){
            case "of":
                return getBit(this.SR,12 );
            case "sf":
                return getBit(this.SR,9 );
            case "zf":
                return getBit(this.SR,8 );
            case "if":
                return getBit(this.SR,7 );
            case "pf":
                return getBit(this.SR,6 );
            case "cf":
                return getBit(this.SR,0 );
            default:
                return false;
        }
    }

    public void setFlag(String flag, boolean value){
        switch(flag){
            case "of":
                 this.SR = (short)setBit(this.SR, 12, value);
            case "sf":
                 this.SR = (short)setBit(this.SR, 9, value);
            case "zf":
                 this.SR = (short)setBit(this.SR, 8, value);
            case "if":
                 this.SR = (short)setBit(this.SR, 7, value);
            case "pf":
                 this.SR = (short)setBit(this.SR, 6, value);
            case "cf":
                 this.SR = (short)setBit(this.SR, 0, value);
        }
    }

    short calculateOpd (String opd){
        // Integrar isso aqui, n entendi como funciona
        // varTable.checkVariable(opds[0], this.Step_Counter_memory);
        // varTable.checkVariable(opds[1], this.Step_Counter_memory);  
        return 1;
    }

}
