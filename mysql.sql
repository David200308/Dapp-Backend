REVOKE ALL ON backend.* FROM 'backend'@'%';
GRANT CREATE, SELECT, INSERT, DELETE, UPDATE ON backend.* TO 'backend'@'%';
FLUSH PRIVILEGES;
