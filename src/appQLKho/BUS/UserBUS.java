package appQLKho.BUS;

import appQLKho.DTO.AccountDTO;
import appQLKho.DTO.UserDTO;
import appQLKho.DTO.AccountRoleDTO;
import appQLKho.DAO.UserDAO;
import appQLKho.DAO.DBConnection;
import appQLKho.DAO.AccountRoleDAO;
import appQLKho.DAO.AccountDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class UserBUS {

    private static UserBUS instance;

    public static UserBUS getInstance() {
        if (instance == null) {
            instance = new UserBUS();
        }
        return instance;
    }
}
