package me.dave.domain.users;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UsersRepositoryTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UsersRepository usersRepository;

    @Test
    public void usersRepository테스트() throws SQLException {

        try(Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println(metaData.getURL());
            System.out.println(metaData.getDriverName());
            System.out.println(metaData.getUserName());

            Users users = new Users();
            users.setUsername("dave");
            users.setPassword("pass");

            Users insertedUsers = usersRepository.save(users);

            assertThat(insertedUsers).isNotNull();

            Optional<Users> selectedUsers = usersRepository.findByUsername(insertedUsers.getUsername());
            assertThat(selectedUsers).isNotEmpty();
            //assertThat(selectedUsers).isNotNull();

            Optional<Users> notInsertedUsers = usersRepository.findByUsername("Mike");
            assertThat(notInsertedUsers).isEmpty();
            //assertThat(notInsertedUsers).isNull();

        }
    }
}
