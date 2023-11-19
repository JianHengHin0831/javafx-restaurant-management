-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:8080
-- Generation Time: May 13, 2023 at 05:12 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `restaurant`
--

-- --------------------------------------------------------

--
-- Table structure for table `food`
--

CREATE TABLE `food` (
  `name` varchar(50) NOT NULL,
  `price` double NOT NULL,
  `stock` int(5) NOT NULL,
  `type` varchar(11) NOT NULL,
  `photo` varchar(500) DEFAULT NULL,
  `sales` int(10) DEFAULT 0,
  `promotion` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `food`
--

INSERT INTO `food` (`name`, `price`, `stock`, `type`, `photo`, `sales`, `promotion`) VALUES
('Americano', 7.99, 1200, 'drinks', 'https://www.mcdonalds.com.my/storage/foods/September2020/MKvP5yzcPwunHAFAdbE5.png', 10, 1),
('Beef Burger', 12.99, 21, 'burger', 'https://www.mcdonalds.com.my/storage/foods/November2022/uznyAntO4RAoWIm99L2s.png', 12, 1),
('Big Breakfast', 14.99, 99999, 'set', 'https://www.mcdonalds.com.my/storage/foods/September2019/rpMB7R0AYVZXje7IuHwH.png', 1, 0),
('Big Mac', 12.59, 2222, 'burger', 'https://th.bing.com/th/id/OIP.mfCbYDpGAnByuVFzraAXzwHaFj?pid=ImgDet&rs=1', 24, 0),
('Bubur Ayam', 7.99, 66, 'others', 'https://www.mcdonalds.com.my/storage/foods/September2019/gqPL7937Jn9f5rGk7xlk.png', 0, 0),
('Cheese Burger', 5.99, 113, 'burger', 'https://www.mcdonalds.com.my/storage/foods/November2022/OeAh4oq3mYAqxO2ppRW4.png', 7, 0),
('Chocolate Sundae', 12.99, 37, 'dessert', 'https://www.mcdonalds.com.my/storage/foods/September2019/yOthCliSefiqycpSn1z0.png', 7, 0),
('Corn Cup', 6.99, 7648, 'dessert', 'https://www.mcdonalds.com.my/storage/foods/September2019/y2JKRbgg7QhOfrEN04Q5.png', 2, 1),
('Double CheeseBurger', 7.8, 129, 'burger', 'https://www.mcdonalds.com.my/storage/foods/November2022/6ju3bTszopkme6HZPZPj.png', 1, 1),
('Drinking Water', 1.99, 3000, 'drinks', 'https://www.mcdonalds.com.my/storage/foods/November2020/TtT9pz7WYUSkbzhuZOMi.jpg', 0, 0),
('Filet O Fish', 5.99, 2222, 'burger', 'https://www.mcdonalds.com.my/storage/foods/November2022/Ll1grnTu89MUhWcvQkYo.png', 0, 0),
('Fish Burger', 22.1, 14, 'burger', 'https://www.mcdonalds.com.my/storage/foods/November2020/dmJneyNJjgq0Vis1LkkG.jpg', 8, 0),
('Fried Chicken(4 pcs)', 12.99, 1199, 'fried', 'https://www.mcdonalds.com.my/storage/foods/September2019/zjxreNuMeXjcMMAjawLt.png', 1, 0),
('Fried Chicken(6 pcs)', 18.99, 1221, 'fried', 'https://www.mcdonalds.com.my/storage/foods/September2019/zjxreNuMeXjcMMAjawLt.png', 1, 0),
('GCB', 12.9, 320, 'burger', 'https://www.mcdonalds.com.my/storage/foods/November2022/i0utyZN7KfmhGiBCLnMv.png', 17, 1),
('Golden Burger', 23, 71, 'burger', 'https://freepngimg.com/download/burger/4-2-burger-png-file.png', 1, 0),
('Happy Meal', 16.99, 1233, 'set', 'https://www.mcdonalds.com.my/storage/foods/September2019/Gf2hJUvtPbURiEF1L5js.png', 0, 0),
('Hash Browns', 3.99, 998, 'fried', 'https://www.mcdonalds.com.my/storage/foods/September2019/tngiz41k4RPjtpn4EOr3.png', 1, 0),
('Hot Milo', 4.99, 76, 'drinks', 'https://www.mcdonalds.com.my/storage/foods/September2019/mWWxETHu3exkYVDgHZ2m.png', 1, 0),
('McCafe', 4.99, 777, 'drinks', 'https://www.mcdonalds.com.my/storage/foods/September2019/PnMzRm0tpNTwu20w7bTq.png', 0, 0),
('McChicken', 13.99, 439, 'burger', 'https://www.mcdonalds.com.my/storage/foods/September2019/eE1uNG6oOqBlJNH2KwMh.png', 5, 0),
('McMuffin', 7.99, 1244, 'set', 'https://www.mcdonalds.com.my/storage/foods/September2019/seEGn339qD60BcDdooO0.png', 0, 0),
('McMuffin with Egg', 7.99, 1232, 'set', 'https://www.mcdonalds.com.my/storage/foods/September2019/YbtOoIWIrp2EdDgI0tr7.png', 1, 0),
('McNuggets', 6.99, 5555, 'fried', 'https://www.mcdonalds.com.my/storage/foods/September2019/QEKk5m0lDuSdcl4mjyJP.png', 0, 0),
('McWrap', 9.99, 88, 'others', 'https://vignette.wikia.nocookie.net/ronaldmcdonald/images/3/37/Snack_Wrap.png/revision/latest?cb=20200330071148', 0, 0),
('Nasi Lemak', 8.99, 77, 'others', 'https://www.mcdonalds.com.my/storage/foods/November2022/HrqimcCyjYwAMytiCMAh.jpg', 0, 0),
('Orange Juice', 3.99, 120, 'drinks', 'https://www.mcdonalds.com.my/storage/foods/September2019/pgSG3hmursx8x6Jr2vCO.png', 0, 0),
('Quater Pounder with Cheese', 23.99, 3, 'burger', 'https://www.mcdonalds.com.my/storage/foods/November2022/kmhyeEknhPnf7q8Jo9yQ.png', 39, 0),
('Spicy McChicken', 9.99, 5555, 'burger', 'https://www.mcdonalds.com.my/storage/foods/September2019/lojYE6LgUCgHSTdBHxC1.png', 0, 0),
('Strawberry Sundae', 12.99, 71, 'dessert', 'https://www.mcdonalds.com.my/storage/foods/September2019/yOthCliSefiqycpSn1z0.png', 1, 0),
('Sundae Cone', 2.99, 555, 'dessert', 'https://www.mcdonalds.com.my/storage/foods/September2019/r4ONq9M1b8VGDY1YR2wT.png', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `order_id` int(10) NOT NULL,
  `user_name` varchar(30) NOT NULL,
  `datetime` varchar(30) DEFAULT NULL,
  `isCompleted` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`order_id`, `user_name`, `datetime`, `isCompleted`) VALUES
(1, 'client', '20200428_000603', 1),
(2, 'client', '20210428_000603', 1),
(3, 'client', '20220428_000603', 1),
(4, 'client', '20230128_000603', 1),
(5, 'client', '20230129_000603', 1),
(6, 'client', '20230130_000603', 1),
(7, 'client', '20230131_000603', 1),
(8, 'client', '20230228_000603', 1),
(9, 'client', '20230328_000603', 1),
(10, 'client', '20230428_000603', 1),
(11, 'client', '20230218_000603', 1),
(12, 'client', '20230208_000603', 1),
(13, 'client', '20230308_000603', 0),
(14, 'client', '20230108_000603', 0),
(15, 'client', '20230118_000603', 1),
(16, 'client', '20230508_122417', 1),
(17, 'client', '20230508_122925', 1),
(18, 'client', '20230509_085113', 0),
(19, 'client', '20230513_014037', 0),
(20, 'client', '20230513_020405', 0),
(21, 'client', '20230513_195842', 0),
(22, 'client', '20230513_200126', 0),
(23, 'client', '20230513_201223', 0),
(24, 'client', '20230513_205211', 0),
(25, 'client', '20230513_213847', 0),
(26, 'client', '20230513_213956', 0),
(27, 'client2333', '20230513_214831', 0);

-- --------------------------------------------------------

--
-- Table structure for table `order_food`
--

CREATE TABLE `order_food` (
  `order_id` int(10) NOT NULL,
  `food_name` varchar(30) NOT NULL,
  `quantity` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_food`
--

INSERT INTO `order_food` (`order_id`, `food_name`, `quantity`) VALUES
(1, 'Americano', 3),
(1, 'Beef Burger', 4),
(1, 'Big Mac', 7),
(1, 'Bubur Ayam', 8),
(1, 'Filet O Fish', 12),
(2, 'Chocolate Sundae', 3),
(2, 'Cheese Burger', 6),
(2, 'Drinking Water', 9),
(2, 'Big Breakfast', 15),
(2, 'Fried Chicken(4 pcs)', 12),
(3, 'GCB', 22),
(3, 'McCafe', 3),
(3, 'Hot Milo', 77),
(3, 'McChicken', 7),
(3, 'McMuffin with Egg', 9),
(3, 'Nasi Lemak', 12),
(4, 'Spicy McChicken', 44),
(4, 'Fish Burger', 66),
(4, 'Beef Burger', 22),
(4, 'Americano', 78),
(3, 'Big Breakfast', 3),
(5, 'Big Mac', 2),
(4, 'Bubur Ayam', 55),
(4, 'Cheese Burger', 92),
(4, 'Chocolate Sundae', 123),
(4, 'Corn Cup', 23),
(5, 'Double CheeseBurger', 1),
(5, 'Drinking Water', 25),
(5, 'Filet O Fish', 23),
(5, 'Fish Burger', 43),
(5, 'Fried Chicken(4 pcs)', 22),
(5, 'Fried Chicken(6 pcs)', 33),
(6, 'GCB', 2),
(6, 'Happy Meal', 44),
(6, 'Hash Browns', 56),
(6, 'Hot Milo', 33),
(6, 'McCafe', 45),
(7, 'McChicken', 45),
(7, 'McMuffin', 67),
(7, 'McMuffin with Egg', 34),
(7, 'McNuggets', 52),
(7, 'McWrap', 14),
(8, 'Nasi Lemak', 23),
(7, 'Orange Juice', 35),
(8, 'Quater Pounder with Cheese', 46),
(8, 'Spicy McChicken', 15),
(8, 'Strawberry Sundae', 35),
(9, 'Sundae Cone', 245),
(9, 'Beef Burger', 12),
(9, 'Big Breakfast', 44),
(9, 'Sundae Cone', 22),
(9, 'Strawberry Sundae', 12),
(10, 'Spicy McChicken', 12),
(10, 'Quater Pounder with Cheese', 23),
(10, 'Orange Juice', 34),
(10, 'Orange Juice', 34),
(10, 'McWrap', 24),
(10, 'McNuggets', 23),
(11, 'McMuffin with Egg', 34),
(15, 'McMuffin', 56),
(11, 'McChicken', 75),
(11, 'McCafe', 56),
(11, 'Hot Milo', 23),
(12, 'Hash Browns', 24),
(15, 'GCB', 56),
(12, 'Fried Chicken(6 pcs)', 2),
(16, 'Cheese Burger', 6),
(16, 'Beef Burger', 2),
(16, 'GCB', 5),
(16, 'Americano', 3),
(16, 'Big Mac', 1),
(17, 'Big Mac', 1),
(17, 'Americano', 3),
(17, 'Chocolate Sundae', 2),
(18, 'Chocolate Sundae', 3),
(18, 'Americano', 3),
(18, 'GCB', 3),
(19, 'Chocolate Sundae', 2),
(19, 'Fried Chicken(6 pcs)', 1),
(19, 'Big Breakfast', 1),
(19, 'Hot Milo', 1),
(20, 'Beef Burger', 1),
(20, 'Americano', 1),
(20, 'McChicken', 2),
(20, 'Double CheeseBurger', 1),
(21, 'GCB', 3),
(21, 'Quater Pounder with Cheese', 1),
(23, 'Big Mac', 1),
(23, 'Strawberry Sundae', 1),
(23, 'GCB', 3),
(24, 'Corn Cup', 2),
(24, 'Golden Burger', 1),
(24, 'McMuffin with Egg', 1),
(24, 'GCB', 2),
(24, 'Fried Chicken(4 pcs)', 1),
(24, 'Hash Browns', 1),
(25, 'McChicken', 3),
(25, 'GCB', 1),
(25, 'Cheese Burger', 1);

-- --------------------------------------------------------

--
-- Table structure for table `support`
--

CREATE TABLE `support` (
  `id` int(11) NOT NULL,
  `username` varchar(30) DEFAULT NULL,
  `email` varchar(70) DEFAULT NULL,
  `message` varchar(500) DEFAULT NULL,
  `isDone` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `support`
--

INSERT INTO `support` (`id`, `username`, `email`, `message`, `isDone`) VALUES
(1, 'JohnDoe', 'johndoe@example.com', 'This is a sample message.', 1),
(2, 'jian heng', 'jianheng1234@abc.com', 'hello', 1),
(4, 'jian heng', 'abc@example.com', 'hello', 0),
(5, 'bcd', 'bcd@exd.com', 'thank you', 0),
(6, 'brandon', 'brd@mcd.com', 'rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr', 0),
(7, 'Hin Jian Heng', 'jhh1e22@soton.ac.uk', ' cannot login', 0),
(10, 'jian heng', 'jianheng123@hotmail.com', 'hellllo', 0),
(12, 'ming', 'ali@gmail.com', 'hellop', 0),
(13, 'hello', 'helo123@gmail.com', 'xxxx', 0),
(14, 'jian heng', 'jianheng@bcd.vom', 'why you never email me', 0);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` varchar(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`name`, `password`, `role`) VALUES
('abu', '23333', 'a'),
('admin', '12345', 'a'),
('admin123', '123', 'a'),
('ali', '345345', 'a'),
('brandy', '123', 's'),
('client', '34567', 'c'),
('client1', 'halooo', 'c'),
('client2', 'cd', 's'),
('client2333', '2333', 'c'),
('comm', 'comm', 'a'),
('finds', 'ddd', 'a'),
('hi', '123', 'c'),
('hong', '098', 's'),
('jian heng', '23456', 's'),
('lalala', '789', 'c'),
('ming yang', '12345', 'a'),
('newton', '678678', 's'),
('qqqq', 'qqq', 's'),
('rrrr', 'rrrr', 's'),
('staff', '45678', 's'),
('tan tan', '123123', 'a'),
('tee tee', '123123', 's'),
('ting ting', '789789', 'a'),
('wei wei', '789789', 's'),
('wendy', '345', 's');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `food`
--
ALTER TABLE `food`
  ADD PRIMARY KEY (`name`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `user_name` (`user_name`);

--
-- Indexes for table `order_food`
--
ALTER TABLE `order_food`
  ADD KEY `order_id` (`order_id`),
  ADD KEY `food_name` (`food_name`);

--
-- Indexes for table `support`
--
ALTER TABLE `support`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`name`,`password`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `support`
--
ALTER TABLE `support`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `order_ibfk_1` FOREIGN KEY (`user_name`) REFERENCES `user` (`name`);

--
-- Constraints for table `order_food`
--
ALTER TABLE `order_food`
  ADD CONSTRAINT `order_food_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`),
  ADD CONSTRAINT `order_food_ibfk_2` FOREIGN KEY (`food_name`) REFERENCES `food` (`name`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
