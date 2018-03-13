//
//  main.c
//  comp3000project
//
//  Created by CHEN JUNHAO on 2018/3/5.
//
#include <jni.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <dirent.h>
#include <unistd.h>
#include <signal.h>
#include <grp.h>
#include <pwd.h>

#include "SmarterMonitor_controller_SystemController.h"

#define MAX_STRING_SIZE 20

typedef struct{
    float cpu;
    char memory[MAX_STRING_SIZE];
    char name[MAX_STRING_SIZE];
    int pid;
    char owner[MAX_STRING_SIZE];
    int ownerid;
    int grpid;
    char ownergrp[MAX_STRING_SIZE];
    unsigned long total_time;
} proc;

//tested
void parseProcToJson(proc* process, char buffer[]){
    /**
     JSON format example
     * {
     "result": [{
     "cpu": 5.66,
     "memory": "3.22GB",
     "name": "chrome",
     "pid": 1234,
     "owner": "root",
     "ownergrp": "root"
     }, {
     "cpu": 45.66,
     "memory": "32.22GB",
     "name": "chrome",
     "pid": 12343,
     "owner": "root",
     "ownergrp": "root"
     }]
     }**/
    strcpy(buffer, "{");
    strcat(buffer, "\"cpu\":");
    char temp[MAX_STRING_SIZE];
    sprintf(temp, "%.2f",process->cpu);
    strcat(buffer, temp);
    strcat(buffer, ",\"memory\":\" ");
    strcat(buffer, process->memory);
    strcat(buffer, " \", \"name\": \" ");
    strcat(buffer, process->name);
    strcat(buffer, " \",\"pid\":");
    strcpy(temp, "");
    sprintf(temp, "%d",process->pid);
    strcat(buffer, temp);
    strcat(buffer, ",\"owner\":\" ");
    strcat(buffer, process->owner);
    strcat(buffer, "\",\"ownergrp\":\"");
    strcat(buffer, process->ownergrp);
    strcat(buffer, "\"}");
}

//tested
void parseToJson(proc* procarr[], int procsize, char jsonstr[]){
    strcpy(jsonstr, "{\"result\":[");
    char* buffer = malloc(102400);
    for (int i = 0;i<procsize;i++){
        if(i!=0){
            strcat(jsonstr, ",");
        }
        parseProcToJson(procarr[i], buffer);
        strcat(jsonstr, buffer);
    }
    strcat(jsonstr, "]}");
}



void delete(proc* procarr[], int size){
    for(int i =0; i<size;i++){
        free(procarr[i]);
    }
}

void getAllDir(char** namearr,int* size){
    (*size) = 0;
    DIR* dirp;

    struct dirent* direntp;
    dirp = opendir("/proc");
    if(dirp == NULL){
        printf("Cannot open directory");
    }
    else{
        for(;;){
        direntp = readdir(dirp);
        if(direntp == NULL) break;
        for (int i = 0; i<(strlen(direntp->d_name));i++){
            if(direntp->d_name[i]<48 || direntp->d_name[i]>57){
                goto nextloop;
            }
        }
        namearr[*size] = malloc(10);
        strcpy(namearr[(*size)++],direntp->d_name);
        nextloop:continue;
    }
        closedir(dirp);
    }
}

void readCpuTime(char* filepath,proc* procsrctp){
    strcat(filepath, "/stat");
    FILE* filep;
    filep = fopen(filepath, "r");
    if (filep == NULL){
        printf("Error: Cannot open file on %s\n",filepath);
        return;
    }

    char line[200];
    fgets(line,200,filep);
    fclose(filep);
    char* linep = line;
    int spacecount = 0;
    unsigned long utime = -1;
    unsigned long ktime = -1;
    for (int i = 0;i<strlen(linep);linep++) {
        if(spacecount == 14){
            //utime
            sscanf(linep, "%lu %lu", &utime, &ktime);
            break;
        }
        if (*linep == 32){
            spacecount++;
        }
    }
    procsrctp->total_time = (utime +  ktime);
    //utime->#14
    //ktime->#15
    //total cpu time =utime+ktime
    //sysconf(_SC_CLK_TCK)->Hz
}

void findGroupName(proc* process){
    struct group* grp = getgrgid(process->grpid);
    strcpy(process->ownergrp, grp->gr_name);
}

void findUserName(proc* process){
    struct passwd* owner = getpwuid(process->ownerid);
    strcpy(process->owner, owner->pw_name);
}

int readMemoryPidOidGid(char* filepath, proc* process){
    strcat(filepath, "/status");
    // /proc/1/status
    FILE* filep = fopen(filepath, "r");
    if(filep==NULL){
        return -1;
    }
    char line[500];
    while ((fgets(line, 500, filep))!=NULL) {
        char memory_num[10];
        char memory_unit[2];
        sscanf(line, "Name: %s",process->name);
        sscanf(line, "Pid:  %d",&process->pid);
        sscanf(line, "Uid:  %d",&process->ownerid);
        sscanf(line, "Gid:  %d",&process->grpid);
        sscanf(line, "VmSize:       %s %s",memory_num,memory_unit);
        //strcat(memory_num,memory_unit);
        strcpy(process->memory,memory_num);
        strcat(process->memory,memory_unit);
    }
    fclose(filep);
    return 1;
}


void readAll(unsigned int waitsec, long waitusec, char* msg){
    char** namearr;
    namearr = malloc(sizeof(char*)*500);
    int size = 0;
    getAllDir(namearr, &size);
    long hz = sysconf(_SC_CLK_TCK);
    int i;

    proc* allprocs_prev[size];
    proc* allprocs_now[size];
    for(i = 0; i< size;i++){
        allprocs_now[i] = malloc(sizeof(proc));
        allprocs_prev[i] = malloc(sizeof(proc));
    }

    for (i = 0; i< size; i++){
        char filepath[MAX_STRING_SIZE];
        allprocs_prev[i] = malloc(sizeof(proc));
        strcpy(filepath,"/proc/");
        strcat(filepath, namearr[i]);
        readCpuTime(filepath, allprocs_prev[i]);
    }

    usleep(waitusec);
    sleep(waitsec);
    //todo:change to user defined updatef;
    for (i = 0; i< size; i++){

        char filepath[MAX_STRING_SIZE];

        allprocs_now[i] = malloc(sizeof(proc));

        strcpy(filepath, "/proc/");

        strcat(filepath, namearr[i]);

        readCpuTime(filepath, allprocs_now[i]);

        float totaltime = waitsec + waitusec/1000000;
        allprocs_now[i]->cpu = ((float) allprocs_now[i]->total_time-allprocs_prev[i]->total_time)/hz/totaltime*100;
        strcpy(filepath, "/proc/");
        strcat(filepath, namearr[i]);


        readMemoryPidOidGid(filepath, allprocs_now[i]);

        findGroupName(allprocs_now[i]);

        findUserName(allprocs_now[i]);

    }
    for(i = 0; i<size;i++){

        free(namearr[i]);
    }
    free(namearr);
    parseToJson(allprocs_now, size, msg);
    delete(allprocs_prev,size);
    delete(allprocs_prev,size);

}


int killprocess(int pid){
    return kill(pid,SIGKILL);
}

/*
int main(int argc, const char * argv[]) {
    // insert code here...
    char* message = malloc(102400);
    readAll(1,0,message);
    printf("\n\n\n %s \n\n",message);
    return 0;
}

*/


JNIEXPORT jstring JNICALL Java_SmarterMonitor_controller_SystemController_getallprocesses(JNIEnv * env, jclass cls){
    char* message = malloc(302400);
    readAll(1,0,message);
    return (*env)->NewStringUTF(env,message);



}



JNIEXPORT jint JNICALL Java_SmarterMonitor_controller_SystemController_killProcess (JNIEnv* env, jclass cls, jint pid){
    return killprocess((int) pid);

}


