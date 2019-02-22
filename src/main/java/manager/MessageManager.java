package manager;

import db.DBConnectionProvider;
import model.Message;
import util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MessageManager {

    private Connection connection;

    public MessageManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }


    public void addMessage(Message message) {

        try {
            String query = "INSERT INTO message(`from_id`,`to_id`,`message`,`send_date`) "
                    + "VALUES (?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, message.getFromId().getId());
            statement.setLong(2, message.getToId().getId());
            statement.setString(3, message.getMessage());
            statement.setString(4, DateUtil.convertDateToString(message.getDate()));
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                message.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Message> getMessages(long id, int toId) {


        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM `message` WHERE from_id = " + id + " AND to_id = " + toId;
            ResultSet resultSet = statement.executeQuery(query);
            UserManager userManager = new UserManager();
            List<Message> messages = new ArrayList<>();
            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getLong(1));
                message.setFromId(userManager.getUserById(resultSet.getInt(2)));
                message.setToId(userManager.getUserById(resultSet.getInt(3)));
                message.setMessage(resultSet.getString(4));
                message.setDate(DateUtil.convertStringToDate(resultSet.getString(5)));
                messages.add(message);

            }
            if (getOtherMessages(id, toId) != null) {
                messages.addAll(getOtherMessages(id, toId));
            }
            return messages;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Message> getOtherMessages(long id, int toId) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM `message` WHERE from_id = " + toId + " AND to_id = " + id;
            ResultSet resultSet = statement.executeQuery(query);
            UserManager userManager = new UserManager();
            List<Message> messages = new ArrayList<>();
            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getLong(1));
                message.setFromId(userManager.getUserById(resultSet.getInt(2)));
                message.setToId(userManager.getUserById(resultSet.getInt(3)));
                message.setMessage(resultSet.getString(4));
                message.setDate(DateUtil.convertStringToDate(resultSet.getString(5)));
                messages.add(message);

            }
            return messages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
