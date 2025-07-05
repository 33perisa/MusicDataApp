package com.musicapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistManager {
    private static Connection connection = DatabaseConnection.getConnection();

    public static List<Artist> getAllArtists() throws SQLException {
        List<Artist> artists = new ArrayList<>();
        String query = "SELECT * FROM artists";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            artists.add(new Artist(
                    resultSet.getInt("ID"),
                    resultSet.getString("name"),
                    resultSet.getString("type"),
                    resultSet.getInt("formation_year"),
                    resultSet.getInt("disbandment_year") != 0 ? resultSet.getInt("disbandment_year") : null,
                    resultSet.getString("official_website")
            ));
        }

        resultSet.close();
        statement.close();
        return artists;
    }

    public static void addArtist(Artist artist) throws SQLException {
        String query = "INSERT INTO artists (name, type, formation_year, disbandment_year, official_website) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, artist.getName());
        preparedStatement.setString(2, artist.getType());
        preparedStatement.setInt(3, artist.getFormationYear());
        if (artist.getDisbandmentYear() != null) {
            preparedStatement.setInt(4, artist.getDisbandmentYear());
        } else {
            preparedStatement.setNull(4, Types.INTEGER);
        }
        preparedStatement.setString(5, artist.getOfficialWebsite());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static void updateArtist(Artist artist) throws SQLException {
        String query = "UPDATE artists SET name = ?, type = ?, formation_year = ?, disbandment_year = ?, official_website = ? WHERE ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, artist.getName());
        preparedStatement.setString(2, artist.getType());
        preparedStatement.setInt(3, artist.getFormationYear());
        if (artist.getDisbandmentYear() != null) {
            preparedStatement.setInt(4, artist.getDisbandmentYear());
        } else {
            preparedStatement.setNull(4, Types.INTEGER);
        }
        preparedStatement.setString(5, artist.getOfficialWebsite());
        preparedStatement.setInt(6, artist.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static void deleteArtist(int id) throws SQLException {
        String query = "DELETE FROM artists WHERE ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static List<Artist> getSoloArtists() throws SQLException {
        List<Artist> artists = new ArrayList<>();
        String query = "SELECT * FROM artists WHERE type = 'solo'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            artists.add(new Artist(
                    resultSet.getInt("ID"),
                    resultSet.getString("name"),
                    resultSet.getString("type"),
                    resultSet.getInt("formation_year"),
                    resultSet.getInt("disbandment_year") != 0 ? resultSet.getInt("disbandment_year") : null,
                    resultSet.getString("official_website")
            ));
        }

        resultSet.close();
        statement.close();
        return artists;
    }

    public static List<Artist> getArtistsFormedAfterYear(int year) throws SQLException {
        List<Artist> artists = new ArrayList<>();
        String query = "SELECT * FROM artists WHERE formation_year > ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, year);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            artists.add(new Artist(
                    resultSet.getInt("ID"),
                    resultSet.getString("name"),
                    resultSet.getString("type"),
                    resultSet.getInt("formation_year"),
                    resultSet.getInt("disbandment_year") != 0 ? resultSet.getInt("disbandment_year") : null,
                    resultSet.getString("official_website")
            ));
        }

        resultSet.close();
        preparedStatement.close();
        return artists;
    }

    public static Discography getDiscographyByArtistId(int artistId) throws SQLException {
        Artist artist = null;
        List<Album> albums = new ArrayList<>();
        String artistQuery = "SELECT * FROM artists WHERE ID = ?";
        PreparedStatement artistStatement = connection.prepareStatement(artistQuery);
        artistStatement.setInt(1, artistId);
        ResultSet artistResultSet = artistStatement.executeQuery();

        if (artistResultSet.next()) {
            artist = new Artist(
                    artistResultSet.getInt("ID"),
                    artistResultSet.getString("name"),
                    artistResultSet.getString("type"),
                    artistResultSet.getInt("formation_year"),
                    artistResultSet.getInt("disbandment_year") != 0 ? artistResultSet.getInt("disbandment_year") : null,
                    artistResultSet.getString("official_website")
            );
        }

        String albumQuery = "SELECT * FROM albums WHERE artist_id = ?";
        PreparedStatement albumStatement = connection.prepareStatement(albumQuery);
        albumStatement.setInt(1, artistId);
        ResultSet albumResultSet = albumStatement.executeQuery();

        while (albumResultSet.next()) {
            albums.add(new Album(
                    albumResultSet.getInt("ID"),
                    albumResultSet.getInt("artist_id"),
                    albumResultSet.getString("title"),
                    albumResultSet.getInt("release_year"),
                    albumResultSet.getString("record_label")
            ));
        }

        artistResultSet.close();
        artistStatement.close();
        albumResultSet.close();
        albumStatement.close();

        return new Discography(artist, albums);
    }

    public static List<Discography> getDiscographiesByRecordLabel(String recordLabel) throws SQLException {
        List<Discography> discographies = new ArrayList<>();
        String query = "SELECT a.*, b.* FROM artists a JOIN albums b ON a.ID = b.artist_id WHERE b.record_label = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, recordLabel);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Artist artist = new Artist(
                    resultSet.getInt("a.ID"),
                    resultSet.getString("a.name"),
                    resultSet.getString("a.type"),
                    resultSet.getInt("a.formation_year"),
                    resultSet.getInt("a.disbandment_year") != 0 ? resultSet.getInt("a.disbandment_year") : null,
                    resultSet.getString("a.official_website")
            );

            Album album = new Album(
                    resultSet.getInt("b.ID"),
                    resultSet.getInt("b.artist_id"),
                    resultSet.getString("b.title"),
                    resultSet.getInt("b.release_year"),
                    resultSet.getString("b.record_label")
            );

            Discography discography = new Discography(artist, List.of(album));
            discographies.add(discography);
        }

        resultSet.close();
        preparedStatement.close();
        return discographies;
    }
}
