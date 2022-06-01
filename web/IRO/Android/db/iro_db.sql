-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 30, 2019 at 06:12 AM
-- Server version: 10.1.31-MariaDB
-- PHP Version: 7.2.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `iro_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `adoptions`
--

CREATE TABLE `adoptions` (
  `adoption_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `pet_id` int(11) NOT NULL,
  `children_age` varchar(100) NOT NULL,
  `reason` varchar(100) NOT NULL,
  `pet_live_type_id` int(11) NOT NULL,
  `current_pet` varchar(100) NOT NULL,
  `past_pet` varchar(100) NOT NULL,
  `past_pet_details` varchar(100) NOT NULL,
  `have_yard` tinyint(1) NOT NULL,
  `is_fenced` tinyint(1) NOT NULL,
  `have_vet` tinyint(1) NOT NULL,
  `vet_name` varchar(50) NOT NULL,
  `vet_address` varchar(100) NOT NULL,
  `circumstances` varchar(50) NOT NULL,
  `is_agreed` tinyint(1) NOT NULL,
  `reason_disagree` varchar(100) NOT NULL,
  `home_visit_time` varchar(50) NOT NULL,
  `comments` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `adoptions`
--

INSERT INTO `adoptions` (`adoption_id`, `user_id`, `pet_id`, `children_age`, `reason`, `pet_live_type_id`, `current_pet`, `past_pet`, `past_pet_details`, `have_yard`, `is_fenced`, `have_vet`, `vet_name`, `vet_address`, `circumstances`, `is_agreed`, `reason_disagree`, `home_visit_time`, `comments`) VALUES
(40, 61, 3, 'I have no children yet', 'He is fun to be with', 2, 'I have 2 aspins', '', '', 1, 1, 0, '', '', 'I would never give up a pet', 1, '', 'Every sunday', 'I love your org');

-- --------------------------------------------------------

--
-- Table structure for table `approved_documents`
--

CREATE TABLE `approved_documents` (
  `id` int(11) NOT NULL,
  `doc_id` int(11) NOT NULL,
  `approved_by` int(11) NOT NULL,
  `approved_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `approved_documents`
--

INSERT INTO `approved_documents` (`id`, `doc_id`, `approved_by`, `approved_date`) VALUES
(2, 21, 62, '2019-11-29');

-- --------------------------------------------------------

--
-- Table structure for table `cancelled_documents`
--

CREATE TABLE `cancelled_documents` (
  `id` int(11) NOT NULL,
  `doc_id` int(11) NOT NULL,
  `reason` varchar(100) NOT NULL,
  `cancelled_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `documents`
--

CREATE TABLE `documents` (
  `doc_id` int(11) NOT NULL,
  `doc_form_id` int(11) NOT NULL,
  `doc_type_id` int(11) NOT NULL,
  `doc_status_id` int(11) NOT NULL,
  `date_submitted` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `documents`
--

INSERT INTO `documents` (`doc_id`, `doc_form_id`, `doc_type_id`, `doc_status_id`, `date_submitted`) VALUES
(21, 40, 1, 1, '2019-11-29');

-- --------------------------------------------------------

--
-- Table structure for table `document_status`
--

CREATE TABLE `document_status` (
  `id` int(11) NOT NULL,
  `doc_status_id` int(11) NOT NULL,
  `doc_status_desc` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `document_status`
--

INSERT INTO `document_status` (`id`, `doc_status_id`, `doc_status_desc`) VALUES
(1, 1, 'Pending'),
(2, 2, 'Approved'),
(3, 3, 'Rejected'),
(4, 4, 'Cancelled');

-- --------------------------------------------------------

--
-- Table structure for table `document_type`
--

CREATE TABLE `document_type` (
  `id` int(11) NOT NULL,
  `doc_type_id` int(11) NOT NULL,
  `doc_type_desc` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `document_type`
--

INSERT INTO `document_type` (`id`, `doc_type_id`, `doc_type_desc`) VALUES
(1, 1, 'Pet Adoption Application Form');

-- --------------------------------------------------------

--
-- Table structure for table `file_attachments`
--

CREATE TABLE `file_attachments` (
  `file_attachment_id` int(11) NOT NULL,
  `doc_id` int(11) NOT NULL,
  `file_name` varchar(50) NOT NULL,
  `file_path` varchar(100) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` date NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `file_attachments`
--

INSERT INTO `file_attachments` (`file_attachment_id`, `doc_id`, `file_name`, `file_path`, `created_by`, `created_date`, `is_active`) VALUES
(11, 21, '20191129_145822.jpg', 'images/attachments/20191129_145822.jpg', 61, '2019-11-29', 1);

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `notification_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `notification_type_id` int(11) NOT NULL,
  `subject` varchar(50) NOT NULL,
  `content` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `is_seen` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `notification_type`
--

CREATE TABLE `notification_type` (
  `id` int(11) NOT NULL,
  `notification_type_id` int(11) NOT NULL,
  `notification_type_desc` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `pets`
--

CREATE TABLE `pets` (
  `pet_id` int(11) NOT NULL,
  `pet_image` varchar(100) NOT NULL,
  `pet_name` varchar(50) NOT NULL,
  `pet_gender` varchar(50) NOT NULL,
  `pet_age` varchar(50) NOT NULL,
  `pet_desc` varchar(255) NOT NULL,
  `pet_type_id` int(11) NOT NULL,
  `pet_status_id` int(11) NOT NULL,
  `date_added` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pets`
--

INSERT INTO `pets` (`pet_id`, `pet_image`, `pet_name`, `pet_gender`, `pet_age`, `pet_desc`, `pet_type_id`, `pet_status_id`, `date_added`) VALUES
(1, 'bob.jpg', 'Bob', 'Male', '2 years old', 'He is playful and active', 3, 1, '2019-10-01'),
(2, 'michael.jpg', 'Michael', 'Male', '5 years old', 'He is playful and active', 2, 1, '2019-11-03'),
(3, 'diablo.jpg', 'Diablo', 'Male', '4 years old', 'Lorem ipsum dolor sit amet consectetuer adipiscing elit. Tellus adipiscing laoreet nisi mi mattis tincidunt adipiscing conubia duis interdum integer magna ut neque eu mus. ', 4, 2, '2019-10-09'),
(4, 'princess.jpg', 'Princess', 'Female', '1 year old', 'She is cute and adorable', 5, 1, '2019-09-18'),
(5, 'shoeshine.jpg', 'Shoeshine', 'Male', '3 months', 'He is playful and active', 6, 1, '2019-08-03'),
(6, 'chow chow.jpg', 'Chiwie', 'Male', '2 months', 'asdasdasfas', 1, 1, '2019-08-10'),
(7, 'shihtzu.jpg', 'Britney', 'Female', '1 year and 3months', 'She eats a lot of food', 4, 1, '2019-11-13');

-- --------------------------------------------------------

--
-- Table structure for table `pet_live_type`
--

CREATE TABLE `pet_live_type` (
  `id` int(11) NOT NULL,
  `pet_live_type_id` int(11) NOT NULL,
  `pet_live_type_desc` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pet_live_type`
--

INSERT INTO `pet_live_type` (`id`, `pet_live_type_id`, `pet_live_type_desc`) VALUES
(1, 1, 'Will the pet be an indoor or outdoor pet?'),
(2, 2, 'Indoor'),
(3, 3, 'Outdoor'),
(4, 4, 'Both');

-- --------------------------------------------------------

--
-- Table structure for table `pet_status`
--

CREATE TABLE `pet_status` (
  `id` int(11) NOT NULL,
  `pet_status_id` int(11) NOT NULL,
  `pet_status_desc` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pet_status`
--

INSERT INTO `pet_status` (`id`, `pet_status_id`, `pet_status_desc`) VALUES
(1, 1, 'Available'),
(2, 2, 'Not Available');

-- --------------------------------------------------------

--
-- Table structure for table `pet_type`
--

CREATE TABLE `pet_type` (
  `id` int(11) NOT NULL,
  `pet_type_id` int(11) NOT NULL,
  `pet_type_desc` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pet_type`
--

INSERT INTO `pet_type` (`id`, `pet_type_id`, `pet_type_desc`) VALUES
(1, 1, 'Aspin'),
(2, 2, 'Labrador Retriever'),
(3, 3, 'Pug'),
(4, 4, 'Boston Terrier'),
(5, 5, 'Chihuahua'),
(6, 6, 'Beagle');

-- --------------------------------------------------------

--
-- Table structure for table `places`
--

CREATE TABLE `places` (
  `place_id` int(11) NOT NULL,
  `place_image` varchar(100) NOT NULL,
  `place_name` varchar(100) NOT NULL,
  `place_vicinity` varchar(200) NOT NULL,
  `place_contact` varchar(15) NOT NULL,
  `place_lat` double NOT NULL,
  `place_lng` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `places`
--

INSERT INTO `places` (`place_id`, `place_image`, `place_name`, `place_vicinity`, `place_contact`, `place_lat`, `place_lng`) VALUES
(1, 'Cebu Veterinary Doctors.jpg', 'Cebu Veterinary Doctors - Mactan Branch', 'CVD Centrum Kagudoy Road, Lapu-Lapu City', '(032) 414 0488', 10.292935, 123.9706674),
(2, 'George Animal Clinic.jpg', 'George Animal Clinic', 'Basak-Marigondon Road, Lapu-Lapu City', '(032) 492 0140', 10.2937821, 123.9655224),
(3, 'Mactan Cat and Dog Clinic.jpg', 'Mactan Cat and Dog Clinic', 'Maximo V. Patalinghug Jr. Avenue, Lapu-Lapu City', '(032) 495 9293', 10.294122, 123.9658213),
(5, 'image_none.png', 'Caminade-South Petshop', 'Opon-Airport-Sangi Road, Lapu-Lapu City', '(032) 340 0681', 10.310808, 123.956352),
(6, 'Mactan Veterinary Clinic.jpg', 'Mactan Veterinary Clinic', 'ML Tan Bldg., South Osmena Street, Lapu-Lapu', '(032) 520 6249', 10.3025452, 123.9579763),
(7, 'Caminade-South Petshop.jpg', 'Caminade South Petshop', 'M.L. Quezon National Hwy, Lapu-Lapu City', '(032) 340 0681', 10.313271, 123.957032),
(8, 'Claws and Paws.jpg', 'Claws And Paws', 'M.L. Quezon National Highway, Lapu-Lapu City', '(032) 341 5976', 10.315479, 123.960086),
(9, 'Animal Wellness Veterinary Hospital.jpg', 'Animal Wellness Veterinary Clinic - ParkMall', 'Alfresco, Park Mall Drive, Mandaue City', '(032) 231 4639', 10.3252397, 123.9331023),
(10, 'Shambala.jpg', 'Shambala', '523-C Hernan Cortes Street, Mandaue City', '(032) 319 0412', 10.329723, 123.921904),
(11, 'Dr C Veterinary Clinic.jpg', 'Dr C Veterinary Clinic', 'South Osmena Street, Lapu-Lapu City', '0923 212 2296', 10.3071217, 123.952085),
(12, 'Dr. C Animal Clinic.jpg', 'Dr. C Animal Clinic', 'South Osmena Street, Poblacion, Lapu-Lapu City', '0923 212 2296', 10.307214, 123.952157),
(13, 'image_none.png', 'Georgia\'s Puppies', 'Block 20 Lot 6 Joanna Legacy Homes, Sudtonggan Road, Lapu-Lapu City', 'No Contact', 10.2795776, 123.9607631),
(14, 'image_none.png', 'Bethphage Animal Clinic', 'J-VER Building, Opon-Airport-Sangi Road, Lapu-Lapu City', 'No Contact', 10.3110041, 123.9568353),
(15, 'image_none.png', 'Vets on the Block Pet Wellness and Birth Control Clinic', '2F Ygot Building, Humay-Humay Road, Lapu-Lapu', 'No Contact', 10.3104226, 123.9529359),
(16, 'Dr. Emmanuel Caintic.jpg', 'Dr. Emmanuel Caintic', 'Mactan Animal Clinic, Door 2-3 Amistiso Building, S Osmena Street, Gun-Ob, Lapu-Lapu City', '(032) 341 1440', 10.30885, 123.950563),
(17, 'City Veterinarians Office.jpg', 'City Veterinarians Office', 'Lapu-Lapu City', 'No Contact', 10.3218341, 123.9665907),
(18, 'image_none.png', 'Dr. Teodoro J. Dabocol', 'C.M. Cabahug Street, Isla de Plama, Cambaro, Looc, Mandaue City', '(032) 344 4568', 10.321427, 123.948564),
(19, 'Animal Touch Veterinary Clinic.jpg', 'Animal Touch Veterinary Clinic', 'M. L. Quezon Ave, Cabangcalan, Mandaue City, 6014 Cebu, Philippines', '(032) 345 0766', 10.3521799, 123.923116),
(20, 'Pro - Veterinary Clinic.jpg', 'Pro - Veterinary Clinic', 'Pat Bldg, Hernan Cortes St, Mandaue City, 6014 Cebu, Philippines', '(032) 516 0323', 10.343387, 123.9259099),
(21, 'Dra. Ivy Alvarez.jpg', 'Dra. Ivy Alvarez', '101, Animal Wellness Vet Clinic, Traders Arcade, Hernan Cortes Street, Mandaue City, 6014 Cebu', '(032) 422 7601', 10.336241, 123.923464),
(22, 'DoggyVerse Veterinary Clinic and Supplies.jpg', 'DoggyVerse Veterinary Clinic and Supplies', 'Door 3, DCJ Building, 225 S.B.Cabahug, Street, Ibabao, Mandaue City, 6014 Cebu', '(032) 239 4211', 10.3340924, 123.9445648);

-- --------------------------------------------------------

--
-- Table structure for table `rejected_documents`
--

CREATE TABLE `rejected_documents` (
  `id` int(11) NOT NULL,
  `doc_id` int(11) NOT NULL,
  `rejected_reason` varchar(50) NOT NULL,
  `rejected_by` int(11) NOT NULL,
  `rejected_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `report_lost_pets`
--

CREATE TABLE `report_lost_pets` (
  `report_lost_pets_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `pet_type_id` int(11) NOT NULL,
  `pet_image` varchar(100) NOT NULL,
  `pet_name` varchar(50) NOT NULL,
  `description` varchar(255) NOT NULL,
  `reporter_location` varchar(200) NOT NULL,
  `date_reported` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `role_desc` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `role_id`, `role_desc`) VALUES
(1, 1, 'User'),
(2, 2, 'Admin'),
(3, 3, 'Superuser'),
(4, 4, 'Superadmin');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `user_image` varchar(100) NOT NULL,
  `user_fname` varchar(50) NOT NULL,
  `user_lname` varchar(50) NOT NULL,
  `user_nname` varchar(50) NOT NULL,
  `user_address` varchar(100) NOT NULL,
  `user_email` varchar(50) NOT NULL,
  `user_contact` varchar(12) NOT NULL,
  `user_occupation` varchar(50) NOT NULL,
  `user_username` varchar(50) NOT NULL,
  `user_password` varchar(50) NOT NULL,
  `vkey` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `user_image`, `user_fname`, `user_lname`, `user_nname`, `user_address`, `user_email`, `user_contact`, `user_occupation`, `user_username`, `user_password`, `vkey`) VALUES
(61, '61.jpg', 'Kevin Dale', 'Tabayocyoc', 'KD', 'Basak LLC', 'kevin_sdlc@yahoo.com', '09363088069', 'College Student', 'kevin_sdlc', 'Aptx2019', '281e838e37108ff7a027d3fd0efbeb7a'),
(62, '34.jpg', 'Zardron Angelo', 'Pesquera', 'Zar', 'Happy Homes Mactan', 'zpesquera@lear.com', '09166361646', 'College Student', 'zpesquera', 'qwe', '281e838e37108ff7a027d3fd0efbeb7a');

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

CREATE TABLE `user_roles` (
  `user_role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `created_date` date NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `verified` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_roles`
--

INSERT INTO `user_roles` (`user_role_id`, `user_id`, `role_id`, `created_date`, `is_active`, `verified`) VALUES
(58, 61, 1, '2019-11-27', 1, 0),
(59, 62, 2, '2019-11-27', 1, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `adoptions`
--
ALTER TABLE `adoptions`
  ADD PRIMARY KEY (`adoption_id`);

--
-- Indexes for table `approved_documents`
--
ALTER TABLE `approved_documents`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cancelled_documents`
--
ALTER TABLE `cancelled_documents`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `documents`
--
ALTER TABLE `documents`
  ADD PRIMARY KEY (`doc_id`);

--
-- Indexes for table `document_status`
--
ALTER TABLE `document_status`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `document_type`
--
ALTER TABLE `document_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `file_attachments`
--
ALTER TABLE `file_attachments`
  ADD PRIMARY KEY (`file_attachment_id`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`notification_id`);

--
-- Indexes for table `pets`
--
ALTER TABLE `pets`
  ADD PRIMARY KEY (`pet_id`);

--
-- Indexes for table `pet_live_type`
--
ALTER TABLE `pet_live_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pet_status`
--
ALTER TABLE `pet_status`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pet_type`
--
ALTER TABLE `pet_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `places`
--
ALTER TABLE `places`
  ADD PRIMARY KEY (`place_id`);

--
-- Indexes for table `rejected_documents`
--
ALTER TABLE `rejected_documents`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `report_lost_pets`
--
ALTER TABLE `report_lost_pets`
  ADD PRIMARY KEY (`report_lost_pets_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `adoptions`
--
ALTER TABLE `adoptions`
  MODIFY `adoption_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `approved_documents`
--
ALTER TABLE `approved_documents`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `cancelled_documents`
--
ALTER TABLE `cancelled_documents`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `documents`
--
ALTER TABLE `documents`
  MODIFY `doc_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `document_status`
--
ALTER TABLE `document_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `document_type`
--
ALTER TABLE `document_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `file_attachments`
--
ALTER TABLE `file_attachments`
  MODIFY `file_attachment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `notification_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pets`
--
ALTER TABLE `pets`
  MODIFY `pet_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `pet_live_type`
--
ALTER TABLE `pet_live_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `pet_status`
--
ALTER TABLE `pet_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `pet_type`
--
ALTER TABLE `pet_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `places`
--
ALTER TABLE `places`
  MODIFY `place_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `rejected_documents`
--
ALTER TABLE `rejected_documents`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `report_lost_pets`
--
ALTER TABLE `report_lost_pets`
  MODIFY `report_lost_pets_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;

--
-- AUTO_INCREMENT for table `user_roles`
--
ALTER TABLE `user_roles`
  MODIFY `user_role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
