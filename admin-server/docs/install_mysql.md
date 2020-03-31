## mysql安装

### 卸载原有的
1. 备份 `mysqldump -uroot -p --all-databases > /opt/database-dump.sql`
2. 停止服务 `systemctl stop mariadb`
3. 卸载 `yum remove mariadb mariadb-server mariadb-libs`

### 安装
1. 添加安装文件 `vi /etc/yum.repos.d/mariadb.repo`
```
[mariadb]
name = MariaDB
baseurl = https://mirrors.cloud.tencent.com/mariadb/mariadb-10.5.2/yum/centos7-amd64/
gpgkey=https://yum.mariadb.org/RPM-GPG-KEY-MariaDB
gpgcheck=1
```

2. `yum clean all`
3. 安装 `yum install MariaDB-client MariaDB-server`
4. 开机启动 `systemctl enable mariadb`
5. 启动 `systemctl start mariadb`
6. `mysql_upgrade`