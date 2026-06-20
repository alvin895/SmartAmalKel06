-- MySQL Database Initialization Script for SMARTAMAL
-- ===================================================
-- Script untuk inisialisasi database-database yang diperlukan oleh SMARTAMAL services

-- ============================================
-- 1. DATABASE DEVICE SERVICE
-- ============================================
-- Database untuk menyimpan data devices, sensor readings, dan device configuration
CREATE DATABASE IF NOT EXISTS db_device
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE db_device;

-- TODO: Buat table devices, sensors, readings, device_config di database ini
-- Dokumentasi: Sesuaikan dengan entity di device-service


-- ============================================
-- 2. DATABASE AUTH SERVICE
-- ============================================
-- Database untuk menyimpan data user, authentication, dan authorization
CREATE DATABASE IF NOT EXISTS db_auth
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE db_auth;

-- TODO: Buat table users, roles, permissions, tokens di database ini
-- Dokumentasi: Sesuaikan dengan entity di auth-service


-- ============================================
-- 3. DATABASE ACCESS CONTROL SERVICE
-- ============================================
-- Database untuk menyimpan data access control, user groups, dan permission management
CREATE DATABASE IF NOT EXISTS db_access_control
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE db_access_control;

-- TODO: Buat table access_rules, user_groups, group_permissions di database ini
-- Dokumentasi: Sesuaikan dengan entity di access-control-service


-- ============================================
-- 4. DATABASE NOTIFICATION SERVICE
-- ============================================
-- Database untuk menyimpan data notification, alerts, dan notification history
CREATE DATABASE IF NOT EXISTS db_notification
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE db_notification;

-- TODO: Buat table notifications, alerts, notification_templates di database ini
-- Dokumentasi: Sesuaikan dengan entity di notification-service


-- ============================================
-- Create MySQL User for SMARTAMAL Services
-- ============================================
-- Username: smartamal_user
-- Password: smartamal_password (CHANGE THIS IN PRODUCTION!)

CREATE USER IF NOT EXISTS 'smartamal_user'@'%' IDENTIFIED BY 'smartamal_password';

-- Grant privileges untuk semua database
GRANT ALL PRIVILEGES ON db_device.* TO 'smartamal_user'@'%';
GRANT ALL PRIVILEGES ON db_auth.* TO 'smartamal_user'@'%';
GRANT ALL PRIVILEGES ON db_access_control.* TO 'smartamal_user'@'%';
GRANT ALL PRIVILEGES ON db_notification.* TO 'smartamal_user'@'%';

-- Apply privileges
FLUSH PRIVILEGES;

-- Verifikasi
SELECT 'Databases created successfully!' as status;
SHOW DATABASES;
