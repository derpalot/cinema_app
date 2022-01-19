package Cinema;

import java.util.ArrayList;

public class Manager extends Staff {
    // private String username
    // private String password

    public Manager(String username, String password) {
        super(username, password);
    }

    public ArrayList<Staff> addStaff(String username, String password, ArrayList<Staff> staffList) {
        for(Staff staff: staffList) {
            if (staff.getUsername().equals(username)) {
                return staffList;
            }
        }

        Staff newStaff = new Staff(username, password);
        staffList.add(newStaff);
        return staffList;
    }

    public ArrayList<Staff> removeStaff(String username, ArrayList<Staff> staffList) {
        int i = 0;
        for(Staff staff: staffList) {
            if (staff.getUsername().equals(username)) {
                staffList.remove(i);
                return staffList;
            }
            i++;
        }
        return null;
    }

    @Override
    public boolean canManageStaff() {
        return true;
    }
}
