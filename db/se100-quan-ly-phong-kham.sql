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
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  `create_by` varchar(70) DEFAULT NULL,
  `update_by` varchar(70) DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `ingredient` text,
  `dosage_form` varchar(100) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `medicine_batches` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount` int DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `expire_date` date DEFAULT NULL,
  `medicine_id` int NOT NULL,
  `manufacturer` varchar(60) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  `create_by` varchar(70) DEFAULT NULL,
  `update_by` varchar(70) DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `medicine_id` (`medicine_id`),
  CONSTRAINT `medicine_batches_ibfk_1` FOREIGN KEY (`medicine_id`) REFERENCES `medicines` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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

CREATE TABLE `service_types` (
  `id` int NOT NULL AUTO_INCREMENT,
  `service_name` varchar(100) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` varchar(70) DEFAULT NULL,
  `update_by` varchar(70) DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `service_records` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` text,
  `patient_id` int NOT NULL,
  `receptionist_id` int NOT NULL,
  `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` varchar(70) DEFAULT NULL,
  `update_by` varchar(70) DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `service_records_ibfk_1` (`patient_id`),
  KEY `service_records_ibfk_2` (`receptionist_id`),
  CONSTRAINT `service_records_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`),
  CONSTRAINT `service_records_ibfk_2` FOREIGN KEY (`receptionist_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `service_ratings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `score` int NOT NULL,
  `feedback` text NOT NULL,
  `create_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `delete_At` datetime DEFAULT NULL,
  `patient_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `service_ratings_patients_id_fk` (`patient_id`),
  CONSTRAINT `service_ratings_patients_id_fk` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
