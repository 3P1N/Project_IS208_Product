package appQLKho.BUS;

import appQLKho.DAO.AccountDAO;
import appQLKho.DAO.AccountRoleDAO;
import appQLKho.DTO.AccountDTO;
import appQLKho.MAIN.PasswordHashing;

public class AccountBUS {

    private final AccountDAO accountDAO;
    private final AccountRoleDAO roleDAO;
    
    
    public AccountBUS() {
        accountDAO = new AccountDAO();
        roleDAO = new AccountRoleDAO();
    }

    public AccountDTO login(String username, String password) {
       
        String hashedPassword = PasswordHashing.hashPassword(password);
        
        return accountDAO.getByUsernameAndPassword(username, hashedPassword);
    }
    public String getRoleByAccountId(int accountId) {
        return roleDAO.getRoleByAccountId(accountId);
    }
}

