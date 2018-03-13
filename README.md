# comp3000



# C library compile command

$ cd "PROJECT ROOT"/src/SmarterMonitor/controller \
$ gcc -fPIC -I /usr/lib/jvm/java-8-openjdk-amd64/include/ -I /usr/lib/jvm/java-8-openjdk-amd64/include/linux/ -o libsystemcontroller.so  -shared SystemController.c
