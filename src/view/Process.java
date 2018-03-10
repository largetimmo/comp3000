package view;

import javafx.beans.property.*;

public class Process {
    private final StringProperty pname;
    private final IntegerProperty pid;
    private final StringProperty test;
    private final StringProperty memory;
    private final FloatProperty cpu;

    public Process(String pName, int pID, String ownerInfo, String memory, float cpu){
        this.pname = new SimpleStringProperty(pName);
        this.pid = new SimpleIntegerProperty(pID);
        this.test = new SimpleStringProperty(ownerInfo);
        this.memory = new SimpleStringProperty(memory);
        this.cpu = new SimpleFloatProperty(cpu);
    }

    public void setpName(String Name){
        pname.set(Name);
    }
    public String getpName(){
        return pname.get();
    }
    public void setpID(int id){
        pid.set(id);
    }
    public int getpID(){
        return pid.get();
    }
    public void setOwnerInfo(String owner){
        test.set(owner);
    }
    public String getonwerInfo(){
        return test.get();
    }
    public void setMemory(String memo){
        memory.set(memo);
    }
    public String getMemory(){
        return memory.get();
    }
    public void setCpu(float Cpu){
        cpu.set(Cpu);
    }
    public float getCpu(){
        return cpu.get();
    }
}
