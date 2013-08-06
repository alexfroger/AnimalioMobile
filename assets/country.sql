-- phpMyAdmin SQL Dump
-- version 2.11.11.1
-- http://www.phpmyadmin.net
--
-- Serveur: 10.0.225.96
-- Généré le : Lun 05 Août 2013 à 18:10
-- Version du serveur: 5.1.31
-- Version de PHP: 5.2.9

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `animalio_mobile`
--

-- --------------------------------------------------------

--
-- Structure de la table `country`
--

CREATE TABLE IF NOT EXISTS `country` (
  `id_country` int(11) NOT NULL AUTO_INCREMENT,
  `country_name` varchar(255) DEFAULT NULL,
  `country_name_uppercase` varchar(255) NOT NULL,
  `country_slug` varchar(255) DEFAULT NULL,
  `code` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id_country`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=238 ;

--
-- Contenu de la table `country`
--

INSERT INTO `country` (`id_country`, `country_name`, `country_name_uppercase`, `country_slug`, `code`) VALUES
(1, 'France', 'FRANCE', 'france', 'fr'),
(2, 'Afghanistan', 'AFGHANISTAN', 'afghanistan', 'af'),
(3, 'Afrique du sud', 'AFRIQUE DU SUD', 'afrique-du-sud', 'za'),
(4, 'Albanie', 'ALBANIE', 'albanie', 'al'),
(5, 'Algérie', 'ALGÉRIE', 'algerie', 'dz'),
(6, 'Allemagne', 'ALLEMAGNE', 'allemagne', 'de'),
(7, 'Arabie saoudite', 'ARABIE SAOUDITE', 'arabie-saoudite', 'sa'),
(8, 'Argentine', 'ARGENTINE', 'argentine', 'ar'),
(9, 'Australie', 'AUSTRALIE', 'australie', 'au'),
(10, 'Autriche', 'AUTRICHE', 'autriche', 'at'),
(11, 'Belgique', 'BELGIQUE', 'belgique', 'be'),
(12, 'Brésil', 'BRÉSIL', 'bresil', 'br'),
(13, 'Bulgarie', 'BULGARIE', 'bulgarie', 'bg'),
(14, 'Canada', 'CANADA', 'canada', 'ca'),
(15, 'Chili', 'CHILI', 'chili', 'cl'),
(16, 'Chine (Rép. pop.)', 'CHINE (RÉP. POP.)', 'chine-rep-pop', 'cn'),
(17, 'Colombie', 'COLOMBIE', 'colombie', 'co'),
(18, 'Corée, Sud', 'CORÉE, SUD', 'coree-sud', 'kr'),
(19, 'Costa Rica', 'COSTA RICA', 'costa-rica', 'cr'),
(20, 'Croatie', 'CROATIE', 'croatie', 'hr'),
(21, 'Danemark', 'DANEMARK', 'danemark', 'dk'),
(22, 'Égypte', 'ÉGYPTE', 'egypte', 'eg'),
(23, 'Émirats arabes unis', 'ÉMIRATS ARABES UNIS', 'emirats-arabes-unis', 'ae'),
(24, 'Équateur', 'ÉQUATEUR', 'equateur', 'ec'),
(25, 'États-Unis', 'ÉTATS-UNIS', 'etats-unis', 'us'),
(26, 'El Salvador', 'EL SALVADOR', 'el-salvador', 'sv'),
(27, 'Espagne', 'ESPAGNE', 'espagne', 'es'),
(28, 'Finlande', 'FINLANDE', 'finlande', 'fi'),
(29, 'Grèce', 'GRÈCE', 'grece', 'gr'),
(30, 'Hong Kong', 'HONG KONG', 'hong-kong', 'hk'),
(31, 'Hongrie', 'HONGRIE', 'hongrie', 'hu'),
(32, 'Inde', 'INDE', 'inde', 'in'),
(33, 'Indonésie', 'INDONÉSIE', 'indonesie', 'id'),
(34, 'Irlande', 'IRLANDE', 'irlande', 'ie'),
(35, 'Israël', 'ISRAËL', 'israel', 'il'),
(36, 'Italie', 'ITALIE', 'italie', 'it'),
(37, 'Japon', 'JAPON', 'japon', 'jp'),
(38, 'Jordanie', 'JORDANIE', 'jordanie', 'jo'),
(39, 'Liban', 'LIBAN', 'liban', 'lb'),
(40, 'Malaisie', 'MALAISIE', 'malaisie', 'my'),
(41, 'Maroc', 'MAROC', 'maroc', 'ma'),
(42, 'Mexique', 'MEXIQUE', 'mexique', 'mx'),
(43, 'Norvège', 'NORVÈGE', 'norvege', 'no'),
(44, 'Nouvelle-Zélande', 'NOUVELLE-ZÉLANDE', 'nouvelle-zelande', 'nz'),
(45, 'Pérou', 'PÉROU', 'perou', 'pe'),
(46, 'Pakistan', 'PAKISTAN', 'pakistan', 'pk'),
(47, 'Pays-Bas', 'PAYS-BAS', 'pays-bas', 'nl'),
(48, 'Philippines', 'PHILIPPINES', 'philippines', 'ph'),
(49, 'Pologne', 'POLOGNE', 'pologne', 'pl'),
(50, 'Porto Rico', 'PORTO RICO', 'porto-rico', 'pr'),
(51, 'Portugal', 'PORTUGAL', 'portugal', 'pt'),
(52, 'République tchèque', 'RÉPUBLIQUE TCHÈQUE', 'republique-tcheque', 'cz'),
(53, 'Roumanie', 'ROUMANIE', 'roumanie', 'ro'),
(54, 'Royaume-Uni', 'ROYAUME-UNI', 'royaume-uni', 'uk'),
(55, 'Russie', 'RUSSIE', 'russie', 'ru'),
(56, 'Singapour', 'SINGAPOUR', 'singapour', 'sg'),
(57, 'Suède', 'SUÈDE', 'suede', 'se'),
(58, 'Suisse', 'SUISSE', 'suisse', 'ch'),
(59, 'Taiwan', 'TAIWAN', 'taiwan', 'tw'),
(60, 'Thailande', 'THAILANDE', 'thailande', 'th'),
(61, 'Turquie', 'TURQUIE', 'turquie', 'tr'),
(62, 'Ukraine', 'UKRAINE', 'ukraine', 'ua'),
(63, 'Venezuela', 'VENEZUELA', 'venezuela', 've'),
(64, 'Yougoslavie', 'YOUGOSLAVIE', 'yougoslavie', 'yu'),
(65, 'Samoa', 'SAMOA', 'samoa', 'as'),
(66, 'Andorre', 'ANDORRE', 'andorre', 'ad'),
(67, 'Angola', 'ANGOLA', 'angola', 'ao'),
(68, 'Anguilla', 'ANGUILLA', 'anguilla', 'ai'),
(69, 'Antarctique', 'ANTARCTIQUE', 'antarctique', 'aq'),
(70, 'Antigua et Barbuda', 'ANTIGUA ET BARBUDA', 'antigua-et-barbuda', 'ag'),
(71, 'Arménie', 'ARMÉNIE', 'armenie', 'am'),
(72, 'Aruba', 'ARUBA', 'aruba', 'aw'),
(73, 'Azerbaïdjan', 'AZERBAÏDJAN', 'azerbaidjan', 'az'),
(74, 'Bahamas', 'BAHAMAS', 'bahamas', 'bs'),
(75, 'Bahrain', 'BAHRAIN', 'bahrain', 'bh'),
(76, 'Bangladesh', 'BANGLADESH', 'bangladesh', 'bd'),
(77, 'Biélorussie', 'BIÉLORUSSIE', 'bielorussie', 'by'),
(78, 'Belize', 'BELIZE', 'belize', 'bz'),
(79, 'Benin', 'BENIN', 'benin', 'bj'),
(80, 'Bermudes (Les)', 'BERMUDES (LES)', 'bermudes-les', 'bm'),
(81, 'Bhoutan', 'BHOUTAN', 'bhoutan', 'bt'),
(82, 'Bolivie', 'BOLIVIE', 'bolivie', 'bo'),
(83, 'Bosnie-Herzégovine', 'BOSNIE-HERZÉGOVINE', 'bosnie-herzegovine', 'ba'),
(84, 'Botswana', 'BOTSWANA', 'botswana', 'bw'),
(85, 'Bouvet (Îles)', 'BOUVET (ÎLES)', 'bouvet-iles', 'bv'),
(86, 'Territoire britannique de l''océan Indien', 'TERRITOIRE BRITANNIQUE DE L''OCÉAN INDIEN', 'territoire-britannique-de-locean-indien', 'io'),
(87, 'Vierges britanniques (Îles)', 'VIERGES BRITANNIQUES (ÎLES)', 'vierges-britanniques-iles', 'vg'),
(88, 'Brunei', 'BRUNEI', 'brunei', 'bn'),
(89, 'Burkina Faso', 'BURKINA FASO', 'burkina-faso', 'bf'),
(90, 'Burundi', 'BURUNDI', 'burundi', 'bi'),
(91, 'Cambodge', 'CAMBODGE', 'cambodge', 'kh'),
(92, 'Cameroun', 'CAMEROUN', 'cameroun', 'cm'),
(93, 'Cap Vert', 'CAP VERT', 'cap-vert', 'cv'),
(94, 'Cayman (Îles)', 'CAYMAN (ÎLES)', 'cayman-iles', 'ky'),
(95, 'République centrafricaine', 'RÉPUBLIQUE CENTRAFRICAINE', 'republique-centrafricaine', 'cf'),
(96, 'Tchad', 'TCHAD', 'tchad', 'td'),
(97, 'Christmas (Île)', 'CHRISTMAS (ÎLE)', 'christmas-ile', 'cx'),
(98, 'Cocos (Îles)', 'COCOS (ÎLES)', 'cocos-iles', 'cc'),
(99, 'Comores', 'COMORES', 'comores', 'km'),
(100, 'Rép. Dém. du Congo', 'RÉP. DÉM. DU CONGO', 'rep-dem-du-congo', 'cg'),
(101, 'Cook (Îles)', 'COOK (ÎLES)', 'cook-iles', 'ck'),
(102, 'Cuba', 'CUBA', 'cuba', 'cu'),
(103, 'Chypre', 'CHYPRE', 'chypre', 'cy'),
(104, 'Djibouti', 'DJIBOUTI', 'djibouti', 'dj'),
(105, 'Dominique', 'DOMINIQUE', 'dominique', 'dm'),
(106, 'République Dominicaine', 'RÉPUBLIQUE DOMINICAINE', 'republique-dominicaine', 'do'),
(107, 'Timor', 'TIMOR', 'timor', 'tp'),
(108, 'Guinée Equatoriale', 'GUINÉE EQUATORIALE', 'guinee-equatoriale', 'gq'),
(109, 'Érythrée', 'ÉRYTHRÉE', 'erythree', 'er'),
(110, 'Estonie', 'ESTONIE', 'estonie', 'ee'),
(111, 'Ethiopie', 'ETHIOPIE', 'ethiopie', 'et'),
(112, 'Falkland (Île)', 'FALKLAND (ÎLE)', 'falkland-ile', 'fk'),
(113, 'Féroé (Îles)', 'FÉROÉ (ÎLES)', 'feroe-iles', 'fo'),
(114, 'Fidji (République des)', 'FIDJI (RÉPUBLIQUE DES)', 'fidji-republique-des', 'fj'),
(115, 'Guyane française', 'GUYANE FRANÇAISE', 'guyane-francaise', 'gf'),
(116, 'Polynésie française', 'POLYNÉSIE FRANÇAISE', 'polynesie-francaise', 'pf'),
(117, 'Territoires français du sud', 'TERRITOIRES FRANÇAIS DU SUD', 'territoires-francais-du-sud', 'tf'),
(118, 'Gabon', 'GABON', 'gabon', 'ga'),
(119, 'Gambie', 'GAMBIE', 'gambie', 'gm'),
(120, 'Géorgie', 'GÉORGIE', 'georgie', 'ge'),
(121, 'Ghana', 'GHANA', 'ghana', 'gh'),
(122, 'Gibraltar', 'GIBRALTAR', 'gibraltar', 'gi'),
(123, 'Groenland', 'GROENLAND', 'groenland', 'gl'),
(124, 'Grenade', 'GRENADE', 'grenade', 'gd'),
(125, 'Guadeloupe', 'GUADELOUPE', 'guadeloupe', 'gp'),
(126, 'Guam', 'GUAM', 'guam', 'gu'),
(127, 'Guatemala', 'GUATEMALA', 'guatemala', 'gt'),
(128, 'Guinée', 'GUINÉE', 'guinee', 'gn'),
(129, 'Guinée-Bissau', 'GUINÉE-BISSAU', 'guinee-bissau', 'gw'),
(130, 'Guyane', 'GUYANE', 'guyane', 'gy'),
(131, 'Haïti', 'HAÏTI', 'haiti', 'ht'),
(132, 'Heard et McDonald (Îles)', 'HEARD ET MCDONALD (ÎLES)', 'heard-et-mcdonald-iles', 'hm'),
(133, 'Honduras', 'HONDURAS', 'honduras', 'hn'),
(134, 'Islande', 'ISLANDE', 'islande', 'is'),
(135, 'Iran', 'IRAN', 'iran', 'ir'),
(136, 'Irak', 'IRAK', 'irak', 'iq'),
(137, 'Côte d''Ivoire', 'CÔTE D''IVOIRE', 'cote-divoire', 'ci'),
(138, 'Jamaïque', 'JAMAÏQUE', 'jamaique', 'jm'),
(139, 'Kazakhstan', 'KAZAKHSTAN', 'kazakhstan', 'kz'),
(140, 'Kenya', 'KENYA', 'kenya', 'ke'),
(141, 'Kiribati', 'KIRIBATI', 'kiribati', 'ki'),
(142, 'Corée du Nord', 'CORÉE DU NORD', 'coree-du-nord', 'kp'),
(143, 'Koweit', 'KOWEIT', 'koweit', 'kw'),
(144, 'Kirghizistan', 'KIRGHIZISTAN', 'kirghizistan', 'kg'),
(145, 'Laos', 'LAOS', 'laos', 'la'),
(146, 'Lettonie', 'LETTONIE', 'lettonie', 'lv'),
(147, 'Lesotho', 'LESOTHO', 'lesotho', 'ls'),
(148, 'Libéria', 'LIBÉRIA', 'liberia', 'lr'),
(149, 'Libye', 'LIBYE', 'libye', 'ly'),
(150, 'Liechtenstein', 'LIECHTENSTEIN', 'liechtenstein', 'li'),
(151, 'Lithuanie', 'LITHUANIE', 'lithuanie', 'lt'),
(152, 'Luxembourg', 'LUXEMBOURG', 'luxembourg', 'lu'),
(153, 'Macau', 'MACAU', 'macau', 'mo'),
(154, 'Macédoine', 'MACÉDOINE', 'macedoine', 'mk'),
(155, 'Madagascar', 'MADAGASCAR', 'madagascar', 'mg'),
(156, 'Malawi', 'MALAWI', 'malawi', 'mw'),
(157, 'Maldives (Îles)', 'MALDIVES (ÎLES)', 'maldives-iles', 'mv'),
(158, 'Mali', 'MALI', 'mali', 'ml'),
(159, 'Malte', 'MALTE', 'malte', 'mt'),
(160, 'Marshall (Îles)', 'MARSHALL (ÎLES)', 'marshall-iles', 'mh'),
(161, 'Martinique', 'MARTINIQUE', 'martinique', 'mq'),
(162, 'Mauritanie', 'MAURITANIE', 'mauritanie', 'mr'),
(163, 'Maurice', 'MAURICE', 'maurice', 'mu'),
(164, 'Mayotte', 'MAYOTTE', 'mayotte', 'yt'),
(165, 'Micronésie (États fédérés de)', 'MICRONÉSIE (ÉTATS FÉDÉRÉS DE)', 'micronesie-etats-federes-de', 'fm'),
(166, 'Moldavie', 'MOLDAVIE', 'moldavie', 'md'),
(167, 'Monaco', 'MONACO', 'monaco', 'mc'),
(168, 'Mongolie', 'MONGOLIE', 'mongolie', 'mn'),
(169, 'Montserrat', 'MONTSERRAT', 'montserrat', 'ms'),
(170, 'Mozambique', 'MOZAMBIQUE', 'mozambique', 'mz'),
(171, 'Myanmar', 'MYANMAR', 'myanmar', 'mm'),
(172, 'Namibie', 'NAMIBIE', 'namibie', 'na'),
(173, 'Nauru', 'NAURU', 'nauru', 'nr'),
(174, 'Nepal', 'NEPAL', 'nepal', 'np'),
(175, 'Antilles néerlandaises', 'ANTILLES NÉERLANDAISES', 'antilles-neerlandaises', 'an'),
(176, 'Nouvelle Calédonie', 'NOUVELLE CALÉDONIE', 'nouvelle-caledonie', 'nc'),
(177, 'Nicaragua', 'NICARAGUA', 'nicaragua', 'ni'),
(178, 'Niger', 'NIGER', 'niger', 'ne'),
(179, 'Nigeria', 'NIGERIA', 'nigeria', 'ng'),
(180, 'Niue', 'NIUE', 'niue', 'nu'),
(181, 'Norfolk (Îles)', 'NORFOLK (ÎLES)', 'norfolk-iles', 'nf'),
(182, 'Mariannes du Nord (Îles)', 'MARIANNES DU NORD (ÎLES)', 'mariannes-du-nord-iles', 'mp'),
(183, 'Oman', 'OMAN', 'oman', 'om'),
(184, 'Palau', 'PALAU', 'palau', 'pw'),
(185, 'Panama', 'PANAMA', 'panama', 'pa'),
(186, 'Papouasie-Nouvelle-Guinée', 'PAPOUASIE-NOUVELLE-GUINÉE', 'papouasie-nouvelle-guinee', 'pg'),
(187, 'Paraguay', 'PARAGUAY', 'paraguay', 'py'),
(188, 'Pitcairn (Îles)', 'PITCAIRN (ÎLES)', 'pitcairn-iles', 'pn'),
(189, 'Qatar', 'QATAR', 'qatar', 'qa'),
(190, 'Réunion (La)', 'RÉUNION (LA)', 'reunion-la', 're'),
(191, 'Rwanda', 'RWANDA', 'rwanda', 'rw'),
(192, 'Géorgie du Sud et Sandwich du Sud (Îles)', 'GÉORGIE DU SUD ET SANDWICH DU SUD (ÎLES)', 'georgie-du-sud-et-sandwich-du-sud-iles', 'gs'),
(193, 'Saint-Kitts et Nevis', 'SAINT-KITTS ET NEVIS', 'saint-kitts-et-nevis', 'kn'),
(194, 'Sainte Lucie', 'SAINTE LUCIE', 'sainte-lucie', 'lc'),
(195, 'Saint Vincent et les Grenadines', 'SAINT VINCENT ET LES GRENADINES', 'saint-vincent-et-les-grenadines', 'vc'),
(196, 'Samoa', 'SAMOA', 'samoa', 'ws'),
(197, 'Saint-Marin (Rép. de)', 'SAINT-MARIN (RÉP. DE)', 'saint-marin-rep-de', 'sm'),
(198, 'São Tomé et Príncipe (Rép.)', 'SÃO TOMÉ ET PRÍNCIPE (RÉP.)', 'sao-tome-et-principe-rep', 'st'),
(199, 'Sénégal', 'SÉNÉGAL', 'senegal', 'sn'),
(200, 'Seychelles', 'SEYCHELLES', 'seychelles', 'sc'),
(201, 'Sierra Leone', 'SIERRA LEONE', 'sierra-leone', 'sl'),
(202, 'Slovaquie', 'SLOVAQUIE', 'slovaquie', 'sk'),
(203, 'Slovénie', 'SLOVÉNIE', 'slovenie', 'si'),
(204, 'Somalie', 'SOMALIE', 'somalie', 'so'),
(205, 'Sri Lanka', 'SRI LANKA', 'sri-lanka', 'lk'),
(206, 'Sainte Hélène', 'SAINTE HÉLÈNE', 'sainte-helene', 'sh'),
(207, 'Saint Pierre et Miquelon', 'SAINT PIERRE ET MIQUELON', 'saint-pierre-et-miquelon', 'pm'),
(208, 'Soudan', 'SOUDAN', 'soudan', 'sd'),
(209, 'Suriname', 'SURINAME', 'suriname', 'sr'),
(210, 'Svalbard et Jan Mayen (Îles)', 'SVALBARD ET JAN MAYEN (ÎLES)', 'svalbard-et-jan-mayen-iles', 'sj'),
(211, 'Swaziland', 'SWAZILAND', 'swaziland', 'sz'),
(212, 'Syrie', 'SYRIE', 'syrie', 'sy'),
(213, 'Tadjikistan', 'TADJIKISTAN', 'tadjikistan', 'tj'),
(214, 'Tanzanie', 'TANZANIE', 'tanzanie', 'tz'),
(215, 'Togo', 'TOGO', 'togo', 'tg'),
(216, 'Tokelau', 'TOKELAU', 'tokelau', 'tk'),
(217, 'Tonga', 'TONGA', 'tonga', 'to'),
(218, 'Trinité et Tobago', 'TRINITÉ ET TOBAGO', 'trinite-et-tobago', 'tt'),
(219, 'Tunisie', 'TUNISIE', 'tunisie', 'tn'),
(220, 'Turkménistan', 'TURKMÉNISTAN', 'turkmenistan', 'tm'),
(221, 'Turks et Caïques (Îles)', 'TURKS ET CAÏQUES (ÎLES)', 'turks-et-caiques-iles', 'tc'),
(222, 'Tuvalu', 'TUVALU', 'tuvalu', 'tv'),
(223, 'Îles Mineures Éloignées des États-Unis', 'ÎLES MINEURES ÉLOIGNÉES DES ÉTATS-UNIS', 'iles-mineures-eloignees-des-etats-unis', 'um'),
(224, 'Ouganda', 'OUGANDA', 'ouganda', 'ug'),
(225, 'Uruguay', 'URUGUAY', 'uruguay', 'uy'),
(226, 'Ouzbékistan', 'OUZBÉKISTAN', 'ouzbekistan', 'uz'),
(227, 'Vanuatu', 'VANUATU', 'vanuatu', 'vu'),
(228, 'Vatican (Etat du)', 'VATICAN (ETAT DU)', 'vatican-etat-du', 'va'),
(229, 'Vietnam', 'VIETNAM', 'vietnam', 'vn'),
(230, 'Vierges (Îles)', 'VIERGES (ÎLES)', 'vierges-iles', 'vi'),
(231, 'Wallis et Futuna (Îles)', 'WALLIS ET FUTUNA (ÎLES)', 'wallis-et-futuna-iles', 'wf'),
(232, 'Sahara Occidental', 'SAHARA OCCIDENTAL', 'sahara-occidental', 'eh'),
(233, 'Yemen', 'YEMEN', 'yemen', 'ye'),
(234, 'Zaïre', 'ZAÏRE', 'zaire', 'zr'),
(235, 'Zambie', 'ZAMBIE', 'zambie', 'zm'),
(236, 'Zimbabwe', 'ZIMBABWE', 'zimbabwe', 'zw'),
(237, 'La Barbad', 'LA BARBAD', 'la-barbad', 'bb');
