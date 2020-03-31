#!/usr/bin/env bash

#!/usr/bin/env bash

GIT_URL=git@github.com:wangmingco/admin-solution.git
WORKSPACE_NAME=admin-solution
SERVER_NAME=admin-server
SERVER_PATH=${WORKSPACE_NAME}/${SERVER_NAME}
JAR_PATH=admin-server-0.1-exec.jar

WORK_HOME=`pwd`/${SERVER_PATH}

echo "WORK_HOME --> " ${WORK_HOME}
echo "GIT_URL --> " ${GIT_URL}
echo "WORKSPACE_NAME --> " ${WORKSPACE_NAME}
echo "SERVER_NAME --> " ${SERVER_NAME}
echo "SERVER_PATH --> " ${SERVER_PATH}
echo "JAR_PATH --> " ${JAR_PATH}

function stop() {
    echo "运行中的 ${SERVER_NAME} 进程:"
    jps -l | grep "${SERVER_NAME}"
    pid=`cat server.pid`
    echo "即将关闭的Java服务Pid:"$pid
    kill -9 $pid
    echo "即将关闭的Java服务完成, 运行中的 ${SERVER_NAME} 进程:"
    jps -l | grep ${SERVER_NAME}
}

function download() {
    if [ -e "${WORKSPACE_NAME}" ]; then
        echo "开始更新代码"
        cd ${WORKSPACE_NAME}
        git pull
        cd ${WORK_HOME}
    else
        echo "开始检出代码"
        git clone ${GIT_URL}
    fi
}

function build() {
    echo "开始构建代码"
    cd ${SERVER_PATH}
    mvn clean package
    echo "构建代码完成"
}

function start() {
    echo "开始启动服务"
    nohup java -jar ./target/${JAR_PATH} >server.log 2>&1 &
    jps -l | grep "${SERVER_NAME}" | awk '{print $1}' > server.pid
    jps -l | grep "${SERVER_NAME}"
    echo "启动服务完成"
}

stop
download
build
start