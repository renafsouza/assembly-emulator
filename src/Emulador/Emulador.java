/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emulador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


/**
 *
 * @author renafs
 */
public class Emulador {

    public enum paramTypes {
        OPD,
        REG,
        NULL
    }

    public Memory memory = new Memory();
    private String[] instructions;
    public int Step_Counter_memory = 0;
    public boolean finished = false;
    private VarTable varTable = new VarTable();
    private Stack pilha = new Stack();

    public ArrayList<Short> inputStream = new ArrayList<Short>();
    private int inputStreamIndex = 0;
    public ArrayList<String> outputStream = new ArrayList<String>();
    public String error;
    public short AX = 0;
    public short DX = 0;
    public short SP = 0;
    public short SI = 0;
    public short IP = 0;
    public short SR = 0;


    // Flags
    private boolean ZF = false;
    
    public void reset(){
        this.finished = false;
        this.Step_Counter_memory = 0;
        this.memory = new Memory();
        this.error = null;
    }
    public void loadInstructions(String file){
        this.instructions = file.split("\\n");
    }
    
    public void run(){
        while(!this.finished){
            String instruction = this.instructions[this.Step_Counter_memory];
            if(inputStreamIndex>=inputStream.size() && instruction.matches("read.*")) break;
            this.step();
        }
    }

    public paramTypes paramType(String param){
        if(param == null)
            return paramTypes.NULL;
        if(param.matches("AX|DX|SP|SI|IP|SR"))
            return paramTypes.REG;
        return paramTypes.OPD;
    }

    public boolean checkParams(String[] params, paramTypes type1, paramTypes type2){
        return paramType(params[0]) == type1 && paramType(params[1])==type2;
    }
    public boolean checkParams(String[] params, String type1, paramTypes type2){
        return params[0].matches(type1) && paramType(params[1])==type2;
    }
    public boolean checkParams(String[] params, String type1, String type2){
        return params[0].matches(type1) && params[1].matches(type2);
    }

    public void step(){
        if(finished){
            reset();
        }
            
        String instruction = this.instructions[this.Step_Counter_memory++];
        System.out.println(this.Step_Counter_memory+" - "+instruction);

        String[] words = instruction.split("(\\s|,)+");
        String mnemonico = words[0];

        String [] params = Arrays.copyOfRange(words,1,3);

        switch(mnemonico){
            case "":
                break;
            case "add":
                if(checkParams(params, "AX", paramTypes.REG)){
                    System.out.println("oof");
                    AX += getRegister(params[1]);
                }else if(checkParams(params, "AX", paramTypes.OPD))
                    AX += calculateOpd(params[1]);
                else error = "Parametros invalidos";
                break;
            case "div":
                // TODO RESTO
                if(checkParams(params, "AX|SI", paramTypes.NULL)){
                    this.AX = (short)(this.AX / getRegister(params[0]));
                    // this.DX = resto;
                }
                else error = "Parametros invalidos";
                break;
            case "sub": 
                if(checkParams(params, "AX", paramTypes.REG)){
                    this.AX -= this.getRegister(params[0]);
                }else if(checkParams(params, "AX", paramTypes.OPD))
                    this.AX -= this.calculateOpd(params[0]);
                else error = "Parametros invalidos";
                break;
            case "mul":
                // TODO RESTO
                if(checkParams(params, "AX|SI", paramTypes.NULL)){
                    this.AX = (short)(this.AX / getRegister(params[0]));
                    // this.DX = overflow;
                }
                else error = "Parametros invalidos";
                break;
            case "cmp":
                if(checkParams(params, "AX", paramTypes.OPD)){
                    setFlag("ZF", this.AX == calculateOpd(params[1]));
                }else if(checkParams(params, "AX", "DX")){
                    setFlag("ZF", this.AX == this.DX);
                }else error = "Parametros invalidos";
                break;
            case "and":
                if(checkParams(params, "AX", paramTypes.OPD)){
                    //TODO bitwise and
                }else if(checkParams(params, "AX", "AX|DX")){
                    //TODO bitwise or
                }else error = "Parametros invalidos";
                break;
            case "not":
                if(checkParams(params, "AX", paramTypes.NULL)){
                    //TODO bitwise not
                }else error = "Parametros invalidos";
                break;
            case "or":
                if(checkParams(params, "AX", paramTypes.OPD)){
                    //TODO bitwise or
                }else if(checkParams(params, "AX", "AX|DX")){
                    //TODO bitwise or
                }else error = "Parametros invalidos";
                break;
            case "xor":
                if(checkParams(params, "AX", paramTypes.OPD)){
                    //TODO bitwise xor
                }else if(checkParams(params, "AX", "AX|DX")){
                    //TODO bitwise xor
                }else error = "Parametros invalidos";
                break;
            case "jmp":
                if(checkParams(params,  paramTypes.OPD,paramTypes.NULL)){
                    this.Step_Counter_memory = calculateOpd(params[0]);
                }else error = "Parametros invalidos";
                break;
            case "jz":
                if(checkParams(params,  paramTypes.OPD,paramTypes.NULL)){
                    if(this.getFlag("zf"))
                        this.Step_Counter_memory = calculateOpd(params[0]);
                }else error = "Parametros invalidos";
                break;
            case "jnz":
                if(checkParams(params,  paramTypes.OPD,paramTypes.NULL)){
                    if(!this.getFlag("zf"))
                        this.Step_Counter_memory = calculateOpd(params[0]);
                }else error = "Parametros invalidos";
                break;
            case "jp":
                if(checkParams(params,  paramTypes.OPD,paramTypes.NULL)){
                    if(!this.getFlag("sf"))
                        this.Step_Counter_memory = calculateOpd(params[0]);
                }else error = "Parametros invalidos";
                break;
            case "ret":
                if(checkParams(params,  paramTypes.NULL,paramTypes.NULL)){
                    this.Step_Counter_memory = (int)this.pilha.pop();
                    this.SP--;
                }else error = "Parametros invalidos";
                break;
            case "call":
                if(checkParams(params,  paramTypes.OPD,paramTypes.NULL)){
                    this.pilha.push(this.Step_Counter_memory);
                    this.SP++;
                    this.Step_Counter_memory = calculateOpd(params[0]);
                }else error = "Parametros invalidos";
                break;
            case "pop":
                if(checkParams(params, paramTypes.REG, paramTypes.NULL)){
                    setRegister(params[0], (short)pilha.pop());
                }else if(checkParams(params, paramTypes.OPD, paramTypes.NULL)){
                    setRegister(params[0], calculateOpd(params[0]));
                }else error = "Parametros invalidos";
                break;
            case "popf":
                if(checkParams(params, paramTypes.NULL, paramTypes.NULL)){
                    this.SR = (short)this.pilha.pop();
                    this.SP++;
                }else error = "Parametros invalidos";
                break;
            case "push":
                if(checkParams(params, paramTypes.REG, paramTypes.NULL)){
                    pilha.push(getRegister(params[0]));
                }else if(checkParams(params, paramTypes.OPD, paramTypes.NULL)){
                    pilha.push(calculateOpd(params[0]));
                }else error = "Parametros invalidos";
                break;
            case "pushf":
                this.pilha.push(this.SR);
                this.SP++;
                break;
            case "store":
                if(checkParams(params, paramTypes.REG, paramTypes.NULL)){
                    memory.setPalavra(AX, getRegister(params[0]));
                }else error = "Parametros invalidos";
                break;
            case "read":
                if(checkParams(params, paramTypes.OPD, paramTypes.NULL)){
                    if(inputStream.size()>inputStreamIndex){
                        memory.setPalavra(inputStream.get(inputStreamIndex++).shortValue(), calculateOpd(params[0]));
                    }else{
                        Step_Counter_memory--;
                    }
                }else error = "Parametros invalidos";
                break;
            case "load": // TODO: ESSE DAQ N APARECE NO PDF
                if(checkParams(params, paramTypes.REG, paramTypes.NULL)){
                    memory.getPalavra(getRegister(params[0]));
                }else error = "Parametros invalidos";
                break;
            case "write":
                if(checkParams(params, paramTypes.OPD, paramTypes.NULL)){
                    System.out.println("out: "+Util.convertIntegerToBinary(calculateOpd(params[0])));
                    outputStream.add(Util.convertIntegerToBinary(calculateOpd(params[0])));
                }else error = "Parametros invalidos";
                break;
            case "hlt":
                this.finished = true;
                break;
            default:
                this.error = "Operação "+mnemonico+" não reconhecida";
                this.finished = true;
        }

    }
    
    private short getRegister(String reg){
        switch(reg){
            case "AX":
            return AX;
            case "DX":
            return DX;
            case "SP":
            return SP;
            case "SI":
            return SI;
            case "IP":
            return IP;
            case "SR":
            return SR;
            default: 
            return 0;
        }
    }
    private short setRegister(String reg, short value){
        switch(reg){
            case "AX":
            return AX = value;
            case "DX":
            return DX = value;
            case "SP":
            return SP = value;
            case "SI":
            return SI = value;
            case "IP":
            return IP = value;
            case "SR":
            return SR = value;
            default: 
            return 0;
        }
    }
    
    public boolean getFlag(String flag){
        switch(flag){
            case "of":
                return Util.getBit(this.SR,12 );
            case "sf":
                return Util.getBit(this.SR,9 );
            case "zf":
                return Util.getBit(this.SR,8 );
            case "if":
                return Util.getBit(this.SR,7 );
            case "pf":
                return Util.getBit(this.SR,6 );
            case "cf":
                return Util.getBit(this.SR,0 );
            default:
                return false;
        }
    }

    public void setFlag(String flag, boolean value){
        switch(flag){
            case "of":
                 this.SR = (short)Util.setBit(this.SR, 12, value);
            case "sf":
                 this.SR = (short)Util.setBit(this.SR, 9, value);
            case "zf":
                 this.SR = (short)Util.setBit(this.SR, 8, value);
            case "if":
                 this.SR = (short)Util.setBit(this.SR, 7, value);
            case "pf":
                 this.SR = (short)Util.setBit(this.SR, 6, value);
            case "cf":
                 this.SR = (short)Util.setBit(this.SR, 0, value);
        }
    }

    short calculateOpd (String opd){
        if(opd.matches("[0-1]+b")){
            return Short.parseShort(opd.replace("b",""),2);
        }if(opd.matches("#[0-9a-fA-F]+")){
            return Short.parseShort(opd.replace("#",""),16);
        }if(opd.matches("[0-9]+")){
            return Short.parseShort(opd);
        }
        // Integrar isso aqui, n entendi como funciona
        // varTable.checkVariable(params[0], this.Step_Counter_memory);
        // varTable.checkVariable(params[1], this.Step_Counter_memory);  
        return 1;
    }

    public void input (String input){
        this.inputStream.add(Short.parseShort(input));
        this.outputStream.add(Util.convertIntegerToBinary(Short.parseShort(input)));
        String instruction = this.instructions[this.Step_Counter_memory];
        if(instruction.matches("read.*")) step();
    }
}