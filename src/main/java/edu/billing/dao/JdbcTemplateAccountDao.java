package edu.billing.dao;

import edu.billing.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateAccountDao implements AccountDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long save(Account account) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", account.getId());
        parameters.put("amount", account.getAmount());

        try {
            new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("account")
                    .execute(new MapSqlParameterSource(parameters));
        } catch (DuplicateKeyException e) {
            return 0;
        }
        return 1;
    }

    @Override
    public Account load(String id) {
        List<Account> accounts = jdbcTemplate.query("SELECT * FROM account WHERE id = ?",
                new Object[]{id}, (resultSet, i) -> toAccount(resultSet));

        if (accounts.size() == 1) {
            return accounts.get(0);
        }
        return null;
    }

    @Override
    public void delete(String id) {
        jdbcTemplate.update("DELETE FROM account WHERE id = ?", id);
    }

    @Override
    public void update(Account account) {
    }

    @Override
    public void updateAmount(String id, long amount) {
        jdbcTemplate.update("UPDATE account SET amount = ? WHERE id = ?"
                , amount, id);
    }

    @Override
    public List<Account> loadAll() {
        return jdbcTemplate.query("SELECT * FROM account", (resultSet, i) -> toAccount(resultSet));
    }

    private Account toAccount(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getString("id"));
        account.setAmount(resultSet.getLong("amount"));
        return account;
    }

    @Override
    public Account findAccountByNumber(String account) {
        List<Account> accounts = jdbcTemplate.query("SELECT * FROM account WHERE id = ?",
                new Object[]{account}, (resultSet, i) -> toAccount(resultSet));

        if (accounts.size() == 1) {
            return accounts.get(0);
        }

        return null;
    }

    @Override
    public long getAccountCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM account",
                Long.class);
    }

    public boolean exists(Account account) {
        List<Account> accounts = jdbcTemplate.query("SELECT * FROM account WHERE " +
                        "id = ?",
                new Object[]{account.getId()}, (resultSet, i) -> toAccount(resultSet));

        return (accounts.size() > 0);
    }

}