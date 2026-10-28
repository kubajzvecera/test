package com.example.vis_reservation.data.db;

import org.h2.tools.Server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Database {

    private static final String DB_URL = "jdbc:h2:file:./vis_reservation";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    private static Database instance;
    private static Server server;

    private Connection connection;

    private Database() {}

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public void init() throws SQLException {
        getConnection();
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.h2.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException("H2 driver not found", e);
            }
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
        return connection;
    }

    public int update(String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            bindParameters(stmt, params);
            return stmt.executeUpdate();
        }
    }

    @FunctionalInterface
    public interface RowMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }

    public <T> List<T> query(String sql, RowMapper<T> mapper, Object... params) throws SQLException {
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            bindParameters(stmt, params);
            try (ResultSet rs = stmt.executeQuery()) {
                List<T> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(mapper.map(rs));
                }
                return result;
            }
        }
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore
            }
            connection = null;
        }
    }

    private void bindParameters(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }

    public void startWebServer() {
        Path h2ServerProperties = Paths.get(System.getProperty("user.home"), ".h2.server.properties");
        String url = "0=Generic H2 (Embedded)|org.h2.Driver|" + DB_URL.replace(":", "\\:") + "|";
        try {
            Files.writeString(h2ServerProperties, url,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopWebServer();
        try {
            server = Server.createWebServer("-webPort", "8082");
            server.start();
            System.out.println("H2 web console: " + server.getURL());
            System.out.println("H2 web server started");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void stopWebServer() {
        if (server != null) {
            server.stop();
            server = null;
            System.out.println("H2 web server stopped");
        }
    }
}
