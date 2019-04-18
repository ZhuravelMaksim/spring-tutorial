package com.it.app.DAO;

import com.it.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcUserDao implements UserDao {

    private static final String SELECT_FROM_USER = "select * from User";
    private static final String SELECT_FROM_USER_WHERE_ID = "select * from User where id=?";
    private static final String SELECT_FROM_USER_WHERE_ID_1 = "select * from User where id=1";
    private static final String SELECT_ID_FROM_USER = "select id from User";
    private static final String SELECT_ID_FROM_USER_WHERE_ID_1 = "select id from User where id=1";
    private static final String SELECT_ID_FROM_USER_WHERE_ID = "select id from User where id=?";
    private static final String DELETE_FROM_USER_WHERE_ID_1 = "delete from User where id=1";
    private static final String DELETE_FROM_USER_WHERE_ID = "delete from User where id=?";
    private static final String DELETE_FROM_USER_WHERE_ID1 = "delete from User where id=?";
    private static final String INSERT_INTO_USER_NAME_VALUES = "insert into User (NAME) values (?)";
    private static final String INSERT_INTO_USER_ID_NAME_VALUES = "insert into User (ID,NAME) values (?,?)";
    private static final String UPDATE_USER_SET_NAME = "update User set name=?";
    private static final String UPDATE_USER_SET_NAME_WHERE_ID = "update User set name=? where id=? ";

    @Autowired
    private JdbcOperations jdbcTemplate;

    private final RowMapper<User> userRowMapper = (ResultSet rs, int rowNum) -> {
        System.out.println("userRowMapper rowNum: " + rowNum);
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        return user;
    };

    private final ResultSetExtractor<List<User>> resultSetExtractor = (ResultSet resultSet) -> {
        List<User> list = new ArrayList<>();
        fillUserList(list, resultSet);
        return list;
    };

    @Override
    public User findOne(Long id) {
        queryForObject();
        execute();
        return null;
    }

    @Override
    public List<User> findAll() {
        query();
        queryForList();
        queryForMap();
        queryForRowSet();
        return null;
    }

    /**
     * jdbcTemplate.update method uses for saving
     */
    @Override
    public User save(User user) {
        saveWithKeyHolder(user);
        saveWithKey(user);
        return user;
    }

    /**
     * jdbcTemplate.update method uses for updating
     */
    @Override
    public int update(User user) {
        int i = jdbcTemplate.update(UPDATE_USER_SET_NAME, preparedStatement -> preparedStatement.setString(1, "UPDATE"));
        System.out.println("update 1:" + i);
        return i;
    }

    /**
     * jdbcTemplate.batchUpdate method uses for batch updating
     */
    @Override
    public int[] batchUpdate(List<User> userList) {
        int[] ints = jdbcTemplate.batchUpdate(UPDATE_USER_SET_NAME_WHERE_ID, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                User user = userList.get(i);
                ps.setString(1, user.getName());
                ps.setLong(2, user.getId());
            }

            @Override
            public int getBatchSize() {
                return userList.size();
            }
        });
        System.out.println("batchUpdate 1:" + Arrays.toString(ints));
        return ints;
    }

    /**
     * jdbcTemplate.batchUpdate method uses for deleting
     */
    @Override
    public int delete(User user) {
        int update1 = jdbcTemplate.update(DELETE_FROM_USER_WHERE_ID_1);
        int update2 = jdbcTemplate.update(DELETE_FROM_USER_WHERE_ID, 1L);
        int update3 = jdbcTemplate.update(DELETE_FROM_USER_WHERE_ID1, new Object[]{1L}, new int[]{Types.BIGINT});

        System.out.println("delete 1:" + update1);
        System.out.println("delete 2:" + update2);
        System.out.println("delete 3:" + update3);

        return 1;
    }

    /**
     * jdbcTemplate.execute can execute every SQL Statement, also CallableStatement and PreparedStatement
     */
    private void execute() {
        jdbcTemplate.execute(SELECT_FROM_USER_WHERE_ID, (PreparedStatementCallback<User>) callableStatement -> {
            callableStatement.setLong(1, 1L);
            callableStatement.execute();
            ResultSet resultSet = callableStatement.getResultSet();
            User user = new User();
            while (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
            }
            System.out.println("execute user:" + user);
            return user;
        });
    }

    /**
     * jdbcTemplate.query returns List of T type,
     * uses: userRowMapper(return method process each row independent),
     * resultSetExtractor(return method process all rows in one method),
     * rowCallbackHandler(void method which uses as all rows extractor)
     */
    private void query() {
        // use userRowMapper
        List<User> userList1 = jdbcTemplate.query(SELECT_FROM_USER, userRowMapper);
        // use resultSetExtractor
        List<User> userList2 = jdbcTemplate.query(SELECT_FROM_USER, resultSetExtractor);
        //use rowCallbackHandler
        List<User> userList3 = new ArrayList<>();
        RowCallbackHandler rowCallbackHandler = (ResultSet resultSet) -> {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            userList3.add(user);
            fillUserList(userList3, resultSet);
        };
        jdbcTemplate.query(SELECT_FROM_USER, rowCallbackHandler);

        System.out.println("userRowMapper userList1:" + userList1);
        System.out.println("resultSetExtractor userList2:" + userList2);
        System.out.println("rowCallbackHandler userList3:" + userList3);
    }

    private void fillUserList(List<User> userList3, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            User user1 = new User();
            user1.setId(resultSet.getLong("id"));
            user1.setName(resultSet.getString("name"));
            userList3.add(user1);
        }
    }

    private void queryForObject() {
        queryForObjectSingleColumn();
        queryForObjectFullObject();
    }

    /**
     * jdbcTemplate.queryForObject returns one column
     */
    private void queryForObjectSingleColumn() {
        Long idFromUser1 = jdbcTemplate.queryForObject(SELECT_ID_FROM_USER_WHERE_ID_1, Long.class);
        Long idFromUser2 = jdbcTemplate.queryForObject(SELECT_ID_FROM_USER_WHERE_ID, Long.class, 1L);
        Long idFromUser3 = jdbcTemplate.queryForObject(SELECT_ID_FROM_USER_WHERE_ID, new Object[]{1L}, new int[]{Types.BIGINT}, Long.class);
        Long idFromUser4 = jdbcTemplate.queryForObject(SELECT_ID_FROM_USER_WHERE_ID, new Object[]{1L}, Long.class);

        System.out.println("queryForObjectSingleColumn idFromUser1:" + idFromUser1);
        System.out.println("queryForObjectSingleColumn idFromUser2:" + idFromUser2);
        System.out.println("queryForObjectSingleColumn idFromUser3:" + idFromUser3);
        System.out.println("queryForObjectSingleColumn idFromUser4:" + idFromUser4);
    }

    /**
     * jdbcTemplate.queryForObject returns object customized by userRowMapper
     */
    private void queryForObjectFullObject() {
        User user1 = jdbcTemplate.queryForObject(SELECT_FROM_USER_WHERE_ID_1, userRowMapper);
        User user2 = jdbcTemplate.queryForObject(SELECT_FROM_USER_WHERE_ID, userRowMapper, 1L);
        User user3 = jdbcTemplate.queryForObject(SELECT_FROM_USER_WHERE_ID, new Object[]{1L}, new int[]{Types.BIGINT}, userRowMapper);
        User user4 = jdbcTemplate.queryForObject(SELECT_FROM_USER_WHERE_ID, new Object[]{1L}, userRowMapper);

        System.out.println("queryForObjectFullObject user1:" + user1);
        System.out.println("queryForObjectFullObject user2:" + user2);
        System.out.println("queryForObjectFullObject user3:" + user3);
        System.out.println("queryForObjectFullObject user4:" + user4);
    }

    private void queryForList() {
        queryForListSingleColumn();
        queryForListFullObject();
    }

    /**
     * jdbcTemplate.queryForList returns list of one columns
     */
    private void queryForListSingleColumn() {
        List<Long> userIdList1 = jdbcTemplate.queryForList(SELECT_ID_FROM_USER, Long.class);
        List<Long> userIdList2 = jdbcTemplate.queryForList(SELECT_ID_FROM_USER_WHERE_ID, Long.class, 1L);
        List<Long> userIdList3 = jdbcTemplate.queryForList(SELECT_ID_FROM_USER_WHERE_ID, new Object[]{1L}, new int[]{Types.BIGINT}, Long.class);
        List<Long> userIdList4 = jdbcTemplate.queryForList(SELECT_ID_FROM_USER_WHERE_ID, new Object[]{1L}, Long.class);

        System.out.println("queryForListSingleColumn userIdList1:" + userIdList1);
        System.out.println("queryForListSingleColumn userIdList2:" + userIdList2);
        System.out.println("queryForListSingleColumn userIdList3:" + userIdList3);
        System.out.println("queryForListSingleColumn userIdList4:" + userIdList4);
    }

    /**
     * jdbcTemplate.queryForList returns list of Map<String, Object> which should be processed
     */
    private void queryForListFullObject() {
        List<Map<String, Object>> userMapList1 = jdbcTemplate.queryForList(SELECT_FROM_USER);
        List<Map<String, Object>> userMapList2 = jdbcTemplate.queryForList(SELECT_FROM_USER_WHERE_ID, new Object[]{1L}, new int[]{Types.BIGINT});
        List<Map<String, Object>> userMapList3 = jdbcTemplate.queryForList(SELECT_FROM_USER_WHERE_ID, 1L);

        System.out.println("queryForListFullObject userMapList1:");
        getUser(userMapList1);
        System.out.println("queryForListFullObject userMapList2:");
        getUser(userMapList2);
        System.out.println("queryForListFullObject userMapList3:");
        getUser(userMapList3);
    }

    private void getUser(List<Map<String, Object>> userMapList1) {
        for (Map<String, Object> row : userMapList1) {
            User user = new User();
            user.setId((Long) row.get("id"));
            user.setName((String) row.get("name"));
            System.out.print(user);
            System.out.println();
        }
    }

    /**
     * jdbcTemplate.queryForMap returns Map<String, Object> which should be processed
     */
    private void queryForMap() {
        Map<String, Object> userMap1 = jdbcTemplate.queryForMap(SELECT_FROM_USER_WHERE_ID_1);
        Map<String, Object> userMap2 = jdbcTemplate.queryForMap(SELECT_FROM_USER_WHERE_ID, new Object[]{1L}, new int[]{Types.BIGINT});
        Map<String, Object> userMap3 = jdbcTemplate.queryForMap(SELECT_FROM_USER_WHERE_ID, 1L);

        System.out.println("queryForMap userMap1:" + userMap1);
        System.out.println("queryForMap userMap2:" + userMap2);
        System.out.println("queryForMap userMap3:" + userMap3);
    }

    /**
     * jdbcTemplate.queryForRowSet returns SqlRowSet which should be processed
     */
    private void queryForRowSet() {
        SqlRowSet userSqlRowSet1 = jdbcTemplate.queryForRowSet(SELECT_FROM_USER);
        SqlRowSet userSqlRowSet2 = jdbcTemplate.queryForRowSet(SELECT_FROM_USER_WHERE_ID, new Object[]{1L}, new int[]{Types.BIGINT});
        SqlRowSet userSqlRowSet3 = jdbcTemplate.queryForRowSet(SELECT_FROM_USER_WHERE_ID, 1L);
        System.out.println("queryForRowSet userSqlRowSet1:");
        getUsersFromRowSet(userSqlRowSet1);
        System.out.println("queryForRowSet userSqlRowSet2:");
        getUsersFromRowSet(userSqlRowSet2);
        System.out.println("queryForRowSet userSqlRowSet3:");
        getUsersFromRowSet(userSqlRowSet3);
    }

    private void getUsersFromRowSet(SqlRowSet userSqlRowSet3) {
        while (userSqlRowSet3.next()) {
            User user = new User();
            user.setId(userSqlRowSet3.getLong("id"));
            user.setName(userSqlRowSet3.getString("name"));
            System.out.println(user);
        }
    }

    private void saveWithKey(User user) {
        Long id = 4L;
        int i = jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(INSERT_INTO_USER_ID_NAME_VALUES);
            statement.setLong(1, id);
            statement.setString(2, user.getName());
            return statement;
        });
        if (i == 1) {
            user.setId(id);
        }
        System.out.println("saveWithKey user:" + user);
    }

    private void saveWithKeyHolder(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int i = jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(INSERT_INTO_USER_NAME_VALUES, new String[]{"id"});
            statement.setString(1, user.getName());
            return statement;
        }, keyHolder);
        if (i == 1) {
            user.setId((Long) keyHolder.getKey());
        }
        System.out.println("saveWithKeyHolder user:" + user);
    }
}
