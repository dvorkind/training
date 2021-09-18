package by.dvorkin.web.model.dao.impl;

import by.dvorkin.web.model.dao.BillDao;
import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.entity.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BillDaoImpl implements BillDao {
    private static final String CREATE = "INSERT INTO `bill` (`subscriber_id`, `invoice_date`, `sum`, `is_paid`, " +
            "`is_deleted`) VALUES (?, ?, ?, ?, ?)";
    private static final String READ = "SELECT * FROM `bill` WHERE  `is_deleted` = 0 AND `id` = ?";
    private static final String UPDATE = "UPDATE `bill` SET `is_paid` = ? WHERE `id` = ?";
    private static final String DELETE = "UPDATE `bill` SET `is_deleted` = ? WHERE `id` = ?";
    private static final String READ_LAST_BILL = "SELECT * FROM `bill` WHERE `invoice_date` IN (SELECT max" +
            "(invoice_date) FROM `bill` WHERE `is_deleted` = 0 AND `subscriber_id` = ?)";
    private static final String READ_ALL_SUBSCRIBERS_BILL = "SELECT * FROM `bill` WHERE `is_deleted` = 0 AND " +
            "`subscriber_id` = ?";
    private static final String READ_ALL_UNPAID_SUBSCRIBERS_BILL =
            "SELECT * FROM `bill` WHERE `is_deleted` = 0 AND `is_paid`= 0  AND `subscriber_id` = ?";
    private static final String READ_ALL_UNPAID = "SELECT * FROM `bill` WHERE `is_deleted` = 0 AND `is_paid` = 0 ";
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Long create(Bill bill) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, bill.getSubscriberId());
            statement.setTimestamp(2, Timestamp.valueOf(bill.getInvoiceDate()));
            statement.setInt(3, bill.getSum());
            statement.setBoolean(4, bill.isPaid());
            statement.setInt(5, 0);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            Long result = resultSet.getLong(1);
            resultSet.close();
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Bill read(Long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(READ)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Bill bill = null;
            if (resultSet.next()) {
                bill = createBill(resultSet);
            }
            resultSet.close();
            return bill;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Bill bill) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setBoolean(1, bill.isPaid());
            statement.setLong(2, bill.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, 1);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public LocalDateTime readLastBill(Long subscriberId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(READ_LAST_BILL)) {
            statement.setLong(1, subscriberId);
            ResultSet resultSet = statement.executeQuery();
            LocalDateTime lastDate = null;
            if (resultSet.next()) {
                lastDate = resultSet.getTimestamp("invoice_date").toLocalDateTime();
            }
            resultSet.close();
            return lastDate;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Bill> readAllSubscribersBill(Long subscriberId) throws DaoException {
        return getSubscribersBillList(READ_ALL_SUBSCRIBERS_BILL, subscriberId);
    }

    @Override
    public List<Bill> readAllUnpaidSubscribersBill(Long subscriberId) throws DaoException {
        return getSubscribersBillList(READ_ALL_UNPAID_SUBSCRIBERS_BILL, subscriberId);
    }

    @Override
    public List<Bill> readAllUnpaid() throws DaoException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(READ_ALL_UNPAID);
            List<Bill> bills = new ArrayList<>();
            while (resultSet.next()) {
                bills.add(createBill(resultSet));
            }
            resultSet.close();
            return bills;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private List<Bill> getSubscribersBillList(String sql, Long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Bill> bills = new ArrayList<>();
            while (resultSet.next()) {
                bills.add(createBill(resultSet));
            }
            resultSet.close();
            return bills;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Bill createBill(ResultSet resultSet) throws SQLException {
        Bill bill = new Bill();
        bill.setId(resultSet.getLong("bill.id"));
        bill.setSubscriberId(resultSet.getLong("bill.subscriber_id"));
        bill.setInvoiceDate(resultSet.getTimestamp("bill.invoice_date").toLocalDateTime());
        bill.setSum(resultSet.getInt("bill.sum"));
        bill.setPaid(resultSet.getBoolean("bill.is_paid"));
        return bill;
    }
}
