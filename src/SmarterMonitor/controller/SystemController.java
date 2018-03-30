package SmarterMonitor.controller;

import javax.print.attribute.standard.NumberUp;

public class SystemController {
    static {
        //todo
        //change the null to your lib path
        //MUST USE EXACT PATH
        //System.load(null);
    }

    public static native int killProcess(int pid);
    @Deprecated
    public static native String getallprocesses();
    public static native String getallpids();
    public static native String getProcInfo(int pid);
    public static String getallprocesses_test(){
        return "{\"result\":[{\"cpu\":154.00,\"memory\":\" 185252kB \", \"name\": \" systemd \",\"pid\":1,\"owner\":\" root\",\"ownergrp\":\"root\"},{\"cpu\":159.00,\"memory\":\" 30928kB \", \"name\": \" systemd-journal \",\"pid\":350,\"owner\":\" root\",\"ownergrp\":\"root\"},{\"cpu\":0.00,\"memory\":\" 30928kB \", \"name\": \" kworker/2:2 \",\"pid\":352,\"owner\":\" root\",\"ownergrp\":\"root\"},{\"cpu\":0.00,\"memory\":\" 158624kB \", \"name\": \" vmware-vmblock- \",\"pid\":366,\"owner\":\" root\",\"ownergrp\":\"root\"},{\"cpu\":0.00,\"memory\":\" 46652kB \", \"name\": \" systemd-udevd \",\"pid\":378,\"owner\":\" root\",\"ownergrp\":\"root\"},{\"cpu\":0.00,\"memory\":\" 102384kB \", \"name\": \" systemd-timesyn \",\"pid\":463,\"owner\":\" systemd-timesync\",\"ownergrp\":\"systemd-timesync\"},{\"cpu\":0.00,\"memory\":\" 102384kB \", \"name\": \" nfit \",\"pid\":522,\"owner\":\" root\",\"ownergrp\":\"root\"},{\"cpu\":0.00,\"memory\":\" 102384kB \", \"name\": \" kworker/u257:0 \",\"pid\":593,\"owner\":\" root\",\"ownergrp\":\"root\"},{\"cpu\":0.00,\"memory\":\" 102384kB \", \"name\": \" kworker/u257:2 \",\"pid\":595,\"owner\":\" root\",\"ownergrp\":\"root\"},{\"cpu\":0.00,\"memory\":\" 291280kB \", \"name\": \" accounts-daemon \",\"pid\":868,\"owner\":\" root\",\"ownergrp\":\"root\"},]}";
    }
}