package manager;

import db.DBConnectionProvider;
import model.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserManager {


    private Connection connection;

    public UserManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public void addUser(User user) {
        try {
            String query = "INSERT INTO user(`name`,`surname`,`email`,`password`, `pic_url`) " +
                    "VALUES(?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getPicUrl());
            System.out.println("executing the following statement ->" + query);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                user.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sendFriendRequest(long fromId, int toId) {
        try {
            String query = "INSERT INTO friend_request(`from_id`,`to_id`)" + "VALUES(?,?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, fromId);
            statement.setInt(2, toId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int id) {
        String query = "SELECT * FROM user WHERE id = " + id;
        return getUserFromDB(query);
    }

    public User getUserByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM user WHERE email = '" + email + "' AND password = '" + password + "'";
        return getUserFromDB(query);
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM user";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<User> users = new LinkedList<User>();
            while (resultSet.next()) {
                users.add(createUserFromResultSet(resultSet));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeUserById(int id) {
        String query = "DELETE  FROM user WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User getUserFromDB(String query) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return createUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(1));
        user.setName(resultSet.getString(2));
        user.setSurname(resultSet.getString(3));
        user.setEmail(resultSet.getString(4));
        user.setPassword(resultSet.getString(5));
        user.setPicUrl(resultSet.getString(6));
        return user;
    }


    public boolean isRequestSend(long id, int toId) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM `friend_request` WHERE `to_id` = " + id + " AND `from_id` = " + toId;
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return true;
            }
            return requestSend(id, toId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean requestSend(long id, int fromId) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM `friend_request` WHERE `from_id` = " + id + " AND `to_id` = " + fromId;
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isFriends(long userId, int friendId) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM `user_friend` WHERE `friend_id` = " + userId + " AND `user_id` = " + friendId;
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return true;
            }
            return isFriendUser(userId, friendId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isFriendUser(long userId, int friendId) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM `user_friend` WHERE `user_id` = " + userId + " AND `friend_id` = " + friendId;
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addFriend(long userId, int fromId) {

        try {

            String query = "INSERT INTO user_friend(`user_id`,`friend_id`)" +
                    " VALUES (?,?);";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, userId);
            statement.setInt(2, fromId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllFrineds(long id) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT `friend_id` FROM `user_friend` WHERE `user_id` = " + id;
            ResultSet resultSet = statement.executeQuery(query);
            List<User> users = new LinkedList<User>();
            while (resultSet.next()) {
                users.add(getUserById(resultSet.getInt(1)));
            }
            if (getAllFriendUser(id) != null) {

                users.addAll(getAllFriendUser(id));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<User> getAllFriendUser(long id) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT `user_id` FROM `user_friend` WHERE `friend_id` = " + id;
            ResultSet resultSet = statement.executeQuery(query);
            List<User> users = new LinkedList<User>();
            while (resultSet.next()) {
                users.add(getUserById(resultSet.getInt(1)));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<User> getAllRequests(long id) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT `from_id` FROM `friend_request` WHERE `to_id` = " + id;
            ResultSet resultSet = statement.executeQuery(query);
            List<User> users = new LinkedList<User>();
            while (resultSet.next()) {
                users.add(getUserById(resultSet.getInt(1)));
            }

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void removeRequest(long toId, int fromId) {
        try {
            Statement statement = connection.createStatement();
            String query = "DELETE FROM `friend_request` WHERE `to_id` = " + toId + " AND `from_id` = " + fromId;
            statement.executeUpdate(query);
            removeRequestSecond(toId, fromId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeRequestSecond(long toId, int fromId) {
        try {
            Statement statement = connection.createStatement();
            String query = "DELETE FROM `friend_request` WHERE `to_id` = " + fromId + " AND `from_id` = " + toId;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeFriend(long id, int userId) {
        try {
            Statement statement = connection.createStatement();
            String query = "DELETE FROM `user_friend` WHERE `user_id` = " + id + " AND `friend_id` = " + userId;
            statement.executeUpdate(query);
            removeFriendOr(id, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeFriendOr(long id, int userId) {
        try {
            Statement statement = connection.createStatement();
            String query = "DELETE FROM `user_friend` WHERE `friend_id` = " + id + " AND `user_id` = " + userId;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void changeUserPassword(User user) {
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE `user` SET `password` = '" + user.getPassword() + "' WHERE `id` = " + user.getId();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeUserData(User user) {
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE `user` SET `name` = '" + user.getName() + "', `surname` = '" + user.getSurname() + "', `pic_url`='" + user.getPicUrl() + "' WHERE `id` = " + user.getId();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}