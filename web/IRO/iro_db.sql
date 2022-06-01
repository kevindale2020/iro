-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 01, 2022 at 01:37 PM
-- Server version: 10.1.31-MariaDB
-- PHP Version: 7.4.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
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
-- Table structure for table `acknowledged_documents`
--

CREATE TABLE `acknowledged_documents` (
  `id` int(11) NOT NULL,
  `doc_id` int(11) NOT NULL,
  `acknowledge_by` int(11) NOT NULL,
  `acknowledged_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `acknowledged_documents`
--

INSERT INTO `acknowledged_documents` (`id`, `doc_id`, `acknowledge_by`, `acknowledged_date`) VALUES
(1, 4, 71, '2018-07-17'),
(2, 5, 71, '2019-03-21'),
(3, 6, 71, '2020-08-27'),
(4, 1, 71, '2017-02-14'),
(5, 9, 71, '2020-04-11'),
(6, 10, 71, '2020-04-11'),
(7, 12, 71, '2020-07-19'),
(8, 16, 71, '2022-06-01');

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
(1, 2, 14, 'jaja', 'jaka', 3, 'jaja', 'jana', 'nana', 0, 0, 0, '', '', 'haja', 0, 'nana', '', 'naja'),
(2, 2, 3, 'nsna', 'bsns', 3, 'hsha', 'nan', 'jaja', 0, 0, 0, '', '', 'nana', 1, '', 'nana', 'nana'),
(3, 74, 10, 'hsjs', 'jsjs', 3, 'usus', 'nana', 'nana', 0, 0, 0, '', '', 'nana', 1, '', 'nana', 'nska'),
(4, 76, 7, 'hshaw', 'nwnw', 2, 'jsja', 'nana', 'jaja', 0, 0, 0, '', '', 'nana', 0, 'jana', '', 'nanna'),
(5, 76, 2, '12', 'Test', 2, 'Test', 'Test', 'Test', 1, 1, 0, 'Test', 'Test', 'Test', 0, 'Test', '', 'Test');

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
(1, 8, 71, '2020-04-11'),
(2, 11, 71, '2020-04-11'),
(3, 15, 71, '2022-06-01');

-- --------------------------------------------------------

--
-- Table structure for table `cancelled_documents`
--

CREATE TABLE `cancelled_documents` (
  `id` int(11) NOT NULL,
  `doc_id` int(11) NOT NULL,
  `cancelled_reason` varchar(100) NOT NULL,
  `cancelled_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cancelled_documents`
--

INSERT INTO `cancelled_documents` (`id`, `doc_id`, `cancelled_reason`, `cancelled_date`) VALUES
(1, 1, 'jss', '2020-02-24');

-- --------------------------------------------------------

--
-- Table structure for table `closed_documents`
--

CREATE TABLE `closed_documents` (
  `id` int(11) NOT NULL,
  `doc_id` int(11) NOT NULL,
  `closed_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `closed_documents`
--

INSERT INTO `closed_documents` (`id`, `doc_id`, `closed_date`) VALUES
(1, 1, '2017-02-14'),
(2, 4, '2018-07-17'),
(3, 5, '2019-03-21'),
(4, 6, '2020-08-27');

-- --------------------------------------------------------

--
-- Table structure for table `committees`
--

CREATE TABLE `committees` (
  `id` int(11) NOT NULL,
  `committee_id` int(11) NOT NULL,
  `committee` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `committees`
--

INSERT INTO `committees` (`id`, `committee_id`, `committee`) VALUES
(1, 1, 'Adoption'),
(2, 2, 'Transportation'),
(3, 3, 'Shelter clean-up'),
(4, 4, 'Educational campaign'),
(5, 5, 'Animal Welfare Implementation'),
(6, 6, 'Fostering'),
(7, 7, 'Dog/Cat Food donation drive (sponsorship)'),
(8, 8, 'Fundraising and Events');

-- --------------------------------------------------------

--
-- Table structure for table `committee_details`
--

CREATE TABLE `committee_details` (
  `id` int(11) NOT NULL,
  `committee_id` int(11) NOT NULL,
  `responsibilities` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `committee_details`
--

INSERT INTO `committee_details` (`id`, `committee_id`, `responsibilities`) VALUES
(1, 1, 'Adoption events (Parkmall, bi-monthly [every 2nd and 4th Saturday], 10am-5pm)\r\n\r\n	Tasks:\r\n\r\n1.	Screen applicants for cat/dog adoption (interview, briefing, etc.)\r\n\r\n2.	Volunteer acquisition (recruitment, orientation)\r\n\r\n3.	Accept donations (cash and in-kind)\r\n\r\n4.	Sell IRO merchandise and fundraiser items\r\n\r\n5.	Distribute flyers; Talk to mall-goers about the org\r\n\r\n6.	IRO Helpdesk (1pm-5pm)\r\n'),
(2, 2, 'Transportation\r\n\r\n	Covers:\r\n\r\n1.	Dog/Cat food delivery (from IRO HQ-Hernan Cortes Mandaue to IRO Center in Pulangbato Talamban, on a weekly basis)\r\n\r\n2.	Vet transports (foster home to vet/shelter to vet/vice versa)\r\n\r\n3.	Adoption days - dog/cat transports(2nd and 4th Saturday of the month, pick up at 8:30am Pulangbato center to Parkmall/send off at 5pm Parkmall to Pulangbato)\r\n\r\n4.	Emergency shelter situations/Rescue Missions\r\n\r\n\r\n'),
(3, 3, 'Shelter clean-up (Pulangbato, Talamban center, usually on a weekend or on a holidy weekday)\r\n\r\n	Includes:\r\n\r\n1.	Dog baths\r\n\r\n2.	Deworming\r\n\r\n3.	Cooking/Feeding\r\n\r\n4.	Clean-up\r\n'),
(4, 4, 'Educational campaigns\r\n\r\n	Includes:\r\n\r\n1.	Educational campaigns (schools, barangay, etc.)\r\n\r\n2.	House visits (rescue reports, adoption follow-ups, etc.)\r\n\r\n3.	Trainings (Rescue, Disaster response, Animal welfare, etc.)\r\n\r\n4.	Impromptu events (Dog shows, Fun run, Decorations, Fundraising, Feeding, etc.)\r\n'),
(5, 5, 'Animal Welfare Implementation:\r\n\r\n1.	Please read R.A. 10631\r\n\r\n'),
(6, 6, 'Fostering\r\n\r\n1.	Temporary shelter for new rescues\r\n\r\n2.	Rehabilitation phase prior to adoption\r\n\r\n3.	Aftercare for newly spayed/neutered cats and dogs\r\n'),
(7, 7, 'Dog/Cat food donation drive\r\n\r\n1.	No Empty Bowl Sponser = 100php/month for the whole year\r\n\r\n2.	Sponsors will receive an IRO mug as souvenir\r\n\r\n3.	For only 1,200php a year, you can help IRO feed the 50+ dogs and 20 cats at the IRO center\r\n\r\n4.	Pledges can be sent via: Chinabank, Paypal, IRO headquarters (Chaka Pet Shop, Hernan Cortes), Parkmall adoption booth (every 2nd and 4th Saturday of the month, 10am-5pm)\r\n\r\n5.	Once a volunteer signs ups, he/she will receive an IRO mug\r\n\r\n6.	Acknowledgement for pledges will be posted regularly on the page and a master list will be posted at the end of the year\r\n\r\n7.	Send us a screenshot of the receipt from every transaction so we could acknowledge properly. Log books will be provided at the drop-off sites\r\n'),
(8, 8, 'Fundraising and Events\r\n\r\n1.	Dog Walks or Runs\r\n\r\nDog walks or runs are the simplest animal shelter fundraising idea to start with. There are several ways to go about organizing a dog walk or a dog run.\r\n\r\n2.	Crowfunding\r\n\r\nFundraising by using crowdfunding is a great way for organizations to grow their online audience and address their fundraising needs, and this is especially true of smaller organizations\r\n');

-- --------------------------------------------------------

--
-- Table structure for table `documents`
--

CREATE TABLE `documents` (
  `doc_id` int(11) NOT NULL,
  `doc_form_id` int(11) NOT NULL,
  `doc_type_id` int(11) NOT NULL,
  `doc_status_id` int(11) NOT NULL,
  `date_submitted` date NOT NULL,
  `date_modified` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `documents`
--

INSERT INTO `documents` (`doc_id`, `doc_form_id`, `doc_type_id`, `doc_status_id`, `date_submitted`, `date_modified`) VALUES
(1, 1, 3, 6, '2020-02-24', '0000-00-00'),
(2, 1, 1, 1, '2020-02-24', '0000-00-00'),
(3, 1, 2, 1, '2020-02-24', '2020-03-09'),
(4, 2, 3, 6, '2020-02-26', '0000-00-00'),
(5, 3, 3, 6, '2020-02-26', '0000-00-00'),
(6, 4, 3, 6, '2020-02-26', '0000-00-00'),
(7, 5, 3, 6, '2020-02-27', '0000-00-00'),
(8, 2, 1, 2, '2020-04-11', '0000-00-00'),
(9, 5, 3, 5, '2020-04-11', '0000-00-00'),
(10, 6, 3, 5, '2020-04-11', '0000-00-00'),
(11, 3, 1, 2, '2020-04-11', '0000-00-00'),
(12, 7, 3, 5, '2020-07-19', '0000-00-00'),
(13, 4, 1, 1, '2020-11-02', '0000-00-00'),
(14, 8, 3, 1, '2022-03-14', '0000-00-00'),
(15, 5, 1, 2, '2022-06-01', '0000-00-00'),
(16, 9, 3, 5, '2022-06-01', '0000-00-00');

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
(4, 4, 'Cancelled'),
(5, 5, 'Acknowledged'),
(6, 6, 'Closed'),
(7, 7, 'Posted');

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
(1, 1, 'Pet Adoption Application Form'),
(2, 2, 'Volunteer Application Form'),
(3, 3, 'Incident Report Form');

-- --------------------------------------------------------

--
-- Table structure for table `donations`
--

CREATE TABLE `donations` (
  `donation_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `donation_type_id` int(11) NOT NULL,
  `account_name` varchar(50) NOT NULL,
  `account_number` varchar(50) NOT NULL,
  `amount` decimal(11,2) NOT NULL,
  `date` date NOT NULL,
  `image` varchar(255) NOT NULL,
  `date_modified` date NOT NULL,
  `date_verified` date NOT NULL,
  `verified_by` int(11) NOT NULL,
  `is_verified` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `donations`
--

INSERT INTO `donations` (`donation_id`, `user_id`, `donation_type_id`, `account_name`, `account_number`, `amount`, `date`, `image`, `date_modified`, `date_verified`, `verified_by`, `is_verified`) VALUES
(1, 2, 5, '', '', '5000.00', '2020-03-03', 'IMG20200302205808.jpg', '0000-00-00', '0000-00-00', 0, 0),
(2, 76, 3, 'sss', '222', '22.00', '2020-09-30', '20200912_220759.jpg', '0000-00-00', '0000-00-00', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `donation_type`
--

CREATE TABLE `donation_type` (
  `id` int(11) NOT NULL,
  `donation_type_id` int(11) NOT NULL,
  `donation_type_desc` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `donation_type`
--

INSERT INTO `donation_type` (`id`, `donation_type_id`, `donation_type_desc`) VALUES
(1, 1, 'Choose Payment Method'),
(2, 2, 'Paypal'),
(3, 3, 'China Bank'),
(4, 4, 'Western Union'),
(5, 5, 'LBC'),
(6, 6, 'Palawan Pera Padala'),
(7, 7, 'Cebuana Lhuillier'),
(8, 8, 'M Lhuiller');

-- --------------------------------------------------------

--
-- Table structure for table `events`
--

CREATE TABLE `events` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `image` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `desc` text NOT NULL,
  `venue` varchar(255) NOT NULL,
  `color` varchar(7) NOT NULL,
  `start` datetime NOT NULL,
  `end` datetime NOT NULL,
  `time_start` varchar(20) NOT NULL,
  `time_end` varchar(20) NOT NULL,
  `date_posted` varchar(50) NOT NULL,
  `is_active` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `events`
--

INSERT INTO `events` (`id`, `user_id`, `image`, `title`, `desc`, `venue`, `color`, `start`, `end`, `time_start`, `time_end`, `date_posted`, `is_active`) VALUES
(1, 71, '50532340_2700764133268898_326989080153817088_n.jpg', 'Fire works display', 'Fire works display for the celebration of All Pets Day', 'Park mall, Parking Lot', '#0071c5', '2020-04-28 00:00:00', '2020-04-28 00:00:00', '08:00 PM', '09:00 PM', 'April 27, 2020, 8:37 pm', 1),
(2, 71, '22539896_1222889571188300_8005974967636228526_n.jpg', 'dfdf', 'dfdf', 'dfdfd', '#FF8C00', '2020-07-14 00:00:00', '2020-07-14 00:00:00', '01:00 PM', '04:00 PM', 'July 14, 2020, 10:34 pm', 1),
(3, 71, 'laundry2.png', 'sdfsd', 'dsfsdf', 'sdfds', '#008000', '2020-07-14 00:00:00', '2020-07-14 00:00:00', '01:00 AM', '02:00 AM', 'July 14, 2020, 10:36 pm', 1),
(4, 71, 'laundry1.jpg', 'Java Seventh Edition', 'fdgdfg', 'fdgdfgdf', '#000', '2020-07-14 00:00:00', '2020-07-14 00:00:00', '01:00 AM', '03:00 AM', 'July 14, 2020, 10:37 pm', 1);

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
  `updated_by` int(11) NOT NULL,
  `updated_date` date NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `file_attachments`
--

INSERT INTO `file_attachments` (`file_attachment_id`, `doc_id`, `file_name`, `file_path`, `created_by`, `created_date`, `updated_by`, `updated_date`, `is_active`) VALUES
(1, 1, 'images (18).jpeg', 'images/attachments/images (18).jpeg', 2, '2020-02-24', 0, '0000-00-00', 1),
(2, 2, 'images (17).jpeg', 'images/attachments/images (17).jpeg', 2, '2020-02-24', 0, '0000-00-00', 1),
(3, 3, 'images (18).jpeg', 'images/attachments/images (18).jpeg', 2, '2020-02-24', 0, '0000-00-00', 1),
(4, 4, '20200216_205206.jpg', 'images/attachments/20200216_205206.jpg', 2, '2020-02-26', 0, '0000-00-00', 1),
(5, 5, 'images (15).jpeg', 'images/attachments/images (15).jpeg', 2, '2020-02-26', 0, '0000-00-00', 1),
(6, 6, 'download (1).jpeg', 'images/attachments/download (1).jpeg', 2, '2020-02-26', 0, '0000-00-00', 1),
(7, 7, '', 'images/attachments/', 0, '2020-02-27', 0, '0000-00-00', 1),
(8, 8, '20200404_230320.jpg', 'images/attachments/20200404_230320.jpg', 2, '2020-04-11', 0, '0000-00-00', 1),
(9, 9, '20200317_231930.jpg', 'images/attachments/20200317_231930.jpg', 2, '2020-04-11', 0, '0000-00-00', 1),
(10, 10, '20200313_020330.jpg', 'images/attachments/20200313_020330.jpg', 74, '2020-04-11', 0, '0000-00-00', 1),
(11, 11, '20200309_145001.jpg', 'images/attachments/20200309_145001.jpg', 74, '2020-04-11', 0, '0000-00-00', 1),
(12, 12, '20200719_201209.jpg', 'images/attachments/20200719_201209.jpg', 76, '2020-07-19', 0, '0000-00-00', 1),
(13, 13, '20200911_221339.jpg', 'images/attachments/20200911_221339.jpg', 76, '2020-11-02', 0, '0000-00-00', 1),
(14, 14, '20220310_211913.jpg', 'images/attachments/20220310_211913.jpg', 76, '2022-03-14', 0, '0000-00-00', 1),
(15, 15, 'chrome_screenshot_1623661360749.png', 'images/attachments/chrome_screenshot_1623661360749.png', 76, '2022-06-01', 0, '0000-00-00', 1),
(16, 16, '20220422_022150.jpg', 'images/attachments/20220422_022150.jpg', 76, '2022-06-01', 0, '0000-00-00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `guidelines`
--

CREATE TABLE `guidelines` (
  `id` int(11) NOT NULL,
  `guideline_id` int(11) NOT NULL,
  `guideline` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `guidelines`
--

INSERT INTO `guidelines` (`id`, `guideline_id`, `guideline`) VALUES
(1, 1, 'Vaccinations'),
(2, 2, 'Spaying and Neutering'),
(3, 3, 'Internal/External Parasite Control'),
(4, 4, 'Vet Visits'),
(5, 5, 'Grooming'),
(6, 6, 'Nutrition');

-- --------------------------------------------------------

--
-- Table structure for table `guideline_details`
--

CREATE TABLE `guideline_details` (
  `id` int(11) NOT NULL,
  `guideline_id` int(11) NOT NULL,
  `details` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `guideline_details`
--

INSERT INTO `guideline_details` (`id`, `guideline_id`, `details`) VALUES
(1, 1, 'It is essential to have your pet vaccinated as soon as they are old enough in order to protect them from contagious and potentially fatal diseases. Even pets that are kept indoors are at risk of catching diseases as many are airborne and there is always the risk that your pet may escape and risk infection. Remember that even after your pet receives their vaccinations, they can take a matter of weeks to become effective so speak to your vet about your pets vaccination schedule to get all the facts.\r\n\r\nYour pets vaccination schedule, including their booster vaccinations, will depend on the type of vaccine used, your pets age, medical history, location or risk of infection of certain diseases. Speak to your vet about what vaccinations your pet requires and what is recommended for their booster vaccinations.\r\n\r\nPuppies and dogs\r\n\r\nEvery puppy or dog should receive a set of core vaccines as recommended by the World Small Animal Veterinary Association (WSAVA). These are vaccines that protect animals from severe life-threatening diseases that have a global distribution and include:\r\n\r\na)	Canine Distemper Virus\r\nb)	Canine Adenovirus (Canine Infectious Hepatitis)\r\nc)	Canine Parvovirus Type 2 (Parvo)\r\nd)	Rabies\r\n\r\nNon-core vaccines may be determined to be necessary by your veterinarian if your puppy or dog is at risk of contracting the diseases. Non-core vaccines include:\r\n\r\na)	Leptospirosis strains\r\nb)	Parainfluenza Virus\r\nc)	Kennel Cough\r\n\r\nKittens and cats\r\n\r\nThe World Small Animal Veterinary Association (WSAVA) recommends the following core vaccines for kittens and cats:\r\n\r\na)	Feline Panleukopenia virus (also known as feline parvovirus or feline infectious enteritis)\r\nb)	Feline Herpes virus\r\nc)	Feline Calicivirus Infection\r\nd)	Rabies\r\n\r\nNon-core vaccines may be determined to be necessary by your veterinarian if your kitten or cat is at risk of contracting the diseases. Non-core vaccines include:\r\n\r\na)	Feline Leukaemia\r\nb)	Chlamydophila felis\r\nc)	Bordetella bronchiseptica\r\nd)	Feline immunodeficiency virus\r\n\r\nMost pets show no side-effects following vaccinations however some may develop one or more of the following:\r\n\r\na)	Sensitivity or swelling where the vaccination was administered\r\nb)	Mild fever\r\nc)	Decreased appetite\r\nd)	Reduced activity or disinterest in play/activity\r\ne)	Sneezing, nasal discharge or coughing\r\n\r\nIf any of the above signs persist for more than a few days, consult your veterinarian.\r\n\r\nSeek IMMEDIATE veterinary attention if your pet displays any of the following signs following a vaccination:\r\n\r\na)	Persistent vomiting or diarrhea\r\nb)	Swelling of the muzzle (nose), face, neck and/or eyes\r\nc)	Difficulty breathing\r\nd)	Severe cough\r\ne)	Persistent scratching and/or skin that feels or looks bumpy\r\nf)	Collapse\r\n\r\n\r\n\r\n'),
(2, 2, 'Spaying (for females) and neutering (for males) not only prevents unwanted litters but can also have a number of health and behavioral advantages for your pet. Spay and neuter operations are routine surgeries performed while the animal is under anesthetic and in most cases, your pet can be back at home that same day or within a few days following their operation.\r\n\r\nSpaying consists of removing the female animals uterus and both ovaries. This will not only stop the animal from menstruating (going into heat) which can be very noisy and messy, it will stop attention from male cats or dogs interested in mating and help stop your female cat or dog from escaping as they attempt to find a mate. Spaying your female pet can also reduce the risk of mammary tumors and womb infections.\r\n\r\nNeutering is the removal of the male animals testicles. This can help to reduce aggressive and territorial behavior such as spraying or fighting which can result in serious injury and risk of contracting diseases such as Feline Immunodeficiency Virus (FIV). Neutering can also reduce the risk of future prostate problems and eliminates the concern for your pet developing testicular cancer.\r\n\r\nEvery day, IRO sees first-hand the consequences of an unspayed female cat or dog and the staggering number of unwanted animals on the street, being injured by traffic accidents or the victims of cruelty or abuse. With a simple surgical procedure, YOU can help prevent more unwanted animals struggling to survive. It really is that simple.');

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE `items` (
  `item_id` int(11) NOT NULL,
  `item_category_id` int(11) NOT NULL,
  `item_image` varchar(100) NOT NULL,
  `item_name` varchar(50) NOT NULL,
  `item_desc` varchar(50) NOT NULL,
  `item_qty` int(11) NOT NULL,
  `item_price` double(11,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`item_id`, `item_category_id`, `item_image`, `item_name`, `item_desc`, `item_qty`, `item_price`) VALUES
(1, 1, 'fan.jpg', 'Fan', 'An IRO fan', 50, 25.00),
(2, 1, 'mug.jpg', 'Mug', 'An IRO mug', 50, 250.00),
(3, 1, 'tumbler.jpg', 'Tumbler', 'An IRO tumbler', 10, 150.00),
(4, 2, 'pin.jpg', 'Pin', 'An IRO pin', 45, 1.00),
(5, 2, 'keychain.jpg', 'Keychain', 'An IRO keychain', 20, 80.00),
(6, 2, 'sticker.jpg', 'Sticker', 'An IRO sticker', 50, 150.00);

-- --------------------------------------------------------

--
-- Table structure for table `item_category`
--

CREATE TABLE `item_category` (
  `id` int(11) NOT NULL,
  `item_category_id` int(11) NOT NULL,
  `item_category_desc` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `item_category`
--

INSERT INTO `item_category` (`id`, `item_category_id`, `item_category_desc`) VALUES
(1, 1, 'Personal Use'),
(2, 2, 'Accessories');

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `notification_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `notification_type_id` int(11) NOT NULL,
  `subject` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `date` varchar(50) NOT NULL,
  `is_seen` tinyint(1) NOT NULL DEFAULT '0',
  `is_seen2` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notifications`
--

INSERT INTO `notifications` (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES
(200, 2, 4, 'Lost and Found', 'Your report for lost and found has been acknowledged', 'February 26, 2020, 10:36 pm', 1, 1),
(201, 2, 6, 'Animal Cruelty', 'Your report for animal cruelty has been acknowledged', 'February 26, 2020, 10:37 pm', 1, 1),
(202, 2, 5, 'Homeless Pet', 'Your report for homeless pet has been acknowledged', 'February 26, 2020, 10:42 pm', 1, 1),
(203, 2, 1, 'Adoption', 'Your application form has been approved. kindly visit our booth at parkmall on 2nd or 4th saturday of the month for final screening of your adoption.', 'April 11, 2020, 9:28 pm', 1, 1),
(204, 2, 4, 'Lost and Found', 'Your report for lost and found has been acknowledged', 'April 11, 2020, 9:29 pm', 1, 1),
(205, 74, 6, 'Animal Cruelty', 'Your report for animal cruelty has been acknowledged', 'April 11, 2020, 9:36 pm', 1, 1),
(206, 74, 1, 'Adoption', 'Your application form has been approved. kindly visit our booth at parkmall on 2nd or 4th saturday of the month for final screening of your adoption.', 'April 11, 2020, 9:38 pm', 1, 0),
(207, 2, 2, 'Event', 'New event has been added', 'April 27, 2020, 8:37 pm', 1, 1),
(208, 3, 2, 'Event', 'New event has been added', 'April 27, 2020, 8:37 pm', 0, 0),
(209, 74, 2, 'Event', 'New event has been added', 'April 27, 2020, 8:37 pm', 0, 0),
(211, 2, 2, 'Event', 'New event has been added', 'July 14, 2020, 10:34 pm', 0, 0),
(212, 3, 2, 'Event', 'New event has been added', 'July 14, 2020, 10:34 pm', 0, 0),
(213, 74, 2, 'Event', 'New event has been added', 'July 14, 2020, 10:34 pm', 0, 0),
(215, 2, 2, 'Event', 'New event has been added', 'July 14, 2020, 10:36 pm', 0, 0),
(216, 3, 2, 'Event', 'New event has been added', 'July 14, 2020, 10:36 pm', 0, 0),
(217, 74, 2, 'Event', 'New event has been added', 'July 14, 2020, 10:36 pm', 0, 0),
(219, 2, 2, 'Event', 'New event has been added', 'July 14, 2020, 10:37 pm', 0, 0),
(220, 3, 2, 'Event', 'New event has been added', 'July 14, 2020, 10:37 pm', 0, 0),
(221, 74, 2, 'Event', 'New event has been added', 'July 14, 2020, 10:37 pm', 0, 0),
(223, 76, 6, 'Animal Cruelty', 'Your report for animal cruelty has been acknowledged', 'July 19, 2020, 9:09 pm', 1, 1),
(224, 2, 8, 'House Visit', ',m,', 'September 8, 2020, 2:41 pm', 0, 0),
(225, 76, 1, 'Adoption', 'Your application form has been approved. kindly visit our booth at parkmall on 2nd or 4th saturday of the month for final screening of your adoption.', 'June 1, 2022, 7:24 pm', 1, 1),
(226, 76, 4, 'Lost and Found', 'Your report for lost and found has been acknowledged', 'June 1, 2022, 7:26 pm', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `notifications_admin`
--

CREATE TABLE `notifications_admin` (
  `notification_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `notification_type_id` int(11) NOT NULL,
  `subject` varchar(50) NOT NULL,
  `content` varchar(100) NOT NULL,
  `date` varchar(50) NOT NULL,
  `is_seen` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notifications_admin`
--

INSERT INTO `notifications_admin` (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`) VALUES
(1, 2, 4, 'Lost & Found', 'A new lost & found report has been submitted', 'February 24, 2020, 1:54 am', 1),
(2, 2, 5, 'Homeless Pet', 'A new homeless pet report has been submitted', 'February 24, 2020, 1:58 am', 1),
(3, 2, 1, 'Adoption', 'A new adoption request has been submitted', 'February 24, 2020, 1:59 am', 0),
(4, 2, 3, 'Volunteer', 'A new volunteer request has been submitted', 'February 24, 2020, 1:59 am', 0),
(5, 2, 4, 'Lost & Found', 'A new lost & found report has been submitted', 'February 26, 2020, 10:36 pm', 1),
(6, 2, 6, 'Animal Cruelty', 'A new animal cruelty report has been submitted', 'February 26, 2020, 10:37 pm', 1),
(7, 2, 5, 'Homeless Pet', 'A new homeless pet report has been submitted', 'February 26, 2020, 10:42 pm', 1),
(8, 0, 6, 'Animal Cruelty', 'A new animal cruelty report has been submitted', 'February 27, 2020, 11:14 am', 0),
(9, 2, 7, 'Donation', 'An amount 5000 was donated', 'March 3, 2020, 10:26 am', 1),
(10, 2, 1, 'Adoption', 'A new adoption request has been submitted', 'April 11, 2020, 9:27 pm', 0),
(11, 2, 4, 'Lost & Found', 'A new lost & found report has been submitted', 'April 11, 2020, 9:29 pm', 1),
(12, 74, 6, 'Animal Cruelty', 'A new animal cruelty report has been submitted', 'April 11, 2020, 9:36 pm', 0),
(13, 74, 1, 'Adoption', 'A new adoption request has been submitted', 'April 11, 2020, 9:38 pm', 0),
(14, 76, 6, 'Animal Cruelty', 'A new animal cruelty report has been submitted', 'July 19, 2020, 9:09 pm', 0),
(15, 76, 7, 'Donation', 'An amount 22 was donated', 'September 30, 2020, 12:01 pm', 0),
(16, 76, 1, 'Adoption', 'A new adoption request has been submitted', 'November 2, 2020, 10:35 am', 0),
(17, 76, 6, 'Animal Cruelty', 'A new animal cruelty report has been submitted', 'March 14, 2022, 1:17 am', 0),
(18, 76, 1, 'Adoption', 'A new adoption request has been submitted', 'June 1, 2022, 7:24 pm', 0),
(19, 76, 4, 'Lost & Found', 'A new lost & found report has been submitted', 'June 1, 2022, 7:25 pm', 1);

-- --------------------------------------------------------

--
-- Table structure for table `notification_type`
--

CREATE TABLE `notification_type` (
  `id` int(11) NOT NULL,
  `notification_type_id` int(11) NOT NULL,
  `notification_type_desc` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notification_type`
--

INSERT INTO `notification_type` (`id`, `notification_type_id`, `notification_type_desc`) VALUES
(1, 1, 'Adoption'),
(2, 2, 'Event'),
(3, 3, 'Volunteer'),
(4, 4, 'Lost and Found'),
(5, 5, 'Homeless Pets'),
(6, 6, 'Animal Cruelty'),
(7, 7, 'Donation'),
(8, 8, 'Home Visit');

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
  `acquired_from` varchar(30) NOT NULL,
  `pet_desc` varchar(255) NOT NULL,
  `pet_type_id` int(11) NOT NULL,
  `pet_status_id` int(11) NOT NULL,
  `date_added` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pets`
--

INSERT INTO `pets` (`pet_id`, `pet_image`, `pet_name`, `pet_gender`, `pet_age`, `acquired_from`, `pet_desc`, `pet_type_id`, `pet_status_id`, `date_added`) VALUES
(1, 'bob.jpg', 'Bob', 'Male', '2 years old', 'Lost and Found', 'He is playful and active', 3, 1, '2019-10-01'),
(2, 'michael.jpg', 'Michael', 'Male', '5 years old', 'Animal Cruelty', 'He is playful and active', 2, 2, '2019-11-03'),
(3, 'diablo.jpg', 'Diablo', 'Male', '4 years old', 'Lost and Found', 'He is hyperactive and eats everything he see', 4, 2, '2019-10-09'),
(4, 'princess.jpg', 'Princess', 'Female', '1 year old', 'Homeless Pet', 'She is cute and adorable', 5, 1, '2019-09-18'),
(5, 'shoeshine.jpg', 'Shoeshine', 'Male', '3 months', 'Homeless Pet', 'He is playful and active', 6, 1, '2019-08-03'),
(6, 'chow chow.jpg', 'Chiwie', 'Male', '2 months', '', 'asdasdasfas', 1, 1, '2019-08-10'),
(7, 'shihtzu.jpg', 'Britney', 'Female', '1 year and 3months', 'Animal Cruelty', 'She eats a lot of food', 4, 1, '2019-11-13'),
(10, 'red-nose-pitbull-1-e1520600821708.jpg', 'Boozer', 'Male', '3 years old', 'Homeless Pet', 'He eats a lot of food.', 4, 2, '2019-12-04');

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

--
-- Dumping data for table `rejected_documents`
--

INSERT INTO `rejected_documents` (`id`, `doc_id`, `rejected_reason`, `rejected_by`, `rejected_date`) VALUES
(1, 72, 'You\'re a irresponsible owner.', 71, '2020-02-20'),
(2, 74, 'dili pwede kay under age paka', 71, '2020-02-20');

-- --------------------------------------------------------

--
-- Table structure for table `reports`
--

CREATE TABLE `reports` (
  `report_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `report_type_id` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` text NOT NULL,
  `reporter_location` varchar(200) NOT NULL,
  `date_reported` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `reports`
--

INSERT INTO `reports` (`report_id`, `user_id`, `report_type_id`, `title`, `description`, `reporter_location`, `date_reported`) VALUES
(1, 2, 3, 'uaua', 'jaja', 'Basak Eskwelahan Rd, Lapu-Lapu City, Cebu, Philippines', '2017-02-14'),
(2, 2, 2, 'I lost my pet', 'I lost my pet Mocha. Last seen outside our gate at around 4pm yesterday. If found please contact 09363088069', 'Basak Eskwelahan Rd, Lapu-Lapu City, Cebu, Philippines', '2018-07-17'),
(3, 2, 4, 'Beaten dog', 'I saw this dog getting beaten up by our neighbors', 'Basak Eskwelahan Rd, Lapu-Lapu City, Cebu, Philippines', '2019-03-21'),
(4, 2, 3, 'This dog needa help asap', 'This dog needs to be rescued', 'Basak Eskwelahan Rd, Lapu-Lapu City, Cebu, Philippines', '2020-08-27'),
(5, 2, 2, 'My dog was lost', 'I lost this pet', 'Basak Eskwelahan Rd, Lapu-Lapu City, Cebu, Philippines', 'April 11, 2020, 9:29 pm'),
(6, 74, 4, 'This cat was abused', 'Cat was abused', 'Basak Eskwelahan Rd, Lapu-Lapu City, Cebu, Philippines', 'April 11, 2020, 9:36 pm'),
(7, 76, 4, 'kim dana tortured mocha', 'tortured pet', 'Basak Eskwelahan Rd, Lapu-Lapu City, Cebu, Philippines', 'July 19, 2020, 9:09 pm'),
(8, 76, 4, 'Test', 'Test', 'Ccxm, Lapu-Lapu City, Cebu, Philippines', 'March 14, 2022, 1:17 am'),
(9, 76, 2, 'Test', 'Test', 'Ccxm, Lapu-Lapu City, Cebu, Philippines', 'June 1, 2022, 7:25 pm');

-- --------------------------------------------------------

--
-- Table structure for table `report_type`
--

CREATE TABLE `report_type` (
  `id` int(11) NOT NULL,
  `report_type_id` int(11) NOT NULL,
  `report_type_desc` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `report_type`
--

INSERT INTO `report_type` (`id`, `report_type_id`, `report_type_desc`) VALUES
(1, 1, 'Choose Incident'),
(2, 2, 'Lost and Found Pet'),
(3, 3, 'Homeless Pet'),
(4, 4, 'Animal Cruelty');

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
(1, 1, 'Pet Enthusiast'),
(2, 2, 'IRO'),
(3, 3, 'Volunteer'),
(4, 4, 'Superadmin'),
(5, 5, 'City Vet');

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
(1, 'user_none.png', 'Super', 'Admin', 'Developer', 'UCLM', 'system.developer@gmail.com', '09193157707', 'Developer', 'superadmin', 'superadmin', ''),
(2, '2.jpg', 'Kevin Dale', 'Tabayocyoc', 'KD', 'Basak LLC', 'kevin_sdlc@yahoo.com', '09363088069', 'College Student', 'kevin_sdlc', 'Aptx2019', '281e838e37108ff7a027d3fd0efbeb7a'),
(3, 'user_none.png', 'Zardron', 'Pesquera', 'Zar', 'Bankal LLC', 'zpesquera@lear.com', '09166361646', 'Web Developer', 'qweqwe', 'qwe123', '370cc0903d14359182eed64a6b1f6971'),
(71, 'uc4.jpg', 'Zardron Angelo', 'Pesquera', ' Zar', 'Happy Homes Mactan', 'a.pesquera1968@gmail.com', '09166361646', 'Web Developer', 'zpesquera', 'qweqweqwe', ''),
(72, 'db design.png', 'Regine', 'Morales', 'Regine', 'Mandaue City', 'reginmeorales.rcm@yahoo.com', '09291226446', 'Project Manager', 'cityvet', 'Password123', ''),
(74, '74.jpg', 'Renz', 'Mandin', 'Renz', 'Cebu City', 'renz.mandin@gmail.com', '09121226446', 'qwe', 'renz123', 'Password123', ''),
(76, '76.jpg', 'Kevin', 'Owens', 'KO', 'Los Angeles California', 'kevindale2017@gmail.com', '09363088069', 'Employee', 'kevin_owens', 'kevin123', 'ffe2fe00253952c01f8b4094b4535361'),
(77, '', 'klaus', 'tabayocyoc', 'klaus', 'basak lapu-lapu', 'klaus.sdlc@gmail.com', '09335184822', 'student', 'klaus', '12345678', 'd1da3ff09d976ac0383903d39a9d0b9d'),
(78, '', 'Kevin Dale', 'Tabayocyoc', 'KD', 'Basak LLC', 'kevin@hurdman.net', '09363088069', 'Employee', 'kevindale2020', 'Aptx@2020', 'aa956671e409a1d4bda804c6dc7c48c5');

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
(58, 2, 3, '2019-11-27', 1, 1),
(67, 3, 1, '2020-02-08', 1, 1),
(70, 1, 4, '2020-02-08', 1, 1),
(71, 71, 2, '2020-02-20', 1, 0),
(72, 72, 5, '2020-02-20', 1, 0),
(74, 74, 1, '2020-02-20', 1, 1),
(76, 76, 1, '2020-02-26', 1, 1),
(77, 77, 1, '2020-07-19', 1, 0),
(78, 78, 1, '2020-09-27', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `volunteer`
--

CREATE TABLE `volunteer` (
  `volunteer_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `is_adoption` tinyint(1) NOT NULL,
  `is_transportation` tinyint(1) NOT NULL,
  `is_shelter_clean_up` tinyint(1) NOT NULL,
  `is_educational_campaign` tinyint(1) NOT NULL,
  `is_animal_welfare` tinyint(1) NOT NULL,
  `is_fostering` tinyint(1) NOT NULL,
  `is_food_donation_drive` tinyint(1) NOT NULL,
  `is_events` tinyint(1) NOT NULL,
  `is_active` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `volunteer`
--

INSERT INTO `volunteer` (`volunteer_id`, `user_id`, `is_adoption`, `is_transportation`, `is_shelter_clean_up`, `is_educational_campaign`, `is_animal_welfare`, `is_fostering`, `is_food_donation_drive`, `is_events`, `is_active`) VALUES
(1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `acknowledged_documents`
--
ALTER TABLE `acknowledged_documents`
  ADD PRIMARY KEY (`id`);

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
-- Indexes for table `closed_documents`
--
ALTER TABLE `closed_documents`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `committees`
--
ALTER TABLE `committees`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `committee_details`
--
ALTER TABLE `committee_details`
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
-- Indexes for table `donations`
--
ALTER TABLE `donations`
  ADD PRIMARY KEY (`donation_id`);

--
-- Indexes for table `donation_type`
--
ALTER TABLE `donation_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `file_attachments`
--
ALTER TABLE `file_attachments`
  ADD PRIMARY KEY (`file_attachment_id`);

--
-- Indexes for table `guidelines`
--
ALTER TABLE `guidelines`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `guideline_details`
--
ALTER TABLE `guideline_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`item_id`);

--
-- Indexes for table `item_category`
--
ALTER TABLE `item_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`notification_id`);

--
-- Indexes for table `notifications_admin`
--
ALTER TABLE `notifications_admin`
  ADD PRIMARY KEY (`notification_id`);

--
-- Indexes for table `notification_type`
--
ALTER TABLE `notification_type`
  ADD PRIMARY KEY (`id`);

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
-- Indexes for table `reports`
--
ALTER TABLE `reports`
  ADD PRIMARY KEY (`report_id`);

--
-- Indexes for table `report_type`
--
ALTER TABLE `report_type`
  ADD PRIMARY KEY (`id`);

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
-- Indexes for table `volunteer`
--
ALTER TABLE `volunteer`
  ADD PRIMARY KEY (`volunteer_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `acknowledged_documents`
--
ALTER TABLE `acknowledged_documents`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `adoptions`
--
ALTER TABLE `adoptions`
  MODIFY `adoption_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `approved_documents`
--
ALTER TABLE `approved_documents`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `cancelled_documents`
--
ALTER TABLE `cancelled_documents`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `closed_documents`
--
ALTER TABLE `closed_documents`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `committees`
--
ALTER TABLE `committees`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `committee_details`
--
ALTER TABLE `committee_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `documents`
--
ALTER TABLE `documents`
  MODIFY `doc_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `document_status`
--
ALTER TABLE `document_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `document_type`
--
ALTER TABLE `document_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `donations`
--
ALTER TABLE `donations`
  MODIFY `donation_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `donation_type`
--
ALTER TABLE `donation_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `events`
--
ALTER TABLE `events`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `file_attachments`
--
ALTER TABLE `file_attachments`
  MODIFY `file_attachment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `guidelines`
--
ALTER TABLE `guidelines`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `guideline_details`
--
ALTER TABLE `guideline_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `items`
--
ALTER TABLE `items`
  MODIFY `item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `item_category`
--
ALTER TABLE `item_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `notification_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=227;

--
-- AUTO_INCREMENT for table `notifications_admin`
--
ALTER TABLE `notifications_admin`
  MODIFY `notification_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `notification_type`
--
ALTER TABLE `notification_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `pets`
--
ALTER TABLE `pets`
  MODIFY `pet_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `reports`
--
ALTER TABLE `reports`
  MODIFY `report_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `report_type`
--
ALTER TABLE `report_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=79;

--
-- AUTO_INCREMENT for table `user_roles`
--
ALTER TABLE `user_roles`
  MODIFY `user_role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=79;

--
-- AUTO_INCREMENT for table `volunteer`
--
ALTER TABLE `volunteer`
  MODIFY `volunteer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
