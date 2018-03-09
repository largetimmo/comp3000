
public class SystemController {
    static {
        //todo
        System.load(null);
    }
    public static native int killProcess(int pid);
    public static native String getallprocesses();
}


