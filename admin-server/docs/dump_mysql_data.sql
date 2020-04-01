-- MySQL dump 10.13  Distrib 5.7.20, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: admin-solution
-- ------------------------------------------------------
-- Server version	5.7.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `BackendPermission`
--

INSERT INTO `BackendPermission` (`id`, `permissionName`, `path`, `status`, `createTime`, `updateTime`) VALUES (1,'/api/user/auth/login','/api/user/auth/login',1,'2020-03-30 14:24:57','2020-03-30 14:24:57');
INSERT INTO `BackendPermission` (`id`, `permissionName`, `path`, `status`, `createTime`, `updateTime`) VALUES (2,'/api/user/auth/info','/api/user/auth/info',1,'2020-03-30 14:24:57','2020-03-30 14:24:57');
INSERT INTO `BackendPermission` (`id`, `permissionName`, `path`, `status`, `createTime`, `updateTime`) VALUES (3,'/api/user/auth/logout','/api/user/auth/logout',1,'2020-03-30 14:24:57','2020-03-30 14:24:57');
INSERT INTO `BackendPermission` (`id`, `permissionName`, `path`, `status`, `createTime`, `updateTime`) VALUES (4,'/api/user/authority/getBackendPermissions','/api/user/authority/getBackendPermissions',1,'2020-03-30 14:24:57','2020-03-30 14:24:57');
INSERT INTO `BackendPermission` (`id`, `permissionName`, `path`, `status`, `createTime`, `updateTime`) VALUES (5,'/api/user/authority/addUser','/api/user/authority/addUser',1,'2020-03-30 14:24:57','2020-03-30 14:24:57');
INSERT INTO `BackendPermission` (`id`, `permissionName`, `path`, `status`, `createTime`, `updateTime`) VALUES (6,'/api/user/authority/getRoles','/api/user/authority/getRoles',1,'2020-03-30 14:24:57','2020-03-30 14:24:57');
INSERT INTO `BackendPermission` (`id`, `permissionName`, `path`, `status`, `createTime`, `updateTime`) VALUES (7,'/api/user/authority/addRole','/api/user/authority/addRole',1,'2020-03-30 14:24:57','2020-03-30 14:24:57');
INSERT INTO `BackendPermission` (`id`, `permissionName`, `path`, `status`, `createTime`, `updateTime`) VALUES (8,'/api/user/authority/getUsers','/api/user/authority/getUsers',1,'2020-03-30 14:24:57','2020-03-30 14:24:57');
INSERT INTO `BackendPermission` (`id`, `permissionName`, `path`, `status`, `createTime`, `updateTime`) VALUES (9,'/api/user/authority/getRolesByUserId','/api/user/authority/getRolesByUserId',1,'2020-03-30 14:24:57','2020-03-30 14:24:57');
INSERT INTO `BackendPermission` (`id`, `permissionName`, `path`, `status`, `createTime`, `updateTime`) VALUES (10,'/api/user/authority/updateUserRole','/api/user/authority/updateUserRole',1,'2020-03-30 14:24:57','2020-03-30 14:24:57');
INSERT INTO `BackendPermission` (`id`, `permissionName`, `path`, `status`, `createTime`, `updateTime`) VALUES (11,'/api/user/authority/getBackendPermissionsByRoleId','/api/user/authority/getBackendPermissionsByRoleId',1,'2020-03-30 14:24:57','2020-03-30 14:24:57');
INSERT INTO `BackendPermission` (`id`, `permissionName`, `path`, `status`, `createTime`, `updateTime`) VALUES (12,'/api/user/authority/updateRoleBackendPermission','/api/user/authority/updateRoleBackendPermission',1,'2020-03-30 14:24:57','2020-03-30 14:24:57');
INSERT INTO `BackendPermission` (`id`, `permissionName`, `path`, `status`, `createTime`, `updateTime`) VALUES (13,'/error','/error',1,'2020-03-30 14:24:57','2020-03-30 14:24:57');
INSERT INTO `BackendPermission` (`id`, `permissionName`, `path`, `status`, `createTime`, `updateTime`) VALUES (15,'/api/user/authority/getUserFrontendPermissions','/api/user/authority/getUserFrontendPermissions',1,'2020-03-30 15:46:49','2020-03-30 15:46:49');
INSERT INTO `BackendPermission` (`id`, `permissionName`, `path`, `status`, `createTime`, `updateTime`) VALUES (16,'/api/user/authority/getFrontendPermissions','/api/user/authority/getFrontendPermissions',1,'2020-03-31 14:35:40','2020-03-31 14:35:40');
INSERT INTO `BackendPermission` (`id`, `permissionName`, `path`, `status`, `createTime`, `updateTime`) VALUES (17,'/api/user/authority/getFrontendPermissionsByRoleId','/api/user/authority/getFrontendPermissionsByRoleId',1,'2020-03-31 14:35:40','2020-03-31 14:35:40');
INSERT INTO `BackendPermission` (`id`, `permissionName`, `path`, `status`, `createTime`, `updateTime`) VALUES (18,'/api/user/authority/updateRoleFrontendPermission','/api/user/authority/updateRoleFrontendPermission',1,'2020-03-31 14:35:40','2020-03-31 14:35:40');

--
-- Dumping data for table `FrontendPermission`
--

INSERT INTO `FrontendPermission` (`id`, `name`, `path`, `component`, `redirect`, `title`, `icon`, `parrent`, `status`, `createTime`, `updateTime`) VALUES (1,'权限配置','/permission','Layout','/permission/frontend','权限配置','example','',1,'2020-03-30 15:52:40','2020-03-31 11:11:06');
INSERT INTO `FrontendPermission` (`id`, `name`, `path`, `component`, `redirect`, `title`, `icon`, `parrent`, `status`, `createTime`, `updateTime`) VALUES (2,'前端权限','frontend','permission/frontend/index',NULL,'前端权限','table','/permission',1,'2020-03-30 15:53:31','2020-03-31 12:19:24');
INSERT INTO `FrontendPermission` (`id`, `name`, `path`, `component`, `redirect`, `title`, `icon`, `parrent`, `status`, `createTime`, `updateTime`) VALUES (3,'后端权限','backend','permission/backend/index',NULL,'后端权限','table','/permission',1,'2020-03-30 15:53:31','2020-03-31 12:19:24');

--
-- Dumping data for table `Role`
--

INSERT INTO `Role` (`id`, `roleName`, `status`, `createTime`, `updateTime`) VALUES (1,'admin',1,'2020-03-03 14:18:03','2020-03-03 14:18:03');

--
-- Dumping data for table `RoleBackendPermissionRelation`
--

INSERT INTO `RoleBackendPermissionRelation` (`id`, `roleId`, `permissionId`, `status`, `createTime`, `updateTime`) VALUES (1,'1','1',1,'2020-03-30 14:28:47','2020-03-30 14:28:47');
INSERT INTO `RoleBackendPermissionRelation` (`id`, `roleId`, `permissionId`, `status`, `createTime`, `updateTime`) VALUES (2,'1','2',1,'2020-03-30 14:28:47','2020-03-30 14:28:47');
INSERT INTO `RoleBackendPermissionRelation` (`id`, `roleId`, `permissionId`, `status`, `createTime`, `updateTime`) VALUES (3,'1','3',1,'2020-03-30 14:28:47','2020-03-30 14:28:47');
INSERT INTO `RoleBackendPermissionRelation` (`id`, `roleId`, `permissionId`, `status`, `createTime`, `updateTime`) VALUES (4,'1','4',1,'2020-03-30 14:28:47','2020-03-30 14:28:47');
INSERT INTO `RoleBackendPermissionRelation` (`id`, `roleId`, `permissionId`, `status`, `createTime`, `updateTime`) VALUES (5,'1','5',1,'2020-03-30 14:28:47','2020-03-30 14:28:47');
INSERT INTO `RoleBackendPermissionRelation` (`id`, `roleId`, `permissionId`, `status`, `createTime`, `updateTime`) VALUES (6,'1','6',1,'2020-03-30 14:28:47','2020-03-30 14:28:47');
INSERT INTO `RoleBackendPermissionRelation` (`id`, `roleId`, `permissionId`, `status`, `createTime`, `updateTime`) VALUES (7,'1','7',1,'2020-03-30 14:28:47','2020-03-30 14:28:47');
INSERT INTO `RoleBackendPermissionRelation` (`id`, `roleId`, `permissionId`, `status`, `createTime`, `updateTime`) VALUES (8,'1','8',1,'2020-03-30 14:28:47','2020-03-30 14:28:47');
INSERT INTO `RoleBackendPermissionRelation` (`id`, `roleId`, `permissionId`, `status`, `createTime`, `updateTime`) VALUES (9,'1','9',1,'2020-03-30 14:28:47','2020-03-30 14:28:47');
INSERT INTO `RoleBackendPermissionRelation` (`id`, `roleId`, `permissionId`, `status`, `createTime`, `updateTime`) VALUES (10,'1','10',1,'2020-03-30 14:28:47','2020-03-30 14:28:47');
INSERT INTO `RoleBackendPermissionRelation` (`id`, `roleId`, `permissionId`, `status`, `createTime`, `updateTime`) VALUES (11,'1','11',1,'2020-03-30 14:28:47','2020-03-30 14:28:47');
INSERT INTO `RoleBackendPermissionRelation` (`id`, `roleId`, `permissionId`, `status`, `createTime`, `updateTime`) VALUES (12,'1','12',1,'2020-03-30 14:28:47','2020-03-30 14:28:47');
INSERT INTO `RoleBackendPermissionRelation` (`id`, `roleId`, `permissionId`, `status`, `createTime`, `updateTime`) VALUES (13,'1','13',1,'2020-03-30 14:28:47','2020-03-30 14:28:47');
INSERT INTO `RoleBackendPermissionRelation` (`id`, `roleId`, `permissionId`, `status`, `createTime`, `updateTime`) VALUES (14,'1','15',1,'2020-03-30 14:28:47','2020-03-30 14:28:47');
INSERT INTO `RoleBackendPermissionRelation` (`id`, `roleId`, `permissionId`, `status`, `createTime`, `updateTime`) VALUES (15,'1','16',1,'2020-03-30 14:28:47','2020-03-30 14:28:47');
INSERT INTO `RoleBackendPermissionRelation` (`id`, `roleId`, `permissionId`, `status`, `createTime`, `updateTime`) VALUES (16,'1','17',1,'2020-03-30 14:28:47','2020-03-30 14:28:47');
INSERT INTO `RoleBackendPermissionRelation` (`id`, `roleId`, `permissionId`, `status`, `createTime`, `updateTime`) VALUES (17,'1','18',1,'2020-03-30 14:28:47','2020-03-30 14:28:47');

--
-- Dumping data for table `RoleFrontendPermissionRelation`
--

INSERT INTO `RoleFrontendPermissionRelation` (`id`, `roleId`, `permissionId`, `status`, `createTime`, `updateTime`) VALUES (2,'1','2',1,'2020-03-30 15:54:40','2020-03-30 15:54:40');
INSERT INTO `RoleFrontendPermissionRelation` (`id`, `roleId`, `permissionId`, `status`, `createTime`, `updateTime`) VALUES (3,'1','3',1,'2020-03-30 15:54:40','2020-03-30 15:54:40');
INSERT INTO `RoleFrontendPermissionRelation` (`id`, `roleId`, `permissionId`, `status`, `createTime`, `updateTime`) VALUES (7,'1','1',1,'2020-03-31 16:46:52','2020-03-31 16:46:52');

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`id`, `username`, `password`, `status`, `createTime`, `updateTime`) VALUES (1,'admin','e5ce40aee5f2f0d33e3861168f69e18020c8a986b70d839177d8d0f81dbc56cc0fa80ada956879d52a4977c2fa0440de83f107fb46300a8b8e1ed100d445d1cc',1,'2020-04-01 15:49:39','2020-04-01 15:49:39');

--
-- Dumping data for table `UserRoleRelation`
--

INSERT INTO `UserRoleRelation` (`id`, `roleId`, `userId`, `status`, `createTime`, `updateTime`) VALUES (2,'1','1',1,'2020-03-30 11:45:37','2020-03-30 11:45:37');
INSERT INTO `UserRoleRelation` (`id`, `roleId`, `userId`, `status`, `createTime`, `updateTime`) VALUES (5,'1','3',1,'2020-03-30 11:46:22','2020-03-30 11:46:22');
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-01 16:00:36
