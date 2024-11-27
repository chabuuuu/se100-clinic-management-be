CREATE TABLE `patients` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `fullname` varchar(100) NOT NULL,
  `gender` boolean NOT NULL,
  `birthday` date NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  `create_at` datetime,
  `update_at` datetime,
  `create_by` varchar(70),
  `update_by` varchar(70),
  `delete_at` datetime
);

CREATE TABLE `employees` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `fullname` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `role` varchar(100),
  `create_at` datetime,
  `update_at` datetime,
  `create_by` varchar(70),
  `update_by` varchar(70),
  `delete_at` datetime
);

-- Add check constraint for role column
ALTER TABLE `employees` ADD CHECK (`role` IN ('RECEPTIONIST', 'DOCTOR', 'PHARMACIST', 'TECHNICIAN'));

-- Add unique constraint for username column
ALTER TABLE `employees` ADD UNIQUE (`username`);

CREATE TABLE `exam_records` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `exam_date` datetime,
  `numerical_order` int,
  `exam_room` varchar(50),
  `symptom` varchar(255) NOT NULL,
  `diagnose` varchar(255) NOT NULL,
  `status` varchar(50),
  `patient_id` int NOT NULL,
  `doctor_id` int NOT NULL,
  `create_at` datetime,
  `update_at` datetime,
  `create_by` varchar(70),
  `update_by` varchar(70),
  `delete_at` datetime
);

CREATE TABLE `prescriptions` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `status` varchar(50),
  `exam_record_id` int NOT NULL,
  `pharmacist_id` int NOT NULL,
  `create_at` datetime,
  `update_at` datetime,
  `create_by` varchar(70),
  `update_by` varchar(70),
  `delete_at` datetime
);

CREATE TABLE `test_records` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `test_date` datetime,
  `numerical_order` int,
  `test_room` varchar(50),
  `test_name` varchar(100),
  `test_artachments` JSON, -- Lưu mảng các file đính kèm
  `diagnose` varchar(255),
  `patient_id` int NOT NULL,
  `technician_id` int NOT NULL,
  `create_at` datetime,
  `update_at` datetime,
  `create_by` varchar(70),
  `update_by` varchar(70),
  `delete_at` datetime
);

CREATE TABLE `invoices` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `total` decimal(10,2),
  `service_type` varchar(50),
  `status` varchar(50),
  `exam_record_id` int NOT NULL,
  `receptionist_id` int NOT NULL,
  `create_at` datetime,
  `update_at` datetime,
  `create_by` varchar(70),
  `update_by` varchar(70),
  `delete_at` datetime
);

CREATE TABLE `medicines` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `create_at` datetime,
  `update_at` datetime,
  `create_by` varchar(70),
  `update_by` varchar(70),
  `delete_at` datetime
);

CREATE TABLE `medicine_batches` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `amount` int,
  `price` decimal(10,2),
  `quantity` int,
  `expire_date` date,
  `medicine_id` int NOT NULL,
  `create_at` datetime,
  `update_at` datetime,
  `create_by` varchar(70),
  `update_by` varchar(70),
  `delete_at` datetime
);

CREATE TABLE `prescription_details` (
  `prescription_id` int,
  `medicine_batche_id` int,
  `dosage` varchar(100),
  `amount` integer,
  `notes` text,
  `create_at` datetime,
  `update_at` datetime,
  `create_by` varchar(70),
  `update_by` varchar(70),
  `delete_at` datetime,
  PRIMARY KEY (`prescription_id`, `medicine_batche_id`)
);

ALTER TABLE `exam_records` ADD FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`);

ALTER TABLE `exam_records` ADD FOREIGN KEY (`doctor_id`) REFERENCES `employees` (`id`);

ALTER TABLE `prescriptions` ADD FOREIGN KEY (`exam_record_id`) REFERENCES `exam_records` (`id`);

ALTER TABLE `prescriptions` ADD FOREIGN KEY (`pharmacist_id`) REFERENCES `employees` (`id`);

ALTER TABLE `test_records` ADD FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`);

ALTER TABLE `test_records` ADD FOREIGN KEY (`technician_id`) REFERENCES `employees` (`id`);

ALTER TABLE `invoices` ADD FOREIGN KEY (`exam_record_id`) REFERENCES `exam_records` (`id`);

ALTER TABLE `invoices` ADD FOREIGN KEY (`receptionist_id`) REFERENCES `employees` (`id`);

ALTER TABLE `medicine_batches` ADD FOREIGN KEY (`medicine_id`) REFERENCES `medicines` (`id`);

ALTER TABLE `prescription_details` ADD FOREIGN KEY (`prescription_id`) REFERENCES `prescriptions` (`id`);

ALTER TABLE `prescription_details` ADD FOREIGN KEY (`medicine_batche_id`) REFERENCES `medicine_batches` (`id`);
