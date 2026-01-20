-- Step-1: Create database 
CREATE DATABASE IF NOT EXISTS glamify;

USE glamify;

-- Step-2: Run Glamify SpringBoot application, it will create all the tables. Then run insert statements to populate sample data in the tables.

INSERT INTO user (user_id, email, full_name, password, phone, user_role, active) VALUES 
(1,'admin@glamify.com','Admin User','admin123','9999999999','ADMIN',b'1');


INSERT INTO beauty_service (id, category, duration, name, price, active, discount) VALUES 
(1,'MAKEUP',240,'Bridal Makeup',5000,1,8),
(2,'SKIN',100,'Facial',1700,1,0),
(3,'HAIR',30,'Hair Cut',300,1,5),
(4,'HAIR',120,'Hair Coloring',2500,1,8),
(5,'NAILS',90,'Manicure & Pedicure',1200,1,0),
(8,'HAIR',105,'Hair Spa',3500,1,0),
(12,'NAILS',45,'Nail art',600,1,15);