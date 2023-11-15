package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateValidatorTest {
    CreateValidator createValidator;
    Bank bank;
    Checking checking;

    @BeforeEach
    void setUP() {
        bank = new Bank();
        createValidator = new CreateValidator(bank);
        checking = new Checking(9.8, "11111111");
        bank.addAccount(checking);
    }

    @Test
    void test_if_id_is_valid_with_non_numbers() {
        boolean actual = createValidator.validate("create checking 0000000L 0.1");
        assertFalse(actual);
    }

    @Test
    void test_if_id_is_valid_with_non_numbers_for_cd() {
        boolean actual = createValidator.validate("Create cd 2222222P 0.1 1000");
        assertFalse(actual);
    }

    @Test
    void test_invalid_account_type() {
        boolean actual = createValidator.validate("Create saves 12345678 0.1");
        assertFalse(actual);
    }

    @Test
    void test_invalid_account_type_cd() {
        boolean actual = createValidator.validate("Create CDE 12345678 0.1 1000");
        assertFalse(actual);
    }

    @Test
    void test_id_having_more_than_eight_digits() {
        boolean actual = createValidator.validate("Create checking 123456789 0.1");
        assertFalse(actual);
    }

    @Test
    void test_id_having_more_than_eight_digits_for_cd() {
        boolean actual = createValidator.validate("Create cd 123456789 0.1 1000");
        assertFalse(actual);
    }

    @Test
    void test_id_having_less_than_eight_digits() {
        boolean actual = createValidator.validate("Create checking 1234567 0.1");
        assertFalse(actual);
    }

    @Test
    void test_id_having_less_than_eight_digits_for_cd() {
        boolean actual = createValidator.validate("Create cd 1234567 0.1 1000");
        assertFalse(actual);
    }

    @Test
    void test_if_apr_can_be_negative() {
        boolean actual = createValidator.validate("Create checking 12345678 -0.1");
        assertFalse(actual);
    }

    @Test
    void test_if_apr_can_be_negative_for_cd() {
        boolean actual = createValidator.validate("Create cd 12345678 -0.1 1000");
        assertFalse(actual);
    }

    @Test
    void test_if_apr_can_go_over_ten() {
        boolean actual = createValidator.validate("Create checking 12345678 100");
        assertFalse(actual);
    }

    @Test
    void test_if_apr_can_go_over_ten_for_cd() {
        boolean actual = createValidator.validate("Create cd 12345678 100 1000");
        assertFalse(actual);
    }

    @Test
    void test_if_apr_can_be_a_non_float() {
        boolean actual = createValidator.validate("Create checking 12345678 abc");
        assertFalse(actual);
    }

    @Test
    void test_if_apr_can_be_a_non_float_for_cd() {
        boolean actual = createValidator.validate("Create checking 12345678 abc 1000");
        assertFalse(actual);
    }

    @Test
    void test_if_duplicate_id_is_possible() {
        boolean actual = createValidator.validate("Create checking 11111111 0.1");
        assertFalse(actual);
    }

    @Test
    void test_if_cd_can_be_created_without_balance() {
        boolean actual = createValidator.validate("Create cd 11111111 0.1");
        assertFalse(actual);
    }

    @Test
    void test_if_savings_can_be_created_with_a_balance() {
        boolean actual = createValidator.validate("Create saving 11111111 0.1 1000");
        assertFalse(actual);
    }

    @Test
    void test_if_only_a_single_account_can_be_created_at_once() {
        boolean actual = createValidator.validate("Create saving 11111111 0.1 checking 12345678 2");
        assertFalse(actual);
    }

    @Test
    void test_if_account_can_be_created_without_apr() {
        boolean actual = createValidator.validate("Create saving 11111111");
        assertFalse(actual);
    }

    @Test
    void test_if_account_can_be_created_without_apr_for_cd() {
        boolean actual = createValidator.validate("Create cd 11111111 1000");
        assertFalse(actual);
    }

    @Test
    void test_if_account_can_be_created_without_id() {
        boolean actual = createValidator.validate("Create saving 1.5");
        assertFalse(actual);
    }

    @Test
    void test_if_account_can_be_created_without_id_for_cd() {
        boolean actual = createValidator.validate("Create cd 1.5 1000");
        assertFalse(actual);
    }

    @Test
    void test_if_account_can_be_created_without_id_and_apr() {
        boolean actual = createValidator.validate("Create saving");
        assertFalse(actual);
    }

    @Test
    void test_if_account_can_be_created_without_id_and_apr_for_cd() {
        boolean actual = createValidator.validate("Create cd");
        assertFalse(actual);
    }

    @Test
    void test_if_cd_can_be_created_with_a_balance_under_one_thousand() {
        boolean actual = createValidator.validate("Create cd 12345678 1.2 999");
        assertFalse(actual);
    }

    @Test
    void test_if_cd_can_be_created_with_a_negative_balance() {
        boolean actual = createValidator.validate("Create cd 12345678 1.2 -1000");
        assertFalse(actual);
    }

    @Test
    void test_if_cd_can_be_created_with_a_balance_over_ten_thousand() {
        boolean actual = createValidator.validate("Create cd 12345678 1.2 10001");
        assertFalse(actual);
    }

    @Test
    void test_if_cd_can_be_created_without_account_type() {
        boolean actual = createValidator.validate("Create 12345678 1.2 5000");
        assertFalse(actual);
    }

    @Test
    void test_case_insensitivity_for_account() {
        boolean actual = createValidator.validate("Create CHECKING 12345678 1.2");
        assertTrue(actual);
    }

    @Test
    void test_case_insensitivity_for_account_for_cd() {
        boolean actual = createValidator.validate("Create CD 12345678 1.2 1000");
        assertTrue(actual);
    }
}
