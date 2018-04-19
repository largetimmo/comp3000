# comp3000


# Environment Configuration 

$ sudo apt install openjdk-8-jdk openjfx


# C library compile command

$ cd "PROJECT ROOT"/src/SmarterMonitor/controller \
$ gcc -fPIC -I /usr/lib/jvm/java-8-openjdk-amd64/include/ -I /usr/lib/jvm/java-8-openjdk-amd64/include/linux/ -o libsystemcontroller.so  -shared SystemController.c

# Run in Linux
1. You need remove the comment Line 10 in SystemController.java, and change "null" to the path of the library address (i.e. /.../libsystemcontroller.so)
2. Change Line 128 from "JSONStr = DATA.getallprocesses_text();" to "JSONStr = DATA.getallprocesses();"
