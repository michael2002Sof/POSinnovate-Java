package posinnovate;

import posinnovate.core.SystemPOS;
import posinnovate.ui.Access;

public class Main {
    public static void main(String[] args) {
        SystemPOS system = new SystemPOS();
        Access.login(system);
    }
}
