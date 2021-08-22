package by.dvorkin.web.model.dao.impl;

import by.dvorkin.web.model.dao.AccountDao;
import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.Role;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDaoImpl implements AccountDao {
    private Connection connection;

    //TODO: создать константы запросов
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Account readByLogin(String login) throws DaoException {
        String sql = "SELECT `id`, `password`, `role` FROM `account` WHERE `login` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            Account account = null;
            if (resultSet.next()) {
                account = new Account();
                account.setId(resultSet.getLong("id"));
                account.setLogin(login);
                account.setPassword(resultSet.getString("password"));
                account.setRole(Role.values()[resultSet.getInt("role")]);
            }
            return account;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Account readByLoginAndPassword(String login, String password) throws DaoException {
        String sql = "SELECT `id`, `role` FROM `account` WHERE `login` = ? AND `password` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, passwordToSHA(password));
            resultSet = statement.executeQuery();
            Account account = null;
            if (resultSet.next()) {
                account = new Account();
                account.setId(resultSet.getLong("id"));
                account.setLogin(login);
                account.setRole(Role.values()[resultSet.getInt("role")]);
            }
            return account;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ignored) {
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ignored) {
            }
        }
    }

    @Override
    public Long create(Account account) throws DaoException {
        String sql = "INSERT INTO `account` (`login`, `password`, `role`) VALUES (?, ?, ?)";
        ResultSet resultSet;
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, account.getLogin());
            statement.setString(2, passwordToSHA(account.getPassword()));
            statement.setInt(3, account.getRole()
                    .ordinal());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private String passwordToSHA(String sourcePassword) {
        MessageDigest messageDigest;
        byte[] bytesEncoded = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(sourcePassword.getBytes(StandardCharsets.UTF_8));
            bytesEncoded = messageDigest.digest();
        } catch (NoSuchAlgorithmException ignored) {
        }
        BigInteger bigInt;
        if (bytesEncoded != null) {
            bigInt = new BigInteger(1, bytesEncoded);
            return bigInt.toString(16);
        }
        return sourcePassword;
    }
}
