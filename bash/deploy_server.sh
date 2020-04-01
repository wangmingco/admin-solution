#!/usr/bin/env bash

#!/usr/bin/env bash

WORKSPACE_NAME=admin-solution
SERVER_NAME=admin-server
JAR_NAME=admin-server-0.1-exec.jar

WORK_HOME='/data/'${WORKSPACE_NAME}
SERVER_HOME=${WORK_HOME}/${SERVER_NAME}

echo "WORK_HOME --> " ${WORK_HOME}
echo "SERVER_HOME --> " ${SERVER_HOME}
echo "JAR_NAME --> " ${JAR_NAME}

function goWorkHome() {
     cd ${WORK_HOME}
}

function goServerHome() {
     cd ${SERVER_HOME}
}

function stop() {
    goServerHome
    echo "运行中的 ${SERVER_NAME} 进程:"
    jps -l | grep "${SERVER_NAME}"
    pid=`cat server.pid`
    echo "即将关闭的Java服务Pid:"$pid
    kill -9 $pid
    echo "即将关闭的Java服务完成, 运行中的 ${SERVER_NAME} 进程:"
    jps -l | grep ${SERVER_NAME}
}

function download() {
    echo "开始更新代码 -> " ${WORK_HOME}
    goWorkHome
    git pull
}

function build() {
    echo "开始构建代码 -> " ${SERVER_HOME}
    goServerHome
    mvn clean package
    echo "构建代码完成"
}

function start() {
    echo "开始启动服务"
    nohup java -jar ${SERVER_HOME}/target/${JAR_NAME} >server.log 2>&1 &
    jps -l | grep "${SERVER_NAME}" | awk '{print $1}' > server.pid
    jps -l | grep "${SERVER_NAME}"
    echo "启动服务完成"
}

stop
download
build
start