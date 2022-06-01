-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 22, 2020 at 03:54 AM
-- Server version: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `iro_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `acknowledged_documents`
--

CREATE TABLE IF NOT EXISTS `acknowledged_documents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_id` int(11) NOT NULL,
  `acknowledge_by` int(11) NOT NULL,
  `acknowledged_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=20 ;

--
-- Dumping data for table `acknowledged_documents`
--

INSERT INTO `acknowledged_documents` (`id`, `doc_id`, `acknowledge_by`, `acknowledged_date`) VALUES
(1, 75, 71, '2020-02-20'),
(16, 76, 71, '2020-02-20'),
(17, 77, 71, '2020-02-20'),
(18, 78, 71, '2020-02-20'),
(19, 79, 71, '2020-02-20');

-- --------------------------------------------------------

--
-- Table structure for table `adoptions`
--

CREATE TABLE IF NOT EXISTS `adoptions` (
  `adoption_id` int(11) NOT NULL AUTO_INCREMENT,
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
  `comments` varchar(100) NOT NULL,
  PRIMARY KEY (`adoption_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=26 ;

--
-- Dumping data for table `adoptions`
--

INSERT INTO `adoptions` (`adoption_id`, `user_id`, `pet_id`, `children_age`, `reason`, `pet_live_type_id`, `current_pet`, `past_pet`, `past_pet_details`, `have_yard`, `is_fenced`, `have_vet`, `vet_name`, `vet_address`, `circumstances`, `is_agreed`, `reason_disagree`, `home_visit_time`, `comments`) VALUES
(16, 2, 3, 'kssanka', 'buavuva', 3, 'viabisna', '9ah9aha', 'biabibaa', 0, 0, 0, '', '', 'biababa', 1, 'ibaibaa', '', 'ibabiaba'),
(25, 2, 6, 'bdhahshsha', 'hdjJajJsnz', 3, 'bzjzjJ', 'bdjzjJzjz', 'jzjznzjjz', 0, 0, 0, '', '', 'bzbzjz', 0, 'hzbzh', '', 'bzjaja');

-- --------------------------------------------------------

--
-- Table structure for table `approved_documents`
--

CREATE TABLE IF NOT EXISTS `approved_documents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_id` int(11) NOT NULL,
  `approved_by` int(11) NOT NULL,
  `approved_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=21 ;

--
-- Dumping data for table `approved_documents`
--

INSERT INTO `approved_documents` (`id`, `doc_id`, `approved_by`, `approved_date`) VALUES
(17, 72, 62, '2020-02-08'),
(18, 73, 62, '2020-02-08'),
(19, 63, 71, '2020-02-20'),
(20, 73, 71, '2020-02-20');

-- --------------------------------------------------------

--
-- Table structure for table `cancelled_documents`
--

CREATE TABLE IF NOT EXISTS `cancelled_documents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_id` int(11) NOT NULL,
  `cancelled_reason` varchar(100) NOT NULL,
  `cancelled_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `closed_documents`
--

CREATE TABLE IF NOT EXISTS `closed_documents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_id` int(11) NOT NULL,
  `closed_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `closed_documents`
--

INSERT INTO `closed_documents` (`id`, `doc_id`, `closed_date`) VALUES
(1, 75, '2020-01-07'),
(9, 76, '2020-12-28'),
(10, 77, '2017-11-13'),
(11, 78, '2018-07-18'),
(12, 79, '2019-05-10');

-- --------------------------------------------------------

--
-- Table structure for table `committees`
--

CREATE TABLE IF NOT EXISTS `committees` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `committee_id` int(11) NOT NULL,
  `committee` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

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

CREATE TABLE IF NOT EXISTS `committee_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `committee_id` int(11) NOT NULL,
  `responsibilities` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

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

CREATE TABLE IF NOT EXISTS `documents` (
  `doc_id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_form_id` int(11) NOT NULL,
  `doc_type_id` int(11) NOT NULL,
  `doc_status_id` int(11) NOT NULL,
  `date_submitted` date NOT NULL,
  `date_modified` date NOT NULL,
  PRIMARY KEY (`doc_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=80 ;

--
-- Dumping data for table `documents`
--

INSERT INTO `documents` (`doc_id`, `doc_form_id`, `doc_type_id`, `doc_status_id`, `date_submitted`, `date_modified`) VALUES
(63, 16, 1, 2, '2020-02-08', '0000-00-00'),
(72, 25, 1, 3, '2020-02-08', '0000-00-00'),
(73, 3, 2, 2, '2020-02-08', '0000-00-00'),
(74, 4, 2, 3, '2020-02-08', '0000-00-00'),
(75, 1, 3, 6, '2020-02-08', '0000-00-00'),
(76, 2, 3, 6, '2020-02-08', '0000-00-00'),
(77, 3, 3, 6, '2020-02-08', '0000-00-00'),
(78, 4, 3, 6, '2020-02-08', '0000-00-00'),
(79, 5, 3, 6, '2020-02-08', '0000-00-00');

-- --------------------------------------------------------

--
-- Table structure for table `document_status`
--

CREATE TABLE IF NOT EXISTS `document_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_status_id` int(11) NOT NULL,
  `doc_status_desc` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

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

CREATE TABLE IF NOT EXISTS `document_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_type_id` int(11) NOT NULL,
  `doc_type_desc` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

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

CREATE TABLE IF NOT EXISTS `donations` (
  `donation_id` int(11) NOT NULL AUTO_INCREMENT,
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
  `is_verified` tinyint(1) NOT NULL,
  PRIMARY KEY (`donation_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `donations`
--

INSERT INTO `donations` (`donation_id`, `user_id`, `donation_type_id`, `account_name`, `account_number`, `amount`, `date`, `image`, `date_modified`, `date_verified`, `verified_by`, `is_verified`) VALUES
(5, 2, 7, '', '', '5000.00', '2020-02-22', 'IMG20200207200322.jpg', '0000-00-00', '0000-00-00', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `donation_type`
--

CREATE TABLE IF NOT EXISTS `donation_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `donation_type_id` int(11) NOT NULL,
  `donation_type_desc` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

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

CREATE TABLE IF NOT EXISTS `events` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
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
  `is_active` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=44 ;

--
-- Dumping data for table `events`
--

INSERT INTO `events` (`id`, `user_id`, `image`, `title`, `desc`, `venue`, `color`, `start`, `end`, `time_start`, `time_end`, `date_posted`, `is_active`) VALUES
(42, 71, 'db design.png', 'CAPSTONE 42 ORAL DEFENSE', 'this is sample description', '208 - Workspace', '#FF0000', '2020-02-20 00:00:00', '2020-02-20 00:00:00', '11:00 AM', '12:00 PM', 'February 20, 2020, 9:16 pm', 1),
(43, 72, 't-d1_paces_for_paws,_me_-_may_26_154521_mobi.jpg', 'SAMPLE TITLE', 'SAMPLE', 'SM Seaside', '#0071c5', '2020-02-20 00:00:00', '2020-02-20 00:00:00', '11:00 AM', '04:00 PM', 'February 20, 2020, 9:20 pm', 1);

-- --------------------------------------------------------

--
-- Table structure for table `file_attachments`
--

CREATE TABLE IF NOT EXISTS `file_attachments` (
  `file_attachment_id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_id` int(11) NOT NULL,
  `file_name` varchar(50) NOT NULL,
  `file_path` varchar(100) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` date NOT NULL,
  `updated_by` int(11) NOT NULL,
  `updated_date` date NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`file_attachment_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=59 ;

--
-- Dumping data for table `file_attachments`
--

INSERT INTO `file_attachments` (`file_attachment_id`, `doc_id`, `file_name`, `file_path`, `created_by`, `created_date`, `updated_by`, `updated_date`, `is_active`) VALUES
(50, 63, '20200208_131847.jpg', 'images/attachments/20200208_131847.jpg', 61, '2020-02-08', 0, '0000-00-00', 1),
(51, 69, 'IMG20200207200322.jpg', 'images/attachments/IMG20200207200322.jpg', 70, '2020-02-08', 0, '0000-00-00', 1),
(52, 72, 'IMG20200206115737.jpg', 'images/attachments/IMG20200206115737.jpg', 70, '2020-02-08', 0, '0000-00-00', 1),
(53, 70, '20200208_203158.jpg', 'images/attachments/20200208_203158.jpg', 61, '2020-02-08', 0, '0000-00-00', 1),
(54, 71, 'IMG20200208000503.jpg', 'images/attachments/IMG20200208000503.jpg', 70, '2020-02-08', 0, '0000-00-00', 1),
(55, 75, 'IMG20200208160656.jpg', 'images/attachments/IMG20200208160656.jpg', 70, '2020-02-08', 0, '0000-00-00', 1),
(56, 76, '20200208_203224.jpg', 'images/attachments/20200208_203224.jpg', 61, '2020-02-08', 0, '0000-00-00', 1),
(57, 80, 'IMG20200206114800.jpg', 'images/attachments/IMG20200206114800.jpg', 70, '2020-02-08', 0, '0000-00-00', 1),
(58, 78, '20200208_203222.jpg', 'images/attachments/20200208_203222.jpg', 61, '2020-02-08', 0, '0000-00-00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `guidelines`
--

CREATE TABLE IF NOT EXISTS `guidelines` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `guideline_id` int(11) NOT NULL,
  `guideline` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

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

CREATE TABLE IF NOT EXISTS `guideline_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `guideline_id` int(11) NOT NULL,
  `details` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

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

CREATE TABLE IF NOT EXISTS `items` (
  `item_id` int(11) NOT NULL AUTO_INCREMENT,
  `item_category_id` int(11) NOT NULL,
  `item_image` varchar(100) NOT NULL,
  `item_name` varchar(50) NOT NULL,
  `item_desc` varchar(50) NOT NULL,
  `item_qty` int(11) NOT NULL,
  `item_price` double(11,2) NOT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

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

CREATE TABLE IF NOT EXISTS `item_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_category_id` int(11) NOT NULL,
  `item_category_desc` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

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

CREATE TABLE IF NOT EXISTS `notifications` (
  `notification_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `notification_type_id` int(11) NOT NULL,
  `subject` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `date` varchar(50) NOT NULL,
  `is_seen` tinyint(1) NOT NULL DEFAULT '0',
  `is_seen2` tinyint(1) NOT NULL,
  PRIMARY KEY (`notification_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=200 ;

--
-- Dumping data for table `notifications`
--

INSERT INTO `notifications` (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES
(198, 2, 7, 'Donation', 'Sorry your donation has been rejected due to incomplete details. Please resubmit complete donation details. Thank you!', 'February 21, 2020, 5:45 pm', 0, 0),
(199, 2, 7, 'Donation', 'Sorry your donation has been rejected due to incomplete details. Please resubmit complete donation details. Thank you!', 'February 21, 2020, 8:07 pm', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `notifications_admin`
--

CREATE TABLE IF NOT EXISTS `notifications_admin` (
  `notification_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `notification_type_id` int(11) NOT NULL,
  `subject` varchar(50) NOT NULL,
  `content` varchar(100) NOT NULL,
  `date` varchar(50) NOT NULL,
  `is_seen` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`notification_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `notification_type`
--

CREATE TABLE IF NOT EXISTS `notification_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `notification_type_id` int(11) NOT NULL,
  `notification_type_desc` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

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

CREATE TABLE IF NOT EXISTS `pets` (
  `pet_id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_image` varchar(100) NOT NULL,
  `pet_name` varchar(50) NOT NULL,
  `pet_gender` varchar(50) NOT NULL,
  `pet_age` varchar(50) NOT NULL,
  `acquired_from` varchar(30) NOT NULL,
  `pet_desc` varchar(255) NOT NULL,
  `pet_type_id` int(11) NOT NULL,
  `pet_status_id` int(11) NOT NULL,
  `date_added` date NOT NULL,
  PRIMARY KEY (`pet_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `pets`
--

INSERT INTO `pets` (`pet_id`, `pet_image`, `pet_name`, `pet_gender`, `pet_age`, `acquired_from`, `pet_desc`, `pet_type_id`, `pet_status_id`, `date_added`) VALUES
(1, 'bob.jpg', 'Bob', 'Male', '2 years old', 'Lost and Found', 'He is playful and active', 3, 1, '2019-10-01'),
(2, 'michael.jpg', 'Michael', 'Male', '5 years old', 'Animal Cruelty', 'He is playful and active', 2, 1, '2019-11-03'),
(3, 'diablo.jpg', 'Diablo', 'Male', '4 years old', 'Lost and Found', 'He is hyperactive and eats everything he see', 4, 2, '2019-10-09'),
(4, 'princess.jpg', 'Princess', 'Female', '1 year old', 'Homeless Pet', 'She is cute and adorable', 5, 1, '2019-09-18'),
(5, 'shoeshine.jpg', 'Shoeshine', 'Male', '3 months', 'Homeless Pet', 'He is playful and active', 6, 1, '2019-08-03'),
(6, 'chow chow.jpg', 'Chiwie', 'Male', '2 months', '', 'asdasdasfas', 1, 1, '2019-08-10'),
(7, 'shihtzu.jpg', 'Britney', 'Female', '1 year and 3months', 'Animal Cruelty', 'She eats a lot of food', 4, 1, '2019-11-13'),
(10, 'red-nose-pitbull-1-e1520600821708.jpg', 'Boozer', 'Male', '3 years old', 'Homeless Pet', 'He eats a lot of food.', 4, 1, '2019-12-04'),
(11, 't-d1_paces_for_paws,_me_-_may_26_154521_mobi.jpg', 'sadasd', 'Male', '1', 'Homeless Pet', 'asdsdasd', 1, 1, '2020-02-20'),
(12, 'db design.png', 'Chowie', 'Male', '1 Year Old', 'Animal Cruelty', 'this pet eat a lot of dog food', 2, 1, '2020-02-20'),
(13, 'ERD.png', 'asdasdasd', 'Male', '1', 'Lost and Found', 'asfasdasd', 2, 1, '2020-02-21');

-- --------------------------------------------------------

--
-- Table structure for table `pet_live_type`
--

CREATE TABLE IF NOT EXISTS `pet_live_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_live_type_id` int(11) NOT NULL,
  `pet_live_type_desc` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

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

CREATE TABLE IF NOT EXISTS `pet_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_status_id` int(11) NOT NULL,
  `pet_status_desc` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

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

CREATE TABLE IF NOT EXISTS `pet_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_type_id` int(11) NOT NULL,
  `pet_type_desc` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

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

CREATE TABLE IF NOT EXISTS `places` (
  `place_id` int(11) NOT NULL AUTO_INCREMENT,
  `place_image` varchar(100) NOT NULL,
  `place_name` varchar(100) NOT NULL,
  `place_vicinity` varchar(200) NOT NULL,
  `place_contact` varchar(15) NOT NULL,
  `place_lat` double NOT NULL,
  `place_lng` double NOT NULL,
  PRIMARY KEY (`place_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=23 ;

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
(13, 'image_none.png', 'Georgia''s Puppies', 'Block 20 Lot 6 Joanna Legacy Homes, Sudtonggan Road, Lapu-Lapu City', 'No Contact', 10.2795776, 123.9607631),
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

CREATE TABLE IF NOT EXISTS `rejected_documents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_id` int(11) NOT NULL,
  `rejected_reason` varchar(50) NOT NULL,
  `rejected_by` int(11) NOT NULL,
  `rejected_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `rejected_documents`
--

INSERT INTO `rejected_documents` (`id`, `doc_id`, `rejected_reason`, `rejected_by`, `rejected_date`) VALUES
(1, 72, 'You''re a irresponsible owner.', 71, '2020-02-20'),
(2, 74, 'dili pwede kay under age paka', 71, '2020-02-20');

-- --------------------------------------------------------

--
-- Table structure for table `reports`
--

CREATE TABLE IF NOT EXISTS `reports` (
  `report_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `report_type_id` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` text NOT NULL,
  `reporter_location` varchar(200) NOT NULL,
  `date_reported` date NOT NULL,
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `reports`
--

INSERT INTO `reports` (`report_id`, `user_id`, `report_type_id`, `title`, `description`, `reporter_location`, `date_reported`) VALUES
(1, 2, 2, 'sdasd', 'asfasd', 'asasdasd', '2020-01-03'),
(2, 2, 3, 'sdasd', 'asfasd', 'asasdasd', '2020-12-25'),
(3, 2, 3, 'sdasd', 'asfasd', 'asasdasd', '2017-11-02'),
(4, 2, 4, 'sdasd', 'asfasd', 'asasdasd', '2018-07-13'),
(5, 2, 4, 'sdasd', 'asfasd', 'asasdasd', '2019-05-03');

-- --------------------------------------------------------

--
-- Table structure for table `report_type`
--

CREATE TABLE IF NOT EXISTS `report_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `report_type_id` int(11) NOT NULL,
  `report_type_desc` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

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

CREATE TABLE IF NOT EXISTS `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `role_desc` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

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

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
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
  `vkey` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=75 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `user_image`, `user_fname`, `user_lname`, `user_nname`, `user_address`, `user_email`, `user_contact`, `user_occupation`, `user_username`, `user_password`, `vkey`) VALUES
(1, 'user_none.png', 'Super', 'Admin', 'Developer', 'UCLM', 'system.developer@gmail.com', '09193157707', 'Developer', 'superadmin', 'superadmin', ''),
(2, '61.jpg', 'Kevin Dale', 'Tabayocyoc', 'KD', 'Basak LLC', 'kevin_sdlc@yahoo.com', '09363088069', 'College Student', 'kevin_sdlc', 'Aptx2019', '281e838e37108ff7a027d3fd0efbeb7a'),
(3, '70.jpg', 'Zardron', 'Pesquera', 'Zar', 'Bankal LLC', 'zpesquera@lear.com', '09166361646', 'Web Developer', 'qweqwe', 'qwe123', '370cc0903d14359182eed64a6b1f6971'),
(71, 't-d1_paces_for_paws,_me_-_may_26_154521_mobi.jpg', 'Zardron Angelo', 'Pesquera', ' Zar', 'Happy Homes Mactan', 'a.pesquera1968@gmail.com', '09166361646', 'Web Developer', 'zpesquera', 'qweqweqwe', ''),
(72, 'db design.png', 'Regine', 'Morales', 'Regine', 'Mandaue City', 'reginmeorales.rcm@yahoo.com', '09291226446', 'Project Manager', 'cityvet', 'Password123', ''),
(74, 'db design.png', 'Renz', 'Mandin', 'Renz', 'Cebu City', 'renz.mandin@gmail.com', '09121226446', 'qwe', 'renz123', 'Password123', '');

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

CREATE TABLE IF NOT EXISTS `user_roles` (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `created_date` date NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `verified` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=75 ;

--
-- Dumping data for table `user_roles`
--

INSERT INTO `user_roles` (`user_role_id`, `user_id`, `role_id`, `created_date`, `is_active`, `verified`) VALUES
(58, 2, 3, '2019-11-27', 1, 1),
(67, 3, 1, '2020-02-08', 1, 1),
(70, 1, 4, '2020-02-08', 1, 1),
(71, 71, 2, '2020-02-20', 1, 0),
(72, 72, 5, '2020-02-20', 1, 0),
(74, 74, 2, '2020-02-20', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `volunteer`
--

CREATE TABLE IF NOT EXISTS `volunteer` (
  `volunteer_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `is_adoption` tinyint(1) NOT NULL,
  `is_transportation` tinyint(1) NOT NULL,
  `is_shelter_clean_up` tinyint(1) NOT NULL,
  `is_educational_campaign` tinyint(1) NOT NULL,
  `is_animal_welfare` tinyint(1) NOT NULL,
  `is_fostering` tinyint(1) NOT NULL,
  `is_food_donation_drive` tinyint(1) NOT NULL,
  `is_events` tinyint(1) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  PRIMARY KEY (`volunteer_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `volunteer`
--

INSERT INTO `volunteer` (`volunteer_id`, `user_id`, `is_adoption`, `is_transportation`, `is_shelter_clean_up`, `is_educational_campaign`, `is_animal_welfare`, `is_fostering`, `is_food_donation_drive`, `is_events`, `is_active`) VALUES
(3, 2, 0, 0, 0, 1, 0, 0, 0, 0, 1),
(4, 3, 1, 0, 0, 1, 0, 0, 0, 0, 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
