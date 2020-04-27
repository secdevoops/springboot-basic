# spring-boot-basic


A basic Spring Boot Project with Spring Security and JWT.

## Database script
```
CREATE TABLE `user_account` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL DEFAULT '',
  `password` varchar(100) NOT NULL DEFAULT '',
  `enabled` tinyint(1) NOT NULL,
  `accountNonExpired` tinyint(1) NOT NULL,
  `credentialsNonExpired` tinyint(1) NOT NULL,
  `accountNonLocked` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY(username)
);

CREATE TABLE `role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `rolename` varchar(60) NOT NULL DEFAULT '',
  `description` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY(rolename)
);

CREATE TABLE `user_account_role` (
  `user_id` bigint(20) unsigned NOT NULL,
  `role_id` bigint(20) unsigned NOT NULL,
  KEY `pk_user` (`user_id`),
  KEY `pk_role` (`role_id`),
  CONSTRAINT `pk_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `pk_user` FOREIGN KEY (`user_id`) REFERENCES `user_account` (`id`)
);

insert into role(rolename,description) values('ADMIN', 'Admin Role');
insert into role(rolename,description) values('USER', 'User Role');

insert into user_account(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked) 
values('admin','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', true, true, true, true);

insert into user_account_role(user_id, role_id) values((select id from user_account where username='admin'), (select id from role where rolename='ADMIN'));
```






