-- MANUAL ONLY. Replace password hash placeholders first; never execute against production.
-- Use your own demo passwords, hash with HashPassword CLI. No live credentials.
INSERT INTO users(username,email,password_hash,role,active,auth_version,created_at) VALUES
 ('admin','admin@example.test','REPLACE_WITH_BCRYPT_HASH','ADMIN',true,0,CURRENT_TIMESTAMP),
 ('reader','reader@example.test','REPLACE_WITH_BCRYPT_HASH','USER',true,0,CURRENT_TIMESTAMP);
INSERT INTO categories(name,image,active) VALUES
 ('Văn học','',true),('Kỹ năng sống','',true),('Công nghệ','',true),('Thiếu nhi','',true);
