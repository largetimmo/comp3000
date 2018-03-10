package SmarterMonitor.view;

import javafx.beans.property.*;

public class Process {
    private final StringProperty name;
    private final IntegerProperty pid;
    private final StringProperty onwer;
    private final StringProperty memory;
    private final FloatProperty cpu;

    public Process(String name, int pID, String ownerInfo, String memory, float cpu){
        this.name = new SimpleStringProperty(name);
        this.pid = new SimpleIntegerProperty(pID);
        this.onwer = new SimpleStringProperty(ownerInfo);
        this.memory = new SimpleStringProperty(memory);
        this.cpu = new SimpleFloatProperty(cpu);
    }

    public void setpName(String pName){
        name.set(pName);
    }
    public String getpName(){
        return name.get();
    }
    public void setpID(int id){
        pid.set(id);
    }
    public int getpID(){
        return pid.get();
    }
    public void setOwnerInfo(String owner){
        onwer.set(owner);
    }
    public String getonwerInfo(){
        return onwer.get();
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

    public IntegerProperty pidProperty() {
        return pid;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty ownerProperty() {
        return onwer;
    }
}
