-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:8889
-- Généré le : lun. 20 mai 2024 à 00:00
-- Version du serveur : 5.7.39
-- Version de PHP : 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `gestiondevolgroupe1`
--

-- --------------------------------------------------------

--
-- Structure de la table `admin`
--

CREATE TABLE `admin` (
  `idAdmin` int(11) NOT NULL,
  `idPersonne` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `admin`
--

INSERT INTO `admin` (`idAdmin`, `idPersonne`) VALUES
(2, 2),
(1, 6);

-- --------------------------------------------------------

--
-- Structure de la table `aeroport`
--

CREATE TABLE `aeroport` (
  `idAeroport` int(11) NOT NULL,
  `idVille` int(11) DEFAULT NULL,
  `nomAeroport` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `avion`
--

CREATE TABLE `avion` (
  `immatriculation` varchar(30) NOT NULL,
  `capacite` int(11) DEFAULT NULL,
  `modele` varchar(30) DEFAULT NULL,
  `idCompagnie` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `avion`
--

INSERT INTO `avion` (`immatriculation`, `capacite`, `modele`, `idCompagnie`) VALUES
('23RU', 300, 'Boing', 1),
('32WER', 250, 'Classique', 1);

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

CREATE TABLE `categorie` (
  `idCategorie` int(11) NOT NULL,
  `nom` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `categorie`
--

INSERT INTO `categorie` (`idCategorie`, `nom`) VALUES
(1, 'Buissness'),
(2, 'Affaire'),
(3, 'economique');

-- --------------------------------------------------------

--
-- Structure de la table `compagnie`
--

CREATE TABLE `compagnie` (
  `idCompagnie` int(11) NOT NULL,
  `nomCompagnie` varchar(30) DEFAULT NULL,
  `motDePasse` varchar(100) DEFAULT NULL,
  `siteWeb` varchar(45) NOT NULL,
  `idAdmin` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `compagnie`
--

INSERT INTO `compagnie` (`idCompagnie`, `nomCompagnie`, `motDePasse`, `siteWeb`, `idAdmin`) VALUES
(1, 'SkyMali', 'fakoro', 'fa.com', 1),
(2, 'Dakar Airlines', 'fakoro', 'da.com', 1);

-- --------------------------------------------------------

--
-- Structure de la table `escale`
--

CREATE TABLE `escale` (
  `id` int(11) NOT NULL,
  `idVille` int(11) DEFAULT NULL,
  `idVol` int(11) DEFAULT NULL,
  `immatriculation` varchar(23) DEFAULT NULL,
  `DateEtHeure` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `infopassager`
--

CREATE TABLE `infopassager` (
  `id` int(11) NOT NULL,
  `idReservation` int(11) DEFAULT NULL,
  `idVol` int(11) DEFAULT NULL,
  `nomPassagerEtranger` varchar(50) NOT NULL,
  `prenomPassagerEtranger` varchar(50) NOT NULL,
  `numeroPasseport` varchar(50) NOT NULL,
  `idCategorie` int(11) DEFAULT NULL,
  `statut` varchar(11) NOT NULL,
  `tarif` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `paiement`
--

CREATE TABLE `paiement` (
  `idPaiement` int(11) NOT NULL,
  `idReservation` int(11) NOT NULL,
  `montant` int(11) DEFAULT NULL,
  `modePaiement` varchar(50) DEFAULT NULL,
  `datePaiement` date DEFAULT NULL,
  `numeroTelephone` varchar(20) DEFAULT NULL,
  `numeroCarte` varchar(16) DEFAULT NULL,
  `numeroCvv` int(11) DEFAULT NULL,
  `dateExpiration` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `passager`
--

CREATE TABLE `passager` (
  `idPassager` int(11) NOT NULL,
  `idPersonne` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `passager`
--

INSERT INTO `passager` (`idPassager`, `idPersonne`) VALUES
(8, 8);

-- --------------------------------------------------------

--
-- Structure de la table `pays`
--

CREATE TABLE `pays` (
  `idPays` int(11) NOT NULL,
  `nom` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `pays`
--

INSERT INTO `pays` (`idPays`, `nom`) VALUES
(1, 'Mali'),
(2, 'France'),
(3, 'Senegal');

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

CREATE TABLE `personne` (
  `idPersonne` int(11) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `numeroDeTelephone` varchar(20) DEFAULT NULL,
  `dateDeNaissance` date DEFAULT NULL,
  `motDePasse` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `personne`
--

INSERT INTO `personne` (`idPersonne`, `nom`, `prenom`, `email`, `numeroDeTelephone`, `dateDeNaissance`, `motDePasse`) VALUES
(2, 'Traore', 'Bouba', 'bouba@gmail.com', '73375200', '2002-02-22', '$2a$10$/MLDMmj4c/SvgTfztW30LeP5WvsRCMvUOaYthQJ7ov2KVYprlEspu'),
(6, 'fa', 'traore', 'fa@gmail.com', '+22373375200', '2002-09-22', '$2a$10$9Jt2PmdwlizLFqqIvFYN8.nL3./uG4FHdc/bBeBsIU0U3hIqY1aX2'),
(8, 'Traore', 'Fakoro', 'fakor88@gmail.com', '+22373375200', '2002-09-22', '$2a$10$DLnMI/07508H4v8X3eZb/uDNI2eqWWBzz.Sfy9Tr6UniQ/BBGXuc2');

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

CREATE TABLE `reservation` (
  `idReservation` int(11) NOT NULL,
  `idPassager` int(11) DEFAULT NULL,
  `dateReservation` date DEFAULT NULL,
  `nombreDePassager` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `ville`
--

CREATE TABLE `ville` (
  `idVille` int(11) NOT NULL,
  `idPays` int(11) DEFAULT NULL,
  `Nom` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `ville`
--

INSERT INTO `ville` (`idVille`, `idPays`, `Nom`) VALUES
(1, 1, 'Bamako'),
(2, 1, 'Kayes'),
(3, 1, 'Sikasso'),
(4, 3, 'Dakar');

-- --------------------------------------------------------

--
-- Structure de la table `vol`
--

CREATE TABLE `vol` (
  `idVol` int(11) NOT NULL,
  `immatriculation` varchar(30) NOT NULL,
  `villeDeDepart` varchar(30) DEFAULT NULL,
  `villeDArrive` varchar(30) DEFAULT NULL,
  `dateDeDepart` date DEFAULT NULL,
  `dateDArrive` date DEFAULT NULL,
  `nombreDEscale` int(11) DEFAULT NULL,
  `tarif` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `vol`
--

INSERT INTO `vol` (`idVol`, `immatriculation`, `villeDeDepart`, `villeDArrive`, `dateDeDepart`, `dateDArrive`, `nombreDEscale`, `tarif`) VALUES
(1, '23RU', 'Bamako', 'Kayes', '2024-05-16', '2024-05-17', 0, 200),
(2, '32WER', 'Bamako', 'Kayes', '2024-05-16', '2024-05-17', 0, 400),
(3, '23RU', 'Bamako', 'Sikasso', '2024-05-23', '2024-05-24', 1, 150);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`idAdmin`),
  ADD KEY `idPersonne` (`idPersonne`);

--
-- Index pour la table `aeroport`
--
ALTER TABLE `aeroport`
  ADD PRIMARY KEY (`idAeroport`),
  ADD KEY `idVille` (`idVille`);

--
-- Index pour la table `avion`
--
ALTER TABLE `avion`
  ADD PRIMARY KEY (`immatriculation`),
  ADD KEY `idCompagnie` (`idCompagnie`);

--
-- Index pour la table `categorie`
--
ALTER TABLE `categorie`
  ADD PRIMARY KEY (`idCategorie`);

--
-- Index pour la table `compagnie`
--
ALTER TABLE `compagnie`
  ADD PRIMARY KEY (`idCompagnie`),
  ADD KEY `idAdmin` (`idAdmin`);

--
-- Index pour la table `escale`
--
ALTER TABLE `escale`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fkville` (`idVille`),
  ADD KEY `fkvol` (`idVol`);

--
-- Index pour la table `infopassager`
--
ALTER TABLE `infopassager`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idReservation` (`idReservation`),
  ADD KEY `idVol` (`idVol`),
  ADD KEY `idCategorie` (`idCategorie`);

--
-- Index pour la table `paiement`
--
ALTER TABLE `paiement`
  ADD PRIMARY KEY (`idPaiement`),
  ADD KEY `fkReservation` (`idReservation`);

--
-- Index pour la table `passager`
--
ALTER TABLE `passager`
  ADD PRIMARY KEY (`idPassager`),
  ADD KEY `idPersonne` (`idPersonne`);

--
-- Index pour la table `pays`
--
ALTER TABLE `pays`
  ADD PRIMARY KEY (`idPays`);

--
-- Index pour la table `personne`
--
ALTER TABLE `personne`
  ADD PRIMARY KEY (`idPersonne`);

--
-- Index pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`idReservation`),
  ADD KEY `reservation_ibfk_1` (`idPassager`);

--
-- Index pour la table `ville`
--
ALTER TABLE `ville`
  ADD PRIMARY KEY (`idVille`),
  ADD KEY `idPays` (`idPays`);

--
-- Index pour la table `vol`
--
ALTER TABLE `vol`
  ADD PRIMARY KEY (`idVol`),
  ADD KEY `fkAvionVol` (`immatriculation`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `admin`
--
ALTER TABLE `admin`
  MODIFY `idAdmin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `aeroport`
--
ALTER TABLE `aeroport`
  MODIFY `idAeroport` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `categorie`
--
ALTER TABLE `categorie`
  MODIFY `idCategorie` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `compagnie`
--
ALTER TABLE `compagnie`
  MODIFY `idCompagnie` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `escale`
--
ALTER TABLE `escale`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `infopassager`
--
ALTER TABLE `infopassager`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `paiement`
--
ALTER TABLE `paiement`
  MODIFY `idPaiement` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `passager`
--
ALTER TABLE `passager`
  MODIFY `idPassager` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `pays`
--
ALTER TABLE `pays`
  MODIFY `idPays` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `personne`
--
ALTER TABLE `personne`
  MODIFY `idPersonne` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `idReservation` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `ville`
--
ALTER TABLE `ville`
  MODIFY `idVille` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `vol`
--
ALTER TABLE `vol`
  MODIFY `idVol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`idPersonne`) REFERENCES `personne` (`idPersonne`);

--
-- Contraintes pour la table `aeroport`
--
ALTER TABLE `aeroport`
  ADD CONSTRAINT `aeroport_ibfk_1` FOREIGN KEY (`idVille`) REFERENCES `ville` (`idVille`);

--
-- Contraintes pour la table `avion`
--
ALTER TABLE `avion`
  ADD CONSTRAINT `avion_ibfk_1` FOREIGN KEY (`idCompagnie`) REFERENCES `compagnie` (`idCompagnie`);

--
-- Contraintes pour la table `compagnie`
--
ALTER TABLE `compagnie`
  ADD CONSTRAINT `compagnie_ibfk_1` FOREIGN KEY (`idAdmin`) REFERENCES `admin` (`idAdmin`);

--
-- Contraintes pour la table `escale`
--
ALTER TABLE `escale`
  ADD CONSTRAINT `fkville` FOREIGN KEY (`idVille`) REFERENCES `ville` (`idVille`),
  ADD CONSTRAINT `fkvol` FOREIGN KEY (`idVol`) REFERENCES `vol` (`idVol`);

--
-- Contraintes pour la table `infopassager`
--
ALTER TABLE `infopassager`
  ADD CONSTRAINT `infopassager_ibfk_1` FOREIGN KEY (`idReservation`) REFERENCES `reservation` (`idReservation`),
  ADD CONSTRAINT `infopassager_ibfk_2` FOREIGN KEY (`idVol`) REFERENCES `vol` (`idVol`),
  ADD CONSTRAINT `infopassager_ibfk_3` FOREIGN KEY (`idCategorie`) REFERENCES `categorie` (`idCategorie`);

--
-- Contraintes pour la table `paiement`
--
ALTER TABLE `paiement`
  ADD CONSTRAINT `fkReservation` FOREIGN KEY (`idReservation`) REFERENCES `reservation` (`idReservation`);

--
-- Contraintes pour la table `passager`
--
ALTER TABLE `passager`
  ADD CONSTRAINT `passager_ibfk_1` FOREIGN KEY (`idPersonne`) REFERENCES `personne` (`idPersonne`);

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`idPassager`) REFERENCES `passager` (`idPassager`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `ville`
--
ALTER TABLE `ville`
  ADD CONSTRAINT `ville_ibfk_1` FOREIGN KEY (`idPays`) REFERENCES `pays` (`idPays`);

--
-- Contraintes pour la table `vol`
--
ALTER TABLE `vol`
  ADD CONSTRAINT `fkAvionVol` FOREIGN KEY (`immatriculation`) REFERENCES `avion` (`immatriculation`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
