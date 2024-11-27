CREATE DATABASE IF NOT EXISTS TaskManagementDB;

use TaskManagementDB;

create table tasks (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
  description TEXT,
  priority ENUM('LOW', 'MEDIUM', 'HIGH') DEFAULT 'MEDIUM',
  status ENUM('PENDING', 'IN-PROGRESS', 'COMPLETED') DEFAULT 'PENDING',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);